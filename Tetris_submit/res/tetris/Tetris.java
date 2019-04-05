package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tetromino.Tetrominoes;

public class Tetris extends JFrame {
	boolean isStarted = false;
	boolean isPaused = false;
	MainBoard stage1;
	
	public Tetris(int playerNum) {
		
		if (playerNum == 1) {
			TetrisBoard tetrisBoard = new TetrisBoard();
			add(tetrisBoard);
			
			stage1 = tetrisBoard.getMainBoard();
			stage1.addKeyListener(new TAdapter(stage1));
			stage1.start(1);

			setSize(400, 600);
			setTitle("Tetris");
			getContentPane().setBackground(Color.WHITE);
			setDefaultCloseOperation(EXIT_ON_CLOSE);// setLocationRelativeTo �� ����ٴϴ� �޼ҵ�
			setVisible(true);

		} else {
			
			TetrisBoard tetrisBoard1 = new TetrisBoard();
			TetrisBoard tetrisBoard2 = new TetrisBoard();
			
			MainBoard mainBoard1p = tetrisBoard1.getMainBoard();
			MainBoard mainBoard2p = tetrisBoard2.getMainBoard();
			
			mainBoard1p.getBoard(mainBoard2p);
			mainBoard2p.getBoard(mainBoard1p);
			
			mainBoard1p.addKeyListener(new TAdapter(mainBoard1p, mainBoard2p));
			mainBoard1p.start(1);
			mainBoard2p.start(2);
			
			setLayout(new GridLayout(1, 2));
			add(tetrisBoard1);
			add(tetrisBoard2);
			setSize(600, 500);
			setTitle("Battle");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		}
	}
}