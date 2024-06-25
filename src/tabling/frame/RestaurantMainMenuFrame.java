package tabling.frame;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import tabling.dto.CustomerDTO;
import tabling.dto.ReservationForRestaurantDTO;
import tabling.dto.RestaurantDTO;
import tabling.request.CustomerRequest;
import tabling.request.ReservationRequest;
import tabling.util.MyMouseListener;

public class RestaurantMainMenuFrame extends JFrame implements MyMouseListener {

	// 패널
	private BackgroundPanel backgroundPanel;

	// 컴포넌트
	private JLabel refreshBtn;
	private JLabel endReserBtn;
	private JLabel backBtn;
	private JLabel filterBtn;

	// 테이블 관련
	private JTable table;
	private JScrollPane scroll;
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	private String[] head = { "고객 닉네임", "고객 전화번호", "예약시간", "예약상태" };
	private String[][] contents;

	// DTO
	private RestaurantDTO restDTO;
	private List<ReservationForRestaurantDTO> reserList;

	// request
	private ReservationRequest reservationRequest;

	public RestaurantMainMenuFrame(RestaurantDTO restDTO) {
		this.restDTO = restDTO;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		reservationRequest = new ReservationRequest();
		reserList = reservationRequest.getCustomerInfoByReservation(restDTO.getRestaurantId());
		backgroundPanel = new BackgroundPanel("img/점주측 고객예약리스트.jpg");
		// 테이블에 담는 메소드
		tableSet();

		refreshBtn = new JLabel(new ImageIcon("img/새로고침.png"));
		endReserBtn = new JLabel(new ImageIcon("img/예약종료.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));
		filterBtn = new JLabel(new ImageIcon("img/대기중인고객버튼.png"));

	}

	private void setInitLayout() {
		setTitle("Tabling - " + restDTO.getRestaurantName());
		setSize(500, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		refreshBtn.setBounds(20, 590, 184, 44);
		backgroundPanel.add(refreshBtn);
		endReserBtn.setBounds(280, 590, 184, 44);
		backgroundPanel.add(endReserBtn);
		backBtn.setBounds(10, 15, 30, 30);
		backgroundPanel.add(backBtn);
		filterBtn.setBounds(330, 60, 130, 44);
		backgroundPanel.add(filterBtn);
	}

	private void addEventListener() {
		refreshBtn.addMouseListener(this);
		endReserBtn.addMouseListener(this);
		filterBtn.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// 새로고침 버튼
		if (e.getSource() == refreshBtn) {
			setList();
			tableSet();
			// 예약 종료 버튼
		} else if (e.getSource() == endReserBtn) {
			int rowNum = table.getSelectedRow();
			int currentRowNum = table.convertRowIndexToModel(rowNum);
			String phone = tableModel.getValueAt(currentRowNum, 1).toString();
			CustomerDTO customerDTO = new CustomerRequest().getCustomerByPhone(phone);
			reservationRequest.cancel(customerDTO.getCustomerId(), restDTO.getRestaurantId());
			setList();
			tableSet();
			// 대기중인 손님만 보기 필터 적용시
		} else if (e.getSource() == filterBtn) {
			try {
				sorter.setRowFilter(RowFilter.regexFilter("대기중"));
			} catch (PatternSyntaxException ex) {
				sorter.setRowFilter(null);
			}
		}
	}

	public void tableSet() {
		if (scroll != null) {
			backgroundPanel.remove(scroll);
		}

		// 리스트 값
		contents = new String[reserList.size()][head.length];

		for (int i = 0; i < reserList.size(); i++) {

			String cName = reserList.get(i).getCustomerName();
			String cPhone = reserList.get(i).getCustomerPhone();
			String time = reserList.get(i).getReservationTime();
			contents[i][0] = cName;
			contents[i][1] = cPhone;
			contents[i][2] = time.substring(5, 16);

			if ((reserList.get(i).getState()).equals("Y")) {
				String state = "대기중";
				contents[i][3] = state;
			} else {
				String state = "예약종료";
				contents[i][3] = state;
			}

		}

		tableModel = new DefaultTableModel(contents, head); // 모델과 데이터 연결
		// 초기화와 동시에 수정 불가 설정
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		// 스크롤
		scroll = new JScrollPane(table);
		backgroundPanel.add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 115);

		// 테이블에 개별 셀을 표시하기 위한 클래스
		DefaultTableCellRenderer centerAlign = new DefaultTableCellRenderer();
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		// 테이블 폰트
		table.setFont(new Font("Noto Sans KR", Font.BOLD, 15));
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false); // 컬럼 이동 불가

		// 테이블에 컬럼 추가
		table.getColumn("고객 닉네임").setPreferredWidth(120);
		table.getColumn("고객 닉네임").setCellRenderer(centerAlign);
		table.getColumn("고객 전화번호").setPreferredWidth(120);
		table.getColumn("고객 전화번호").setCellRenderer(centerAlign);
		table.getColumn("예약시간").setPreferredWidth(80);
		table.getColumn("예약시간").setCellRenderer(centerAlign);
		table.getColumn("예약상태").setPreferredWidth(90);
		table.getColumn("예약상태").setCellRenderer(centerAlign);

		// 컬럼 이동 불가
		table.getTableHeader().setReorderingAllowed(false);

		//
		repaint();

	}

	// List<>에 값을 담는 메소드
	private void setList() {
		reserList = reservationRequest.getCustomerInfoByReservation(restDTO.getRestaurantId());
	}
}
