package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dto.CustomerDTO;

public class CustomerLoginFrame extends JFrame {

	private JLabel loginBtn;
	private JLabel registerBtn;
	private JLabel quitBtn;
	private JTextField phoneText;
	private boolean loginCheck;
	private BackgroundPanel backgroundPanel;
	private SignInFrame signInFrame;
	private CustomerDAO userDao;
	private LoginSelectFrame mainLoginF; // TODO
	private CustomerDTO customerDTO;

	public CustomerLoginFrame(LoginSelectFrame mainLoginF) {
		this.mainLoginF = mainLoginF;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JLabel(new ImageIcon("img/loginBtn.png"));
		registerBtn = new JLabel(new ImageIcon("img/signInBtn.png"));
		quitBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
		phoneText = new JTextField();

		signInFrame = new SignInFrame();
		userDao = new CustomerDAO();
	}

	private void setInitLayout() {
		setTitle("Tabling - 식당 예약 테이블[고객]");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(false); // TODO
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		loginBtn.setBounds(35, 390, 314, 46);
		backgroundPanel.add(loginBtn);

		registerBtn.setBounds(35, 450, 314, 46);
		backgroundPanel.add(registerBtn);

		quitBtn.setBounds(10, 10, 30, 50);
		backgroundPanel.add(quitBtn);

		phoneText.setBounds(90, 260, 100, 30);
		phoneText.setSize(220, 25);
		phoneText.setBorder(null);
		phoneText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(phoneText);

	}

	private void initListener() {

		registerBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				signInFrame.setVisible(true);
			}
		});

		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (!phoneText.getText().equals("")) {
					if ((customerDTO = userDao.authenticatePhone(phoneText.getText())) != null) {
						JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
						setVisible(false);
						new CustomerMainMenuFrame(customerDTO);
					} else {
						JOptionPane.showMessageDialog(null, "존재하지 않는 유저 정보입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				mainLoginF.setVisible(true);
			}
		});

		// 전화번호 8자 이상 입력불가 & 숫자만 입력 가능
		phoneText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (phoneText.getText().length() > 10) {
					e.consume();
				} else if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					e.consume();
				}
			}

		});

	}
	

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/loginBg.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}

	}

//	public static void main(String[] args) {
//		new CustomerLoginFrame();
//	}

}
