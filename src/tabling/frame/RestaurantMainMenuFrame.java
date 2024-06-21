package tabling.frame;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tabling.dao.RestaurantReservationDAO;
import tabling.dto.RestaurantDTO;

public class RestaurantMainMenuFrame extends JFrame {

	private RestaurantDTO restDTO;
	// TODO - 테이블로 리스트 띄우기 하는 중
	private RestaurantReservationDAO restDAO;
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;
//	private TableRowSorter<DefaultTableModel> sorter; // sorter 필요없음
	// DefaultTableModel 클래스 파라미터 값 2개 - head, contents
	private String[] head = { "고객 닉네임", "고객 전화번호", "예약시간","예약상태" };
	private String[][] contents = { { "중구", "0101111111", "09:00","예약종료" }, { "진구", "01022222222", "13:00","대기중" } };
	// 리스트
//	private List<RestaurantDTO> reserList = new ArrayList<>();

	public RestaurantMainMenuFrame(RestaurantDTO restDTO) {
		this.restDTO = restDTO;
//		this.reserList = reserList;
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		// 테이블에 담는 메소드
		tableSet();
	}

	private void setInitLayout() {
		setTitle("Tabling - " + restDTO.getRestaurantName());
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

	}

	private void initListener() {
		// TODO Auto-generated method stub

	}

	private void tableSet() {
//		contents = new String[contents][head.length];

		// 테이블
		DefaultTableModel tableModel = new DefaultTableModel(contents, head); // 모델과 데이터 연결
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// 테이블 검색 필드 초기화 -- 필요없음
//		sorter = new TableRowSorter<>(tableModel);
//		table.setRowSorter(sorter);

		// 스크롤
		scroll = new JScrollPane(table);
		add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 170);

		// 테이블에 개별 셀을 표시하기 위한 클래스
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		// 테이블 폰트
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가

		// 테이블에 컬럼 추가
		table.getColumn("고객 닉네임").setPreferredWidth(120);
		table.getColumn("고객 전화번호").setPreferredWidth(120);
		table.getColumn("예약시간").setPreferredWidth(80);
		table.getColumn("예약상태").setPreferredWidth(90);

		//
		repaint();

	}

}
