package nl.vandoren.app.uraandroid.Model;

/**
 * Created by Alex on 5/22/2015.
 */
public class User {
    public String login;
    public String password;
    public long lastTimeConnected = 0;
    public String fullName;
    public int uraAccount_Dbid;
    public String ura_syntus;
    public String capa_Dbid;

    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
