package currency.exchanger.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for retrieving currency pairs from csv file
 */
public class CurrencyExchangeUtils {

    private static final String EXCHANGE_RATES_CSV = "exchangeRates.csv";

    private CurrencyExchangeUtils() {
    }

    public static Map<String, BigDecimal> getCurrentExchangeRate() {
        Path exchangeRatesFilePath = getExchangeRatesFilePath();

        try (Stream<String> csvLines = Files.lines(exchangeRatesFilePath)) {
            return csvLines.map(line -> line.split(","))
                    .collect(Collectors.toMap(line -> String.valueOf(line[0]), // the key
                            line -> BigDecimal.valueOf(Double.parseDouble(line[1])), // the value
                            (first, second) -> first, // deduplicate currencies
                            () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
                    ));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not parse csv file", e);
        }
    }

    private static Path getExchangeRatesFilePath() {
        try {
            URL resource = CurrencyExchangeUtils.class.getClassLoader().getResource(EXCHANGE_RATES_CSV);
            return Paths.get(resource.toURI());
        } catch (URISyntaxException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ExchangeRates file was not found", e);
        }
    }
}
