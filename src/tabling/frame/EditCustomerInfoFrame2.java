package tabling.frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tabling.dao.CustomerDAO;
import tabling.dto.CustomerDTO;

public class EditCustomerInfoFrame2 extends JFrame {

	private BackgroundPanel backgroundPanel;
	private CustomerDTO customerDTO;
	private CustomerDAO customerDAO; // TODO
	private JTextField nameField;
	private JTextField locationField;
	private JTextField phoneField;
	private JLabel checkBtn;
	private JLabel quitBtn;

	public EditCustomerInfoFrame2(CustomerDTO customerDTO) {
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
		checkBtn = new JLabel(new ImageIcon("img/checkBtn.png"));
		quitBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));
	}

	private void setInitLayout() {
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		phoneField.setBounds(90, 260, 300, 30);
		phoneField.setText(customerDTO.getPhone());
		phoneField.setEditable(false);
		backgroundPanel.add(phoneField);

		checkBtn.setBounds(85, 480, 314, 46);
		backgroundPanel.add(checkBtn);

		quitBtn.setBounds(10, 30, 15, 24);
		backgroundPanel.add(quitBtn);
	}

	private void initListener() {

		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new EditCustomerInfoFrame1(customerDTO);
				setVisible(false);
			}
		});

		checkBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});

	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/EditCustomerInfoBg1.jpg").getImage();
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
