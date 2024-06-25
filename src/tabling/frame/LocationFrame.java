package tabling.frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.RestaurantRequest;
import tabling.util.Define;

public class LocationFrame extends JFrame implements ActionListener {

	private BackgroundPanel frame;
	private JLabel background;
	private JLabel home;

	private JButton[] locationbutton;
	private CustomerDTO customerDTO;
	private RestaurantRequest restaurantRequest;

	public LocationFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		frame = new BackgroundPanel();

		restaurantRequest = new RestaurantRequest();
		locationbutton = new JButton[17]; // 0 ~ 17 배열
		setTitle("지역 찾기");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Icon icon = new ImageIcon("img/location.png");
		background = new JLabel(icon);
		// 413, 355
		background.setSize(420, 370); // 413, 355
		background.setLocation(32, 100); // 35, 50

		Icon icon4 = new ImageIcon("img/house-solid.png");
		home = new JLabel(icon4);
		home.setSize(50, 50);
		home.setLocation(217, 580);

		Icon icon3 = new ImageIcon("img/지역별전체보기버튼.png");
		locationbutton[Define.LOCATION_ALL] = new JButton(icon3);
		locationbutton[Define.LOCATION_ALL].setBounds(37, 495, 410, 60); // 190, 460, 120, 40 // 37, 495, 410, 60
		locationbutton[Define.LOCATION_ALL].setBorderPainted(false);
		locationbutton[Define.LOCATION_ALL].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_ALL].setFocusPainted(false);
		for (int i = Define.LOCATION_GANGSEOGU; i <= Define.LOCATION_GIJANGGUN; i++) {
			locationbutton[i] = new JButton();
			locationbutton[i].setBorderPainted(false);
			locationbutton[i].setContentAreaFilled(false);
			locationbutton[i].setFocusPainted(false);
		}
		locationbutton[Define.LOCATION_GANGSEOGU].setBounds(40, 220, 70, 50);
		locationbutton[Define.LOCATION_SAHAGU].setBounds(110, 300, 50, 50);
		locationbutton[Define.LOCATION_SASANGGU].setBounds(125, 210, 40, 40);
		locationbutton[Define.LOCATION_BUKGU].setBounds(160, 120, 40, 50);
		locationbutton[Define.LOCATION_SEOGU].setBounds(165, 270, 20, 20);
		locationbutton[Define.LOCATION_JUNGGU].setBounds(185, 300, 20, 20);
		locationbutton[Define.LOCATION_DONGGU].setBounds(195, 260, 23, 23);
		locationbutton[Define.LOCATION_YEONGDOGU].setBounds(210, 320, 20, 20);
		locationbutton[Define.LOCATION_NAMGU].setBounds(230, 280, 23, 23);
		locationbutton[Define.LOCATION_DONGNAEGU].setBounds(210, 160, 30, 30);
		locationbutton[Define.LOCATION_BUSANSGINGU].setBounds(180, 210, 40, 40);
		locationbutton[Define.LOCATION_YEONJEGU].setBounds(225, 200, 25, 20);
		locationbutton[Define.LOCATION_SUYEONGGU].setBounds(256, 220, 23, 23);
		locationbutton[Define.LOCATION_GEUMJEONGGU].setBounds(220, 110, 50, 40);
		locationbutton[Define.LOCATION_HAEUNDAEGU].setBounds(290, 190, 40, 30);
		locationbutton[Define.LOCATION_GIJANGGUN].setBounds(320, 50, 80, 80);

	} // end of initData

	private void setInitLayout() {

		frame.setSize(getWidth(), getHeight());
		frame.setLayout(null);
//		setLayout(null);
		setResizable(false);

		for (int i = 1; i < 17; i++) {
			background.add(locationbutton[i]);
		}
		frame.add(locationbutton[Define.LOCATION_ALL]);

//		frame.add(backBtn);
		frame.add(home);
		frame.add(background);
		add(frame);
		setVisible(true);
	}

	private void addEventListener() {

		for (int i = Define.LOCATION_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {
			locationbutton[i].addActionListener(this);
		}

		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				new CustomerMainMenuFrame(customerDTO);
				super.mousePressed(e);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = Define.CATEGORY_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {

			if (e.getSource() == locationbutton[Define.LOCATION_ALL]) {
				List<RestaurantDTO> list = restaurantRequest.getAllRestaurants(customerDTO.getCustomerId());
				new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION_ALL);
				setVisible(false);
				dispose();
				break;
			}
			if (e.getSource() == locationbutton[i]) {

				List<RestaurantDTO> list = restaurantRequest.getRestaurantsByLocation(i, customerDTO.getCustomerId());
				new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION);
				setVisible(false);
				dispose();
				break;
			}
		}

	} // end of actionPerformed

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/locationFrameBg.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}

	}

} // end of class