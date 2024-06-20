package tabling.frame;

import java.awt.Choice;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import lombok.Data;
import tabling.dao.CustomerDAO;

@Data
//swing 회원가입 화면
public class SignInFrame extends JFrame {

	private JLabel Bg;
	private JTextField userName;
	private Choice localTel;
	private JTextField userPhone;
	private Choice myLocation;
	private JLabel duplicateCheck;
	private JLabel signInBtn;
	private String[] localArray;
	private String[] localTelArray;
	private boolean canCheck;
	private boolean canLogin;
	CustomerDAO signin = new CustomerDAO();

	public SignInFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("회원가입");
		setSize(400, 600);
		setResizable(false);

		Bg = new JLabel(new ImageIcon("img/signInBg.jpg"));

		setContentPane(Bg);

		userName = new JTextField(20);
		userName.setBounds(90, 165, 200, 25);

		localTel = new Choice();
		localTelArray = new String[] { "010", "011", "016", "017", "018", "019" };
		for (int i = 0; i < localTelArray.length; i++) {
			localTel.add(localTelArray[i]);
		}
		localTel.setBounds(90, 270, 50, 25);

		userPhone = new JTextField();
		userPhone.setBounds(150, 270, 140, 25);

		myLocation = new Choice();
		localArray = new String[] { "강서구", "사하구", "사상구", "북구", "서구", "중구", "동구", "부산진구", "영도구", "남구", "동래구", "연제구", "수영구", "금정구", "해운대구", "기장군" };
		for (int i = 0; i < localArray.length; i++) {
			myLocation.add(localArray[i]);
		}
		myLocation.setBounds(90, 380, 200, 25);

		canCheck = false;
		canLogin = false;

		duplicateCheck = new JLabel(new ImageIcon("img/duplicateBtn.png"));
		duplicateCheck.setBounds(200, 300, 90, 25);

		signInBtn = new JLabel(new ImageIcon("img/signInBtn.png"));
		signInBtn.setBounds(35, 480, 314, 46);
	}

	private void setInitLayout() {
		setLayout(null);
		setVisible(false);
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

				if (canLogin == true) {
					if (userName.getText().length() > 50) {
						JOptionPane.showMessageDialog(null, "닉네임은 50자까지만 기입 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
					} else if (userPhone.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "전화번호는 비워둘 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					} else {
						try {
							signin.addUser(userName.getText(), localTel.getSelectedItem() + userPhone.getText(), myLocation.getSelectedIndex() + 1);
							JOptionPane.showMessageDialog(null, "회원가입 성공!", "경고", JOptionPane.WARNING_MESSAGE);
							setVisible(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		duplicateCheck.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (userPhone.getText().length() == 8 && canCheck == true) {

					if (signin.authenticatePhone(localTel.getSelectedItem() + userPhone.getText()) != null) {
						JOptionPane.showMessageDialog(null, "중복되는 번호입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "가입 가능한 전화번호입니다.", "경고", JOptionPane.WARNING_MESSAGE);
						signInBtn.setEnabled(true);
						canLogin = true;
					}

				} else if (canCheck == true) {
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
				} else if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					e.consume();
				}
			}

		});

	}

} // end of class
