package currency.exchanger.services;

import currency.exchanger.dto.ClientRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static currency.exchanger.utils.CurrencyExchangeUtils.getCurrentExchangeRate;

/**
 * CurrencyExchangeService is responsible calculating exchangeAmount between different pairs
 */
@Component
public class CurrencyExchangeService {

    public BigDecimal calculateExchangedCurrencyAmount(ClientRequestDto clientRequestDTO) {
        BigDecimal multiply = clientRequestDTO.getAmount().multiply(calculateExchangeRate(clientRequestDTO));
        return multiply.setScale(18, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateExchangeRate(ClientRequestDto clientRequestDTO) {
        Map<String, BigDecimal> currentExchangeRate = getCurrentExchangeRate();
        BigDecimal currentCurrencyExchangeRate = currentExchangeRate.get(clientRequestDTO.getCurrentCurrency());
        BigDecimal targetCurrencyExchangeRate = currentExchangeRate.get(clientRequestDTO.getTargetCurrency());

        if (currentCurrencyExchangeRate == null || targetCurrencyExchangeRate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It is not possible to exchange " +
                    clientRequestDTO.getCurrentCurrency() + "/" +
                    clientRequestDTO.getTargetCurrency() + " pair");
        }

        return BigDecimal.ONE.divide(targetCurrencyExchangeRate, 18, RoundingMode.HALF_UP)
                .multiply(currentCurrencyExchangeRate);
    }
}
