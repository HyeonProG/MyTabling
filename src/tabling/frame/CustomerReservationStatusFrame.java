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

import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.request.RestaurantRequest;
import tabling.util.MyMouseListener;

public class CustomerReservationStatusFrame extends JFrame implements MyMouseListener {

	// 프레임
	private RestaurantFrame restaurantFrame;

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel backBtn;
	private JLabel cancelBtn;
	private JLabel detailBtn;
	private JLabel refreshBtn;
	private JLabel customerQueue;
	private JLabel restaurantName;
	private JLabel customerName;

	// DTO
	private CustomerDTO customerDTO;
	private ReservationDTO reservationDTO;
	private RestaurantDTO restaurantDTO;

	// request
	private RestaurantRequest restaurantRequest;
	private ReservationRequest reservationRequest;

	// 대기열 수
	private int count;

	public CustomerReservationStatusFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {

		// request 초기화
		restaurantRequest = new RestaurantRequest();
		reservationRequest = new ReservationRequest();

		// 고객 id를 통해서 예약 내역, 예약 순서, 식당 내역을 다 받아옴
		reservationDTO = reservationRequest.getReservationByCustomer(customerDTO.getCustomerId());
		restaurantDTO = restaurantRequest.getRestaurantByRestaurantId(reservationDTO.getRestaurantId());
		count = reservationRequest.checkReservation(customerDTO.getCustomerId(), restaurantDTO.getRestaurantId());

		backgroundPanel = new BackgroundPanel();

		// 컴포넌트 초기화
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
		cancelBtn = new JLabel(new ImageIcon("img/그룹 25.png"));
		detailBtn = new JLabel(new ImageIcon("img/그룹 24.png"));
		refreshBtn = new JLabel(new ImageIcon("img/새로고침.png"));
		customerQueue = new JLabel(String.valueOf(count));
		restaurantName = new JLabel(restaurantDTO.getRestaurantName());
		customerName = new JLabel(customerDTO.getCustomerName());
	}

	private void setInitLayout() {
		setTitle("예약 현황");
		setSize(500, 700);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		backBtn.setBounds(0, 10, 70, 70);
		backgroundPanel.add(backBtn);

		customerName.setBounds(42, 73, 100, 70);
		customerName.setFont(new Font("Noto Sans KR", Font.BOLD, 18));
		customerName.setSize(100, 100);
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

		refreshBtn.setBounds(150, 550, 184, 44);
		backgroundPanel.add(refreshBtn);

		setVisible(true);
	}

	private void addEventListener() {

		backBtn.addMouseListener(this);
		cancelBtn.addMouseListener(this);
		detailBtn.addMouseListener(this);
		refreshBtn.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == backBtn) {
			new CustomerMainMenuFrame(customerDTO);
			setVisible(false);
			dispose();
		} else if (e.getSource() == cancelBtn) {
			reservationRequest.cancel(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
			JOptionPane.showMessageDialog(null, "예약 취소 되었습니다.", "성공", JOptionPane.WARNING_MESSAGE);
			if (restaurantFrame != null) {
				restaurantFrame.setVisible(false);
				restaurantFrame.dispose();
			}
			customerDTO = new CustomerRequest().getCustomerByPhone(customerDTO.getPhone());
			new CustomerMainMenuFrame(customerDTO);
			setVisible(false);
			dispose();
		} else if (e.getSource() == detailBtn) {
			restaurantFrame = new RestaurantFrame(customerDTO, restaurantDTO, null);
		} else if (e.getSource() == refreshBtn) {
			if (reservationRequest.getReservationByCustomer(customerDTO.getCustomerId()) == null) {
				JOptionPane.showMessageDialog(null, "예약이 취소 되었습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				new CustomerMainMenuFrame(customerDTO);
				setVisible(false);
				dispose();
			}
			count = reservationRequest.checkReservation(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
			customerQueue.setText(String.valueOf(count));
			repaint();
		}
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
}