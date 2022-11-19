package linknote.note;

import linknote.exception.LinknoteException;

import java.io.IOException;
import java.util.*;

public class NoteManager
{
    private final TreeMap<String, TreeMap<String, Note>> categoriesToNotes = new TreeMap<>();

    private final Stack<Note> visNotes = new Stack<>();

    private final Stack<Note> unvisNotes = new Stack<>();

    private final CategoryNode root = new CategoryNode("");
    private final Stack<CategoryNode> nowCategory = new Stack<>();

    {
        nowCategory.push(root);
    }

    public void newNote(String noteName, String noteOwner, String category) throws IOException
    {
        String[] cateSplit = category.split("/");
        String path, directory = "";
        path = cateSplit[0];
        if (cateSplit.length > 1)
            directory = cateSplit[1];
        root.addCategoryFromHere(Arrays.stream(path.split("\\.")).toList());
        if (!categoriesToNotes.containsKey(path))
            categoriesToNotes.put(path, new TreeMap<>());
        var notes = categoriesToNotes.get(path);
        notes.put(directory, new Note(noteOwner, category, noteName));
    }

    public void openNote(String noteName) throws LinknoteException
    {
        String totalCategory = joinTotalCategory(noteName);
        if (!categoriesToNotes.containsKey(totalCategory))
            throw new LinknoteException("note set not found");
        var notes = categoriesToNotes.get(totalCategory).values().toArray(new Note[0]);
        for (int i = notes.length-1; i >= 1; i--)
            unvisNotes.push(notes[i]);
        visNotes.push(notes[0]);
    }

    public void closeNote()
    {
        unvisNotes.clear();
        visNotes.clear();
    }

    public Note getCurrentNote() throws LinknoteException
    {
        if (visNotes.size() > 0)
            return visNotes.peek();
        else
            throw new LinknoteException("now doesn't have note");
    }

    public List<String> showCategories()
    {
        var pwd = nowCategory.peek();
        return pwd.showChildrenInfo();
    }

    public String showNowCategory()
    {
        return String.join(".", nowCategory.stream().map(CategoryNode::getCateName).toList());
    }

    public void gotoCategory(String cateNodeName) throws LinknoteException
    {
        if (cateNodeName.startsWith(".."))
        {
            nowCategory.pop();
            return;
        }
        var peekNode = nowCategory.peek();
        var targetCateNode = peekNode.getChild(cateNodeName);
        if (targetCateNode.isTerminal())
            throw new LinknoteException("this category is a note set");
        nowCategory.push(targetCateNode);
    }

    private String joinTotalCategory(String noteName)
    {
        var nowCateAsStr = String.join(
                ".",
                nowCategory.stream()
                        .map(CategoryNode::getCateName)
                        .filter(cateName -> !cateName.equals(""))
                        .toList());
        return nowCateAsStr + "." + noteName;
    }
}

class CategoryNode
{
    private final List<CategoryNode> children = new ArrayList<>();
    private final String nodeName;

    public CategoryNode(String name)
    {
        this.nodeName = name;
    }

    public void addCategoryFromHere(List<String> category)
    {
        if (category.size() == 0)
            return;
        var findNode = children.stream()
                .filter(node -> node.nodeName.equals(category.get(0)))
                .findFirst();
        if (findNode.isPresent())
        {
            var nodeFounded = findNode.get();
            nodeFounded.addCategoryFromHere(category.subList(1, category.size()));
        }
        else
        {
            var nodeCreated = new CategoryNode(category.get(0));
            children.add(nodeCreated);
            nodeCreated.addCategoryFromHere(category.subList(1, category.size()));
        }
    }

    public List<String> showChildrenInfo()
    {
        return children.stream().map(CategoryNode::getCateName).toList();
    }

    public CategoryNode getChild(String childName) throws LinknoteException
    {
        var findNode = children.stream()
                .filter(node -> node.nodeName.equals(childName))
                .findFirst();
        if (findNode.isPresent())
            return findNode.get();
        else
            throw new LinknoteException("category not found");
    }


    public String getCateName()
    {
        return nodeName;
    }

    public boolean isTerminal()
    {
        return children.size() == 0;
    }
}