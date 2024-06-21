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
import tabling.dto.CustomerDTO;

public class EditCustomerInfoFrame1 extends JFrame {

	private BackgroundPanel backgroundPanel;
	private CustomerDTO customerDTO;
	private CustomerDAO dao = new CustomerDAO(); // TODO
	private JTextField nameField;
	private JTextField locationField;
	private JTextField phoneField;
	private JLabel checkBtn;
	private JLabel quitBtn;

	private Choice myLocation;
	private String[] localArray;

	public EditCustomerInfoFrame1(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
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
		checkBtn = new JLabel(new ImageIcon("img/그룹 20.png")); // 수정 버튼
		quitBtn = new JLabel(new ImageIcon("img/quitBtn2.png")); // 뒤로가기 버튼

		nameField = new JTextField();
		locationField = new JTextField();

	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		nameField.setBounds(90, 155, 300, 30); // 닉네임 필드 텍스트
		nameField.setText(customerDTO.getCustomerName());
		backgroundPanel.add(nameField);

		phoneField.setBounds(90, 260, 300, 30); // 전화번호 필드 텍스트 (수정불가)
		phoneField.setText(customerDTO.getPhone());
		phoneField.setEditable(false);
		backgroundPanel.add(phoneField);

		myLocation = new Choice(); // 콤보박스 생성
		localArray = new String[] { "강서구", "사하구", "사상구", "북구", "서구", "중구", "동구", "부산진구", "영도구", "남구", "동래구", "연제구",
				"수영구", "금정구", "해운대구", "기장군" };
		for (int i = 0; i < localArray.length; i++) {
			myLocation.add(localArray[i]); // myLocation 콤보 박스에 localArray 값을 넣는다.
		}
		myLocation.setBounds(90, 380, 300, 30);
		backgroundPanel.add(myLocation);

		backgroundPanel.add(locationField);

		checkBtn.setBounds(85, 480, 314, 46); // 수정하기
		backgroundPanel.add(checkBtn);

		quitBtn.setBounds(10, 30, 15, 24);
		backgroundPanel.add(quitBtn);

	}

	private void initListener() {

		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerMainMenuFrame(customerDTO);
				System.out.println("quitBtn");
				setVisible(false);
			}
		});

		checkBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(nameField.getText());

				try {
					dao.updateCustomer(nameField.getText(), myLocation.getSelectedIndex() + 1, customerDTO.getPhone()); 
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					customerDTO = dao.authenticatePhone(customerDTO.getPhone());
					setVisible(false);
					dispose();
					new CustomerMainMenuFrame(customerDTO);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	} // end of initListener()

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
