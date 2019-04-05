package tetris;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import tetromino.Shape;
import tetromino.Tetrominoes;

public class MainBoard extends JPanel implements ActionListener {

   final int BoardWidth = 10;//최대 블록 쌓을 수 있는 개수
   final int BoardHeight = 22;//최대 블록 쌓을 수 있는 개수

   Timer timer;//타이머 첫 선언
   boolean isFallingFinished = false;
   boolean isStarted = false;
   boolean isPaused = false;
   boolean ghost = true;
   int numLinesRemoved = 0;
   int curX = 0;
   int curY = 0;
   int ghostX = 0;
   int ghostY = 0;
   public Shape curPiece;
   private Shape ghostPiece; //떨어지는 위치 보여주기 
   private Tetrominoes[] board;
   private MainBoard enemy;
   private TetrisBoard tb;
   private JLabel statusbar;
   private JLabel stage;
   private TetrisBoard tetrisBoard;
   Shape next = new Shape();
   Shape nexttest= new Shape();
   int level=1;
   
   private Music music;// = new Music();

   
   public MainBoard() {};
   
   
   
   public MainBoard(TetrisBoard parent) {

      setFocusable(true);//키 이벤트의 포커스를 받을 수 있는 컴포넌트가 여러개 있을 때 울선적으로 입력받기 위해 설정
      curPiece = new Shape();//curPiece는 shape 클래스의 변수
      ghostPiece = new Shape();///////
      timer = new Timer(500, this);
      timer.start();
      statusbar = parent.getStatusBar();
      stage = parent.getStage();
      tetrisBoard = parent;
      board = new Tetrominoes[BoardWidth * BoardHeight];//Board는  쪼그만 칸 하나( 테트리스는 Board 네개로 구성)
      clearBoard();
      
      music = new Music();
      
      
   }
   
   public void getBoard(MainBoard board) { //Board 1p 와 Board 2p의 상호작용을 위해
      enemy = board;
   }
   
   public void actionPerformed(ActionEvent e) {
      if (isFallingFinished) {
         isFallingFinished = false;//다 떨어지면 더 이상 떨어지는게 불가. 새로운 조각 불러오기
         newPiece();
         
      } else {
         oneLineDown();
      }
   }

    int squareWidth() {//실제 블록넓이 -> 대충 최적화 시켜주는 것
      return (int) getSize().getWidth() / BoardWidth;
   }

   int squareHeight() {//실제 블록높이-> 대충 최적화 시켜주는 것
      return (int) getSize().getHeight() / BoardHeight;
   }

   Tetrominoes shapeAt(int x, int y) {//paint함수에서 부름
      return board[(y * BoardWidth) + x];//y번째행의 x번째 칸
   }

   public void start(int a) {//시작함수
	    
      if (isPaused)
         return;
      getLevel();
      stage.setText(String.valueOf(level));
      
      isStarted = true;
      isFallingFinished = false;
      numLinesRemoved = 0;
      clearBoard();
      if(level==1)
          next.setRandomShape1();
       else if(level ==2)
          next.setRandomShape2();
       else
          next.setRandomShape3();
      newPiece();
      timer.start();
      music.BGMstart();   
      
      if(a==2) {
    	  music.MusicStop();
      }
      
   }
   
  

   public void pause(int a) {//일시정지 함수
      if (!isStarted)
         return;//아직 시작되지 않았다면 일시정지 할 수 없다!

      isPaused = !isPaused;
      if (isPaused) {
    	 
         timer.stop();
         music.MusicStop();
         statusbar.setText("paused");
      } else {
         timer.start();
         if(a==1) {
         music.MusicRestart();
        }
         else music.MusicStop();
         statusbar.setText(String.valueOf(numLinesRemoved));
      }
      repaint();
      getLevel();
      stage.setText(String.valueOf(level));
   }
   

   public void paint(Graphics g) {
      super.paint(g);

      Dimension size = getSize();//dimension 클래스는 특정한 사각형을 관리하기에 편한 클래스! getSize도 여기 클래스에 속한 함수
      int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();//squareHeight 블록 한칸  높이
      for (int i = 0; i < BoardHeight; ++i) {//22
         for (int j = 0; j < BoardWidth; ++j) {//10
            Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
            if (shape != Tetrominoes.NoShape)//모양이 있다면
               drawSquare(g, 0 + j * squareWidth(), boardTop /*  boardTop은 맨 위에서부터 0  */+ i * squareHeight(), shape);//블록을 만든다.
         }
      }
      
      if (curPiece.getShape() != Tetrominoes.NoShape) {
         for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i); // x = 현재 블록 x축 
            int y = curY - curPiece.y(i); //y = 현재 블록 y축
            drawSquare(g,  x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
                  curPiece.getShape());
         }
         for (int i = 0; i < 4; ++i) { ///////
                ghost(g); 
                int x = ghostX + ghostPiece.x(i); 
                int y = ghostY - ghostPiece.y(i); 
                drawGhostSquare(g, x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), 
                        ghostPiece.getShape()); 
            }
      } 
   }

   
   private void ghost(Graphics g) { ///////
        Dimension size = getSize(); 
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight(); // Determines the unused space at the top of the board 
        updateUI(); 
        int newY = curY; 
        while (newY > 0) { 
            if (!tryGhostMove(curPiece, curX, newY - 1)) { 
                break; 
            } 
            --newY; 
        } 
    } 
   
   public void dropDown() {//아예 다 내려오는 함수
      int newY = curY;//currentY
      while (newY > 0) {
         if (!tryMove(curPiece, curX, newY - 1))
            break;
         --newY;
      }
      pieceDropped();
   }
   
   public void oneLineDown() {
      
      if (!tryMove(curPiece, curX, curY - 1))
         pieceDropped();
      
   }
   
   public void clearBoard() {//보드 치우기
      for (int i = 0; i < BoardHeight * BoardWidth; ++i) {
         board[i] = Tetrominoes.NoShape;
      }
   }

   private void pieceDropped() {
      for (int i = 0; i < 4; ++i) {//4라는 숫자 굉장히 구체적이군 ㅇㅅㅇ,,
         int x = curX + curPiece.x(i);//x좌표 만들기
         int y = curY - curPiece.y(i);//y좌표 만들기
         board[(y * BoardWidth) + x] = curPiece.getShape();
      }

      removeFullLines();
      
      if (!isFallingFinished)
         newPiece();
   }
   
   
   

   private void newPiece() {
      
      curPiece.setShape(next.getShape());
      getLevel();
      if(level==1)
         next.setRandomShape1();
      else if(level ==2)
         next.setRandomShape2();
      else
         next.setRandomShape3();
      
      
      tetrisBoard.mSideBoard.getNext(next);
      
      curX = BoardWidth / 2 + 1;//뭔 개소릴까 ㅇㅅㅇ,,=6
      curY = BoardHeight - 1 + curPiece.minY();//21+
      
      if (!tryGhostMove(curPiece, curX, curY)) {///////
         curPiece.setShape(Tetrominoes.NoShape);
         timer.stop();
         //게임오버시 소리 정지
         music.MusicStop();
         isStarted = false;
         statusbar.setText("game over");
         
         
         enemy.timer.stop();
         enemy.music.MusicStop();
         enemy.statusbar.setText("You win!");
         //isPaused = true;
         statusbar.setText("game over");
         
         
      }
   }
   
   //대공사 들어간다
   public int getLevel() {
      return level;
   }
   
   char c ='c';
   char g ='g';
   
   public boolean tryMove(Shape newPiece, int newX, int newY) {
	   
	   return move(newPiece,newX, newY,c);
     
         
   }
   
   private boolean tryGhostMove(Shape newPiece, int newX, int newY) {///////
      
	   return move(newPiece,newX, newY,g); 
   }
   
   private boolean move(Shape newPiece, int newX, int newY, char a) {
	   for (int i = 0; i < 4; ++i) {
	         int x = newX + newPiece.x(i);
	         int y = newY - newPiece.y(i);
	         if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
	            return false;
	         if (shapeAt(x, y) != Tetrominoes.NoShape)
	            return false;
	      }
	   if(a=='c') {
		   curPiece = newPiece; 
           curX = newX; 
           curY = newY;  
           repaint(); 
           return true;
	   }
	   else {
		   ghostPiece = newPiece; 
           ghostX = newX; 
           ghostY = newY; 
           repaint(); 
           return true; 
	   }
	   
   }

   private void removeFullLines() {
      int numFullLines = 0;

      for (int i = BoardHeight - 1; i >= 0; --i) {
         boolean lineIsFull = true;

         for (int j = 0; j < BoardWidth; ++j) {
            if (shapeAt(j, i) == Tetrominoes.NoShape) {
               lineIsFull = false;
               break;
            }
         }

         if (lineIsFull) {
            ++numFullLines;
            for (int k = i; k < BoardHeight - 1; ++k) {
               for (int j = 0; j < BoardWidth; ++j)
                  board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
            }
         }
      }

      if (numFullLines > 0) {
    	  
              
         numFullLines *= 100; //한 줄 없앨 때 score 100 점
         numLinesRemoved += numFullLines;
         statusbar.setText(String.valueOf(numLinesRemoved));
         isFallingFinished = true;
         curPiece.setShape(Tetrominoes.NoShape);
         repaint();
         stage();
         
          }
      
    
      }
   

   public void stage() {
	   	 if(numLinesRemoved>200 && numLinesRemoved<=500) { 
	         level =2;
	         pause(1);
	         timer.stop();
	         music.MusicStop();
	         statusbar.setText("0");
	                 
	         statusbar.setText(String.valueOf(numLinesRemoved));
	         timer.setDelay(300);
	         pause(1);
	         }
	   	 
	   	 	else if(numLinesRemoved >500) {
	   		  	 pause(1);
	             level=3;
	             timer.stop();
	             music.MusicStop();
	             
	           
	             timer.setDelay(200);
	             pause(1);
	             
	          }
    	
 
        
         }


   private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
      Color colors[] = { new Color(0, 0, 0), new Color(0,255,204), new Color(136,255,77),
            new Color(255,255,0), new Color(204, 0, 255), new Color(51, 153, 255), new Color(255, 92, 51),
            new Color(255, 230, 242) };//걍 색깔들

      Color color = colors[shape.ordinal()];

      g.setColor(color);
      g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
      
      g.setColor(color.brighter());//밝게 하는거겠지 머,,
      g.drawLine(x, y + squareHeight() - 1, x, y);
      g.drawLine(x, y, x + squareWidth() - 1, y);

      g.setColor(color.darker());//어둡게 하는거겠지 머,,
      g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
      g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
   }
   
   
   private void drawGhostSquare(Graphics g, int x, int y, Tetrominoes shape) {///////
      Color colors[] = { new Color(0, 0, 0,122), new Color(0,255,204,122), new Color(136,255,77,122),
            new Color(255,255,0, 122), new Color(204, 0, 255,122), new Color(51, 153, 255,122),
            new Color(255, 92, 51,122), new Color(255, 230, 242,122) };

      Color color = colors[shape.ordinal()];

      g.setColor(color);
      g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
      
      g.setColor(color.brighter());
      g.drawLine(x, y + squareHeight() - 1, x, y);
      g.drawLine(x, y, x + squareWidth() - 1, y);

      g.setColor(color.darker());
      g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
      g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
   }
   
   
   

   
}