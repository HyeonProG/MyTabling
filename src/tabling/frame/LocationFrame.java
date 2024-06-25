package tabling.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.RestaurantRequest;
import tabling.util.Define;

public class LocationFrame extends JFrame implements ActionListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel background;
	private JLabel home;
	private JButton[] locationbutton;

	// DTO
	private CustomerDTO customerDTO;

	// request
	private RestaurantRequest restaurantRequest;

	public LocationFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		backgroundPanel = new BackgroundPanel("img/locationFrameBg.jpg");

		restaurantRequest = new RestaurantRequest();
		locationbutton = new JButton[17]; // 0 ~ 17 배열
		setTitle("지역 찾기");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		background = new JLabel(new ImageIcon("img/location.png"));
		background.setSize(420, 370);
		background.setLocation(32, 100);

		home = new JLabel(new ImageIcon("img/house-solid.png"));
		home.setSize(50, 50);
		home.setLocation(217, 580);

		// 전체보기 버튼
		for (int i = Define.LOCATION_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {
			locationbutton[i] = new JButton();
			locationbutton[i].setBorderPainted(false);
			locationbutton[i].setContentAreaFilled(false);
			locationbutton[i].setFocusPainted(false);
		}
		// 전체보기 버튼만 이미지가 있음
		locationbutton[Define.LOCATION_ALL].setIcon(new ImageIcon("img/지역별전체보기버튼.png"));

		// 지도에 맞추어 숨겨진 버튼이기 때문에 지역마다 버튼 크기가 다름
		locationbutton[Define.LOCATION_ALL].setBounds(37, 495, 410, 60);
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

	}

	private void setInitLayout() {

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		setResizable(false);

		for (int i = Define.LOCATION_GANGSEOGU; i <= Define.LOCATION_GIJANGGUN; i++) {
			background.add(locationbutton[i]);
		}
		backgroundPanel.add(locationbutton[Define.LOCATION_ALL]);
		backgroundPanel.add(home);
		backgroundPanel.add(background);
		add(backgroundPanel);
		setVisible(true);
	}

	private void addEventListener() {

		for (int i = Define.LOCATION_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {
			locationbutton[i].addActionListener(this);
		}

		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CustomerMainMenuFrame(customerDTO);
				setVisible(false);
				dispose();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 전체 선택시
		if (e.getSource() == locationbutton[Define.LOCATION_ALL]) {
			List<RestaurantDTO> list = restaurantRequest.getAllRestaurants(customerDTO.getCustomerId());
			new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION_ALL);
			setVisible(false);
			dispose();
		}
		// 각 로케이션 버튼 클릭시
		for (int i = Define.LOCATION_GANGSEOGU; i <= Define.LOCATION_GIJANGGUN; i++) {

			if (e.getSource() == locationbutton[i]) {

				List<RestaurantDTO> list = restaurantRequest.getRestaurantsByLocation(i, customerDTO.getCustomerId());
				new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION);
				setVisible(false);
				dispose();
				break;
			}
		}

	}
}