package tabling.frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;

public class CustomerMainMenuFrame extends JFrame {

	private BackgroundPanel backgroundPanel;

	private JLabel categoryBtn;
	private JLabel locationBtn;
	private JLabel reservationBtn;
	private JLabel userInfoBtn;

	private CustomerDTO customerDTO;
	public CustomerMainMenuFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel();
		reservationBtn = new JLabel(new ImageIcon("img/reservationStateBtn.png"));
		categoryBtn = new JLabel(new ImageIcon("img/categoryBtn.png"));
		locationBtn = new JLabel(new ImageIcon("img/locationBtn.png"));
		userInfoBtn = new JLabel(new ImageIcon("img/gearSolid.png"));
	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		reservationBtn.setBounds(50, 330, 80, 120);
//		reservationBtn.setSize(70, 100);
		backgroundPanel.add(reservationBtn);

		categoryBtn.setBounds(210, 335, 70, 120);
//		categoryBtn.setSize(330, 130);
		backgroundPanel.add(categoryBtn);

		locationBtn.setBounds(360, 335, 70, 120);
//		locationBtn.setSize(330, 130);
		backgroundPanel.add(locationBtn);

		userInfoBtn.setBounds(430, 20, 30, 30);
		backgroundPanel.add(userInfoBtn);

	}

	private void initListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerReservationStatusFrame(customerDTO);
				setVisible(false);

			}
		});
		categoryBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CategoryFrame(customerDTO);
				setVisible(false);
			}
		});
		locationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new LocationFrame();
				setVisible(false);
			}
		});
		
		userInfoBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				new EditCustomerInfo(customerDTO);
				setVisible(false);
			}
		});
	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/customerMainMenuBg.jpg").getImage();
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
		// new MainMenuFrame();
	}

}
