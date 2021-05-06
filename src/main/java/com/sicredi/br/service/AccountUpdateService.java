package com.sicredi.br.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.sicredi.br.exceptions.AccountUpdateException;
import com.sicredi.br.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AccountUpdateService {

    public void accountUpdate(String csvPath) {

        if (!csvPath.contains(".csv"))
            throw new InvalidFormatException("Formato de Arquivo inválido");

        try {

            var csvReader = new CSVReader(new FileReader(csvPath));

            // Passa o arquivo CSV inserido para um Array de Strings, possibilitando a manipulação.
            List<String[]> csvData = csvReader.readAll();

            String[] header = getCsvHeader(csvData);

            CSVWriter writer = createOutputFile(csvPath);

            //Escrevendo o cabeçalho no arquivo de saída com as colunas.
            writer.writeNext(header);

            updateAccounts(csvData, writer);

            //Fecha a escrita do arquivo de saída
            writer.close();

        } catch (IOException | CsvException | AccountUpdateException e) {
            e.printStackTrace();
        }

    }

    private void updateAccounts(List<String[]> csvData, CSVWriter writer) throws AccountUpdateException {
        var receitaService = new ReceitaService();

        //Itera os dados do csv mandando um a um para o serviço da receita.
        csvData.forEach(line ->
        {

                // Aqui os dados do csv em variáveis.
                // A agencia está localizada na primeira posição do array.
                var agencia = line[0];

                // Ao atribuir a conta, o caracter "-" deve ser retirado para se encaixar no padrão esperado do serviço.
                var conta = line[1].replace("-", "");

                // O valor do saldo tem que ser passado para double que é o esperado pelo serviço.
                var saldo = Double.parseDouble(line[2].replace(",", "."));

                // O status está na posição 3 do array
                var status = line[3];

                try {
                    // chama o serviço de atualizar a conta e recupera o resultado se foi atualizado ou não (true or false).
                    var accountUpdated = receitaService.atualizarConta(agencia, conta, saldo, status);

                    if (!accountUpdated)
                        throw new AccountUpdateException();

                    // A linha com os dados da conta e o resultado retornado pelo serviço é montada
                    String[] result = {agencia, conta, String.valueOf(saldo), status, String.valueOf(accountUpdated)};
                    // Por fim a linha é escrita no arquivo
                    writer.writeNext(result);
                } catch (RuntimeException | InterruptedException e) {
                    System.err.println("Ocorreu um erro no serviço de atualização das contas, tente novamente mais tarde");
                } catch (AccountUpdateException e) {
                    System.err.println("Ocorreu um erro de validação dos dados da linha: " + Arrays.toString(line) + ", verifique e tente novamente");
                }

        });
    }

    public CSVWriter createOutputFile(String csvPath) throws IOException {
        // Criando o arquivo de saída.
        var outputFile = new File(csvPath.replace(".csv", "_result.csv"));
        var outputFileWriter = new FileWriter(outputFile);
        var writer = new CSVWriter(outputFileWriter);
        return writer;
    }

    public String[] getCsvHeader(List<String[]> csvData) {
        // Salva a primeira linha do arquivo com os nomes das colunas.
        String[] columns = csvData.get(0);

        // Remove a linha do arquivo que contém as colunas.
        csvData.remove(0);

        // Adiciona a coluna de resultado do processo de envio no cabeçalho que vai ser escrito no arquivo de saída.
        String[] header = {columns[0], columns[1], columns[2], columns[3], "result"};
        return header;
    }
}

