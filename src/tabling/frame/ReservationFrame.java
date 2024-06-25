package tabling.frame;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.util.Time;

public class ReservationFrame extends JFrame {

	// 예약 완료시 식당 리스트 프레임을 종료 시키기 위해 참조함
	private RestaurantListFrame restaurantListFrame;

	// 컴포넌트
	private JLabel background;
	private JLabel reservationBtn;
	private JLabel backBtn;
	private JTextArea reservationCount;
	private JLabel restaurantNameLabel;

	// DTO
	private RestaurantDTO restaurantDTO;
	private CustomerDTO customerDTO;

	// request
	private ReservationRequest reservationRequest;

	// 자주 사용하는 변수
	private int customerId;
	private int restaurantId;

	// 대기열 수
	private int count;

	public ReservationFrame(CustomerDTO customerDTO, RestaurantDTO restaurantDTO,
			RestaurantListFrame restaurantListFrame) {
		this.customerDTO = customerDTO;
		this.restaurantDTO = restaurantDTO;
		customerId = customerDTO.getCustomerId();
		restaurantId = restaurantDTO.getRestaurantId();
		this.restaurantListFrame = restaurantListFrame;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("예약하기");
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);

		background = new JLabel(new ImageIcon("img/reservationFrame.jpg"));

		reservationRequest = new ReservationRequest();

		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());

		reservationCount = new JTextArea();

		backBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));

		reservationBtn = new JLabel(new ImageIcon("img/예약하기버튼2.png"));
		setVisible(true);
	}

	private void setInitLayout() {
		background.setSize(getWidth(), getHeight());
		add(background);

		restaurantNameLabel.setBounds(0, 105, 500, 50);
		restaurantNameLabel.setFont(new Font("Noto Sans KR", Font.BOLD, 30));
		restaurantNameLabel.setHorizontalAlignment(JLabel.CENTER);
		restaurantNameLabel.setOpaque(false);
		background.add(restaurantNameLabel);

		reservationCount.setBounds(250, 320, 100, 100);
		reservationCount.setFont(new Font("Noto Sans KR", Font.BOLD, 50));
		reservationCount.setOpaque(false);
		reservationCount.setLineWrap(true);
		reservationCount.setEditable(false);
		count = reservationRequest.checkReservation(customerId, restaurantId);
		reservationCount.append("" + count);

		background.add(reservationCount);

		backBtn.setBounds(20, 2, 50, 50);
		background.add(backBtn);

		reservationBtn.setBounds(65, 500, 350, 40);
		background.add(reservationBtn);
	}

	private void addEventListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				// 현재 영업중인지 확인하는 코드
				if (new Time(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
						LocalDateTime.now().getDayOfWeek().toString()).isOpen(restaurantDTO) == false) {
					JOptionPane.showMessageDialog(null, "현재 영업중이 아닙니다.");
					return;
				}

				// 예약 버튼 입력시
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
								customerDTO = new CustomerRequest().getCustomerByPhone(customerDTO.getPhone());
								// 예약이 완료되면 메인 화면으로 돌아가면서 식당 리스트 프레임을 꺼버림
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
