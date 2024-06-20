package tabling.frame;

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

public class LoginSelectFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JLabel customerBtn;
	private JLabel restaurantBtn;
	private LoginSelectFrame mainLoginFrame;

	public LoginSelectFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();

		mainLoginFrame = this;
		setTitle("테이블링");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);

		customerBtn = new JLabel(new ImageIcon("img/customerStartBtn.png"));
		restaurantBtn = new JLabel(new ImageIcon("img/ownerStartBtn.png"));
		setVisible(true);
	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		customerBtn.setBounds(40, 120, 50, 50);
		customerBtn.setSize(320, 200);
		customerBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(customerBtn);

		restaurantBtn.setBounds(40, 310, 50, 50);
		restaurantBtn.setSize(320, 200);
		restaurantBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(restaurantBtn);
	}

	private void initListener() {
		customerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				new CustomerLoginFrame();
				new CustomerLoginFrame(mainLoginFrame).setVisible(true);
				;
			}
		});

		restaurantBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new RestaurantLoginFrame();
				setVisible(false);
			}
		});

	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/mainBg.jpg").getImage();
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
		new LoginSelectFrame();
	}

}
