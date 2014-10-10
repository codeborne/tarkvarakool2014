package controllers.kool2;

import java.util.HashMap;
import java.util.Map;

interface LoginInfo {
    public static final Map<String, String > ACCOUNTS = new HashMap<String, String>(){{
        put("user","pass");
        put("Dmitri","pass");
        put("Monika","password");
    }};
}
