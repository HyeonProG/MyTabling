package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.RestaurantDAO;
import tabling.dto.RestaurantDTO;

public class RestaurantLoginFrame extends JFrame {

	private JLabel loginBtn;
	private JTextField nameText;
	private boolean loginCheck;
	private JLabel backBtn;
	private BackgroundPanel backgroundPanel;
	
	// TODO - 임시로 클래스 설계
	private RestaurantDAO restaurantDao;
	private RestaurantDTO restaurantDto;
	private int restaurantId;

	public RestaurantLoginFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JLabel(new ImageIcon("img/loginBtn.png"));
		nameText = new JTextField();
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
	
		restaurantDao = new RestaurantDAO();
		restaurantDto = new RestaurantDTO();
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

		loginBtn.setBounds(35, 420, 314, 46);
		loginBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(loginBtn);

		backBtn.setBounds(10, 10, 30, 50);
		backBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(backBtn);

		nameText.setBounds(90, 285, 100, 30);
		nameText.setSize(220, 25);
		nameText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		nameText.setBorder(null);
		backgroundPanel.add(nameText);

	}

	private void initListener() {
		loginBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (!nameText.getText().equals("")) {
					restaurantId = Integer.parseInt(nameText.getText());
					if((restaurantDto = restaurantDao.authenticateOwnerId(restaurantId)) != null) {
						JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
						setVisible(false);
					}
				}
				
			}
			
		});
		
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new LoginSelectFrame();
				setVisible(false);
			}
		});

	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/restaurantLoginBg.jpg").getImage();
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
