package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class TetrisBoard extends JPanel{
	
	public MainBoard mMainBoard;
	public SideBoard mSideBoard;
	private StateBoard mStateBoard;
	private int level;
	
	public TetrisBoard() {
		
		mStateBoard = new StateBoard();
		mMainBoard = new MainBoard(this);
		mSideBoard = new SideBoard();
		drawbackground pb= new drawbackground();
		//decide ratio with GridBagLayout()
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = gbc.weighty = 70;
		gbc.insets = new Insets(4,4,4,0);
		add(pb,gbc);
		add(mMainBoard,gbc);
		backGround();
//		add(pb,mMainBoard);
		
		//if(mStateBoard.getStatusBar()==500) {
			
		//}
		
		
//		mMainBoard.paintbackground();
		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 20;
		gbc.insets = new Insets(4,4,4,3);
		
		mSideBoard.add(mStateBoard, BorderLayout.WEST);
		add(mSideBoard, gbc);
		
		validate();
		repaint();
	}
	
	public MainBoard getMainBoard() {
		return mMainBoard;
	}
	
	public JLabel getStatusBar() {
		return mStateBoard.getStatusBar();
	}
	
	public JLabel getStage() {
		return mStateBoard.getStage();
	}
	
	public void backGround() {
		validate();
		repaint();
		level = mMainBoard.getLevel();
			if(level==1) {
				mMainBoard.setBackground(Color.BLACK);
				
			}
			else if(level ==2) {
				mMainBoard.setBackground(Color.DARK_GRAY);
			}
			else if(level ==3) {
				mMainBoard.setBackground(Color.CYAN);
			}
			repaint();
	}
	
	
	class drawbackground extends JPanel{
		
	
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	final int BoardWidth = 10;//최대 블록 쌓을 수 있는 개수
	final int BoardHeight = 22;//최대 블록 쌓을 수 있는 개수
	
	int squareWidth() {//실제 블록넓이 -> 대충 최적화 시켜주는 것
		return (int) getSize().getWidth() / BoardWidth;
	}

	int squareHeight() {//실제 블록높이-> 대충 최적화 시켜주는 것
		return (int) getSize().getHeight() / BoardHeight;
	}

	
	public void paintbackground(Graphics g){
		super.paint(g);
			for (int i = 0; i < BoardHeight; ++i) {
				for (int j = 0; j < BoardWidth; ++j){
					g.setColor(Color.BLACK);
					g.fillRect(0+squareWidth()*j, 0+i*squareHeight() ,squareWidth() , squareHeight());
					g.setColor(Color.WHITE);
					g.drawRect(0+squareWidth()*j, 0+i*squareHeight() ,squareWidth() , squareHeight());
					}
			}
	}
}
}
