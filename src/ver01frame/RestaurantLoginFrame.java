package ver01frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RestaurantLoginFrame extends JFrame {

	private JButton loginBtn;
	private JTextField nameText;
	private JLabel nameLabel;
	private boolean loginCheck;
	private JButton backBtn;
	private BackgroundPanel backgroundPanel;

	public RestaurantLoginFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JButton("로그인");
		nameLabel = new JLabel("식당ID");
		nameText = new JTextField();
		backBtn = new JButton("뒤로가기");
	}

	private void setInitLayout() {
		setTitle("Tabling - 식당 예약 테이블[점주]");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		loginBtn.setBounds(260, 265, 100, 50);
		loginBtn.setSize(90, 25);
		loginBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(loginBtn);

		backBtn.setBounds(160, 300, 100, 50);
		backBtn.setSize(90, 30);
		backBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(backBtn);

		nameLabel.setBounds(30, 210, 100, 50);
		nameLabel.setSize(70, 70);
		nameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(nameLabel);

		nameText.setBounds(30, 265, 100, 30);
		nameText.setSize(220, 25);
		nameText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(nameText);

	}

	private void initListener() {
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new MainLoginFrame();
				setVisible(false);
			}
		});
	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
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
		new RestaurantLoginFrame();
	}

}
