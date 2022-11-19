package test.linknote.note.content;

import linknote.note.content.ContentManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ContentManagerTest
{
    @Test
    public void testContentManager() throws IOException
    {
        ContentManager manager = new ContentManager(".\\src\\test\\linknote\\note\\content\\test.note");
        manager.open();
        manager.append("12345");
        manager.append("abc");
        manager.append("765");
        Assert.assertEquals("1 12345\n2 abc\n3 765\n", manager.getContent());
        manager.delete(2);
        Assert.assertEquals("1 12345\n2 765\n", manager.getContent());
        manager.close();

        manager.open();
        Assert.assertEquals("1 12345\n2 765\n", manager.getContent());
        manager.append("new line");
        manager.append("new line2");
        manager.close();

        manager.open();
        Assert.assertEquals("1 12345\n2 765\n3 new line\n4 new line2\n", manager.getContent());
        manager.delete(2, 4);
        Assert.assertEquals("1 12345\n2 new line2\n", manager.getContent());
        manager.delete(1);
        Assert.assertEquals("1 new line2\n", manager.getContent());
        manager.delete(1);
        Assert.assertEquals("", manager.getContent());
        manager.close();
    }
}
