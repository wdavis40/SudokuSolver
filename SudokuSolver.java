
public class SudokuSolver {
	protected int[][] board = new int[9][9];
	private boolean solved = false;

	public SudokuSolver() {
	}

	public int[][] solve(int[][] newBoard) {
		board = newBoard;
		solved = this.solve(0, 0, board);
		if(solved)
			return board;
		else
			return newBoard;
	}

	private boolean solve(int i, int j, int[][] board) {
		if (j == 9) {
			j = 0;
			if (++i == 9)
				return true;
		}
		if (board[i][j] != 0)
			return solve(i, j + 1, board);
		for (int val = 1; val <= 9; val++) {
			if (correct(i, j, val, board)) {
				board[i][j] = val;
				if (solve(i, j + 1, board))
					return true;
			}
		}
		board[i][j] = 0;
		return false;

	}

	boolean correct(int i, int j, int val, int[][] board2) {
		for (int temp = 0; temp < 9; temp++) {
			if (temp == i)
				;
			else if (board2[temp][j] == val)
				return false;
			else
				;
		}
		for (int temp = 0; temp < 9; temp++) {
			if (temp == j)
				;
			else if (board2[i][temp] == val)
				return false;
			else
				;
		}
		int rowNum = (i / 3) * 3;
		int colNum = (j / 3) * 3;
		for (int temp = rowNum; temp < rowNum + 3; temp++) {
			for (int temp2 = colNum; temp2 < colNum + 3; temp2++)
				if (temp == i && temp2 == j)
					;
				else {
					if (board2[temp][temp2] == val)
						return false;
				}

		}
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
