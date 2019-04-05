package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import tetromino.Shape;
import tetromino.Tetrominoes;

public class SideBoard extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Shape next;
	final int BoardWidth = 5;//�ִ� ��� ���� �� �ִ� ����
	final int BoardHeight = 20;//�ִ� ��� ���� �� �ִ� ����
	public SideBoard() {
			
		setBackground(Color.lightGray);
		setVisible(true);
	}
	public void paint(Graphics g) {
		super.paint(g);
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();//squareHeight ��� ��ĭ  ����
		g.drawRect(20, 225, 90, 180);
		
		for (int i = 0; i < 4; ++i) {
			int x = 2+ next.x(i);// x = ���� ��� x�� 
			int y = 8 - next.y(i); //y = ���� ��� y��
			drawSquare(g,  x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
					next.getShape());
		}
	}
	public void getNext(Shape parent) {
		next = parent;
//		next = parent.mMainBoard.nextPiece();
		repaint();
	}
	int squareWidth() {//���� ��ϳ��� -> ���� ����ȭ �����ִ� ��
		return (int) getSize().getWidth() / BoardWidth;
	} 

	int squareHeight() {//���� ��ϳ���-> ���� ����ȭ �����ִ� ��
		return (int) getSize().getHeight() / BoardHeight;
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color colors[] = { new Color(0, 0, 0), new Color(0,255,204), new Color(136,255,77),
				new Color(255,255,0), new Color(204, 0, 255), new Color(51, 153, 255), new Color(255, 92, 51),
				new Color(255, 230, 242) };//�� �����

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		
		g.setColor(color.brighter());//��� �ϴ°Ű��� ��,,
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());//��Ӱ� �ϴ°Ű��� ��,,
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}
	
	
}