package controllers;

import framework.Controller;

public class Calc extends Controller {
  public Calculator calculator;

  @Override
  public void get() {
    calculator = (Calculator) session.getAttribute("calculator");
    if (calculator == null) {
      calculator = new Calculator();
      session.setAttribute("calculator", calculator);
    }
    calculator.value ++;
  }

  private static class Calculator {
    public double value;
  }
}
