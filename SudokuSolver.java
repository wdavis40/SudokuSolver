public class SudokuSolver {
  protected int[][] board = new int[9][9];
  private boolean solved = false;

  public SudokuSolver(int[][] inputBoard) {
    board = inputBoard;
  }

  /** 
   * Solve method that solves the sudoku board by calling the recursive
   * solve method.
   *
   * @param newBoard  the input board to solve
   */

  public boolean solve(){
    int[][] tempBoard = new int[9][9];
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[0].length; j++){
        tempBoard[i][j] = board[i][j];
      }
    }
    boolean result = solve(0,0,tempBoard);
    if(result)
      board = tempBoard;
    return result;
  }

  /**
   * Recursive method that solves the board and fills in the board
   * instance variable with the correct values. Returns true/false, if
   * the board was able to be solved.
   *
   * @return whether the board could be solved or not
   */

  private boolean solve(int i, int j, int[][] tempBoard) {
    if (j == 9) {
      j = 0;
      if (++i == 9)
        return true;
    }
    if (tempBoard[i][j] != 0)
      return solve(i, j + 1, tempBoard);
    /* try each value in this spot */
    for (int val = 1; val <= 9; val++) {
      if (correct(i, j, val, tempBoard)) {
        tempBoard[i][j] = val;
        if (solve(i, j + 1, tempBoard))
          return true;
      }
    }
    tempBoard[i][j] = 0;
    return false;

  }

  /**
   * Tests if the given value in the given spot is correct by checking
   * the rest of the values around it (the row, col, and box).
   *
   */

  public boolean correct(int row, int col, int val, int[][] tempBoard){
    /* check the rows and columns to see if the values are valid by
       sudoku rules */
    for(int i = 0; i < 9; i++){
      // Check the column
      if(i != col && tempBoard[i][col] == val)
        return false;
      // Check the row
      if(i != row && tempBoard[row][i] == val)
        return false;
    }

    /* check the current box for validity */
    
    //find the first row and col
    int rowNum = (row/3)*3;
    int colNum = (col/3)*3;

    for(int i = rowNum; i < rowNum + 3; i++){
      for(int j = colNum; j < colNum + 3; j++){
        if( i != row && j != col && tempBoard[i][j] == val)
          return false;
      }
    }

    //return true if we find that the value isn't invalid
    return true;

  }

  public int[][] getBoard() {
    return board;
  }
  
  public boolean getResult(){
    return solved;
  }
  public void setResult(Boolean set){
    solved = set;
  }
}
