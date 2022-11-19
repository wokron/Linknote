package linknote.content;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * ContentManager 类用于实现对笔记文本内容的管理。
 * 这是一个父类，其他子类还会实现更多的，对应不同笔记类型的操作
 */
public class ContentManager
{
    private final Path filePath;
    private final List<String> contentCache = new ArrayList<>();
    private boolean isOpen = false;

    public ContentManager(String filePath) throws IOException
    {
        this.filePath = Paths.get(filePath);
        if (Files.notExists(this.filePath))
            Files.createFile(this.filePath);
    }

    /**
     * 此方法打开文件并加载其中内容。在打开文件之前进行的任何修改内容的操作都不会对内容产生改变
     * @throws IOException 当无法读取文件内容时抛出
     */
    public void open() throws IOException
    {
        isOpen = true;
        contentCache.clear();
        contentCache.addAll(Files.readAllLines(filePath));
    }

    /**
     * 此方法用于向笔记中增加一行内容
     * @param appendLine 要添加的文本内容
     */
    public void append(String appendLine)
    {
        if (!isOpen)
            return;
        contentCache.add(appendLine);
    }

    /**
     * 该方法用于删除在范围内的行内容。
     * @param lineFrom 删除的起始行号（包括）。当 lineFrom 不在行号范围内时，不会任删除何行。
     * @param lineTo 删除的结束行号（不包括）。当 lineTo 小于 lineFrom 时，不会任删除何行。
     */
    public void delete(int lineFrom, int lineTo)
    {
        if (!isOpen || lineFrom <= 0 || lineFrom > contentCache.size() || lineTo <= lineFrom)
            return;
        for (int i = 0; i < lineTo-lineFrom; i++)
            contentCache.remove(lineFrom-1);
    }

    /**
     * 该方法用于删除对应行的内容。
     * @param line 要删除的行号，若行号不在范围内，不会删除任何行
     */
    public void delete(int line)
    {
        if (!isOpen)
            return;
        this.delete(line, line+1);
    }

    /**
     * 调用该方法意味着结束对内容的修改，所有修改的结果将在此刻写回文件中
     * @throws IOException 当内容无法写入文件时抛出
     */
    public void close() throws IOException
    {
        isOpen = false;
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);
        Files.writeString(filePath, String.join("\n", contentCache), StandardOpenOption.WRITE);
        contentCache.clear();
    }


    /**
     * 调用该方法以获得文件当前的内容（包含行号）。只能在文件被打开时使用
     * @return 文件当前内容
     */
    public String getContent()
    {
        return getContent(true);
    }

    /**
     * 调用该方法以获得文件当前的内容。只能在文件被打开时使用。可以选择是否添加行号
     * @param hasLineNum 是否需要添加行号
     * @return 文件当前内容
     */
    public String getContent(boolean hasLineNum)
    {
        if (!isOpen)
            return "";
        StringBuilder sb = new StringBuilder();
        int lineNum = 1;
        for (var line : contentCache)
        {
            if (hasLineNum)
                sb.append(lineNum).append(" ");
            sb.append(line).append("\n");
            lineNum++;
        }
        return sb.toString();
    }
}
