package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import tetromino.Tetrominoes;

public class TAdapter extends KeyAdapter {
   
   private MainBoard board1p = null;
   private MainBoard board2p = null;
   private int playerNum = 1;
   private Music alarm = new Music();
   
   public TAdapter(MainBoard board) {
      this.board2p = board;
      this.playerNum = 1;
   }

   public TAdapter(MainBoard board1p, MainBoard board2p) {
      this.board1p = board1p;
      this.board2p = board2p;
      this.playerNum = 2;
   }
   
   public void keyPressed(KeyEvent e) {

      int keycode = e.getKeyCode();
      if (playerNum == 1) {
         // 2p
         if (!board2p.isStarted || board2p.curPiece.getShape() == Tetrominoes.NoShape) {
            return;
         }

         if (keycode == 'p' || keycode == 'P') {
            board2p.pause(1);
            return;
         }

         if (board2p.isPaused) {
            return;
         }
         
         switch (keycode) {
         case KeyEvent.VK_LEFT:
            board2p.tryMove(board2p.curPiece, board2p.curX - 1, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_RIGHT:
            board2p.tryMove(board2p.curPiece, board2p.curX + 1, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_DOWN:
            board2p.tryMove(board2p.curPiece.rotateRight(), board2p.curX, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_UP:
            board2p.tryMove(board2p.curPiece.rotateLeft(), board2p.curX, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_SPACE:
            board2p.dropDown();
            alarm.AlStart();
            break;
         case 'm':
            board2p.oneLineDown();
            alarm.AlStart();
            break;
         case 'M':
            board2p.oneLineDown();
            alarm.AlStart();
            break;
         }

      } else if (playerNum == 2) {
         // 2p
         if (!board2p.isStarted || board2p.curPiece.getShape() == Tetrominoes.NoShape) {
            return;
         }

         if (keycode == 'p' || keycode == 'P') {
            board2p.pause(1);
            board1p.pause(2);

            return;
         }
         if (board1p.isPaused && board2p.isPaused)
            return;

         switch (keycode) {
         case KeyEvent.VK_LEFT:
            board2p.tryMove(board2p.curPiece, board2p.curX - 1, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_RIGHT:
            board2p.tryMove(board2p.curPiece, board2p.curX + 1, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_DOWN:
            board2p.tryMove(board2p.curPiece.rotateRight(), board2p.curX, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_UP:
            board2p.tryMove(board2p.curPiece.rotateLeft(), board2p.curX, board2p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_SPACE:
            board2p.dropDown();
            alarm.AlStart();
            break;
         case 'm':
            board2p.oneLineDown();
            alarm.AlStart();
            break;
         case 'M':
            board2p.oneLineDown();
            alarm.AlStart();
            break;
         }
         if (!board1p.isStarted || board1p.curPiece.getShape() == Tetrominoes.NoShape) {
            return;
         }

         switch (keycode) {
         case 's':
            board1p.tryMove(board1p.curPiece, board1p.curX - 1, board1p.curY);
            alarm.AlStart();
            break;
         case 'S':
            board1p.tryMove(board1p.curPiece, board1p.curX - 1, board1p.curY);
            alarm.AlStart();
            break;
         case 'f':
            board1p.tryMove(board1p.curPiece, board1p.curX + 1, board1p.curY);
            alarm.AlStart();
            break;
         case 'F':
            board1p.tryMove(board1p.curPiece, board1p.curX + 1, board1p.curY);
            alarm.AlStart();
            break;
         case 'd':
            board1p.tryMove(board1p.curPiece.rotateRight(), board1p.curX, board1p.curY);
            alarm.AlStart();
            break;
         case 'D':
            board1p.tryMove(board1p.curPiece.rotateRight(), board1p.curX, board1p.curY);
            alarm.AlStart();
            break;
         case 'e':
            board1p.tryMove(board1p.curPiece.rotateLeft(), board1p.curX, board1p.curY);
            alarm.AlStart();
            break;
         case 'E':
            board1p.tryMove(board1p.curPiece.rotateLeft(), board1p.curX, board1p.curY);
            alarm.AlStart();
            break;
         case KeyEvent.VK_SHIFT:
            board1p.dropDown();
            alarm.AlStart();
            break;
         case 'z':
            board1p.oneLineDown();
            alarm.AlStart();
            break;
         case 'Z':
            board1p.oneLineDown();
            alarm.AlStart();
            break;

         }
      }
   }
}