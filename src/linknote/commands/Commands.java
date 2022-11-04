package linknote.commands;

/**
 * Commands 类是静态类，用来汇总所有 Linknote 的命令对应的方法。
 */
public class Commands
{
    private Commands() { }

    /**
     * 对应命令 register
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String register(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 login
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String login(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 logout
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String logout(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 new
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String newNote(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 open
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String open(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令（文本操作） :d
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textDelete(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
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
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 cate
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showCategory(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 now
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showNowCategory(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 goto
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String gotoCategory(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
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
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 link
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String showLinks(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应命令 jump
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String jumpToLinkedNote(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }

    /**
     * 对应默认的文本操作，即在末尾增加一行
     * @param args 传入的参数，包括命令名
     * @return 命令执行完成后的结果
     */
    public static String textAppendLine(String... args)
    {
        return "not implement command:"+String.join(" ", args)+"\n";
    }
}
