package linknote.note;

import linknote.note.content.ContentManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Note implements Comparable
{
    private final String category;
    private final String noteName;
    private final Set<Note> links = new HashSet<>();
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

    public void append(String appendLine) throws IOException
    {
        if (!contentManager.isOpen())
            contentManager.open();
        contentManager.append(appendLine);
    }

    public void delete(int lineFrom, int lineTo) throws IOException
    {
        if (!contentManager.isOpen())
            contentManager.open();
        contentManager.delete(lineFrom, lineTo);
    }

    public void delete(int line) throws IOException
    {
        if (!contentManager.isOpen())
            contentManager.open();
        contentManager.delete(line);
    }

    public void close() throws IOException
    {
        contentManager.close();
    }

    public void makeLink(Note note)
    {
        links.add(note);
    }

    public List<String> showLinks()
    {
        return links.stream().map(Note::toString).toList();
    }

    public String getContent()
    {
        return contentManager.getContent();
    }

    @Override
    public int compareTo(Object o)
    {
        String s = (String) o;
        return category.compareTo(s);
    }

    @Override
    public String toString()
    {
        return this.noteName + "|" + this.category;
    }
}
