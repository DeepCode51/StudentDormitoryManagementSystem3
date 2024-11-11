//登录界面 以及按钮代码
package student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class land extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	//不手动修改的话 java默认serialVersionUID属性就是1L.
	static SQL sq;
	JFrame frame = new JFrame("用户登陆");
	JPanel panel = (JPanel)frame.getContentPane();
	JPanel panel2 = new JPanel();
	JButton buttonLand = new JButton("登陆");
	//JButton就是按钮，后面同理，这里就是 定义登陆按钮
	ImageIcon img1 = new ImageIcon("D://1.jpg");
	//ImageIcon是图标，括号中的是图标图片的地址，可以自己更改想要的图片地址为括号里的，也可以换地址。
	JLabel background = new JLabel(img1);
	JLabel label1 = new JLabel("账号:");
	JLabel label2 = new JLabel("密码:");
	JTextField textName = new JTextField(10);
	JPasswordField textPassword = new JPasswordField(20);
//JTextField与JPasswordField都是Java swing的组件，后者是前者子类，后者显示*（例如输入123 出来是***）
	public land() {
	frame.setSize(500,300);//设置组件大小，setsize（a,b)，a是长，b是宽
	frame.setVisible(true);//也是组件，表示可见性，true即可见
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//setDefaultCloseOperation方法用于设置窗口默认关闭操作
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);//窗口是否可以调节大小，false 不能
	//背景图
	background.setBounds(0, 0,img1.getIconWidth(),img1.getIconHeight());
    frame.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
	//内容窗板
    panel.setOpaque(false);
	panel.setLayout(null);
	panel.add(buttonLand);
	buttonLand.setBounds(360, 100, 120, 35);
	//setBounds(int x,y,width,height) x,y是起始坐标，其余是宽度和高度
	//setBounds用来设置变量的上下姐
	buttonLand.addActionListener(this);
	panel.add(label1);
	label1.setBounds(90, 50, 70, 60);
	label1.setForeground(Color.CYAN);
	panel.add(label2);
	label2.setBounds(90, 120, 70, 60);
	label2.setForeground(Color.CYAN);
	panel.add(textName);
	textName.setBounds(140, 65, 180, 30);
	panel.add(textPassword);
	textPassword.setBounds(140, 130, 180, 30);
}
	@Override
	public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
		if((JButton)e.getSource()== buttonLand)
		{

		String name = textName.getText().trim();
		String password = String.valueOf(textPassword.getPassword()).trim();
	    int num = sq.landing(name, password);
	    if(num==1)
	    {
	    	JOptionPane.showMessageDialog(frame, "欢迎进入学生管理系统!","提示：",JOptionPane.PLAIN_MESSAGE);
	    	system system = new system();
	    	frame.dispose();
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(frame, "账号或者密码错误！","提示：",JOptionPane.ERROR_MESSAGE);
	    }
		}
	}
	public static void main(String[] args) {
	 new land();
	 sq = new SQL();
	}
}
