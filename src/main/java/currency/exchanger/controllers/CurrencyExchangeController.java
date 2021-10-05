package currency.exchanger.controllers;

import currency.exchanger.dto.ClientRequestDto;
import currency.exchanger.services.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @PostMapping("/currencyExchange")
    private ResponseEntity<StringBuilder> getExchangedCurrency(@Valid @RequestBody ClientRequestDto clientRequestDTO) {
        String exchangedCurrencyAmount = currencyExchangeService.getExchangedCurrencyAmount(clientRequestDTO);
        StringBuilder message = new StringBuilder()
                .append("Exchanged ")
                .append(clientRequestDTO.getAmount())
                .append(" ")
                .append(clientRequestDTO.getCurrentCurrency())
                .append(" to ")
                .append(exchangedCurrencyAmount)
                .append(" ")
                .append(clientRequestDTO.getTargetCurrency());
        return ResponseEntity.ok(message);
    }

}
