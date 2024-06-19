package ver01frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	private JLabel loginBtn;
	private JLabel registerBtn;
	private JTextField nameText;
	private JTextField phoneText;
//	private JLabel nameLabel;
//	private JLabel phoneLabel;
	private boolean loginCheck;
	private JLabel quitBtn;
	private BackgroundPanel backgroundPanel;
	
	// TODO
	private SignInFrame signInFrame; 

	public LoginFrame() {
		initData();
		setInitLayout();
		initListener();
		signInFrame = new SignInFrame();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JLabel(); // TODO
		registerBtn = new JLabel(); // TODO
//		nameLabel = new JLabel("닉네임");
		nameText = new JTextField();
//		phoneLabel = new JLabel("전화번호");
		phoneText = new JTextField();
		quitBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
	}

	private void setInitLayout() {
		setTitle("Tabling - 식당 예약 테이블");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		loginBtn.setBounds(270, 450, 100, 50);
		loginBtn.setSize(90, 85);
		loginBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(loginBtn);

		registerBtn.setBounds(30, 450, 100, 50);
		registerBtn.setSize(90, 30);
		registerBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(registerBtn);

		quitBtn.setBounds(160, 450, 24, 34);
//		quitBtn.setSize(90, 30);
//		quitBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(quitBtn);

//		nameLabel.setBounds(30, 170, 100, 50);
//		nameLabel.setSize(70, 70);
//		nameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
//		backgroundPanel.add(nameLabel);

//		phoneLabel.setBounds(30, 230, 100, 50);
//		phoneLabel.setSize(70, 70);
//		phoneLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
//		backgroundPanel.add(phoneLabel);

		nameText.setBounds(90, 230, 100, 30);
		nameText.setSize(220, 25);
		nameText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(nameText);

		phoneText.setBounds(90, 300, 100, 30);
		phoneText.setSize(220, 25);
		phoneText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(phoneText);

	}

	private void initListener() {
		
//		registerBtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				signInFrame.setVisible(true);
//			}
//		});
		
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

	public static void main(String[] args) {
		new LoginFrame();
	}

}
