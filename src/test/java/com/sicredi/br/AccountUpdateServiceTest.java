package com.sicredi.br;

import com.sicredi.br.exceptions.InvalidFormatException;
import com.sicredi.br.service.AccountUpdateService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountUpdateServiceTest {

    @Test
    public void should_throw_exception_when_file_format_is_wrong() {

        var accountUpdateService = new AccountUpdateService();

        String csvPath = "/test.xml";

        assertThrows(InvalidFormatException.class, () -> accountUpdateService.accountUpdate(csvPath));

    }

    @Test
    public void should_return_correct_csv_header() {

        var accountUpdateService = new AccountUpdateService();

        String[] header = {"agencia", "conta", "saldo", "status"};
        String[] line1 = {"0101", "122256", "100.0", "A"};

        List<String[]> csvData = new ArrayList<>();

        csvData.add(header);
        csvData.add(line1);
        var csvHeader = accountUpdateService.getCsvHeader(csvData);

        assertEquals(csvHeader.length, 5);
        assertEquals(csvHeader[csvHeader.length - 1], "result");

    }

    @Test
    public void should_return_not_null_csv_writer() throws IOException {

        var accountUpdateService = new AccountUpdateService();

        String csvPath = "src/main/resources/csvfiles/test.csv";

        var csvWriter = accountUpdateService.createOutputFile(csvPath);

        assertNotNull(csvWriter);
    }

    @Test
    public void should_create_successful_file() {

        var accountUpdateService = new AccountUpdateService();

        String csvPath = "src/main/resources/csvfiles/test.csv";

        accountUpdateService.accountUpdate(csvPath);

        var outputFile = new File("src/main/resources/csvfiles/test_result.csv");

        assertTrue(outputFile.exists());

        //Clean
        if (outputFile.exists())
            outputFile.delete();

    }

}