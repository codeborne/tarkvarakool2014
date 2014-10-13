package controllers.kool2;

import framework.Controller;
import framework.Redirect;


public class Reset extends Controller {

    @Override
    public void get() {
        session.removeAttribute("calc");
        throw new Redirect("calc");
    }

}
