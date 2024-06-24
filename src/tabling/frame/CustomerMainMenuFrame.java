package tabling.frame;

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

import tabling.dao.ReservationDAO;
import tabling.dto.CustomerDTO;

public class CustomerMainMenuFrame extends JFrame {

	private BackgroundPanel backgroundPanel;

	private JLabel categoryBtn;
	private JLabel locationBtn;
	private JLabel reservationBtn;
	private JLabel userInfoBtn;
	private JLabel homeBtn;
	
	private CustomerDTO customerDTO;
	public CustomerMainMenuFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel();
		reservationBtn = new JLabel(new ImageIcon("img/reservationStateBtn.png"));
		categoryBtn = new JLabel(new ImageIcon("img/categoryBtn.png"));
		locationBtn = new JLabel(new ImageIcon("img/locationBtn.png"));
		userInfoBtn = new JLabel(new ImageIcon("img/user-solid.png"));
		homeBtn = new JLabel(new ImageIcon("img/house-solid.png"));
	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		reservationBtn.setBounds(50, 280, 80, 120);
		backgroundPanel.add(reservationBtn);

		categoryBtn.setBounds(210, 280, 70, 120);
		backgroundPanel.add(categoryBtn);

		locationBtn.setBounds(360, 280, 70, 120);
		backgroundPanel.add(locationBtn);

		userInfoBtn.setBounds(430, 15, 30, 30);
		backgroundPanel.add(userInfoBtn);

		homeBtn.setBounds(217, 605, 50, 50);
		backgroundPanel.add(homeBtn);
		
	}

	private void addEventListener() {
		reservationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					if (new ReservationDAO().getReservationByCustomer(customerDTO.getCustomerId()) != null) {
						new CustomerReservationStatusFrame(customerDTO);
						setVisible(false);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "현재 예약한 곳이 없습니다.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		categoryBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CategoryFrame(customerDTO);
				setVisible(false);
				dispose();
			}
		});
		locationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new LocationFrame(customerDTO);
				setVisible(false);
				dispose();
			}
		});
		
		
		userInfoBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new EditCustomerInfoFrame(customerDTO);
				setVisible(false);
				dispose();
			}
		});
	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/customerMainMenuBg.jpg").getImage();
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
