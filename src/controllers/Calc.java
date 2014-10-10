package controllers;

import framework.Controller;

public class Calc extends Controller {
  public Calculator calculator = fromSession(Calculator.class);

  @Override
  public void get() {
    calculator.value ++;
  }

  public static class Calculator {
    public double value;
  }
}
