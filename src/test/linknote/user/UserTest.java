package test.linknote.user;

import linknote.exception.LinknoteException;
import linknote.user.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UserTest
{
    @Test
    public void test() throws IOException, LinknoteException
    {
        User user = new User("yyt2", "124");
        user.newNote("note6", "c1.c3/t1.sub1");
        user.newNote("note7", "c1.c3/t1.sub2");
        user.newNote("note8", "c1.c3/t2.sub1");
        user.newNote("note1", "c1.c2/t1.sub1");
        user.newNote("note2", "c1.c2/t1.sub1");
        user.newNote("note3", "c1.c2/t1.sub2");
        user.newNote("note4", "c1.c2/t2.sub1");
        user.newNote("note5", "c1.c2/t2.sub2");

        Assert.assertEquals("", user.showNowCategory());
        List<String> cates = user.showCategories();
        Assert.assertEquals(1, cates.size());
        Assert.assertEquals("c1", cates.get(0));

        user.gotoCategory("c1");
        Assert.assertEquals("c1", user.showNowCategory());
        cates = user.showCategories();
        Assert.assertEquals(2, cates.size());
        Assert.assertEquals("c2", cates.get(0));
        Assert.assertEquals("c3", cates.get(1));

        user.openNoteSet("c2");
        user.openNote();
        Assert.assertEquals("", user.showContent());
        user.contentAppend("123");
        Assert.assertEquals("1 123\n", user.showContent());
        user.closeNote();
        var links = user.showLinks();
        Assert.assertEquals(1, links.size());
        Assert.assertEquals("next", links.get(0));
        user.jumpToLinkedNote("next");
        user.openNote();
        Assert.assertEquals("", user.showContent());
        user.contentAppend("456");
        Assert.assertEquals("1 456\n", user.showContent());
        user.closeNote();
        user.goBackToPreNote();
        user.openNote();
        Assert.assertEquals("1 123\n", user.showContent());
        user.closeNote();

        user.makeLink("test link", "c1.c3/t1.sub2", "note7");
        links = user.showLinks();
        Assert.assertEquals(2, links.size());
        Assert.assertEquals("next", links.get(0));
        Assert.assertEquals("test link", links.get(1));
        user.jumpToLinkedNote("test link");
        user.openNote();
        user.contentAppend("link!!!");
        user.closeNote();
        user.goBackToPreNote();
        user.jumpToLinkedNote("test link");
        user.openNote();
        Assert.assertEquals("1 link!!!\n", user.showContent());
        user.closeNote();
        user.closeNoteSet();
    }
}
