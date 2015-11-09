package inso.revex.androidannotations.util;


import inso.revex.androidannotations.rest.model.AuthToken;
import inso.revex.androidannotations.rest.model.User;

/**
 * Created by Elisabeth on 14.05.2015.
 */
public class UtilitiesManager {
    private static UtilitiesManager instance;

    private AuthToken authToken;
    private User user;

    private UtilitiesManager() {
    }
    public static synchronized UtilitiesManager getInstance () {
        if (UtilitiesManager.instance == null) {
            UtilitiesManager.instance = new UtilitiesManager();
        }
        return UtilitiesManager.instance;
    }

    public  AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
