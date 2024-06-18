package ver01;

import java.awt.Choice;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import lombok.Data;

@Data
//swing 회원가입 화면
public class SignIn extends JFrame {

	private JLabel Bg;
	private JTextField userName;
	private Choice localTel;
	private JTextField userPhone;
	private Choice myLocation;
//	private JButton duplicateCheck;
//	private JButton signInBtn;
	private JLabel duplicateCheck;
	private JLabel signInBtn;
	
	private boolean canCheck = false;

	public SignIn() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("회원가입");
		setSize(400, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Bg = new JLabel(new ImageIcon("img/signInBg.jpg"));

		setContentPane(Bg);

		userName = new JTextField(20);
		userName.setBounds(90, 165, 200, 25);

		localTel = new Choice();
		localTel.addItem("010");
		localTel.addItem("011");
		localTel.addItem("016");
		localTel.addItem("017");
		localTel.addItem("018");
		localTel.addItem("019");
		localTel.setBounds(90, 270, 50, 25);

		userPhone = new JTextField();
		userPhone.setBounds(150, 270, 140, 25);

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
		myLocation.setBounds(90, 380, 200, 25);

//		duplicateCheck = new JButton("중복확인");
//		duplicateCheck.setBounds(200, 190, 90, 25);
//		signInBtn = new JButton("가입");
//		signInBtn.setBounds(200, 370, 120, 30);

		duplicateCheck = new JLabel(new ImageIcon("img/duplicateBtn.jpg"));
		duplicateCheck.setBounds(200, 300, 90, 25);
		
		signInBtn = new JLabel(new ImageIcon("img/signInBtn.jpg"));
		signInBtn.setBounds(35, 480, 314, 46);
	}

	private void setInitLayout() {
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);

		add(userName);
		add(localTel);
		add(userPhone);
		add(myLocation);
		add(duplicateCheck);
		add(signInBtn);

		duplicateCheck.setEnabled(false);
		signInBtn.setEnabled(false);

	}

	private void addEventListener() {

		signInBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (userName.getText().length() > 50) {
					JOptionPane.showMessageDialog(null, "닉네임은 50자까지만 기입 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else if (userPhone.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전화번호는 비워둘 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					System.out.println(userName.getText());
					System.out.println(userPhone.getText());
					// TODO - 선택한 지역 받아오기
					System.out.println(myLocation.getSelectedItem());
				}
			}
		});

		duplicateCheck.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (userPhone.getText().length() == 8 && canCheck == true) {
					JOptionPane.showMessageDialog(null, "가입 가능한 전화번호입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					signInBtn.setEnabled(true);
				} else if(canCheck == true){
					JOptionPane.showMessageDialog(null, "전화번호가 8자리가 아닙니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		// 전화번호 8자 이상 입력불가
		userPhone.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				System.out.println(userPhone.getText().length());

				if (userPhone.getText().length() < 9) {
					duplicateCheck.setEnabled(false);
					canCheck = false;
				}
				if (6 < userPhone.getText().length()) {
					duplicateCheck.setEnabled(true);
					canCheck = true;
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (userPhone.getText().length() > 7) {
					e.consume();
				}
			}

		});

	}

	// test
	public static void main(String[] args) {
		new SignIn();
	}

} // end of class
