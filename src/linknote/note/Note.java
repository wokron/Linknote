package linknote.note;

import linknote.exception.LinknoteException;
import linknote.note.content.ContentManager;

import java.io.IOException;
import java.util.*;

public class Note implements Comparable<Note>
{
    private final String category;
    private final String noteName;
    private final Map<String, Note> links = new HashMap<>();
    protected final ContentManager contentManager;
    public Note(String owner, String category, String noteName) throws IOException
    {
        this.category = category;
        this.noteName = noteName;
        contentManager = new ContentManager(categoryToPath(owner, category, noteName));
    }

    private String categoryToPath(String owner, String category, String noteName)
    {
        return "./" + owner + "/" +
                category.replace('.', '/') +
                "/" + noteName + ".note";
    }

    public void open() throws IOException
    {
        contentManager.open();
    }

    /**
     *
     * @param appendLine
     * @throws IOException
     */
    public void append(String appendLine) throws IOException
    {
        contentManager.append(appendLine);
    }

    public void delete(int lineFrom, int lineTo) throws IOException
    {
        contentManager.delete(lineFrom, lineTo);
    }

    public void delete(int line) throws IOException
    {
        contentManager.delete(line);
    }

    public void close() throws IOException
    {
        contentManager.close();
    }

    public void makeLink(String linkName, Note note)
    {
        links.put(linkName, note);
    }

    public List<String> showLinks()
    {
        return links.keySet().stream().toList();
    }

    public Note jumpToLinkedNote(String linkName) throws LinknoteException
    {
        if (links.containsKey(linkName))
            return links.get(linkName);
        else
            throw new LinknoteException("link name not found");
    }

    public String getContent() throws IOException
    {
        return contentManager.getContent();
    }

    @Override
    public String toString()
    {
        return this.noteName + "|" + this.category;
    }

    @Override
    public int compareTo(Note o)
    {
        return this.category.compareTo(o.category);
    }
}
