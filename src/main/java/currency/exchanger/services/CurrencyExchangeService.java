package currency.exchanger.services;

import currency.exchanger.dto.ClientRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CurrencyExchangeService is responsible for retrieving exchangeRates from local exchangeRates.csv file and
 * calculating exchangeAmount between different pairs
 */
@Component
public class CurrencyExchangeService {

    private static final String EXCHANGE_RATES_CSV = "exchangeRates.csv";

    public String getExchangedCurrencyAmount(ClientRequestDto clientRequestDTO) {
        double exchangedCurrencyAmount = clientRequestDTO.getAmount().doubleValue() * calculateExchangeRate(clientRequestDTO);
        return String.format("%.18f", exchangedCurrencyAmount);
    }



    private double calculateExchangeRate(ClientRequestDto clientRequestDTO) {
        Map<String, Double> currentExchangeRate = getCurrentExchangeRate();
        Double currentCurrencyExchangeRate = currentExchangeRate.get(clientRequestDTO.getCurrentCurrency());
        Double targetCurrencyExchangeRate = currentExchangeRate.get(clientRequestDTO.getTargetCurrency());
        if (currentCurrencyExchangeRate == null || targetCurrencyExchangeRate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Right now it is not possible to exchange " +
                    clientRequestDTO.getCurrentCurrency() + "/" +
                    clientRequestDTO.getTargetCurrency() + " pair");
        }

        return 1 / targetCurrencyExchangeRate * currentCurrencyExchangeRate;
    }

    public Map<String, Double> getCurrentExchangeRate() {
        Path exchangeRatesFilePath = getExchangeRatesFilePath();

        try (Stream<String> csvLines = Files.lines(exchangeRatesFilePath)) {
            return csvLines.map(line -> line.split(","))
                    .collect(Collectors.toMap(line -> String.valueOf(line[0]), line -> Double.parseDouble(line[1])));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not parse csv file", e);
        }
    }

    private Path getExchangeRatesFilePath() {
        try {
            URL resource = getClass().getClassLoader().getResource(EXCHANGE_RATES_CSV);
            return Paths.get(resource.toURI());
        } catch (URISyntaxException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ExchangeRates file was not found", e);
        }
    }

}
