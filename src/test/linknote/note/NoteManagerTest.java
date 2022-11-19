package test.linknote.note;

import linknote.exception.LinknoteException;
import linknote.note.NoteManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NoteManagerTest
{
    @Test
    public void test() throws IOException, LinknoteException
    {
        var manager = new NoteManager();
        manager.newNote("test1", "yyt", "y1.cateNew/subtitle1.chapter1");
        manager.newNote("test2", "yyt", "y1.cateNew");
        manager.newNote("test3", "yyt", "y1.cateNew/subtitle1.chapter2");
        manager.newNote("test4", "yyt", "y1.cateNew/subtitle2.chapter1");
        manager.newNote("test5", "yyt", "y1.cateNew/subtitle2.chapter2");
        manager.newNote("test6", "yyt", "y2.cateNew");
        manager.newNote("test7", "yyt", "y1.cateNew");
        manager.newNote("test8", "yyt", "y1.cateNew");

        // at root
        Assert.assertEquals("", manager.showNowCategory());
        var cates = manager.showCategories();
        Assert.assertEquals(2, cates.size());
        Assert.assertEquals("y1", cates.get(0));
        Assert.assertEquals("y2", cates.get(1));

        manager.gotoCategory(cates.get(1));
        // at y2
        cates = manager.showCategories();
        Assert.assertEquals(1, cates.size());
        Assert.assertEquals("cateNew", cates.get(0));

        manager.gotoCategory("../");
        // at root
        manager.gotoCategory(manager.showCategories().get(0));
        // at y1
        cates = manager.showCategories();
        Assert.assertEquals(1, cates.size());
        Assert.assertEquals("cateNew", cates.get(0));

        manager.openNote(cates.get(0));
        var note = manager.getCurrentNote();
        note.append("123");
        note.close();
        manager.closeNote();

//        manager.gotoCategory(manager.showCategories().get(0));


    }
}
