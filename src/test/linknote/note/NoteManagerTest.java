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
        manager.newNote("test11", "yyt", "y1.cateNew/subtitle1.chapter1");

        // at root
        Assert.assertEquals("", manager.showNowCategory());
        var cates = manager.showCategories();
        Assert.assertEquals(2, cates.size());
        Assert.assertEquals("y1", cates.get(0));
        Assert.assertEquals("y2", cates.get(1));

        manager.gotoCategory("y2");
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

        manager.openNoteSet("cateNew");

        var note = manager.getCurrentNote();
        note.open();
        note.append("1");
        note.close();

        var links = manager.showLinks();
        Assert.assertEquals(1, links.size());
        Assert.assertEquals("next", links.get(0));

        manager.jumpToLinkedNote("next");
        manager.getCurrentNote().open();
        manager.getCurrentNote().append("2");
        manager.getCurrentNote().close();

        manager.jumpToLinkedNote("next");
        manager.getCurrentNote().open();
        manager.getCurrentNote().append("3");
        manager.getCurrentNote().close();

        manager.goBackToPreNote();
        Assert.assertEquals("1 2\n", manager.getCurrentNote().getContent());

        manager.makeLink("goto_y2", "y2.cateNew", "test6");
        links = manager.showLinks();
        Assert.assertEquals(2, links.size());
        Assert.assertEquals("next", links.get(0));
        Assert.assertEquals("goto_y2", links.get(1));

        manager.jumpToLinkedNote("goto_y2");
        Assert.assertEquals("test6|y2.cateNew", manager.getCurrentNote().toString());
        manager.getCurrentNote().append("amazing jump!!!");
        manager.getCurrentNote().close();

        manager.goBackToPreNote();

        manager.goBackToPreNote();
        manager.goBackToPreNote();
        manager.goBackToPreNote();
        manager.goBackToPreNote();
        manager.goBackToPreNote();
        Assert.assertEquals("1 1\n", manager.getCurrentNote().getContent());

        manager.closeNoteSet();
//        manager.gotoCategory(manager.showCategories().get(0));


    }
}
