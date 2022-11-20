package linknote.note;

import linknote.exception.LinknoteException;
import linknote.note.content.ContentManager;

import java.io.IOException;
import java.util.*;

/**
 * Note 类用于存储单个 Note 的信息，以及实现对该 Note 的操作
 */
public class Note
{
    private final String category;
    private final String noteName;
    private final Map<String, Note> links = new HashMap<>();
    protected final ContentManager contentManager;

    /**
     * @param owner owner 创建该 Note 的用户名
     * @param category 该 Note 所在的 Category
     * @param noteName 该 Note 的名字
     * @throws IOException 当无法创建该 Note 对应的文件时抛出
     */
    public Note(String owner, String category, String noteName) throws IOException
    {
        this.category = category;
        this.noteName = noteName;
        contentManager = new ContentManager(categoryToPath(owner, category, noteName));
    }

    /**
     * 该方法根据 Note 的创建者、category和笔记名，创建该笔记对应的文件的文件路径
     * @param owner owner 创建该 Note 的用户名
     * @param category 该 Note 所在的 Category
     * @param noteName 该 Note 的名字
     * @return 笔记对应的文件的文件路径
     */
    private String categoryToPath(String owner, String category, String noteName)
    {
        return "./" + owner + "/" +
                category.replace('.', '/') +
                "/" + noteName + ".note";
    }

    /**
     * 此方法打开文件并加载其中内容。在打开文件之前进行的任何修改内容的操作都不会对内容产生改变
     * @throws IOException 当无法读取文件内容时抛出
     */
    public void open() throws IOException
    {
        contentManager.open();
    }

    /**
     * 此方法用于向笔记中增加一行内容，使用前需要调用 open 以启用修改
     * @param appendLine 要添加的文本内容
     */
    public void append(String appendLine)
    {
        contentManager.append(appendLine);
    }

    /**
     * 该方法用于删除在范围内的行内容。使用前需要调用 open 以启用修改
     * @param lineFrom 删除的起始行号（包括）。当 lineFrom 不在行号范围内时，不会任删除何行。
     * @param lineTo 删除的结束行号（不包括）。当 lineTo 小于 lineFrom 时，不会任删除何行。
     */
    public void delete(int lineFrom, int lineTo)
    {
        contentManager.delete(lineFrom, lineTo);
    }

    /**
     * 该方法用于删除对应行的内容。使用前需要调用 open 以启用修改
     * @param line 要删除的行号，若行号不在范围内，不会删除任何行
     */
    public void delete(int line)
    {
        contentManager.delete(line);
    }

    /**
     * 调用该方法意味着结束对内容的修改，所有修改的结果将在此刻写回文件中
     * @throws IOException 当内容无法写入文件时抛出
     */
    public void close() throws IOException
    {
        contentManager.close();
    }

    /**
     * 调用该方法创建一个本笔记到另一个笔记的链接
     * @param linkName 链接名
     * @param note 被链接指向的笔记
     */
    public void makeLink(String linkName, Note note)
    {
        links.put(linkName, note);
    }

    /**
     * 调用该方法以获取本笔记目前具有的链接
     * @return 本笔记目前具有的链接的链接名
     */
    public List<String> showLinks()
    {
        return links.keySet().stream().toList();
    }

    /**
     * 该方法通过链接名获取对应的笔记
     * @param linkName 链接名
     * @return 链接指向的笔记
     * @throws LinknoteException 当该链接名不存在时抛出
     */
    public Note jumpToLinkedNote(String linkName) throws LinknoteException
    {
        if (links.containsKey(linkName))
            return links.get(linkName);
        else
            throw new LinknoteException("link name not found");
    }

    /**
     * 调用该方法以获得文件当前的内容（包含行号）。只能在文件被打开时使用
     * @return 文件当前内容
     */
    public String getContent()
    {
        return contentManager.getContent();
    }

    /**
     * @return 文件名和文件的 category
     */
    @Override
    public String toString()
    {
        return this.noteName + "|" + this.category;
    }
}
