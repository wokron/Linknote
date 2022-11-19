package test.linknote.note;

import linknote.note.Note;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NoteTest
{
    @Test
    public void test() throws IOException
    {
        Note note = new Note("yyt", "y1.y2/x1.x2", "testNote");
        note.append("123");
        note.append("456");
        Assert.assertEquals("1 123\n2 456\n", note.getContent());
        note.close();

        note.delete(1);
        note.delete(1);
        Assert.assertEquals("", note.getContent());
        note.close();

        Assert.assertEquals("testNote|y1.y2/x1.x2", note.toString());
    }
}
