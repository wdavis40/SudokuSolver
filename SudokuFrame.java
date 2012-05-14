/**
 * A Frame that holds both a SudokuBoard (holding all of the cells a.k.a.
 * JTextAreas) and a SudokuSolver (which is where the solving occurs).
 * This class facilitates the interaction between the SudokuBoard and 
 * SudokuSolver.
 */

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {

  private JPanel boardPanel;
  private JPanel buttonPanel;
  private SudokuSolver solver;
  private SudokuBoard board;
  private JLabel statusText;
  //JLabel statusText is instance variable because we edit it from within
  //the actionListeners, and since it was a local variable an error
  //was thrown. Why is this?
  
  public SudokuFrame (){

    super("Sudoku Solver");
    setSize(600, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(1, 2));

    solver = new SudokuSolver();
    board = new SudokuBoard();
    buttonPanel = new JPanel(new GridLayout(1,2));

    setupBoardPanel();
    setupButtonPanel();

    this.add(board);
    this.add(buttonPanel);
  }

  /**
   * Adds two buttons (Solve and Clear) to the buttonPanel and adds 
   * the appropriate actionListeners to those buttons. 
   */

  private void setupButtonPanel(){
    JButton solveBtn = new JButton("Solve");
    buttonPanel.add(solveBtn);
    JButton clearBtn = new JButton("Clear");
    buttonPanel.add(clearBtn);
    statusText = new JLabel("Please enter your board.");
    buttonPanel.add(statusText);

    //get input from cells, give to the Solver, solve, display solved
    solveBtn.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e){
        int[][] inputFromBoard = board.getInput();
        solver.setBoard(inputFromBoard);
        boolean solved = solver.solve();
        int[][] resultBoard = solver.getBoard();
        if(solved){
          board.setBoard(solver.getBoard());
          statusText.setText("Success!");
        } else {
          statusText.setText("Failure. The given sudoku board is unsolvable");
        }

      }
    });

    //clear the solver board and the TextArea board
    clearBtn.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e){
        solver.clear();
        board.clear();
        //super.setStatus("Please enter your board.");
        statusText.setText("Please enter your board.");
      }
    });
  }

  private void setupBoardPanel(){
    
  }

}
