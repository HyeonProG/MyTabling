package ver01;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import lombok.Data;

@Data
//swing 회원가입 화면
public class SignIn extends JFrame {

	private JLabel Bg;
	private JTextField userName;
	private Choice localTel;
	private JTextField userPhone;
	private Choice myLocation;
	private JButton duplicateCheck;
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
		userName.setBounds(220, 100, 160, 25);

		localTel = new Choice();
		localTel.addItem("010");
		localTel.addItem("011");
		localTel.addItem("016");
		localTel.addItem("017");
		localTel.addItem("018");
		localTel.addItem("019");
		localTel.setBounds(190, 190, 50, 25);

		userPhone = new JTextField();
		userPhone.setBounds(245, 190, 140, 25);

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
		myLocation.setSize(160, 25);
		myLocation.setLocation(220, 280);

		duplicateCheck = new JButton("중복확인");
		duplicateCheck.setBounds(390, 190, 90, 25);

		signInBtn = new JButton("가입");
		signInBtn.setBounds(240, 350, 120, 30);
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

		signInBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (userName.getText().length() > 50) {
					JOptionPane.showMessageDialog(null, "닉네임은 50자까지만 기입 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else if (userPhone.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전화번호는 비워둘 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				System.out.println(userPhone.getText());
			}
		});

		duplicateCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (userPhone.getText().length() == 8) {
					signInBtn.setEnabled(true);
				} else {
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
				}
				if (6 < userPhone.getText().length()) {
					duplicateCheck.setEnabled(true);
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
