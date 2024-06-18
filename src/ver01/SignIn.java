package ver01;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

//swing 회원가입 화면
public class SignIn extends JFrame {

	private JLabel Bg;
	private JTextField userName;
	private JTextField userPhone;
	private JTextField myLocation;
	private JButton signInBtn;

	public SignIn() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("회원가입");
		setSize(600, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Bg = new JLabel(new ImageIcon("img/signInBg.jpg"));
		
		setContentPane(Bg);
		
		userName = new JTextField(20);
		userName.setSize(160,25);
		userName.setLocation(220,110);
		
		userPhone = new JTextField(20);
		userPhone.setSize(160,25);
		userPhone.setLocation(220,200);
		
		myLocation = new JTextField(20);
		myLocation.setSize(160,25);
		myLocation.setLocation(220,300);
		
		signInBtn = new JButton("가입");
		signInBtn.setSize(120,30);
		signInBtn.setLocation(240,370);
	}

	private void setInitLayout() {
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		
		add(userName);
		add(userPhone);
		add(myLocation);
		add(signInBtn);
		
	}

	private void addEventListener() {

	}

	// test
	public static void main(String[] args) {
		new SignIn();
	}

} // end of class
