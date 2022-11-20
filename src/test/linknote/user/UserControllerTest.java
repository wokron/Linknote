package test.linknote.user;

import linknote.exception.LinknoteException;
import linknote.user.UserController;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class UserControllerTest
{
    @Test
    public void test() throws LinknoteException
    {
        UserController controller = new UserController();
        controller.register("Ann", "12345678");
        controller.login("Ann", "12345678");
        controller.logout();
        try
        {
            controller.getCurrentUser();
        }
        catch (LinknoteException e)
        {
            Assert.assertEquals("not login", e.getMessage());
        }
//        controller.login("Ann", "12345678");
    }
}
