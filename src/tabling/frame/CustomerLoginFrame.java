package tabling.frame;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tabling.dto.CustomerDTO;
import tabling.request.CustomerRequest;
import tabling.util.MyMouseListener;

// 고객 로그인 화면
public class CustomerLoginFrame extends JFrame implements MyMouseListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel loginBtn;
	private JLabel registerBtn;
	private JLabel backBtn;
	private JTextField phoneText;

	// DTO
	private CustomerDTO customerDTO;

	// request
	private CustomerRequest request;

	public CustomerLoginFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel("img/loginBg.jpg");

		// 컴포넌트 초기화
		loginBtn = new JLabel(new ImageIcon("img/loginBtn.png"));
		registerBtn = new JLabel(new ImageIcon("img/signInBtn.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
		phoneText = new JTextField();

		// request 초기화
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

		// 컴포넌트 세팅
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

		// 회원가입 버튼
		if (e.getSource() == registerBtn) {
			new SignInFrame();
			// 로그인 버튼
		} else if (e.getSource() == loginBtn) {
			login();
			// 뒤로가기 버튼
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
				// 동일한 전화번호로 이미 접속중인지 확인하는 메서드 false 반환시 접속중
				String loginCheck = request.loginCustomer(phoneText.getText());
				if (loginCheck.equals("false")) {
					JOptionPane.showMessageDialog(null, "이미 접속 중인 사용자 입니다.", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 서버에 로그인 요청
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
}
