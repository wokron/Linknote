package linknote.user;

import linknote.exception.LinknoteException;

import java.util.TreeMap;
import java.util.regex.Pattern;

public class UserController
{
    private final TreeMap<String, User> allUsers = new TreeMap<>();
    private User nowUser = null;

    public void register(String userName, String password) throws LinknoteException
    {
        if (allUsers.containsKey(userName))
            throw new LinknoteException("user already exist");
        if (!isLegalUserName(userName))
            throw new LinknoteException("illegal user name");
        if (!isLegalPassword(password))
            throw new LinknoteException("illegal password");
        var newUser = new User(userName, password);
        allUsers.put(userName, newUser);
    }

    public void login(String userName, String password) throws LinknoteException
    {
        if (!allUsers.containsKey(userName))
            throw new LinknoteException("user not found");
        var user = allUsers.get(userName);
        if (user.matchPassword(password))
            nowUser = user;
        else
            throw new LinknoteException("password not right");
    }

    public void logout()
    {
        nowUser = null;
    }

    public User getCurrentUser() throws LinknoteException
    {
        if (nowUser == null)
            throw new LinknoteException("not login");
        return nowUser;
    }


    private static boolean isLegalUserName(String name)
    {
        return Pattern.matches("^([A-Z])([a-z]{0,19})$",name);
    }


    private static boolean isLegalPassword(String password)
    {
        return Pattern.matches("^([a-zA-Z_0-9]{8,15})$",password);
    }
}
