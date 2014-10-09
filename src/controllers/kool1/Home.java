package controllers.kool1;

import controllers.Controller;

public class Home extends Controller {
    public void get(){

    }

    //String name="kkk";
    public String getName(){
        return request.getParameter("name");
    }
}
