package currency.exchanger.controllers;

import currency.exchanger.dto.ClientRequestDto;
import currency.exchanger.services.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @PostMapping("/currencyExchange")
    public ResponseEntity<StringBuilder> getExchangedCurrency(@Valid @RequestBody ClientRequestDto clientRequestDTO) {
        BigDecimal exchangedCurrencyAmount = currencyExchangeService.calculateExchangedCurrencyAmount(clientRequestDTO);
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
