import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Font;


public class SudokuBoard extends JPanel{

  private JTextField[][] board;
  //private JTextField[][] board = JTextField[9][9];

  public SudokuBoard(){
    super(new GridLayout(9,9));
    board = new JTextField[9][9];

    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[0].length; j++){
        board[i][j] = new JTextField();
        add(board[i][j]);
      }
    }

  }

  public void clear(){
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[0].length; j++){
        board[i][j].setText("");
        //sets the font back to normal (aka non bold)
        board[i][j].setFont(new Font(board[i][j].getFont().getName(), Font.PLAIN,
          board[i][j].getFont().getSize()));
      }
    }
  }

  public int[][] getInput(){
    int[][] temp = new int[9][9];
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[0].length; j++){
        String text = board[i][j].getText();
        if(text.trim().length() == 0)
          temp[i][j] = 0;
        else{
          temp[i][j] = Integer.parseInt(text);
          board[i][j].setFont(new Font(board[i][j].getFont().getName(), Font.BOLD,
                              board[i][j].getFont().getSize()));
        }
      }
    }
    return temp;
  }

  public void setBoard(int[][] inputBoard){
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[0].length; j++){
        board[i][j].setText(""+inputBoard[i][j]);
      }
    }
  }

}
