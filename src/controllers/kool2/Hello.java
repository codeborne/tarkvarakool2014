package controllers.kool2;

import framework.Controller;
import framework.Redirect;

public class Hello extends Controller implements LoginInfo {
    public boolean accessAllowed = false;
    public String username;
    public String password;
    public boolean login;

    @Override
    public void post() {
        if (password.equals(ACCOUNTS.get(username)))
            accessAllowed = true;
    }
}
