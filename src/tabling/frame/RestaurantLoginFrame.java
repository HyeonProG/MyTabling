package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tabling.dto.RestaurantDTO;
import tabling.request.RestaurantRequest;

public class RestaurantLoginFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JTextField resIdText;
	private JPasswordField resPwText;
	private JLabel loginBtn;
	private JLabel backBtn;
	private RestaurantRequest restaurantRequest;
	private RestaurantDTO restDTO;
	private int restaurantId;

	public RestaurantLoginFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		loginBtn = new JLabel(new ImageIcon("img/loginBtn.png"));
		resIdText = new JTextField();
		resPwText = new JPasswordField();
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));

		restaurantRequest = new RestaurantRequest();
		restDTO = new RestaurantDTO();
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

		loginBtn.setBounds(35, 425, 314, 46);
		loginBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(loginBtn);

		backBtn.setBounds(10, 10, 30, 50);
		backBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(backBtn);

		resIdText.setBounds(85, 240, 100, 30);
		resIdText.setSize(220, 25);
		resIdText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		resIdText.setBorder(null);
		backgroundPanel.add(resIdText);

		resPwText.setBounds(85, 330, 100, 30);
		resPwText.setSize(220, 25);
		resPwText.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		resPwText.setBorder(null);
		backgroundPanel.add(resPwText);
	}

	private void addEventListener() {
		loginBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				char[] pwChars = resPwText.getPassword();
				String pw = new String(pwChars);
				try {
					if (!resIdText.getText().equals("") || !pw.equals("")) {
						restaurantId = Integer.parseInt(resIdText.getText());
						if ((restDTO = restaurantRequest.getRestaurantByRestaurantId(restaurantId)) != null && pw.equals("1111")) {
							JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
							setVisible(false);
							dispose();
							new RestaurantMainMenuFrame(restDTO);
						} else if ((restDTO = restaurantRequest.getRestaurantByRestaurantId(restaurantId)) == null) {
							JOptionPane.showMessageDialog(null, "아이디가 올바르지 않습니다.", "실패", JOptionPane.WARNING_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호가 올바르지 않습니다.", "실패", JOptionPane.WARNING_MESSAGE);
						}
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 ID입니다.", "실패", JOptionPane.WARNING_MESSAGE);
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

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char[] pwChars = resPwText.getPassword();
				String pw = new String(pwChars);
				try {
					if (!resIdText.getText().equals("") || !pw.equals("")) {
						restaurantId = Integer.parseInt(resIdText.getText());
						if ((restDTO = restaurantRequest.getRestaurantByRestaurantId(restaurantId)) != null && pw.equals("1111")) {
							JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
							setVisible(false);
							dispose();
							new RestaurantMainMenuFrame(restDTO);
						} else if ((restDTO = restaurantRequest.getRestaurantByRestaurantId(restaurantId)) == null) {
							JOptionPane.showMessageDialog(null, "아이디가 올바르지 않습니다.", "실패", JOptionPane.WARNING_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "비밀번호가 올바르지 않습니다.", "실패", JOptionPane.WARNING_MESSAGE);
						}
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 ID입니다.", "실패", JOptionPane.WARNING_MESSAGE);
				}
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

}
