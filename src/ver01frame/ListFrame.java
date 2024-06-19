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

import ver01.RestaurantDAO;
import ver01.RestaurantDTO;

public class ListFrame extends JFrame {
	
	JTable table;
	JScrollPane scroll;
	JComboBox<String> filter;
	JButton filterBtn;
	
	List<RestaurantDTO> restaurantList = new ArrayList<>();
	Vector<String> head = new Vector<>();
	Vector<Vector<String>> contents = new Vector<>();
	
	public ListFrame(List<RestaurantDTO> restaurantList) {
		this.restaurantList = restaurantList;
		initData();
		setInitLayout();
		//addEventListener();
	}

	private void initData() {
		head.add("식당명");
		head.add("평점");
		head.add("지역");
		head.add("마감시간");
		for (int i = 0; i < restaurantList.size(); i++) {
			contents.add(new Vector<>());
			String restaurantName = restaurantList.get(i).getRestaurantName();
			double rating = restaurantList.get(i).getRating();
			// TODO 임시 지역 이름
			String locationId = "부산진구";
			String closeTime = restaurantList.get(i).getCloseTime();
			contents.get(i).add(restaurantName);
			contents.get(i).add(String.valueOf(rating));
			contents.get(i).add(locationId);
			contents.get(i).add(closeTime);
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
	}

	private void setInitLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Frame -> root Panel
		setTitle(" 리스트 화면 ");
		setSize(500, 700);
		setLayout(null); // 좌표값으로 배치
		setResizable(false); // 프레임 조절 불가
		setLocationRelativeTo(null); // JFrame을 모니터 가운데 자동 배치
		
		add(filter);
		filter.setLocation(270, 60);
		filter.setSize(120, 20);
		filter.setFont(new Font("Noto Sans KR", Font.BOLD, 12));
		
		add(filterBtn);
		filterBtn.setLocation(410, 60);
		filterBtn.setSize(60, 20);
		filterBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 12));
		
		add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 95);
		
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가
		table.getColumn("식당명").setPreferredWidth(200);
		table.getColumn("평점").setPreferredWidth(40);
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		table.getColumn("평점").setCellRenderer(centerAlign);
		table.getColumn("지역").setPreferredWidth(60);
		table.getColumn("지역").setCellRenderer(centerAlign);
		table.getColumn("마감시간").setPreferredWidth(70);
		table.getColumn("마감시간").setCellRenderer(centerAlign);
		
		setVisible(true);
	}

//	private void addEventListener() {
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {
//					int rowNum = table.getSelectedRow();
//					System.out.println("됨" + rowNum);
//					for (String strings : contents[rowNum]) {
//						System.out.print(strings + " ");
//					}
//				}
//			}
//		});
//	}


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
