package test.linknote;

import linknote.Linknote;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LinknoteTest
{
    @Test
    public void testParseCommand() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        String[] testCmds = new String[]{
                "123 456",
                "123 456 785",
                "    123   546     94",
                "  12 43     665    ",
                "123 \"123 3435\" 335 554",
                "123 \"   123 434\"   ",
                "123 \"123 434   \"   ",
                "123 \"   123 434   \"   ",
                "",
                "\"3 123 4343 \"  ",
                "  \"2 123 4343 \"",
                "\"1 123 4343 \"",
                " 3453 \"12 434 54 45",
        };

        String[][] expectResults = new String[][]{
                new String[]{"123", "456"},
                new String[]{"123", "456", "785"},
                new String[]{"123", "546", "94"},
                new String[]{"12", "43", "665"},
                new String[]{"123", "123 3435", "335", "554"},
                new String[]{"123", "   123 434"},
                new String[]{"123", "123 434   "},
                new String[]{"123", "   123 434   "},
                new String[]{},
                new String[]{"3 123 4343 "},
                new String[]{"2 123 4343 "},
                new String[]{"1 123 4343 "},
                new String[]{"3453", "12 434 54 45"},
        };
        Method parseCommand = Linknote.class.getDeclaredMethod("parseCommand", String.class);
        parseCommand.setAccessible(true);
        for (int i = 0; i < testCmds.length; i++)
        {
            String testCmd = testCmds[i];
            String[] expectResult = expectResults[i];
            String[] actualResult = (String[]) parseCommand.invoke(new Linknote(), testCmd);
            Assert.assertArrayEquals(expectResult, actualResult);
        }
    }
}
