package currency.exchanger.controllers;

import currency.exchanger.services.CurrencyExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.*;

public class CurrencyExchangeControllerTest {

    private CurrencyExchangeController currencyExchangeController;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    public void before() {
        currencyExchangeController = new CurrencyExchangeController();
    }

    @Test
    public void ada() {

    }


    @DataProvider
    public Object[][] test() {
        return new Object[][] {
                new Object[] {"G", "asd"
        }};
    }
}