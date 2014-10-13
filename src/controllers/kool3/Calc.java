package controllers.kool3;

import framework.Controller;
import model.kool3.Calculator;

public class Calc extends Controller {

  public String operator;
  public Double operand;
  public boolean divisorIsZero = false;
  public String warning;


  public Calculator getCalculator() {
    Calculator calculator = (Calculator) session.getAttribute("tere");
    if (calculator == null) {
      calculator = new Calculator();
      session.setAttribute("tere", calculator);
    }
    return calculator;
  }


  public void post() {
    Calculator calculator = getCalculator();
    if (operator==null&&operand==null){
      warning = "Please choose an operator and enter a number";
    }
    else if (operand == null) {
      warning = "Please enter a number!";
    }
    else if (operator == null) {
      warning = "Please choose an operator!";
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
        case "/":
          try {
            calculator.divide(operand);
          }
          catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero, please try again, current value is: " + calculator.getCurrentValue());
            divisorIsZero = true;
          }
          break;
        // other values are filtered out by askOperator()
      }
    }
  }


}
