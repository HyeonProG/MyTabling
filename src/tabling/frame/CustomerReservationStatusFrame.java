package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dao.CustomerReservationDAO;
import tabling.dao.MenuDAO;
import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantDAO;
import tabling.dao.RestaurantReservationDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;

public class CustomerReservationStatusFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private JPanel reservationPanel;
	private JButton backBtn;
	private JButton cancelBtn;
	private JButton detailBtn;
	private CustomerDTO customerDTO;
	private JLabel customerId;
	private JLabel phone;
	private JLabel customerState;
	private JLabel locationId;
	private JLabel reservationState;
	private JLabel restaurantId;
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
		restaurantDAO =new RestaurantDAO();
		
		try {
			List<RestaurantDTO> list=restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
			reservationDTO = reservationDAO.getReservationByCustomer(customerDTO.getCustomerId());
			
			int count = reservationDAO.checkReservation
					(reservationDTO.getRestaurantId(),reservationDTO.getReservationId());
			String name=list.get(0).getRestaurantName();
			
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
			detailBtn = new JButton("상세 이동");
			customerId = new JLabel(String.valueOf(count));
			restaurantId=new JLabel(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

		customerId.setBounds(150, 50, 100, 30);
		customerId.setBorder(null);
		customerId.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(customerId);
		
		restaurantId.setBounds(150, 100, 100, 30);
		restaurantId.setBorder(null);
		restaurantId.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(restaurantId);
		
		detailBtn.setBounds(250, 100, 100, 30);
		detailBtn.setBorder(null);
		detailBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		reservationPanel.add(detailBtn);

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
					customerReservationDAO.candel(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
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
				CustomerReservationDAO customerReservationDAO = new CustomerReservationDAO();
				try {
					List<RestaurantDTO> list=restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
					reservationDTO = reservationDAO.getReservationByCustomer(customerDTO.getCustomerId());
					RestaurantDTO dto=list.get(0);
					MenuDAO dao= new MenuDAO();
					new RestaurantFrame(dto, dao.getMenuById(dto.getCategoryId()));
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
