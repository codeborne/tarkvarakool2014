package controllers;

import framework.Controller;
import framework.Result;

public class MockController extends Controller {
  public Result action() throws Exception {
    return render();
  }
  public Result secondAction() { return render(); }
}
