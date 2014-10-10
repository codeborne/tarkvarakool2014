package controllers.kool2;
import framework.Controller;

public class Profile extends Controller implements LoginInfo {
    public boolean accessAllowed = false;
    public String username;
    public String password;
    public boolean send;
    public String sex;
    public boolean java;
    public String colors;

    public String getLikeJava() {
        if (java)
            return "You Love Java";
        else
            return "You Hate Java";
    }

    @Override
    public void post() {
        if (USERNAME.equals(username) && PASSWORD.equals(password))
            accessAllowed = true;
    }
}
