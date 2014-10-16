package framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Result {

  abstract void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
