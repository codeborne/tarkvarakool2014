package model.kool2;

public class Calculator {

    public double result;

    public double getResult() {
        return result;
    }

    public void add(double value) {
        result = result + value;
    }

    public void subtract(double value) {
        result = result - value;
    }

    public void multiply(double value) {
        result = result * value;
    }

    public void divide(double value) {

        if (value == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        else {
            result = result / value;
        }
    }

    @Override
    public String toString() {
        return "I am a calculator, and my current value is " + result;
    }

    public void riseToThePower(double power) {
        result = Math.pow(result, power);
    }
}





