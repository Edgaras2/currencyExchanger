package currency.exchanger.services;

import currency.exchanger.dto.ClientRequestDto;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CurrencyExchangeServiceTest {

    private static final int EXPECTED_LENGTH = 18;
    private CurrencyExchangeService currencyExchangeService;

    @BeforeMethod
    void before() {
        currencyExchangeService = new CurrencyExchangeService();
    }

    @Test(dataProvider = "testClientRequestData")
    public void calculateExchangedCurrencyAmount_AmountIsExchanged_IfRequestIsValid(BigDecimal amount, String currentCurrency, String targetCurrency) {
        // Given
        ClientRequestDto clientRequestDto = createClientRequestDto(amount, currentCurrency, targetCurrency);

        // When
        BigDecimal result = currencyExchangeService.calculateExchangedCurrencyAmount(clientRequestDto);

        // Then
        assertEquals(result.scale(), EXPECTED_LENGTH, "Incorrect amount's length");
        assertNotNull(result, "Expected value after exchange");
    }


    @Test(expectedExceptions = ResponseStatusException.class)
    public void calculateExchangedCurrencyAmount_InvalidRequest_ExceptionThrown() {
        // Given
        ClientRequestDto clientRequestDto = createClientRequestDto(BigDecimal.ONE, "currentCurrency", "USD");

        // When
        currencyExchangeService.calculateExchangedCurrencyAmount(clientRequestDto);
    }

    @DataProvider
    public Object[][] testClientRequestData() {
        return new Object[][]{new Object[]{BigDecimal.valueOf(5.3123123123423), "EUR", "USD"},
                new Object[]{BigDecimal.valueOf(5.3123123123423), "BTC", "GBP"},
                new Object[]{BigDecimal.valueOf(5.3123123123423), "btc", "eur"},
                new Object[]{BigDecimal.valueOf(1), "eth", "USD"},
                new Object[]{BigDecimal.valueOf(5.3123123123423), "usd", "FKE"},
        };
    }

    private ClientRequestDto createClientRequestDto(BigDecimal amount, String currentCurrency, String targetCurrency) {
        return new ClientRequestDto(amount, currentCurrency, targetCurrency);
    }
}