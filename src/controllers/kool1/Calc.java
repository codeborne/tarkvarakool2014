package controllers.kool1;

import framework.Controller;
import model.kool1.Calculator;

public class Calc extends Controller {
  public Double operand;
  public String operator;
  public String note;
  public String message;


  public Calculator getCalculator() {
    Calculator calculator = (Calculator) session.getAttribute("calc");
    if (calculator == null) {
      calculator = new Calculator();
      session.setAttribute("calc", calculator);
    }
    return calculator;
  }

  public void post() {
    Calculator calculator = getCalculator();

    if (operand == null && operator == null) {
      message = "Vali tehe ja sisesta number";
    }
    else if (operator == null) {
      message = "Vali tehe!";
    }
    else if (operand == null) {
      message = "Sisesta number!";
    }

    else {


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
          catch (IllegalArgumentException e) {
            note = "Nulliga jagada ei saa. See on eelmine tulemus.";
          }
          break;
        default:
          throw new IllegalArgumentException(operator);


      }
    }

  }
}


