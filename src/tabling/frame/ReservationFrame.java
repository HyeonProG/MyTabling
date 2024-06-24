package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import tabling.dao.ReservationDAO;
import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.util.Time;

public class ReservationFrame extends JFrame {

	private RestaurantListFrame restaurantListFrame;

	private RestaurantDTO restaurantDTO;
	private ReservationRequest reservationRequest;
	private ReservationDAO reservationDAO;
	private CustomerDTO customerDTO;
	private ReservationDTO reservationDTO;
	private int customerId;
	private int restaurantId;
	private int count;

	private JButton reservationBtn;
	private JButton backBtn;
	private JTextArea reservationStatus;
	private JLabel restaurantNameLabel;

	public ReservationFrame(CustomerDTO customerDTO, RestaurantDTO restaurantDTO, RestaurantListFrame restaurantListFrame) {
		this.customerDTO = customerDTO;
		this.restaurantDTO = restaurantDTO;
		customerId = customerDTO.getCustomerId();
		restaurantId = restaurantDTO.getRestaurantId();
		this.restaurantListFrame = restaurantListFrame;
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

		reservationRequest = new ReservationRequest();
		reservationDAO = new ReservationDAO();
		try {
			reservationDTO = reservationDAO.getReservationByCustomer(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
			if (reservationDTO != null) {
				count = reservationDAO.checkReservation(restaurantId, reservationDTO.getReservationId());
			} else {
				count = reservationDAO.checkReservation(restaurantId, 0);
			}
			reservationStatus.append("현재 가게의 대기 인원은 " + count + " 명 입니다.");
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
				if (new Time(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
							LocalDateTime.now().getDayOfWeek().toString()).isOpen(restaurantDTO) == false) {
					JOptionPane.showMessageDialog(null, "현재 영업중이 아닙니다.");
					return;
				}
				int result = JOptionPane.showConfirmDialog(null, "예약하시겠습니까?", "예약", 2, 1);
				if (result == JOptionPane.YES_OPTION) {
					try {
						if (customerDTO.getState().equalsIgnoreCase("Y")) {
							JOptionPane.showMessageDialog(null, "이미 예약된 식당이 있습니다.");
						} else {
							// 방어적 코드 작성
							if (restaurantListFrame != null) {
								JOptionPane.showMessageDialog(null, "예약되었습니다.");
								reservationRequest.reservation(customerId, restaurantId);
								customerDTO = new CustomerRequest().select(customerDTO.getPhone());
								new CustomerMainMenuFrame(customerDTO);
								restaurantListFrame.setVisible(false);
								restaurantListFrame.dispose();
								setVisible(false);
								dispose();
							}
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new RestaurantFrame(customerDTO, restaurantDTO, restaurantListFrame);
				setVisible(false);
				dispose();
			}
		});

	}

}
