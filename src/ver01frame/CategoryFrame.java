package ver01frame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CategoryFrame extends JFrame {

	private JLabel cateLabel1;
	private JLabel cateLabel2;
	private JLabel cateLabel3;
	private JLabel cateLabel4;
	private JLabel cateLabel5;
	private JLabel cateLabel6;
	private JLabel cateLabel7;
	private JLabel cateLabel8;
	private JLabel cateLabel9;
	private JLabel cateLabel10;
	private JLabel cateLabel11;
	private JLabel cateLabel12;
	private JLabel cateLabel13;

	private JLabel cateTextLabel1;
	private JLabel cateTextLabel2;
	private JLabel cateTextLabel3;
	private JLabel cateTextLabel4;
	private JLabel cateTextLabel5;
	private JLabel cateTextLabel6;
	private JLabel cateTextLabel7;
	private JLabel cateTextLabel8;
	private JLabel cateTextLabel9;
	private JLabel cateTextLabel10;
	private JLabel cateTextLabel11;
	private JLabel cateTextLabel12;
	private JLabel cateTextLabel13;
	private JTextField cateText1;
	private JTextField cateText2;
	private JTextField cateText3;
	private JTextField cateText4;
	private JTextField cateText5;
	private JTextField cateText6;
	private JTextField cateText7;
	private JTextField cateText8;
	private JTextField cateText9;
	private JTextField cateText10;
	private JTextField cateText11;
	private JTextField cateText12;
	private JTextField cateText13;
	private BackgroundPanel backgroundPanel;
	private MainPanel mainPanel;
	private JScrollPane scrollPane;
//	private List<JLabel> cateLabels= new ArrayList<>(13);
	public CategoryFrame() {
		initData();
		setInitLayout();
		initListener();
	}

	private void initData() {
		backgroundPanel = new BackgroundPanel();
		ImageIcon image = new ImageIcon("img/chi1.jpg");
//		for (JLabel cateJlabels : cateLabels) {
//			cateJlabels=new JLabel(image);
//		}
		cateLabel1 = new JLabel(new ImageIcon("img/cutlet.png"));
		cateLabel2 = new JLabel(new ImageIcon("img/coffee.png"));
		cateLabel3 = new JLabel(new ImageIcon("img/dduk.png"));
		cateLabel4 = new JLabel(new ImageIcon("img/bbq.png"));
		cateLabel5 = new JLabel(new ImageIcon("img/chinese.png"));
		cateLabel6 = new JLabel(new ImageIcon("img/hamburger.png"));
		cateLabel7 = new JLabel(new ImageIcon("img/korean.png"));
		cateLabel8 = new JLabel(new ImageIcon("img/chicken.png"));
		cateLabel9 = new JLabel(new ImageIcon("img/sashimi.png"));
		cateLabel10 = new JLabel(new ImageIcon("img/family.png"));
		cateLabel11 = new JLabel(new ImageIcon("img/japanese.png"));
		cateLabel12 = new JLabel(new ImageIcon("img/noodle.png"));
		cateLabel13 = new JLabel(new ImageIcon("img/pub.png"));
		cateTextLabel1 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel2 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel3 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel4 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel5 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel6 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel7 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel8 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel9 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel10 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel11 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel12 = new JLabel(new ImageIcon("img/korean_btn.png"));
		cateTextLabel13 = new JLabel(new ImageIcon("img/korean_btn.png"));
//		cateText1=new JTextField("경양식");
//		cateText2=new JTextField("카페");
//		cateText3=new JTextField("분식");
//		cateText4=new JTextField("숯불구이");
//		cateText5=new JTextField("중식");
//		cateText6=new JTextField("패스트푸드");
//		cateText7=new JTextField("한식");
//		cateText8=new JTextField("치킨");
//		cateText9=new JTextField("회집");
//		cateText10=new JTextField("패밀리레스토랑");
//		cateText11=new JTextField("일식");
//		cateText12=new JTextField("냉면");
//		cateText13=new JTextField("");
		mainPanel = new MainPanel();
		scrollPane = new JScrollPane(mainPanel);
		//리스트 생각중
		mainPanel.add(cateTextLabel1);
		mainPanel.add(cateLabel1);
		mainPanel.add(cateTextLabel2);
		mainPanel.add(cateLabel2);
		mainPanel.add(cateTextLabel3);
		mainPanel.add(cateLabel3);
		mainPanel.add(cateTextLabel4);
		mainPanel.add(cateLabel4);
		mainPanel.add(cateTextLabel5);
		mainPanel.add(cateLabel5);
		mainPanel.add(cateTextLabel6);
		mainPanel.add(cateLabel6);
		mainPanel.add(cateTextLabel7);
		mainPanel.add(cateLabel7);
		mainPanel.add(cateTextLabel8);
		mainPanel.add(cateLabel8);
		mainPanel.add(cateTextLabel9);
		mainPanel.add(cateLabel9);
		mainPanel.add(cateTextLabel10);
		mainPanel.add(cateLabel10);
		mainPanel.add(cateTextLabel11);
		mainPanel.add(cateLabel11);
		mainPanel.add(cateTextLabel12);
		mainPanel.add(cateLabel12);
		mainPanel.add(cateTextLabel13);
		mainPanel.add(cateLabel13);
	}

	private void setInitLayout() {
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);

		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanel.setLayout(null);
		add(backgroundPanel);

		scrollPane.setBounds(10, 80, 360, 400);
		backgroundPanel.add(scrollPane);

		mainPanel.setLayout(new GridLayout(0, 2));
	}

	private void initListener() {
		cateLabel9.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	private class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	private class MainPanel extends JPanel {
		private Image backgroundImage;

		public MainPanel() {
			backgroundImage = new ImageIcon("img/backgroundimage.jpg").getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	public static void main(String[] args) {
		new CategoryFrame();
	}
}