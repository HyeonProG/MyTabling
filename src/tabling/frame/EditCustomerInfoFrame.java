package tabling.frame;

import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tabling.dto.CustomerDTO;
import tabling.dto.ReservationDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.util.Define;
import tabling.util.MyMouseListener;

public class EditCustomerInfoFrame extends JFrame implements MyMouseListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JTextField nameField;
	private JTextField locationField;
	private JTextField phoneField;
	private JLabel editBtn;
	private JLabel quitBtn;
	private JLabel resignBtn;
	private JComboBox<String> myLocation;

	// DTO
	private CustomerDTO customerDTO;

	// request
	private CustomerRequest request;

	public EditCustomerInfoFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		request = new CustomerRequest();
		setTitle("회원 정보 수정 " + customerDTO.getCustomerName() + "님"); // 제목 타이틀
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel("img/인트로화면 – 4.jpg");

		// 컴포넌트 초기화
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

		// 콤보 박스에 로케이션 값 초기화
		myLocation = new JComboBox<String>();
		for (int i = Define.LOCATION_GANGSEOGU; i <= Define.LOCATION_GIJANGGUN; i++) {
			myLocation.addItem(Define.LOCATIONS[i]);
		}
		myLocation.setBounds(90, 380, 300, 30);
		myLocation.setFont(new Font("Noto Sans KR", Font.BOLD, 13));
		backgroundPanel.add(myLocation);

		backgroundPanel.add(locationField);

		editBtn.setBounds(85, 480, 314, 46);
		backgroundPanel.add(editBtn);

		quitBtn.setBounds(10, 30, 15, 24);
		backgroundPanel.add(quitBtn);

		resignBtn.setBounds(85, 540, 314, 46);
		backgroundPanel.add(resignBtn);

	}

	private void addEventListener() {
		quitBtn.addMouseListener(this);
		editBtn.addMouseListener(this);
		resignBtn.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 나가기 버튼 입력시 메인 메뉴로 돌아감
		if (e.getSource() == quitBtn) {
			new CustomerMainMenuFrame(customerDTO);
			setVisible(false);
			dispose();
			// 수정하기 버튼
		} else if (e.getSource() == editBtn) {
			if (nameField.getText().matches("^\\s.*")) {
				JOptionPane.showMessageDialog(null, "닉네임은 공백으로 시작할 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				nameField.setText("");
				return;
			} else if (nameField.getText().matches(".*\\s$")) {
				JOptionPane.showMessageDialog(null, "닉네임은 공백으로 끝날 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				nameField.setText("");
				return;
			} else if (nameField.getText().length() > 50) {
				JOptionPane.showMessageDialog(null, "닉네임은 50자까지만 기입 가능합니다.", "경고", JOptionPane.WARNING_MESSAGE);
				nameField.setText("");
			} else {
				request.updateCustomer(nameField.getText(), customerDTO.getPhone(), myLocation.getSelectedIndex() + 1);
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
				customerDTO = request.getCustomerByPhone(customerDTO.getPhone());
				new CustomerMainMenuFrame(customerDTO);
				setVisible(false);
				dispose();
			}
			// 탈퇴 버튼
		} else if (e.getSource() == resignBtn) {
			int result = JOptionPane.showConfirmDialog(null, "탈퇴하시겠습니까", "탈퇴", 2, 1);
			if (result == JOptionPane.YES_OPTION) {
				ReservationDTO reservationDTO = new ReservationRequest()
						.getReservationByCustomer(customerDTO.getCustomerId());
				if (reservationDTO != null) {
					new ReservationRequest().cancel(reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
				}
				request.deleteCustomer(customerDTO.getPhone());
				new LoginSelectFrame();
				setVisible(false);
				dispose();
			}
		}
	}
}
