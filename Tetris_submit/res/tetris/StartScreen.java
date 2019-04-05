package tetris;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {
	
	boolean isStarted = false;
	boolean isPaused = false;
	//배경음악 생성.
	Music background = new Music();
	//image button 만들기
	ImageIcon img1 = new ImageIcon("res/images/tetrisname.png");
	ImageIcon img2 = new ImageIcon("res/images/button1.png"); 
	ImageIcon img3 = new ImageIcon("res/images/button2.png"); 
	ImageIcon img4 = new ImageIcon("res/images/pressbutton1.png");
	ImageIcon img5 = new ImageIcon("res/images/pressbutton2.png");
	ImageIcon img6 = new ImageIcon("res/images/On.png");	
	ImageIcon img7 = new ImageIcon("res/images/Off.png");
	ImageIcon img8 = new ImageIcon("res/images/pressOn.png");
	ImageIcon img9 = new ImageIcon("res/images/pressOff.png");
	//생성
	JFrame jf = new JFrame();
	JLabel jl = new JLabel(img1);
	JButton One = new JButton(img2);
	JButton Two = new JButton(img3);
	JButton OnButton = new JButton(img6);
	JButton OffButton = new JButton(img7);
	
	JPanel jpanel1 = new JPanel();
	JPanel jpanel2 = new JPanel();
	JPanel jpanel3 = new JPanel();
	
	public StartScreen() {
		
		background.MusicPlay("res/music/start.wav");
		
		getContentPane().setBackground(Color.BLACK);
		setTitle("Tetris");
		setSize(400, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//button image 알맞게 맞추기
		One.setBorderPainted(false);
		One.setFocusPainted(false);
		One.setContentAreaFilled(false);
		Two.setBorderPainted(false);
		Two.setFocusPainted(false);
		Two.setContentAreaFilled(false);
		OnButton.setBorderPainted(false);
		OnButton.setFocusPainted(false);
		OnButton.setContentAreaFilled(false);
		OffButton.setBorderPainted(false);
		OffButton.setFocusPainted(false);
		OffButton.setContentAreaFilled(false);
		
		
		//버튼 눌렀을 때 이미지 버튼 변화
		One.setPressedIcon(img4);
		Two.setPressedIcon(img5);
		OnButton.setPressedIcon(img8);
		OffButton.setPressedIcon(img9);
		
		//border,flow layout 만들기
		setLayout(new BorderLayout(0,50));
		FlowLayout f = new FlowLayout(FlowLayout.CENTER,60,50); //button 간의 거리 조정.
		
		//borderLayout에 위 공백 생성
		jpanel1.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		
		add(jpanel1, BorderLayout.NORTH);
		jpanel1.add(jl, BorderLayout.SOUTH);
		jpanel1.setBackground(Color.BLACK);
		
		add(jpanel2, BorderLayout.CENTER);
		jpanel2.setBackground(Color.BLACK);
		jpanel2.setLayout(f);//flowLayout으로 jpanel2 button 2개 놓기
		jpanel2.add(One);
		jpanel2.add(Two);
		jpanel2.add(OnButton);
		jpanel2.add(OffButton);
		//validate();	
		

		setLocationRelativeTo(null);
		
		//button click -> background sound on/off.
		OnButton.addMouseListener(new OnMouse());
		OffButton.addMouseListener(new OffMouse());
		
		//button click -> change to another frame.
		One.addMouseListener(new ToTetris());
		Two.addMouseListener(new ToBattle());

		
	}
	
	//MouseAdapter
	class OffMouse extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			
			background.MusicStop();
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	class OnMouse extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			background.MusicStop();
			background.MusicPlay("res/music/start.wav");
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	//화면 전환
	class ToTetris extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			
			background.MusicStop();
			//테트리스
			Tetris game = new Tetris(1);
			game.setLocationRelativeTo(null);//윈도우 창을 화면의 가운데에 띄우는 역할.
			game.setVisible(true);
			setVisible(false);
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	//2인용 화면전환
	class ToBattle extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			Music music = new Music();
			setVisible(false);
			background.MusicStop();
			Tetris bat = new Tetris(2);
			bat.setLocationRelativeTo(null);
			bat.setVisible(true);
			setVisible(false);
		}
		public void mouseRelased(MouseEvent e) {
			
		}
	}
	
	public static void main(String[] args) {
		new StartScreen();
	}
}