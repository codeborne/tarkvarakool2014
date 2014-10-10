package controllers.kool2;

import framework.Controller;
import model.kool2.*;

public class Calc extends Controller {
    Calculator calculator;

    public double currentValue;
    public double operand;
    public String operator;
    public String error;
    public boolean submit;
    public boolean reset;

    @Override
    public void get() {
        calculator = (Calculator) session.getAttribute("calc");
        if (calculator != null)
            currentValue = calculator.getResult();
    }

    @Override
    public void post() {
        calculator = (Calculator) session.getAttribute("calc");
        if (calculator == null) {
            calculator = new Calculator();
            session.setAttribute("calc", calculator);
        }

        if (reset) {
            calculator.result = 0;
        } else {
            Throwable operandError = errors.get("operand");
            if (operandError != null) {
                error = "Please insert a valid number";
            } else {
                try {
                    switch (operator) {
                        case "+":
                            calculator.add(operand);
                            break;
                        case "-":
                            calculator.subtract(operand);
                            break;
                        case "*":
                            calculator.multiply(operand);
                            break;
                        case "/":
                            calculator.divide(operand);
                            break;
                        case "^":
                            calculator.riseToThePower(operand);
                            break;
                        default:
                            throw new Exception("Unsupported operator");
                    }
                } catch (Exception e) {
                    error = e.getMessage();
                }
            }
        }
        currentValue = calculator.getResult();
    }
}
