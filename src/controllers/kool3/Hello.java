package controllers.kool3;

import framework.Controller;

public class Hello extends Controller {

    public String getName (){
        return request.getParameter("name");

    }

    public String getEmail () {
        return request.getParameter("email");
    }
//    public void post (){
//        System.out.println(request.getParameter("name"));





    }



