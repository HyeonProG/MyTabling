package ver01;

import java.awt.Choice;

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
	private Choice myLocation;
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
		userName.setLocation(220,100);
		
		userPhone = new JTextField(20);
		userPhone.setSize(160,25);
		userPhone.setLocation(220,190);
		
		myLocation = new Choice();
		myLocation.addItem("강서구");
		myLocation.addItem("사하구");
		myLocation.addItem("사상구");
		myLocation.addItem("북구");
		myLocation.addItem("서구");
		myLocation.addItem("중구");
		myLocation.addItem("동구");
		myLocation.addItem("부산진구");
		myLocation.addItem("영도구");
		myLocation.addItem("남구");
		myLocation.addItem("동래구");
		myLocation.addItem("연제구");
		myLocation.addItem("수영구");
		myLocation.addItem("금정구");
		myLocation.addItem("해운대구");
		myLocation.addItem("기장군");
		myLocation.setSize(160,25);
		myLocation.setLocation(220,280);
		
		signInBtn = new JButton("가입");
		signInBtn.setSize(120,30);
		signInBtn.setLocation(240,350);
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
