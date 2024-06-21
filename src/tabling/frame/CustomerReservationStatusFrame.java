package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dao.CustomerReservationDAO;
import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantDAO;
import tabling.dao.RestaurantReservationDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;

public class CustomerReservationStatusFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JLabel backBtn;
	private JLabel cancelBtn;
	 private JLabel detailBtn;
	private CustomerDTO customerDTO;
	private JLabel customerQueue;
	private JLabel phone;
	private JLabel customerState;
	private JLabel locationId;
	private JLabel reservationState;
	private JLabel restaurantId;
	private JLabel restaurantName;
	private JLabel customerName;
	private ReservationDTO reservationDTO;
	private ReservationDAO reservationDAO;  
	private RestaurantDAO restaurantDAO;

	public CustomerReservationStatusFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		
		
		
		reservationDAO = new ReservationDAO();
		restaurantDAO= new RestaurantDAO();
		try {
			reservationDTO = reservationDAO.getReservationByCustomer(customerDTO.getCustomerId());
			int count = reservationDAO.checkReservation(reservationDTO.getRestaurantId(),
					reservationDTO.getReservationId());
			List<RestaurantDTO> list = restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
            RestaurantDTO dto=list.get(0);


			backgroundPanel = new BackgroundPanel();
			backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
			cancelBtn = new JLabel(new ImageIcon("img/그룹 25.png"));
			detailBtn = new JLabel(new ImageIcon("img/그룹 24.png"));
			customerQueue = new JLabel(String.valueOf(count));
			restaurantName = new JLabel(dto.getRestaurantName());
			customerName = new JLabel(customerDTO.getCustomerName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setInitLayout() {
		setTitle("예약 현황");
		setSize(500, 700);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);


		backBtn.setBounds(0, 10, 70, 70);
		backgroundPanel.add(backBtn);
		
		customerName.setBounds(42, 73, 100, 70);
		customerName.setFont(new Font("Noto Sans KR", Font.BOLD, 18));
		customerName.setSize(100,100);
		backgroundPanel.add(customerName);

		cancelBtn.setBounds(340, 480, 120, 60);
		backgroundPanel.add(cancelBtn);

		customerQueue.setBounds(240, 180, 190, 150);
		customerQueue.setFont(new Font("Noto Sans KR", Font.BOLD, 60));
		backgroundPanel.add(customerQueue);
		
		restaurantName.setBounds(140, 395, 120, 50);
		restaurantName.setFont(new Font("Noto Sans KR", Font.BOLD, 18));
		backgroundPanel.add(restaurantName);
		
		detailBtn.setBounds(340, 390, 130, 60);
		detailBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		backgroundPanel.add(detailBtn);

	}

	private void initListener() {
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				new CustomerMainMenuFrame(customerDTO);
				setVisible(false);
			}
		});

		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				reservationDAO = new ReservationDAO();
				CustomerReservationDAO customerReservationDAO = new CustomerReservationDAO();
				try {
					reservationDTO = reservationDAO.getReservationByCustomer(customerDTO.getCustomerId());
					System.out.println(reservationDTO.getCustomerId()+"번 ID "+ reservationDTO.getRestaurantId()+"번 음식점 취소완료");
					customerReservationDAO.cancel(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
					JOptionPane.showMessageDialog(null, "예약 취소 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}


			}
		});
		
		detailBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				reservationDAO = new ReservationDAO();
				restaurantDAO= new RestaurantDAO();
				try {
					reservationDTO = reservationDAO.getReservationByCustomer(customerDTO.getCustomerId());
					List<RestaurantDTO> list = restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
		            RestaurantDTO dto=list.get(0);
		            new RestaurantFrame(customerDTO, dto);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		
	}
	


	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/예약현황.jpg").getImage();
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