package controllers.kool1;

import framework.Controller;
import model.kool1.Calculator;

public class Calc extends Controller {
  public double operand;
  public String operator;
  public String note;

  public Calculator getCalculator() {
    Calculator calculator = (Calculator)session.getAttribute("calc");
    if (calculator == null) {
      calculator = new Calculator();
      session.setAttribute("calc", calculator);
    }
    return calculator;
  }

  public void post(){
    Calculator calculator = getCalculator();



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
      case "^":
        calculator.raiseToThePower(operand);
        break;
      case "/":
        try {
          calculator.divide(operand);
        }
        catch (IllegalArgumentException e){
          note="Can't divide by zero. This is previous value.";
        }
        break;
      default:
        throw new IllegalArgumentException(operator);
    }
  }

}


