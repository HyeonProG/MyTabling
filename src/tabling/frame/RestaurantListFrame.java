package tabling.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import tabling.dao.CategoryDAO;
import tabling.dao.CustomerDAO;
import tabling.dao.LocationDAO;
import tabling.dao.MenuDAO;
import tabling.dao.RestaurantDAO;
import tabling.dto.CustomerDTO;
import tabling.dto.RestaurantDTO;
import tabling.util.Time;

public class RestaurantListFrame extends JFrame {

	private RestaurantListFrame frame;
	private RestaurantFrame restaurantFrame;
	
	private JTable table;
	private JScrollPane scroll;
	private JComboBox<String> filter;
	private JButton filterBtn;
	private JLabel homeBtn;
	private JLabel backBtn;
	private JTextField searchField;
	private LocationDAO locationDAO;
	private CategoryDAO categoryDAO;
	private RestaurantDAO restaurantDAO;
	private CustomerDTO customerDTO;
	private int categoryId;
	private int locationId;
	private int type;
	public static final int CATEGORY = 0;
	public static final int LOCATION = 1;
	public static final int CATEGORY_ALL = 2;
	public static final int LOCATION_ALL = 3;

	private final String RESET = "초기화";
	private final String OPEN = "영업중";
	private final String LIKE = "좋아요만";
	private final String GANADA_UP = "가나다순";
	private final String GANADA_DOWN = "가나다역순";
	private final String RATING_UP = "평점역순";
	private final String RATING_DOWN = "평점순";

	private Time currentTime;
	private List<RestaurantDTO> restaurantList = new ArrayList<>();
	private List<RestaurantDTO> defaultList = new ArrayList<>();

	private String[] head = { "식당명", "카테고리", "지역", "영업중", "평점" };
	private String[][] contents;
	private TableRowSorter<DefaultTableModel> sorter;

	public RestaurantListFrame(List<RestaurantDTO> restaurantList, CustomerDTO customerDTO, int type) {
		this.customerDTO = customerDTO;
		this.restaurantList = restaurantList;
		for (RestaurantDTO restaurantDTO : restaurantList) {
			defaultList.add(restaurantDTO);
		}
		this.type = type;
		typeSet();
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		frame = this;
		locationDAO = new LocationDAO();
		categoryDAO = new CategoryDAO();
		restaurantDAO = new RestaurantDAO();
		// TODO 임시 시간 설정
		currentTime = new Time(19, 30, "월요일");
		// 테이블에 담는 과정
		tableSet();
		filter = new JComboBox<String>();
		filterBtn = new JButton("적용");
		homeBtn = new JLabel(new ImageIcon("img/home.png"));
		backBtn = new JLabel(new ImageIcon("img/quitBtn2.png"));
		searchField = new JTextField();
	}

	private void setInitLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(" 리스트 화면 " + customerDTO.getCustomerName() + "님");
		setSize(500, 700);
		setLayout(null); // 좌표값으로 배치
		setResizable(false); // 프레임 조절 불가
		setLocationRelativeTo(null); // JFrame을 모니터 가운데 자동 배치

		add(filter);
		filter.setLocation(270, 80);
		filter.setSize(110, 30);
		filter.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		filter.addItem(RESET);
		filter.addItem(OPEN);
		filter.addItem(LIKE);
		filter.addItem(GANADA_UP);
		filter.addItem(GANADA_DOWN);
		filter.addItem(RATING_DOWN);
		filter.addItem(RATING_UP);

		add(filterBtn);
		filterBtn.setLocation(400, 80);
		filterBtn.setSize(70, 30);
		filterBtn.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		add(homeBtn);
		homeBtn.setLocation(200, 590);
		homeBtn.setSize(70, 70);

		add(backBtn);
		backBtn.setLocation(20, 20);
		backBtn.setSize(15, 24);

		add(searchField);
		searchField.setLocation(20, 80);
		searchField.setSize(202, 30);
		searchField.setFont(new Font("Noto Sans KR", Font.BOLD, 15));

		setVisible(true);
	}

	private void addEventListener() {

		filterBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				System.out.println(filter.getSelectedItem().toString());
				switch (filter.getSelectedItem().toString()) {
				case RESET:
					restaurantDAO.setOpenFilter(false);
					restaurantDAO.setLikeFilter(false);
					setRestaurantList();
					tableSet();
					break;
				case OPEN:
					restaurantDAO.setOpenFilter(true);
					restaurantDAO.setCurrentTime(currentTime);
					setRestaurantList();
					tableSet();
					break;
				case LIKE:
					restaurantDAO.setLikeFilter(true);
					setRestaurantList();
					tableSet();
					break;
				case GANADA_UP:
					RestaurantDTO.setSortType(RestaurantDTO.GANADA);
					Collections.sort(restaurantList, Collections.reverseOrder());
					tableSet();
					break;
				case GANADA_DOWN:
					RestaurantDTO.setSortType(RestaurantDTO.GANADA);
					Collections.sort(restaurantList);
					tableSet();
					break;
				case RATING_DOWN:
					RestaurantDTO.setSortType(RestaurantDTO.RATING);
					Collections.sort(restaurantList);
					tableSet();
					break;
				case RATING_UP:
					RestaurantDTO.setSortType(RestaurantDTO.RATING);
					Collections.sort(restaurantList, Collections.reverseOrder());
					tableSet();
					break;
				}
			}
		});
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				switch (type) {
				case CATEGORY:
					frame.setVisible(false);
					frame.dispose();
					new CategoryFrame(customerDTO);
					break;
				case LOCATION:
					frame.setVisible(false);
					frame.dispose();
					new LocationFrame(customerDTO);
					break;
				case CATEGORY_ALL:
					frame.setVisible(false);
					frame.dispose();
					new CategoryFrame(customerDTO);
					break;
				case LOCATION_ALL:
					frame.setVisible(false);
					frame.dispose();
					new LocationFrame(customerDTO);
					break;
				}
			}
		});
		homeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				frame.setVisible(false);
				frame.dispose();
				new CustomerMainMenuFrame(customerDTO);
			}
		});

		searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}

			private void search() {
				String searchText = searchField.getText().trim();

				// 검색어가 없으면 모든 행을 다시 보여준다.
				if (searchText.isEmpty()) {
					sorter.setRowFilter(null);

				} else {
					try {
						sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
					} catch (PatternSyntaxException ex) {
						// 검색어가 정규식으로 변환되지 않으면 모든 행을 보여준다.
						sorter.setRowFilter(null);
					}
				}
			}
		});
	}

	private void typeSet() {
		if (!restaurantList.isEmpty()) {
			switch (type) {
			case CATEGORY:
				categoryId = restaurantList.get(0).getCategoryId();
				break;
			case LOCATION:
				locationId = restaurantList.get(0).getLocationId();
				break;
			}
		}
	}

	private void tableSet() {
		if (scroll != null) {
			remove(scroll);
		}
		contents = new String[restaurantList.size()][head.length];

		for (int i = 0; i < restaurantList.size(); i++) {
			String isOpen = null;
			if (currentTime.isOpen(restaurantList.get(i))) {
				isOpen = "O";
			} else {
				isOpen = "X";
			}
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

			contents[i][0] = restaurantName;
			contents[i][1] = categoryName;
			contents[i][2] = locationName;
			contents[i][3] = isOpen;
			contents[i][4] = String.valueOf(rating);
		}
		DefaultTableModel tableModel = new DefaultTableModel(contents, head);
		table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);
		scroll = new JScrollPane(table);
		add(scroll);
		scroll.setSize(450, 450);
		scroll.setLocation(20, 130);
		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(0, 0));
				btn.setMinimumSize(new Dimension(0, 0));
				btn.setMaximumSize(new Dimension(0, 0));
				return btn;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(0, 0));
				btn.setMinimumSize(new Dimension(0, 0));
				btn.setMaximumSize(new Dimension(0, 0));
				return btn;
			}

			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.ORANGE;
				this.trackColor = Color.white;
			}
		});

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
		repaint();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 더블 클릭시
				if (e.getClickCount() == 2) {
					int rowNum = table.getSelectedRow();
					int currentRowNum = table.convertRowIndexToModel(rowNum);
					String name = tableModel.getValueAt(currentRowNum, 0).toString();
					RestaurantDTO dto = null;
					for (RestaurantDTO restaurantDTO : restaurantList) {
						if (restaurantDTO.getRestaurantName().equals(name)) {
							dto = restaurantDTO;
						}
					}
					if (dto == null) {
						return;
					}
					if (restaurantFrame != null) {
						restaurantFrame.setVisible(true);
						restaurantFrame.dispose();
					}
					restaurantFrame = new RestaurantFrame(customerDTO, dto, frame);
				}
			}
		});
	}

	private void setRestaurantList() {
		try {
			if (type == LOCATION) {
				restaurantList = restaurantDAO.getRestaurantsByLocation(locationId, customerDTO.getCustomerId());
			} else if (type == CATEGORY) {
				restaurantList = restaurantDAO.getRestaurantsByCategory(categoryId, customerDTO.getCustomerId());
			} else {
				restaurantList = restaurantDAO.getAllRestaurants(customerDTO.getCustomerId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
