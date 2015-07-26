package nl.vandoren.vandorencrm.Model;

/**
 * Created by Aleksei on 6/7/2015.
 */
public class User {

    public long lastTimeConnected = 0;
    public String login;
    public String password;

    public static User ourInstance = new User();

    public static User getInstance(){return ourInstance;}


}