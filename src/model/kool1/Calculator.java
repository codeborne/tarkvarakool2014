package model.kool1;

public class Calculator {



    private double result;

    public double getResult() {
        return result;
    }

    public void add(double operand) {
        result = result + operand;
    }

    public void subtract(double operand) {
        result -=  operand;
    }

    public void multiply(double operand) {
        result *= operand;
    }

    public void divide(double operand)
    {
        if (operand==0) {
            throw new IllegalArgumentException();
        }
        else {
            result = result / operand;
        }
    }

    public void raiseToThePower(double power) {
        result = Math.pow(result, power);
    }
}