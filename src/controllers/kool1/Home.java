package controllers.kool1;

import framework.Controller;

public class Home extends Controller {
    public void get(){

    }

    //String name="kkk";
    public String getName(){
        return request.getParameter("name");
    }
    public String getSurname(){
        return request.getParameter("surname");
    }
}
