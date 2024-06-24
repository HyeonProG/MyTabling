package tabling.frame;

import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dao.CustomerReservationDAO;
import tabling.dao.ReservationDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.request.CustomerRequest;

public class EditCustomerInfoFrame extends JFrame {

	private BackgroundPanel backgroundPanel;
	private CustomerDTO customerDTO;
	private CustomerDAO dao;
	private CustomerRequest request;
	private JTextField nameField;
	private JTextField locationField;
	private JTextField phoneField;
	private JLabel editBtn;
	private JLabel quitBtn;
	private JLabel resignBtn;

	private Choice myLocation;
	private String[] localArray;

	public EditCustomerInfoFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		dao = new CustomerDAO();
		request = new CustomerRequest();
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님"); // 제목 타이틀
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel();
		nameField = new JTextField();
		phoneField = new JTextField();
		locationField = new JTextField();
		editBtn = new JLabel(new ImageIcon("img/그룹 20.png")); // 수정 버튼
		quitBtn = new JLabel(new ImageIcon("img/quitBtn2.png")); // 뒤로가기 버튼
		resignBtn = new JLabel(new ImageIcon("img/resignBtn.png")); // TODO 이미지 변경예정

		nameField = new JTextField();
		locationField = new JTextField();

	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		// 닉네임 필드 텍스트
		nameField.setBounds(90, 155, 300, 30);
		nameField.setText(customerDTO.getCustomerName());
		backgroundPanel.add(nameField);

		// 전화번호 필드 텍스트 (수정불가)
		phoneField.setBounds(90, 260, 300, 30);
		phoneField.setText(customerDTO.getPhone());
		phoneField.setEditable(false);
		backgroundPanel.add(phoneField);

		myLocation = new Choice(); // 콤보박스 생성
		localArray = new String[] { "강서구", "사하구", "사상구", "북구", "서구", "중구", "동구", "부산진구", "영도구", "남구", "동래구", "연제구", "수영구", "금정구", "해운대구", "기장군" };
		for (int i = 0; i < localArray.length; i++) {
			myLocation.add(localArray[i]); // myLocation 콤보 박스에 localArray 값을 넣는다.
		}
		myLocation.setBounds(90, 380, 300, 30);
		backgroundPanel.add(myLocation);

		backgroundPanel.add(locationField);

		editBtn.setBounds(85, 480, 314, 46); // 수정하기
		backgroundPanel.add(editBtn);

		quitBtn.setBounds(10, 30, 15, 24);
		backgroundPanel.add(quitBtn);

		resignBtn.setBounds(85, 540, 314, 46);
		backgroundPanel.add(resignBtn);

	}

	private void addEventListener() {

		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerMainMenuFrame(customerDTO);
				setVisible(false);
				dispose();
			}
		});

		editBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (nameField.getText().matches("^\\s.*")) {
					JOptionPane.showMessageDialog(null, "닉네임은 공백으로 시작할 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					nameField.setText("");
					return;
				} else if (nameField.getText().matches("\\s*$")) {
					JOptionPane.showMessageDialog(null, "닉네임은 공백으로 끝날 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					nameField.setText("");
					return;
				} else if (nameField.getText().length() > 50) {
					JOptionPane.showMessageDialog(null, "닉네임은 50자까지만 기입 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
					nameField.setText("");
				} else {
					request.update(nameField.getText(), customerDTO.getPhone(), myLocation.getSelectedIndex() + 1);
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					customerDTO = request.select(customerDTO.getPhone());
					new CustomerMainMenuFrame(customerDTO);
					setVisible(false);
					dispose();
				}

			}
		});

		resignBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "탈퇴하시겠습니까", "탈퇴", 2, 1);
				if (result == JOptionPane.YES_OPTION) {
					try {
						ReservationDTO reservationDTO = new ReservationDAO().getReservationByCustomer(customerDTO.getCustomerId());
						if (reservationDTO != null) {
							new CustomerReservationDAO().cancel(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
						}
						request.delete(customerDTO.getPhone());
						new LoginSelectFrame();
						setVisible(false);
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

	}

	private class BackgroundPanel extends JPanel { // 배경화면 패널
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/인트로화면 – 4.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}

	} // end of BackgroundPanel

}
