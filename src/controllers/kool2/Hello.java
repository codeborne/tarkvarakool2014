package controllers.kool2;

import controllers.Controller;

import javax.lang.model.element.Name;

public class Hello extends Controller {
    public void get() {

    }

    public String getName() {
        String name = request.getParameter("name");
        return (name == null || name.equals("")) ? "unknown" : name;
    }

    public String getGender() {
        String name = request.getParameter("sex");
        return name == null ? "unknown" : name;
    }

    public String getLikeJava() {
        if (request.getParameter("java") == null)
            return "I Hate Java";
        else
            return "I Like Java";
    }

    public String getSubmit() {
        return request.getParameter("submit") == null ? "false" : "true";
    }

    public String getColors() {
        String colors = request.getParameter("colors");
        return colors == null ? "unknown" : colors;
    }



}