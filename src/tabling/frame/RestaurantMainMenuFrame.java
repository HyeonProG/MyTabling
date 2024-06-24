package tabling.frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import tabling.dao.CustomerDAO;
import tabling.dao.CustomerReservationDAO;
import tabling.dao.RestaurantReservationDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.ReservationForRestaurantDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Define;

public class RestaurantMainMenuFrame extends JFrame {

	private RestaurantMainMenuFrame frame; // TODO
	private BackgroundPanel backgroundPanel;
	private RestaurantDTO restDTO;
	private RestaurantReservationDAO restDAO;
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;
	// DefaultTableModel 클래스 파라미터 값 2개 - head, contents
	DefaultTableModel tableModel;
	private String[] head = { "고객 닉네임", "고객 전화번호", "예약시간", "예약상태" };
	private String[][] contents;
	// 리스트
	private List<ReservationForRestaurantDTO> reserList;
	//
	private JLabel resetBtn;
	private JLabel endReserBtn;
	private JLabel backBtn;

	private boolean canEnd;
	private ReservationForRestaurantDTO dto;
	private int rowNum;
	
	CustomerReservationDAO customerReservationDAO;

	public RestaurantMainMenuFrame(RestaurantDTO restDTO) {
		this.restDTO = restDTO;
		restDAO = new RestaurantReservationDAO();
		try {
			reserList = restDAO.getCustomerInfoByReservation(restDTO.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		frame = this;
		// 테이블에 담는 메소드
		backgroundPanel = new BackgroundPanel();
		tableSet();
		restDAO = new RestaurantReservationDAO();

		resetBtn = new JLabel(new ImageIcon("img/새로고침.png"));
		endReserBtn = new JLabel(new ImageIcon("img/예약종료.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn.png"));

		canEnd = false;
		
		customerReservationDAO = new CustomerReservationDAO();
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

		resetBtn.setBounds(20, 590, 184, 44);
		backgroundPanel.add(resetBtn);
		endReserBtn.setBounds(280, 590, 184, 44);
		backgroundPanel.add(endReserBtn);
		backBtn.setBounds(10, 15, 30, 30);
		backgroundPanel.add(backBtn);
	}

	private void initListener() {

		resetBtn.addMouseListener(new MouseAdapter() {
			// 리스트 다시 담기.
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("새로고침");
				setList();
				tableSet();
			}
		});

		endReserBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("예약종료");
				rowNum = table.getSelectedRow();
				int currentRowNum = table.convertRowIndexToModel(rowNum);
				String phone = tableModel.getValueAt(currentRowNum, 1).toString();
				try {
					CustomerDTO customerDTO = new CustomerDAO().getCustomerByPhone(phone);
					customerReservationDAO.cancel(customerDTO.getCustomerId(), restDTO.getRestaurantId());
					setList();
					tableSet();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public void tableSet() {
		if(scroll != null) {
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

		// 테이블
		tableModel = new DefaultTableModel(contents, head); // 모델과 데이터 연결
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

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
		try {
			reserList = restDAO.getCustomerInfoByReservation(restDTO.getRestaurantId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class BackgroundPanel extends JPanel {
		private JPanel backgroundPanel;
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/점주측 고객예약리스트.jpg").getImage();
			backgroundPanel = new JPanel();
			add(backgroundPanel);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}

	}

}
