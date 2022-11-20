package linknote.user;

import linknote.exception.LinknoteException;
import linknote.note.NoteManager;

import java.io.IOException;
import java.util.List;

/**
 * @author erreurist
 */
public class User {
    private final String name;
    private final String password;

    private final NoteManager noteManager = new NoteManager();

    public User(String name,String password)
    {
        this.name = name;
        this.password = password;
    }

    public boolean matchPassword(String password)
    {
        return this.password.equals(password);
    }

    public void newNote(String noteName, String category)
            throws IOException
    {
        noteManager.newNote(noteName, this.name, category);
    }

    public void openNoteSet(String noteSetName) throws LinknoteException
    {
        noteManager.openNoteSet(noteSetName);
    }

    public void openNote() throws LinknoteException, IOException
    {
        noteManager.getCurrentNote().open();
    }

    public void contentAppend(String appendLine) throws LinknoteException
    {
        noteManager.getCurrentNote().append(appendLine);
    }

    public void contentDelete(int from, int to) throws LinknoteException
    {
        noteManager.getCurrentNote().delete(from, to);
    }

    public void contentDelete(int line) throws LinknoteException
    {
        noteManager.getCurrentNote().delete(line);
    }

    public String showContent() throws LinknoteException
    {
        return noteManager.getCurrentNote().getContent();
    }

    public void closeNote() throws LinknoteException, IOException
    {
        noteManager.getCurrentNote().close();
    }

    public void closeNoteSet()
    {
        noteManager.closeNoteSet();
    }

    public List<String> showCategories()
    {
        return noteManager.showCategories();
    }

    public String showNowCategory()
    {
        return noteManager.showNowCategory();
    }

    public void gotoCategory(String cateNodeName) throws LinknoteException
    {
        noteManager.gotoCategory(cateNodeName);
    }

    public void makeLink(String linkName, String category, String noteName)
            throws LinknoteException
    {
        noteManager.makeLink(linkName, category, noteName);
    }

    public List<String> showLinks() throws LinknoteException
    {
        return noteManager.showLinks();
    }

    public void jumpToLinkedNote(String linkName) throws LinknoteException
    {
        noteManager.jumpToLinkedNote(linkName);
    }

    public void goBackToPreNote()
    {
        noteManager.goBackToPreNote();
    }

    public String showNoteInfo() throws LinknoteException
    {
        return noteManager.getCurrentNote().getInfo();
    }
}

