package linknote.note;

import linknote.exception.LinknoteException;

import java.io.IOException;
import java.util.*;

/**
 * NoteManager 类管理一个用户名下的所有笔记
 */
public class NoteManager
{
    private final TreeMap<String, TreeMap<String, Note>> categoriesToNotes = new TreeMap<>();

    private final Stack<Note> visNotes = new Stack<>();

    private final Stack<Note> unvisNotes = new Stack<>();

    private final CategoryNode root = new CategoryNode("");
    private final Stack<CategoryNode> nowCategory = new Stack<>();

    {
        // 这是一个初始化块，因为我没找到初始化栈的方法，只好这样。。。
        nowCategory.push(root);
    }

    /**
     * 创建一个新的 Note
     * @param noteName 笔记名
     * @param noteOwner 笔记创建者
     * @param category 笔记的 category
     * @throws IOException 当无法创建该笔记对应的文件时抛出
     */
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
        notes.put(directory + "|" + noteName, new Note(noteOwner, category, noteName));
    }

    /**
     * 打开一个笔记集，笔记集指的是同一外部 category 的许多笔记。
     * 打开笔记集后，才可进行链接跳转和打开笔记内容等操作
     * @param noteSetName 笔记集的名字
     * @throws LinknoteException 当笔记集不存在时抛出
     */
    public void openNoteSet(String noteSetName) throws LinknoteException
    {
        String totalCategory = joinTotalCategory(noteSetName);
        if (!categoriesToNotes.containsKey(totalCategory))
            throw new LinknoteException("note set not found");
        var notes = categoriesToNotes.get(totalCategory).values().toArray(new Note[0]);
        for (int i = notes.length-1; i >= 1; i--)
            unvisNotes.push(notes[i]);
        visNotes.push(notes[0]);
    }

    /**
     * 关闭笔记集
     */
    public void closeNoteSet()
    {
        unvisNotes.clear();
        visNotes.clear();
    }

    /**
     * 获取当前打开的笔记集中的当前笔记
     * @return 当前笔记
     * @throws LinknoteException 当无当前笔记时抛出，这通常意味着并没有打开笔记集
     */
    public Note getCurrentNote() throws LinknoteException
    {
        if (visNotes.size() > 0)
            return visNotes.peek();
        else
            throw new LinknoteException("now doesn't have note");
    }

    /**
     * 返回当前路径下的 category。
     * 返回的 category 名要么是可以打开的笔记集名字，要么是可以继续进入的路径 category
     * @return 当前路径下的 category
     */
    public List<String> showCategories()
    {
        var pwd = nowCategory.peek();
        return pwd.showChildrenInfo();
    }

    /**
     * 返回当前所在的 category
     * @return 当前所在的 category
     */
    public String showNowCategory()
    {
        var nodes = nowCategory
                .stream()
                .map(CategoryNode::getCateName)
                .filter(name -> !name.equals(""))
                .toList();
        return String.join(".", nodes);
    }

    /**
     * 进入到下一集或上一级 category。这个category必须不能是笔记集的名字
     * @param cateNodeName 要进入的 category 名
     * @throws LinknoteException 当category不能进入，或category不存在时抛出
     */
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

    /**
     * 获取当前笔记的所有链接名，包括 next 链接。
     * 请不要使用 getCurrentNote().showLinks() 获取当前笔记的链接。
     * @return 当前笔记的所有链接名
     * @throws LinknoteException 当无当前笔记时抛出，这通常意味着并没有打开笔记集
     */
    public List<String> showLinks() throws LinknoteException
    {
        var links = new ArrayList<>(getCurrentNote().showLinks());
        if (unvisNotes.size() > 0)
            links.add(0, "next");
        return links;
    }

    /**
     * 通过category和笔记名创建一个当前笔记到对应笔记的名字为linkName的链接
     * @param linkName 链接名
     * @param category 被链接的笔记的category
     * @param noteName 被链接的笔记的名字
     * @throws LinknoteException 当category获笔记名不存在时抛出
     */
    public void makeLink(String linkName, String category, String noteName) throws LinknoteException
    {
        String[] cateSplit = category.split("/");
        String path, directory = "";
        path = cateSplit[0];
        if (cateSplit.length > 1)
            directory = cateSplit[1];
        if (!categoriesToNotes.containsKey(path))
            throw new LinknoteException("category not find");
        var notes = categoriesToNotes.get(path);
        if (!notes.containsKey(directory + "|" + noteName))
            throw new LinknoteException("inner category not find");
        var noteFounded = notes.get(directory + "|" + noteName);
        getCurrentNote().makeLink(linkName, noteFounded);
    }

    /**
     * 使用链接名跳转到对应笔记
     * @param linkName 链接名
     * @throws LinknoteException 当链接名不存在时抛出
     */
    public void jumpToLinkedNote(String linkName) throws LinknoteException
    {
        Note linkedNote;
        if (linkName.equals("next"))
        {
            if (unvisNotes.size() > 0)
                linkedNote = unvisNotes.pop();
            else
                throw new LinknoteException("link name not found");
        }
        else
            linkedNote = getCurrentNote().jumpToLinkedNote(linkName);
        visNotes.push(linkedNote);
    }

    /**
     * 返回跳转前的笔记，当当前笔记已经是打开笔记集后的第一个笔记时，还停留在该笔记
     */
    public void goBackToPreNote()
    {
        if (visNotes.size() > 1)
            unvisNotes.push(visNotes.pop());
    }

    /**
     * 该函数将category的相对路径转化为绝对路径
     * @param noteName 从当前路径开始的相对路径
     * @return 完整的category
     */
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

/**
 * 这是一个辅助类，用于表示category形成的路径树
 */
class CategoryNode implements Comparable<CategoryNode>
{
    private final Set<CategoryNode> children = new TreeSet<>();
    private final String nodeName;

    public CategoryNode(String name)
    {
        this.nodeName = name;
    }

    /**
     * 从当前位置，添加一条category路径
     * @param category 一个List，其中每个元素是category中的每一部分名字
     */
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

    /**
     * @return 返回子节点的名字
     */
    public List<String> showChildrenInfo()
    {
        return children.stream().map(CategoryNode::getCateName).toList();
    }

    /**
     * 通过名字获取当前category节点的子节点
     * @param childName 子节点名
     * @return 子节点
     * @throws LinknoteException 当不存在该子节点时抛出
     */
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

    /**
     * 判断当前节点是否为叶节点（是叶节点说明该节点名表示一个笔记集）
     * @return 当前节点是否为叶节点
     */
    public boolean isTerminal()
    {
        return children.size() == 0;
    }

    @Override
    public int compareTo(CategoryNode o)
    {
        return this.nodeName.compareTo(o.nodeName);
    }
}