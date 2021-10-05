package currency.exchanger.controllers;

import currency.exchanger.dto.ClientRequestDto;
import currency.exchanger.services.CurrencyExchangeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.testng.AssertJUnit.assertEquals;

public class CurrencyExchangeControllerTest {

    @InjectMocks
    private CurrencyExchangeController currencyExchangeController;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @BeforeMethod
    public void before() {
        currencyExchangeController = new CurrencyExchangeController();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getExchangedCurrency_ResponseStatusOk_IfRequestIsValid() {
        // Given
        ClientRequestDto clientRequestDto = new ClientRequestDto(BigDecimal.ONE, "USD", "EUR");

        // When
        ResponseEntity<StringBuilder> exchangedCurrency = currencyExchangeController.getExchangedCurrency(clientRequestDto);

        // Then
        verify(currencyExchangeService).calculateExchangedCurrencyAmount(clientRequestDto);
        assertEquals("Received unexpected HttpStatus", exchangedCurrency.getStatusCode(), HttpStatus.OK);
    }
}