package model.kool3;

public class Calculator {
    private Double currentValue = 0d;

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public void add(Double operand) {
        setCurrentValue(getCurrentValue() + operand);
    }

    public void subtract(Double operand) {
        setCurrentValue(getCurrentValue() - operand);
    }

    public void multiply(Double operand) {
        setCurrentValue(getCurrentValue() * operand);
    }

    public void divide(Double operand) throws ArithmeticException {
        if (operand==0.0)
            throw new ArithmeticException("Cannot divide by zero");

        setCurrentValue(getCurrentValue() / operand);
    }

    public String toString() {
        return "Current value: " + getCurrentValue();
    }
}
