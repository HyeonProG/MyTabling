package ver01frame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ver01.RestaurantDAO;
import ver01.RestaurantDTO;

public class LocationFrame extends JFrame implements MouseListener {
	
	private JLabel background;
	
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
	
	public LocationFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}
	
	private void initData() {
		setTitle("지역 찾기");
		setSize(500, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Icon icon = new ImageIcon("img/location.png");
		background = new JLabel(icon);
		// 413, 355
		background.setSize(413, 355);
		background.setLocation(35,50);
		
		Gangseogu = new JButton(); // 강서구
		Gangseogu.setBounds(40,220, 70, 50);
		Gangseogu.setBorderPainted(false); 
		Gangseogu.setContentAreaFilled(false); 
		Gangseogu.setFocusPainted(false);
		
		Sahagu = new JButton(); // 사하구
		Sahagu.setBounds(110, 300, 50, 50);
		Sahagu.setBorderPainted(false); 
		Sahagu.setContentAreaFilled(false); 
		Sahagu.setFocusPainted(false);
		
		Sasanggu = new JButton(); // 사상구
		Sasanggu.setBounds(125, 210, 40, 40);
		Sasanggu.setBorderPainted(false); 
		Sasanggu.setContentAreaFilled(false); 
		Sasanggu.setFocusPainted(false);
		
		Bukgu = new JButton(); // 북구
		Bukgu.setBounds(160, 120, 40, 50);
		Bukgu.setBorderPainted(false); 
		Bukgu.setContentAreaFilled(false); 
		Bukgu.setFocusPainted(false);
		
		Seogu = new JButton(); // 서구
		Seogu.setBounds(165, 270, 20, 20);
		Seogu.setBorderPainted(false); 
		Seogu.setContentAreaFilled(false); 
		Seogu.setFocusPainted(false);
		
		Junggu = new JButton(); // 중구
		Junggu.setBounds(185, 300, 20, 20);
		Junggu.setBorderPainted(false); 
		Junggu.setContentAreaFilled(false); 
		Junggu.setFocusPainted(false);
		
		Donggu = new JButton(); // 동구
		Donggu.setBounds(195, 260, 23, 23);
		Donggu.setBorderPainted(false); 
		Donggu.setContentAreaFilled(false); 
		Donggu.setFocusPainted(false);
		
		Busangingu = new JButton(); // 부산진구
		Busangingu.setBounds(180, 210, 40, 40);
		Busangingu.setBorderPainted(false); 
		Busangingu.setContentAreaFilled(false); 
		Busangingu.setFocusPainted(false);
		
		Yeongdogu = new JButton(); // 영도구
		Yeongdogu.setBounds(210, 320, 20, 20);
		Yeongdogu.setBorderPainted(false); 
		Yeongdogu.setContentAreaFilled(false); 
		Yeongdogu.setFocusPainted(false);
		
		Namgu = new JButton(); // 남구
		Namgu.setBounds(230, 260, 23, 23);
		Namgu.setBorderPainted(false); 
		Namgu.setContentAreaFilled(false); 
		Namgu.setFocusPainted(false);
		
		Dongnaegu = new JButton(); // 동래구
		Dongnaegu.setBounds(210, 160, 30, 30);
		Dongnaegu.setBorderPainted(false); 
		Dongnaegu.setContentAreaFilled(false); 
		Dongnaegu.setFocusPainted(false);
		
		Yeonjegu = new JButton(); // 연제구
		Yeonjegu.setBounds(220, 190, 25, 20);
		Yeonjegu.setBorderPainted(false); 
		Yeonjegu.setContentAreaFilled(false); 
		Yeonjegu.setFocusPainted(false);
		
		Suyeonggu = new JButton(); // 수영구
		Suyeonggu.setBounds(256, 220, 23, 23);
		Suyeonggu.setBorderPainted(false); 
		Suyeonggu.setContentAreaFilled(false); 
		Suyeonggu.setFocusPainted(false);
		
		Geumjeonggu = new JButton(); // 금정구
		Geumjeonggu.setBounds(220, 110, 50, 40);
		Geumjeonggu.setBorderPainted(false); 
		Geumjeonggu.setContentAreaFilled(false); 
		Geumjeonggu.setFocusPainted(false);
		
		Haeundaegu = new JButton(); // 해운대구
		Haeundaegu.setBounds(290, 190, 40, 30);
		Haeundaegu.setBorderPainted(false); 
		Haeundaegu.setContentAreaFilled(false); 
		Haeundaegu.setFocusPainted(false);
		
		Gijanggun = new JButton(); // 기장군
		Gijanggun.setBounds(320, 50, 80, 80);
		Gijanggun.setBorderPainted(false); 
		Gijanggun.setContentAreaFilled(false); 
		Gijanggun.setFocusPainted(false);
		
		Allselect = new JButton("전체 검색"); // 지역구 모두 검색 
		Allselect.setBounds(190, 460, 120, 40);
				
	}
	
	private void setInitLayout() {
		
		setLayout(null);
		add(background);
		setVisible(true);
		setResizable(false);
		
		background.add(Gangseogu);
		background.add(Sahagu);
		background.add(Sasanggu);
		background.add(Bukgu);
		background.add(Seogu);
		background.add(Junggu);
		background.add(Donggu);
		background.add(Busangingu);
		background.add(Yeongdogu);
		background.add(Namgu);
		background.add(Dongnaegu);
		background.add(Yeonjegu);
		background.add(Suyeonggu);
		background.add(Geumjeonggu);
		background.add(Haeundaegu);
		background.add(Gijanggun);
		
		add(Allselect);
		
	}
	
	private void addEventListener() {
		
		Gangseogu.addMouseListener(this);
		Sahagu.addMouseListener(this);
		Sasanggu.addMouseListener(this);
		Bukgu.addMouseListener(this);
		Seogu.addMouseListener(this);
		Junggu.addMouseListener(this);
		Donggu.addMouseListener(this);
		Busangingu.addMouseListener(this);
		Yeongdogu.addMouseListener(this);
		Namgu.addMouseListener(this);
		Dongnaegu.addMouseListener(this);
		Yeonjegu.addMouseListener(this);
		Suyeonggu.addMouseListener(this);
		Geumjeonggu.addMouseListener(this);
		Haeundaegu.addMouseListener(this);
		Gijanggun.addMouseListener(this);
		
		Allselect.addMouseListener(this);
	}
			
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == Gangseogu ) {
			
			RestaurantDAO dao = new RestaurantDAO();
			try {
				List<RestaurantDTO> list = dao.getRestaurantsByLocation(1);
				new ListFrame(list);
			} catch (SQLException e1) {
				// TODO: handle exception
				e1.printStackTrace();
				
				} 
			} else if(e.getSource() == Sahagu ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(2);
					new ListFrame(list);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				} 
			} else if(e.getSource() == Sasanggu ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(3);
					new ListFrame(list);
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			} else if(e.getSource() == Bukgu ) {
				
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(4);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Seogu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(5);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Junggu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(6);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Donggu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(7);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Busangingu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(8);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Yeongdogu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(9);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Namgu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(10);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Dongnaegu) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(11);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Yeonjegu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(12);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Suyeonggu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(13);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Geumjeonggu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(14);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Haeundaegu ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(15);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getSource() == Gijanggun ) {
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getRestaurantsByLocation(16);
					new ListFrame(list);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			} else if(e.getSource() == Allselect ) { // 지역구 음식점 모두 검색
				RestaurantDAO dao = new RestaurantDAO();
				try {
					List<RestaurantDTO> list = dao.getAllRestaurants();
					new ListFrame(list);
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