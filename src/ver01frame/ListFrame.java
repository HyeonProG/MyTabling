package ver01frame;


import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ver01.LocationDAO;
import ver01.RestaurantDAO;
import ver01.RestaurantDTO;
import ver01.Restaurant_detailDAO;
import ver01.Time;

public class ListFrame extends JFrame {
	
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;
	private JButton filterBtn;
	private JButton homeBtn;
	private JButton categoryBtn;
	private JButton locationBtn;
	private LocationDAO locationDAO;
	private CategoryDAO categoryDAO;
	
	private final String GANADA_UP = "가나다순";
	private final String GANADA_DOWN = "가나다역순";
	private final String RATING_UP = "평점역순";
	private final String RATING_DOWN = "평점순";
	private final String OPEN = "영업중";
	
	Time currentTime;
	List<RestaurantDTO> restaurantList = new ArrayList<>();
	Vector<String> head = new Vector<>();
	Vector<Vector<String>> contents = new Vector<>();
	
	public ListFrame(List<RestaurantDTO> restaurantList) {
		this.restaurantList = restaurantList;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		locationDAO = new LocationDAO();
		categoryDAO = new CategoryDAO();
		// TODO 임시 시간 설정
		currentTime = new Time(19, 30, "월요일");
		head.add("식당명");
		head.add("카테고리");
		head.add("지역");
		head.add("영업중");
		head.add("평점");
		// 테이블에 담는 과정
		for (int i = 0; i < restaurantList.size(); i++) {
			contents.add(new Vector<>());
			String restaurantName = restaurantList.get(i).getRestaurantName();
			int categoryId = restaurantList.get(i).getCategoryId();
			double rating = restaurantList.get(i).getRating();
			int locationId = restaurantList.get(i).getLocationId();
			String categoryName = null;
			String locationName = null;
			try {
				categoryName = categoryDAO.getCategoryName(categoryId);
				locationName = locationDAO.getLocationName(locationId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			String closeTime = restaurantList.get(i).getCloseTime().substring(0, 5);
//			String openTime = restaurantList.get(i).getOpenTime().substring(0, 5);
			String isOpen = null;
			if (currentTime.isOpen(restaurantList.get(i))) {
				isOpen = "O";
			} else {
				isOpen = "X";
			}
			contents.get(i).add(restaurantName);
			contents.get(i).add(categoryName);
			contents.get(i).add(locationName);
			contents.get(i).add(isOpen);
			contents.get(i).add(String.valueOf(rating));
		}
		table = new JTable(contents, head) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scroll = new JScrollPane(table);
		filter = new JComboBox<String>();
		filterBtn = new JButton("적용");
		homeBtn = new JButton("홈");
		categoryBtn = new JButton("카테고리");
		locationBtn = new JButton("지역");
	}

	private void setInitLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(" 리스트 화면 ");
		setSize(500, 700);
		setLayout(null); // 좌표값으로 배치
		setResizable(false); // 프레임 조절 불가
		setLocationRelativeTo(null); // JFrame을 모니터 가운데 자동 배치
		
		add(filter);
		filter.setLocation(230, 120);
		filter.setSize(150, 30);
		filter.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		
		add(filterBtn);
		filterBtn.setLocation(400, 120);
		filterBtn.setSize(70, 30);
		filterBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		
		add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 170);
		
		add(homeBtn);
		
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가
		table.getColumn("식당명").setPreferredWidth(200);
		table.getColumn("카테고리").setPreferredWidth(90);
		table.getColumn("카테고리").setCellRenderer(centerAlign);
		table.getColumn("지역").setPreferredWidth(70);
		table.getColumn("지역").setCellRenderer(centerAlign);
		table.getColumn("영업중").setPreferredWidth(40);
		table.getColumn("영업중").setCellRenderer(centerAlign);
		table.getColumn("평점").setPreferredWidth(30);
		table.getColumn("평점").setCellRenderer(centerAlign);
		
		setVisible(true);
	}

	private void addEventListener() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 더블 클릭시
				if (e.getClickCount() == 2) {
					int rowNum = table.getSelectedRow();
					Restaurant_detailDAO dao = new Restaurant_detailDAO();
					RestaurantDTO dto = restaurantList.get(rowNum);
					new RestaurantFrame(dto, dao.getMenuById(dto.getCategoryId()));
				}
			}
		});
	}
	

	// 테스트 코드
	public static void main(String[] args) {
		RestaurantDAO dao = new RestaurantDAO();
		try {
			new ListFrame(dao.getAllRestaurants());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
