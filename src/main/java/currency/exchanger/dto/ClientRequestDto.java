package currency.exchanger.dto;

import javax.validation.constraints.NotEmpty;

public class ClientRequestDto {

    private Double amount;
    @NotEmpty(message = "Current currency can not be empty")
    private String currentCurrency;
    @NotEmpty(message = "Target currency can not be empty")
    private String targetCurrency;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
