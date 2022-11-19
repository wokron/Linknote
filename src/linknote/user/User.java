package linknote.user;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * @ClassName: User
 * @Author: erreurist
 * @
 */
public class User {
    /**
     * the propertier of the users
     */
    private String name,password;

    private int mode;

    /**
     * 维护所有user对象的集合
     */
    public static TreeMap<String,User> allUsers= new TreeMap<>();

    /*
     *构造方法
     */
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * @Title: judgeName
     * @Description: judge the name
     * @param: str
     * @return Boolean
     */
    public static boolean judgeName(String str){
        boolean res=true;
        if(!Pattern.matches("^([A-Z])([a-z]{0,19})$",str))
            res=false;
        return res;
    }

    /**
     * @Title: judgeName
     * @Description: judge the password
     * @Param: Str
     * @return Boolean
     */
    public static boolean judgePassword(String str){
        boolean res=false;
        if(Pattern.matches("^([A-Za-z])([a-zA-Z_0-9]{7,15})$",str))
            res=true;
        return res;
    }

    /**
     * @Title: register
     * @Description: register a new user
     * @Param: String str,User a
     * @return void
     */
    public static void register(String str,User a){
        String[] s=str.split("\\s+");
        /*
         *参数格式为register name pwd即长度为3
         */
        if(s.length!=3) System.out.println("arguments illegal");
        /*
         *假设外部无人登录有个虚假的对象mode为0，每次登出都换成它,mode==0即为无人登陆状态
         */
        else if(a.mode!=0) System.out.println("already logged in");
        /*
         *名字不合法
         */
        else if(!judgeName(s[1])) System.out.println("user name illegal");
        /*
         *名字重复
         */
        else if(allUsers.containsKey(s[1])) System.out.println("user name already exists");
        /*
         *密码不合法
         */
        else if(!judgePassword(s[2])) System.out.println("password illegal");
        /*
         *注册成功，加入全体用户集合维护
         */
        else{
            User newUser=new User(s[1],s[2]);
            allUsers.put(newUser.getName(),newUser);
            System.out.println("register success");
        }

    }

    /**
     * @Title: login
     * @Description: login
     * @Param: String str,User a
     * @return User
     */
    public static User login(String str,User a){
        String[] s=str.split("\\s+");
        /*
         *参数格式为login name pwd即长度为3
         */
        if(s.length!=3) {
            System.out.println("arguments illegal");
            return null;
        }
        /*
        *假设外部无人登录有个虚假的对象mode为0，每次登出都换成它,mode==0即为无人登陆状态
        */
        else if(a.mode!=0) {
            System.out.println("already logged in");
            return null;
        }
        /*
         *不存在此用户
         */
        else if(!allUsers.containsKey(s[1])){
            System.out.println("user not exist");
            return null;
        }
        /*
         *密码错误
         */
        else if(!Objects.equals(a.getPassword(),s[2])) {
            System.out.println("wrong password");
            return null;
        }
        /*
         *成功登录，返回该对象，外部对象切换为此对象
         */
        else{
            User currentUser = allUsers.get(s[1]);
            System.out.println("login success,hello~ "+currentUser.getName());
            return currentUser;
        }
    }

    /**
     * @Title: logout
     * @Description: logout
     * @Param: String str,User a
     * @return boolean 成功登出返回true
     */
    public static boolean logout(String str,User a){
        String[] s=str.split("\\s+");
        /*
         *无参数即长度为1
         */
        if(s.length!=1) {
            System.out.println("arguments illegal");
            return false;
        }
        /*
         *未登录状态无法登出
         */
        else if(a.mode==1) {
            System.out.println("not login");
            return false;
        }
        /*
         *成功登出
         */
        else{
            System.out.println("logout success, bye~");
            return true;
        }
    }

}

