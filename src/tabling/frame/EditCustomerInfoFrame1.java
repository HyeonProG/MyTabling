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
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel();
		nameField = new JTextField();
		phoneField = new JTextField();
		locationField = new JTextField();
		checkBtn = new JLabel(new ImageIcon("img/그룹 20.png"));
		quitBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));
		
		nameField = new JTextField();
		locationField = new JTextField();
			
	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);
		
		nameField.setBounds(90, 155, 300, 30); // 닉네임
		nameField.setText(customerDTO.getCustomerName());
		backgroundPanel.add(nameField);

		phoneField.setBounds(90, 260, 300, 30); // 전화번호 (수정불가)
		phoneField.setText(customerDTO.getPhone());
		phoneField.setEditable(false);
		backgroundPanel.add(phoneField);
		
//		locationField.setBounds(90, 380, 300, 30); // 선호지역
//		locationField.setText(Integer.toString(customerDTO.getLocationId()));
//		locationField.setText(locationDTO.getLocationName());
		
		myLocation = new Choice();
		localArray = new String[] { "강서구", "사하구", "사상구", "북구", "서구", "중구", "동구", "부산진구", "영도구", "남구", "동래구", "연제구", "수영구", "금정구", "해운대구", "기장군" };
		for (int i = 0; i < localArray.length; i++) {
		myLocation.add(localArray[i]);
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
//			String  locationStr = locationField.getText();
//			int locationNum=Integer.parseInt(locationStr);
			System.out.println(nameField.getText() );
//			System.out.println(locationNum);
			
			try {
				System.out.println("오류어디까지?");
				
				dao.updateCustomer(nameField.getText(), myLocation.getSelectedIndex()+1, customerDTO.getPhone());
				System.out.println("dao.updateCustomer");
				
				JOptionPane.showMessageDialog(null, "아이콘이 없는 메시지", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("오류여기까지인가?");
			System.out.println("지역코드 : "+ myLocation.getSelectedIndex());
			System.out.println("휴대폰 번호 : " +customerDTO.getPhone());
			System.out.println("닉네임 : " + nameField.getText());
			
			
//			checkBtn.addMouseListener(this);
				
				
				
			}
		});

	}

	private class BackgroundPanel extends JPanel {
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

	}

}
