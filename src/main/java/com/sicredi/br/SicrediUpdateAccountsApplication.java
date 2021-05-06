package com.sicredi.br;

import com.sicredi.br.service.AccountUpdateService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SicrediUpdateAccountsApplication {

    public static void main(String[] args) {
        try {
            var accountUpdateService = new AccountUpdateService();

            String csvPath = args[0];

            accountUpdateService.accountUpdate(csvPath);

        } catch (IndexOutOfBoundsException exception) {
            System.err.println("Adicione o arquivo csv como argumento da execução do programa. Ex: java -jar SincronizacaoReceita contas.csv");
        }
    }
}