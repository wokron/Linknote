package linknote;

import linknote.commands.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Linknote 类是对该项目的最大包装，包含有最基本的一些控制方法，
 * 如按照命令行形式运行的方法 run，或者进行单命令处理的方法 process。
 * 注意使用 Linknote 类需要创建实例。
 */
public class Linknote
{
    /**
     * 以命令行形式运行 Linknote，并且无预启动参数
     */
    public void run()
    {
        Scanner in = new Scanner(System.in);
        System.out.print(Commands.getCommandNotification());
        while (in.hasNextLine())
        {
            String cmd = in.nextLine();
            if (cmd.equalsIgnoreCase("quit"))
                break;
            String[] args = parseCommand(cmd);
            String result = this.process(args);
            System.out.print(result);
            System.out.print(Commands.getCommandNotification());
        }
        System.out.println("quit success");
    }

    /**
     * 对输入的一行命令进行解析。
     * 基本地，会将命令按空格分割为参数，但如果遇到由双引号引起来的内容，则会将内容视为同一个参数（包括其中的空格）。
     * @param cmdStr 表示输入命令的字符串
     * @return 解析后得到的参数，以数组形式返回
     */
    private String[] parseCommand(String cmdStr)
    {
        List<String> args = new ArrayList<>();
        cmdStr = cmdStr.trim();
        int i, pre;
        for (i = 0, pre = 0; i < cmdStr.length(); i++)
        {
            if (cmdStr.charAt(i) == ' ')
            {
                args.add(cmdStr.substring(pre, i));
                pre = i + 1;
            }
            if (cmdStr.charAt(i) == '\"')
            {
                int pairIdx = cmdStr.indexOf('\"', i+1);
                if (pairIdx == -1)
                    pairIdx = cmdStr.length();
                args.add(cmdStr.substring(i+1, pairIdx));
                pre = pairIdx + 1;
                i = pairIdx;
            }
        }
        if (pre < cmdStr.length())
            args.add(cmdStr.substring(pre));
        return args.stream().filter(s -> !s.equals("")).toArray(String[]::new);
    }

    /**
     * 以命令行形式运行 Linknote，但具有预启动参数。
     * 该方法会先将预启动参数按$符号分割为部分，并将每部分作为一条命令，依次执行，随后才进入一般的命令行 Linknote。
     * @param args 预启动参数，以$作为划分不同命令的标准
     */
    public void run(String[] args)
    {
        List<String> commandArgs = new ArrayList<>();
        for (var arg : args)
        {
            if (arg.equals("$") && commandArgs.size() != 0)
            {
                System.out.print(this.process(commandArgs.toArray(String[]::new)));
                commandArgs.clear();
                continue;
            }
            commandArgs.add(arg);
        }
        if (commandArgs.size() != 0)
            System.out.print(this.process(commandArgs.toArray(String[]::new)));
        this.run();
    }

    /**
     * 获取一条命令，进行 Linknote 的相应处理，并返回处理结果
     * @param args 输入的命令即其参数
     * @return 字符串形式的处理结果，末尾可能会包含换行符
     */
    public String process(String... args)
    {
        if (args.length < 1)
            return "";
        String commandName = args[0];
        Function<String[], String> command;
        command = switch (commandName)
        {
            case "register" -> Commands::register;
            case "login" -> Commands::login;
            case "logout" -> Commands::logout;
            case "new" -> Commands::newNote;
            case "open" -> Commands::openNoteSet;
            case "modify" -> Commands::openNote;
            case ":w" -> Commands::textAppendLine;
            case ":d" -> Commands::textDelete;
            case ":y" -> Commands::textCopy;
            case ":p" -> Commands::textInsert;
            case ":q" -> Commands::textQuit;
            case "close" -> Commands::closeNoteSet;
            case "cate" -> Commands::showCategories;
            case "now" -> Commands::showNowCategory;
            case "goto" -> Commands::gotoCategory;
            case "mklink" -> Commands::makeLink;
            case "link" -> Commands::showLinks;
            case "jump" -> Commands::jumpToLinkedNote;
            case "pre" -> Commands::goBackToPreNote;
            default -> strings -> "command not found\n";

        };
        return command.apply(args);
    }
}
