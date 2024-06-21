package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import tabling.dao.CustomerReservationDAO;
import tabling.dao.MenuDAO;
import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;

public class ReservationFrame extends JFrame {

	RestaurantDTO restaurantDTO;
	ReservationDTO reservationDTO;
	CustomerReservationDAO customerReservationDAO;
	ReservationDAO reservationDAO;
	CustomerDTO customerDTO;

	private JButton reservationBtn;
	private JButton backBtn;
	private JTextArea reservationStatus;
	private JLabel restaurantNameLabel;

	public ReservationFrame(CustomerDTO customerDTO, RestaurantDTO restaurantDTO) {
		this.customerDTO = customerDTO;
		this.restaurantDTO = restaurantDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		setTitle("예약하기");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		
		customerReservationDAO = new CustomerReservationDAO();
		reservationDAO = new ReservationDAO();

		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());

		reservationStatus = new JTextArea();

		backBtn = new JButton("뒤로가기");

		reservationBtn = new JButton("예약하기");
		setVisible(true);
	}

	private void setInitLayout() {
		restaurantNameLabel.setBounds(65, 45, 150, 50);
		restaurantNameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		restaurantNameLabel.setOpaque(true);
		restaurantNameLabel.setBackground(Color.WHITE);
		add(restaurantNameLabel);

		reservationStatus.setBounds(65, 100, 350, 350);
		reservationStatus.setBackground(Color.WHITE);
		reservationStatus.setLineWrap(true);
		try {
			reservationStatus.append("현재 가게의 대기 인원은 " + reservationDAO.checkReservation(1, 1) + " 명 입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		add(reservationStatus);

		backBtn.setBounds(320, 50, 90, 30);
		add(backBtn);

		reservationBtn.setBounds(65, 460, 350, 40);
		add(reservationBtn);
	}

	private void initListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "예약하시겠습니까?", "예약", 2, 1);
				if (result == JOptionPane.YES_OPTION) {
					try {
						if (reservationDAO.getReservationByCustomer(1).getReservationState().equalsIgnoreCase("Y")) {
							JOptionPane.showMessageDialog(null, "이미 예약된 식당이 있습니다.");
						} else {
							try {
								JOptionPane.showMessageDialog(null, "예약되었습니다.");
								customerReservationDAO.reservation(1, 1);
								dispose();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}						
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					new RestaurantFrame(new RestaurantDAO().getAllRestaurants(1).get(93), new MenuDAO().getMenuById(93));
					setVisible(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

//	public static void main(String[] args) {
//		try {
//			new ReservationFrame(new CustomerDTO().getCustomerId(), new RestaurantDAO().getAllRestaurants().get(93));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
