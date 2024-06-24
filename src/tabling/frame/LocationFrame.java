package tabling.frame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Define;

public class LocationFrame extends JFrame implements ActionListener {

	
	private BackgroundPanel frame;
	private JLabel background;

	private JButton[] locationbutton;
	private CustomerDTO customerDTO;
	private RestaurantDAO dao;

//	private JLabel backBtn;

	public LocationFrame(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {
		frame = new BackgroundPanel();
		
		dao = new RestaurantDAO();
		locationbutton = new JButton[17]; // 0 ~ 17 배열
		setTitle("지역 찾기");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Icon icon = new ImageIcon("img/location.png");
		background = new JLabel(icon);
		// 413, 355
		background.setSize(413, 355);
		background.setLocation(35, 50);

//		Icon icon2 = new ImageIcon("img/quitBtn2.png");
//		backBtn = new JLabel(icon2);

//		backBtn.setSize(40, 40);
//		backBtn.setLocation(34, 40);

		locationbutton[Define.LOCATION_ALL] = new JButton("전체 검색"); // 지역구 모두 검색 (전체 검색)
		locationbutton[Define.LOCATION_ALL].setBounds(190, 460, 120, 40); // 190, 460, 120, 40

		locationbutton[Define.LOCATION_GANGSEOGU] = new JButton(); // 강서구
		locationbutton[Define.LOCATION_GANGSEOGU].setBounds(40, 220, 70, 50);
		locationbutton[Define.LOCATION_GANGSEOGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_GANGSEOGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_GANGSEOGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_SAHAGU] = new JButton(); // 사하구
		locationbutton[Define.LOCATION_SAHAGU].setBounds(110, 300, 50, 50);
		locationbutton[Define.LOCATION_SAHAGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_SAHAGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_SAHAGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_SASANGGU] = new JButton(); // 사상구
		locationbutton[Define.LOCATION_SASANGGU].setBounds(125, 210, 40, 40);
		locationbutton[Define.LOCATION_SASANGGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_SASANGGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_SASANGGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_BUKGU] = new JButton(); // 북구
		locationbutton[Define.LOCATION_BUKGU].setBounds(160, 120, 40, 50);
		locationbutton[Define.LOCATION_BUKGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_BUKGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_BUKGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_SEOGU] = new JButton(); // 서구
		locationbutton[Define.LOCATION_SEOGU].setBounds(165, 270, 20, 20);
		locationbutton[Define.LOCATION_SEOGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_SEOGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_SEOGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_JUNGGU] = new JButton(); // 중구
		locationbutton[Define.LOCATION_JUNGGU].setBounds(185, 300, 20, 20);
		locationbutton[Define.LOCATION_JUNGGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_JUNGGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_JUNGGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_DONGGU] = new JButton(); // 동구
		locationbutton[Define.LOCATION_DONGGU].setBounds(195, 260, 23, 23);
		locationbutton[Define.LOCATION_DONGGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_DONGGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_DONGGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_BUSANSGINGU] = new JButton(); // 부산진구
		locationbutton[Define.LOCATION_BUSANSGINGU].setBounds(180, 210, 40, 40);
		locationbutton[Define.LOCATION_BUSANSGINGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_BUSANSGINGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_BUSANSGINGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_YEONGDOGU] = new JButton(); // 영도구
		locationbutton[Define.LOCATION_YEONGDOGU].setBounds(210, 320, 20, 20);
		locationbutton[Define.LOCATION_YEONGDOGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_YEONGDOGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_YEONGDOGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_NAMGU] = new JButton(); // 남구
		locationbutton[Define.LOCATION_NAMGU].setBounds(230, 260, 23, 23);
		locationbutton[Define.LOCATION_NAMGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_NAMGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_NAMGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_DONGNAEGU] = new JButton(); // 동래구
		locationbutton[Define.LOCATION_DONGNAEGU].setBounds(210, 160, 30, 30);
		locationbutton[Define.LOCATION_DONGNAEGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_DONGNAEGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_DONGNAEGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_YEONJEGU] = new JButton(); // 연제구
		locationbutton[Define.LOCATION_YEONJEGU].setBounds(220, 190, 25, 20);
		locationbutton[Define.LOCATION_YEONJEGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_YEONJEGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_YEONJEGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_SUYEONGGU] = new JButton(); // 수영구
		locationbutton[Define.LOCATION_SUYEONGGU].setBounds(256, 220, 23, 23);
		locationbutton[Define.LOCATION_SUYEONGGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_SUYEONGGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_SUYEONGGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_GEUMJEONGGU] = new JButton(); // 금정구
		locationbutton[Define.LOCATION_GEUMJEONGGU].setBounds(220, 110, 50, 40);
		locationbutton[Define.LOCATION_GEUMJEONGGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_GEUMJEONGGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_GEUMJEONGGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_HAEUNDAEGU] = new JButton(); // 해운대구
		locationbutton[Define.LOCATION_HAEUNDAEGU].setBounds(290, 190, 40, 30);
		locationbutton[Define.LOCATION_HAEUNDAEGU].setBorderPainted(false);
		locationbutton[Define.LOCATION_HAEUNDAEGU].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_HAEUNDAEGU].setFocusPainted(false);

		locationbutton[Define.LOCATION_GIJANGGUN] = new JButton(); // 기장군
		locationbutton[Define.LOCATION_GIJANGGUN].setBounds(320, 50, 80, 80);
		locationbutton[Define.LOCATION_GIJANGGUN].setBorderPainted(false);
		locationbutton[Define.LOCATION_GIJANGGUN].setContentAreaFilled(false);
		locationbutton[Define.LOCATION_GIJANGGUN].setFocusPainted(false);

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
		frame.add(background);
		add(frame);
		setVisible(true);
	}

	private void addEventListener() {

		for (int i = Define.LOCATION_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {
			locationbutton[i].addActionListener(this);
		}

//		backBtn.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				new CustomerMainMenuFrame(customerDTO);
//				setVisible(false);
//				dispose();
//			}
//		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = Define.CATEGORY_ALL; i <= Define.LOCATION_GIJANGGUN; i++) {

			if (e.getSource() == locationbutton[Define.LOCATION_ALL]) {
				try {
					List<RestaurantDTO> list = dao.getAllRestaurants(customerDTO.getCustomerId());
					new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION_ALL);
					setVisible(false);
					dispose();
					break;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource() == locationbutton[i]) {

				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(i, customerDTO.getCustomerId());
					new RestaurantListFrame(list, customerDTO, RestaurantListFrame.LOCATION);
					setVisible(false);
					dispose();
					break;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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