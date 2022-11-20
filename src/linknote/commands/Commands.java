package linknote.commands;

import linknote.exception.LinknoteException;
import linknote.user.UserController;

import java.io.IOException;

/**
 * Commands 类是静态类，用来汇总所有 Linknote 的命令对应的方法。
 */
public class Commands
{
    private static final UserController userController = new UserController();
    private Commands() { }

    /**
     * 对应命令 register
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String register(String... args)
    {
        try
        {
            checkArgumentsNum(3, args);
            userController.register(args[1], args[2]);
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        return "register success\n";
    }

    /**
     * 对应命令 login
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String login(String... args)
    {
        try
        {
            checkArgumentsNum(3, args);
            userController.login(args[1], args[2]);
            return String.format("user:%s login success\n", args[1]);
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 logout
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String logout(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            userController.logout();
            return "logout\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 new
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String newNote(String... args)
    {
        try
        {
            checkArgumentsNum(3, args);
            userController.getCurrentUser().newNote(args[1], args[2]);
            return String.format("note:%s|%s create success\n", args[2], args[1]);
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }
    }

    /**
     * 对应命令 open
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String openNoteSet(String... args)
    {
        try
        {
            checkArgumentsNum(2, args);
            var currentUser = userController.getCurrentUser();
            currentUser.openNoteSet(args[1]);
            currentUser.openNote();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            currentUser.closeNote();
            return noteInfo + "\n" + content + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }
    }

    public static String openNote(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            var currentUser = userController.getCurrentUser();
            currentUser.openNote();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            return noteInfo + "\n" + content;
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }
    }

    /**
     * 对应命令（文本操作） :d
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textDelete(String... args)
    {
        try
        {
            if (args.length == 2)
                userController.getCurrentUser().contentDelete(Integer.parseInt(args[1]));
            else if (args.length == 3)
                userController.getCurrentUser().contentDelete(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            else
                throw new LinknoteException("arguments illegal");
            var currentUser = userController.getCurrentUser();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            return noteInfo + "\n" + content;
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令（文本操作） :y
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textCopy(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令（文本操作） :i
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textInsert(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令（文本操作） :q
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textQuit(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            userController.getCurrentUser().closeNote();
            return "edit quit\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }
    }

    public static String closeNoteSet(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            userController.getCurrentUser().closeNoteSet();
            return "close note set\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 cate
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showCategories(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            var categories = userController.getCurrentUser().showCategories();
            return String.join(" ", categories) + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 now
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showNowCategory(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            return userController.getCurrentUser().showNowCategory() + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 goto
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String gotoCategory(String... args)
    {
        try
        {
            checkArgumentsNum(2, args);
            userController.getCurrentUser().gotoCategory(args[1]);
            return "goto category: " + args[1] + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 read
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String readNotesWithCategory(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 mklink
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String makeLink(String... args)
    {
        try
        {
            checkArgumentsNum(4, args);
            userController.getCurrentUser().makeLink(args[1], args[2], args[3]);
            return "here ---> " + args[1] + " link success\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 link
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showLinks(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            var links = userController.getCurrentUser().showLinks();
            return "links:\n" + String.join("\n", links) + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    /**
     * 对应命令 jump
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String jumpToLinkedNote(String... args)
    {
        try
        {
            checkArgumentsNum(2, args);
            userController.getCurrentUser().jumpToLinkedNote(args[1]);
            var currentUser = userController.getCurrentUser();
            currentUser.openNote();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            currentUser.closeNote();
            return noteInfo + "\n" + content + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }
    }

    /**
     * 对应命令 pre
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String goBackToPreNote(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            userController.getCurrentUser().goBackToPreNote();
            var currentUser = userController.getCurrentUser();
            currentUser.openNote();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            currentUser.closeNote();
            return noteInfo + "\n" + content + "\n";
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
        catch (IOException e)
        {
            return "file operation failed\n";
        }

    }

    /**
     * 对应默认的文本操作，即在末尾增加一行
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textAppendLine(String... args)
    {
        try
        {
            checkArgumentsNum(1, args);
            userController.getCurrentUser().contentAppend(args[0]);
            var currentUser = userController.getCurrentUser();
            String noteInfo = currentUser.showNoteInfo();
            String content = currentUser.showContent();
            return noteInfo + "\n" + content;
        }
        catch (LinknoteException e)
        {
            return e.getMessage() + "\n";
        }
    }

    private static void checkArgumentsNum(int num, String... args) throws LinknoteException
    {
        if (args.length != num)
            throw new LinknoteException("arguments illegal");
    }
}
