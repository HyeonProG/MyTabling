package tabling.frame;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

import tabling.dao.MenuDAO;
import tabling.dao.RestaurantDAO;
import tabling.dto.MenuDTO;
import tabling.dto.RestaurantDTO;
import ver01.restaurant_detailDAO;

public class RestaurantFrame extends JFrame {
	
	RestaurantDTO restaurantDTO;
	List<MenuDTO> menuDTO = new ArrayList<>();
	MenuDAO menuDAO;
	
	private JTable table;
	private JScrollPane scroll;
	Vector<String> head = new Vector<>();
	Vector<Vector<String>> contents = new Vector<>();
	
	private JButton backBtn;
	private JButton reservationBtn;
	
	private JLabel restaurantNameLabel;
	private JTextArea restaurantDetail;
	private JScrollPane detailScroll;
	
	public RestaurantFrame(RestaurantDTO restaurantDTO, List<MenuDTO> menuDTO) {
		this.restaurantDTO = restaurantDTO;
		this.menuDTO = menuDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("음식점 상세페이지");
		setSize(500, 700);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		
		detailScroll = new JScrollPane();
		
		restaurantNameLabel = new JLabel();
		restaurantNameLabel.setText(restaurantDTO.getRestaurantName());
		restaurantDetail = new JTextArea();
		restaurantDetail.setLineWrap(true);
		restaurantDetail.append("주소 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("가게 번호 : " + restaurantDTO.getPhone() + "\n");
		restaurantDetail.append("가게 오픈 시간 : " + restaurantDTO.getOpenTime() + " ~ " + restaurantDTO.getCloseTime() + "\n");
		restaurantDetail.append("가게 휴무일 : " + restaurantDTO.getRestDay() + "\n");
		restaurantDetail.append("가게 위치 : " + restaurantDTO.getAddress() + "\n");
		restaurantDetail.append("상세 설명 \n");
		restaurantDetail.append(restaurantDTO.getContent() + "\n");

		
		menuDAO = new MenuDAO();
		
		head.add("메뉴 이름");
		head.add("가격");		
		for (int i = 0; i < menuDTO.size(); i++) {
			contents.add(new Vector<>());
			int price = menuDTO.get(i).getPrice();
			int foodId = menuDTO.get(i).getFoodId();
			String menuName = null;
			try {
				menuName = menuDAO.getMenuName(foodId);
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			contents.get(i).add(menuName);
			contents.get(i).add(String.valueOf(price));
		}
		
		table = new JTable(contents, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		backBtn = new JButton("뒤로가기");
		reservationBtn = new JButton("예약하기");
		
		scroll = new JScrollPane(table);
		setVisible(true);
	}

	private void setInitLayout() {
		reservationBtn.setBounds(320, 50, 90, 30);
		add(reservationBtn);
		
		backBtn.setBounds(320, 550, 90, 30);
		add(backBtn);
		
		add(scroll);
		scroll.setSize(350, 148);
		scroll.setLocation(65, 380);
		
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가
		table.getColumn("메뉴 이름").setPreferredWidth(200);
		table.getColumn("가격").setPreferredWidth(40);
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn("가격").setCellRenderer(centerAlign);
		table.getColumn("메뉴 이름").setPreferredWidth(70);
		table.getColumn("메뉴 이름").setCellRenderer(centerAlign);
		
		restaurantNameLabel.setBounds(65, 45, 150, 50);
		restaurantNameLabel.setOpaque(true);
		restaurantNameLabel.setBackground(Color.WHITE);
		add(restaurantNameLabel);
		
		restaurantDetail.setBounds(65, 250, 350, 150);
		restaurantDetail.setOpaque(true);
		restaurantDetail.setBackground(Color.WHITE);
		add(restaurantDetail);
		

	}

	private void addEventListener() {
		
	}
	
	public static void main(String[] args) {
		try {
			new RestaurantFrame(new RestaurantDAO().getAllRestaurants().get(93), new restaurant_detailDAO().getMenuById(93));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
