package tabling.frame;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tabling.dao.RestaurantDAO;
import tabling.dto.RestaurantDTO;
import tabling.util.Define;

public class LocationFrame extends JFrame implements MouseListener {
	
	private JLabel background;
	
	private JButton[] locationbutton;
	
	private JButton Gangseogu; // 강서구 Gangseo-gu
	private JButton Sahagu; // 사하구 Saha-gu
	private JButton Sasanggu; // 사상구 Sasang-gu
	private JButton Bukgu; // 북구 Buk-gu
	private JButton Seogu; // 서구 Seo-gu
	private JButton Junggu; // 중구 Jung-gu
	private JButton Donggu; // 동구 Dong-gu
	private JButton Busangingu; // 부산진구 Busangin-gu
	private JButton Yeongdogu; // 영도구 Yeongdo-gu
	private JButton Namgu; // 남구 Nam-gu
	private JButton Dongnaegu; // 동래구 Dongnae-gu
	private JButton Yeonjegu; // 연제구 Yeonje-gu
	private JButton Suyeonggu; // 수영구 Suyeong-gu
	private JButton Geumjeonggu; // 금정구 Geumjeong-gu
	private JButton Haeundaegu; // 해운대구 Haeundae-gu
	private JButton Gijanggun; // 기장군 Gijang-gun
	
	private JButton Allselect; // 지역구 음식점 모두 검색 
	
	private JButton back;
	
	public LocationFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}
	
	private void initData() {
		locationbutton = new JButton[17]; // 0 ~ 17 배열
		setTitle("지역 찾기");
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Icon icon = new ImageIcon("img/location.png");
		background = new JLabel(icon);
		// 413, 355
		background.setSize(413, 355);
		background.setLocation(35,50);
		
		locationbutton[Define.LOCATION_ALL] = new JButton("전체 검색"); // 지역구 모두 검색 (전체 검색)
		locationbutton[Define.LOCATION_ALL].setBounds(190, 460, 120, 40); // 190, 460, 120, 40
		
		locationbutton[Define.LOCATION_GANGSEOGU] = new JButton(); // 강서구
		locationbutton[Define.LOCATION_GANGSEOGU].setBounds(40,220, 70, 50);
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
		
	
		back = new JButton("뒤로 가기");
		back.setBounds(40, 60, 90, 40);
		
	} // end of initData
	
	private void setInitLayout() {
		
		setLayout(null);
		add(background);
		setVisible(true);
		setResizable(false);
		
		for (int i =1 ; i < 17; i++) {
			background.add(locationbutton[i]);
			System.out.println(i);
		}
		add(locationbutton[Define.LOCATION_ALL]);
		add(back);
		
	}
	
	private void addEventListener() {
		
		
		locationbutton[Define.LOCATION_GANGSEOGU].addMouseListener(this);
		locationbutton[Define.LOCATION_SAHAGU].addMouseListener(this);
		locationbutton[Define.LOCATION_SASANGGU].addMouseListener(this);
		locationbutton[Define.LOCATION_BUKGU].addMouseListener(this);
		locationbutton[Define.LOCATION_SEOGU].addMouseListener(this);
		locationbutton[Define.LOCATION_JUNGGU].addMouseListener(this);
		locationbutton[Define.LOCATION_DONGGU].addMouseListener(this);
		locationbutton[Define.LOCATION_BUSANSGINGU].addMouseListener(this);
		locationbutton[Define.LOCATION_YEONGDOGU].addMouseListener(this);
		locationbutton[Define.LOCATION_NAMGU].addMouseListener(this);
		locationbutton[Define.LOCATION_DONGNAEGU].addMouseListener(this);
		locationbutton[Define.LOCATION_YEONJEGU].addMouseListener(this);
		locationbutton[Define.LOCATION_SUYEONGGU].addMouseListener(this);
		locationbutton[Define.LOCATION_GEUMJEONGGU].addMouseListener(this);
		locationbutton[Define.LOCATION_HAEUNDAEGU].addMouseListener(this);
		locationbutton[Define.LOCATION_GIJANGGUN].addMouseListener(this);
		locationbutton[Define.LOCATION_ALL].addMouseListener(this);
		
		back.addMouseListener(this);
		
	}		
			
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == locationbutton[Define.LOCATION_GANGSEOGU] ) {
			
			RestaurantDAO dao = new RestaurantDAO();
			try {
				List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_GANGSEOGU);
				new RestaurantListFrame(list);
			} catch (SQLException e1) {
				// TODO: handle exception
				e1.printStackTrace();
				
				} 
			} else if(e.getSource() == locationbutton[Define.LOCATION_SAHAGU] ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_SAHAGU);
					new RestaurantListFrame(list);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				} 
			} else if(e.getSource() == locationbutton[Define.LOCATION_SASANGGU] ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_SASANGGU);
					new RestaurantListFrame(list);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			} 
			else if(e.getSource() == locationbutton[Define.LOCATION_BUKGU] ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_BUKGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_SEOGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_SEOGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_JUNGGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_JUNGGU);
					System.out.println(list);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_DONGGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_DONGGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_BUSANSGINGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_BUSANSGINGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_YEONGDOGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_YEONGDOGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_NAMGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_NAMGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_DONGNAEGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_DONGNAEGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_YEONJEGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_YEONJEGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_SUYEONGGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_SUYEONGGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_GEUMJEONGGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_GEUMJEONGGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_HAEUNDAEGU] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_HAEUNDAEGU);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == locationbutton[Define.LOCATION_GIJANGGUN] ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(Define.LOCATION_GIJANGGUN);
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			} 
	else if(e.getSource() == locationbutton[Define.LOCATION_ALL] ) { // 지역구 음식점 모두 검색
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getAllRestaurants();
					new RestaurantListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		
	} // end of mousePressed

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	
	// TODO TEST
	public static void main(String[] args) {
		new LocationFrame();
	} // end of main 
	
} // end of class