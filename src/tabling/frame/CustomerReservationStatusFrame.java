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
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;

public class CustomerReservationStatusFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JPanel reservationPanel;
	private JButton backBtn;
	private JButton cancelBtn;
	private CustomerDTO customerDTO;
	private JLabel customerId;
	private JLabel phone;
	private JLabel customerState;
	private JLabel locationId;
	private JLabel reservationState;
	private JLabel restaurantId;

	public CustomerReservationStatusFrame(CustomerDTO customerDTO) {
		this.customerDTO=customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		setTitle("예약 현황");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		backgroundPanel = new BackgroundPanel();
		reservationPanel = new JPanel();
		backBtn = new JButton("뒤로가기");
		cancelBtn = new JButton("예약 취소");
		customerId=new JLabel(String.valueOf(customerDTO.getCustomerId()));
		phone=new JLabel(customerDTO.getPhone());
		customerState=new JLabel(customerDTO.getState());
		locationId=new JLabel(String.valueOf(customerDTO.getLocationId()));
//		reservationState=new JTextField(reservationDTO.getReservationState());
//		restaurantId=new JTextField(reservationDTO.getRestaurantId());
	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);
		
		reservationPanel.setBounds(40, 200, 300, 300);
		reservationPanel.setSize(410, 300);
		backgroundPanel.add(reservationPanel);
		
		backBtn.setBounds(350, 20, 50, 50);
		backBtn.setSize(100, 50);
		backgroundPanel.add(backBtn);
		
		cancelBtn.setBounds(350, 520, 50, 50);
		cancelBtn.setSize(100, 50);
		backgroundPanel.add(cancelBtn);

		customerId.setBounds(30, 260, 100, 30);
		customerId.setBorder(null);
		customerId.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(customerId);
		
		phone.setBounds(90, 260, 100, 30);
		phone.setBorder(null);
		phone.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(phone);
		
		customerState.setBounds(140, 260, 100, 30);
		customerState.setBorder(null);
		customerState.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(customerState);
		
		locationId.setBounds(30, 400, 100, 30);
		locationId.setBorder(null);
		locationId.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(locationId);
//		
//		reservationState.setBounds(90, 300, 100, 30);
//		reservationState.setBorder(null);
//		reservationState.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
//		reservationPanel.add(reservationState);
//		
//		restaurantId.setBounds(140, 400, 100, 30);
//		restaurantId.setBorder(null);
//		restaurantId.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
//		reservationPanel.add(restaurantId);
		
	}

	private void initListener() {
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerMainMenuFrame();
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
		new CustomerReservationStatusFrame(new CustomerDAO().authenticatePhone("01011112222"));
	}

}
