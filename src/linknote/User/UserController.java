package linknote.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 *简单的GUI界面，慢慢完善 这个类似于记事本
 */
public class UserController extends JFrame implements ActionListener{

    JTextArea jta = null;
    JScrollPane jsp = null;
    /*
     *菜单条
     */
    JMenuBar jmb = null;
    /*
     *第一个JMenu
     */
    JMenu file = null;
    JMenuItem open = null;
    JMenuItem save = null;
    JMenuItem exit = null;
    public static void main(String[] args){
        new UserController();
    }

    UserController() {
        jta = new JTextArea();
        jsp = new JScrollPane(jta);
        jmb = new JMenuBar();
        file = new JMenu("文件(F)");
        /*
         *设置助记符:使用alt+F可以激活相应的事件
         */
        file.setMnemonic('F');
        open = new JMenuItem("打开(O)");
        open.setMnemonic('O');
        save = new JMenuItem("保存(S)");
        save.setMnemonic('S');
        exit = new JMenuItem("退出(X)");
        exit.setMnemonic('X');
        file.setActionCommand("file");
        open.setActionCommand("open");
        open.addActionListener(this);
        save.setActionCommand("save");
        save.addActionListener(this);
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        this.setJMenuBar(jmb);
        jmb.add(file);
        file.add(open);
        file.add(save);
        file.add(exit);
        this.add(jsp);
        this.setTitle("Linknote" );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(300, 100, 800, 600);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("open")){
            JFileChooser jfc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
            jfc.setDialogTitle("请选择文件");
            jfc.setFileFilter(filter);
            int ret = jfc.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION){
                return;
            }
            String filename = jfc.getSelectedFile().getAbsolutePath();
            try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                String line = null;
                jta.setText("");
                while((line = in.readLine())!=null){
                    jta.setText(jta.getText() +line+ System.getProperty("line.separator"));
                }
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getActionCommand().equals("save")){
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("另存为");
            /*
             *按默认的方式显示
             */
            jfc.showSaveDialog(null);
            jfc.setVisible(true);
            /*
             *得到用户希望保存的路劲
             */
            String file = jfc.getSelectedFile().getAbsolutePath();
            String str = jta.getText();
            try {
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter ow = new OutputStreamWriter(fos);
                ow.write(str);
                ow.flush();
                ow.close();
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("exit")){
            System.exit(0);
        }
    }

}
