package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantReservationDAO;
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
	private ReservationDTO reservationDTO;
	private ReservationDAO reservationDAO;
	int count;

	public CustomerReservationStatusFrame(CustomerDTO customerDTO) {
		this.customerDTO=customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		reservationDAO=new ReservationDAO();
		reservationDTO=new ReservationDTO();
		int reservationId= reservationDTO.getReservationId();
		if(reservationId==customerDTO.getCustomerId()) {
				try {
					count=reservationDAO.checkReservation(reservationDTO.getRestaurantId(),reservationId );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
			
		
		
		
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
		customerId=new JLabel(String.valueOf(count));
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
