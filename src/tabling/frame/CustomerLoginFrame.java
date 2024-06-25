package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dto.CustomerDTO;
import tabling.request.CustomerRequest;
import tabling.util.MyMouseListener;

// 고객 로그인 화면
public class CustomerLoginFrame extends JFrame implements MyMouseListener {

	private JLabel loginBtn;
	private JLabel registerBtn;
	private JLabel backBtn;
	private JTextField phoneText;
	private BackgroundPanel backgroundPanel;
	private CustomerRequest request;
	private CustomerDTO customerDTO;

	public CustomerLoginFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JLabel(new ImageIcon("img/loginBtn.png"));
		registerBtn = new JLabel(new ImageIcon("img/signInBtn.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
		phoneText = new JTextField();

		request = new CustomerRequest();
	}

	private void setInitLayout() {
		setTitle("Tabling - 식당 예약 테이블[고객]");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		loginBtn.setBounds(35, 390, 314, 46);
		backgroundPanel.add(loginBtn);

		registerBtn.setBounds(35, 450, 314, 46);
		backgroundPanel.add(registerBtn);

		backBtn.setBounds(10, 10, 30, 50);
		backgroundPanel.add(backBtn);

		phoneText.setBounds(90, 260, 100, 30);
		phoneText.setSize(220, 25);
		phoneText.setBorder(null);
		phoneText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(phoneText);

		setVisible(true);
	}

	private void addEventListener() {

		registerBtn.addMouseListener(this);
		loginBtn.addMouseListener(this);
		backBtn.addMouseListener(this);

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

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
	}
	
	// 마우스 입력 이벤트 처리
	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getSource() == registerBtn) {
			new SignInFrame();
		} else if (e.getSource() == loginBtn) {
			login();
		} else if (e.getSource() == backBtn) {
			new LoginSelectFrame();
			setVisible(false);
			dispose();
		}
	}

	// 로그인 버튼 클릭시 & 엔터버튼 입력시 호출
	private void login() {
		if (!phoneText.getText().equals("")) {
			try {
				String loginCheck = request.loginCustomer(phoneText.getText());
				if (loginCheck.equals("false")) {
					JOptionPane.showMessageDialog(null, "이미 접속 중인 사용자 입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if ((customerDTO = request.getCustomerByPhone(phoneText.getText())) != null) {
					JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
					new CustomerMainMenuFrame(customerDTO);
					setVisible(false);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "존재하지 않는 유저 정보입니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 패널 내부 클래스
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

}
