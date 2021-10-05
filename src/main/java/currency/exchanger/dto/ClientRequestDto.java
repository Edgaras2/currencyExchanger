package currency.exchanger.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class ClientRequestDto {

    public ClientRequestDto(BigDecimal amount, String currentCurrency, String targetCurrency) {
        this.amount = amount;
        this.currentCurrency = currentCurrency;
        this.targetCurrency = targetCurrency;
    }

    @DecimalMin(value = "0.0", inclusive = false, message = "Negative amount is not accepted")
    private BigDecimal amount;
    @NotEmpty(message = "Current currency can not be empty")
    private String currentCurrency;
    @NotEmpty(message = "Target currency can not be empty")
    private String targetCurrency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrentCurrency() {
        return currentCurrency;
    }

    public void setCurrentCurrency(String currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
