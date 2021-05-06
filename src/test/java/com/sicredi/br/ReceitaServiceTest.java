package com.sicredi.br;

import com.sicredi.br.service.ReceitaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReceitaServiceTest {


    @Test
    public void should_return_false_when_agency_is_wrong() throws InterruptedException {

        var receitaService = new ReceitaService();

        var agencia = "12345";
        var conta = "122256";
        var saldo = 10.0;
        var status = "A";

        var result = receitaService.atualizarConta(agencia, conta, saldo, status);

        assertFalse(result);

    }

    @Test
    public void should_return_false_when_account_is_wrong() throws InterruptedException {

        var receitaService = new ReceitaService();

        var agencia = "1234";
        var conta = "12225666";
        var saldo = 10.0;
        var status = "A";

        var result = receitaService.atualizarConta(agencia, conta, saldo, status);

        assertFalse(result);

    }

    @Test
    public void should_return_false_when_status_is_wrong() throws InterruptedException {

        var receitaService = new ReceitaService();

        var agencia = "1234";
        var conta = "122256";
        var saldo = 10.0;
        var status = "Z";

        var result = receitaService.atualizarConta(agencia, conta, saldo, status);

        assertFalse(result);

    }

}