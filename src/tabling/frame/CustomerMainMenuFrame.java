package tabling.frame;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tabling.dto.CustomerDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.util.MyMouseListener;

public class CustomerMainMenuFrame extends JFrame implements MyMouseListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel categoryBtn;
	private JLabel locationBtn;
	private JLabel reservationBtn;
	private JLabel userInfoBtn;
	private JLabel homeBtn;
	private JLabel linkLabel;
	private JLabel adLabelLeft;
	private JLabel adLabelRight;
	private ImageIcon[] adImg;

	// 링크를 위한 카운트 변수
	private int countLeft = 0;
	private int countRight = 1;

	// DTO
	private CustomerDTO customerDTO;

	public CustomerMainMenuFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		logout();
		initData();
		setInitLayout();
		addEventListener();
		labelShift();
	}

	// 프로그램 종료시 로그아웃
	private void logout() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			new CustomerRequest().logoutCustomer(customerDTO.getPhone());
		}));
	}

	private void initData() {
		setTitle("메인 메뉴 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		backgroundPanel = new BackgroundPanel("img/customerMainMenuBg.jpg");

		// 컴포넌트 초기화
		reservationBtn = new JLabel(new ImageIcon("img/reservationStateBtn.png"));
		categoryBtn = new JLabel(new ImageIcon("img/categoryBtn.png"));
		locationBtn = new JLabel(new ImageIcon("img/locationBtn.png"));
		userInfoBtn = new JLabel(new ImageIcon("img/user-solid.png"));
		homeBtn = new JLabel(new ImageIcon("img/house-solid.png"));
		linkLabel = new JLabel(new ImageIcon("img/linkLabel.jpg"));
		adLabelLeft = new JLabel();
		adLabelRight = new JLabel();
		adImg = new ImageIcon[4];
		String imgStr = "img/adLabel";
		for (int i = 0; i < adImg.length; i++) {
			adImg[i] = new ImageIcon(imgStr + i + ".png");
		}
	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		reservationBtn.setBounds(50, 320, 80, 120);
		backgroundPanel.add(reservationBtn);

		categoryBtn.setBounds(210, 320, 70, 120);
		backgroundPanel.add(categoryBtn);

		locationBtn.setBounds(360, 320, 70, 120);
		backgroundPanel.add(locationBtn);

		userInfoBtn.setBounds(430, 15, 30, 30);
		backgroundPanel.add(userInfoBtn);

		homeBtn.setBounds(217, 605, 50, 50);
		backgroundPanel.add(homeBtn);

		linkLabel.setBounds(25, 65, 430, 242);
		backgroundPanel.add(linkLabel);

		adLabelLeft.setBounds(10, 480, 230, 115);
		backgroundPanel.add(adLabelLeft);
		adLabelRight.setBounds(240, 480, 230, 115);
		backgroundPanel.add(adLabelRight);
	}

	private void addEventListener() {
		reservationBtn.addMouseListener(this);
		categoryBtn.addMouseListener(this);
		locationBtn.addMouseListener(this);
		userInfoBtn.addMouseListener(this);
		linkLabel.addMouseListener(this);
		adLabelLeft.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == reservationBtn) {
			if (new ReservationRequest().getReservationByCustomer(customerDTO.getCustomerId()) != null) {
				new CustomerReservationStatusFrame(customerDTO);
				setVisible(false);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "현재 예약한 곳이 없습니다.");
			}
		} else if (e.getSource() == categoryBtn) {
			new CategoryFrame(customerDTO);
			setVisible(false);
			dispose();
		} else if (e.getSource() == locationBtn) {
			new LocationFrame(customerDTO);
			setVisible(false);
			dispose();
		} else if (e.getSource() == userInfoBtn) {
			new EditCustomerInfoFrame(customerDTO);
			setVisible(false);
			dispose();
		} else if (e.getSource() == linkLabel) {
			try {
				Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?app=desktop&v=3rzvkwcmorA"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == adLabelLeft) {
			clickLink();
		} else if (e.getSource() == adLabelRight) {
			clickLink();
		}
	}

	private void clickLink() {
		try {
			switch (countLeft) {
			case 0:
				Desktop.getDesktop().browse(new URI("https://www.coupangeats.com/"));
				break;
			case 1:
				Desktop.getDesktop().browse(new URI("https://baemin.com/"));
				break;
			case 2:
				Desktop.getDesktop().browse(new URI("https://www.yogiyo.co.kr/"));
				break;
			case 3:
				Desktop.getDesktop().browse(new URI("https://app.catchtable.co.kr/"));
				break;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	private void labelShift() {
		new Thread(() -> {
			while (true) {
				adLabelLeft.setIcon(adImg[countLeft]);
				adLabelRight.setIcon(adImg[countRight]);
				repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countLeft++;
				countRight++;
				if (countLeft > 3) {
					countLeft = 0;
				}
				if (countRight > 3) {
					countRight = 0;
				}
			}
		}).start();
	}
}
