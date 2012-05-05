import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
class Cell extends JTextField {
	private int number;

	public Cell() {
		number = 0;
	}

	public Cell(int columns) {
		setColumns(columns);
	}

	public void setNumber(int number) {
		this.number = number;
		if (this.number == 0)
			this.setText("");
		else
			this.setText("" + number);
	}

	public int getNumber() {
		return number;
	}
}

@SuppressWarnings("serial")
class Box extends JPanel {
	public final int CELL_COUNT = 9;
	public ArrayList<Cell> temp1 = new ArrayList<Cell>();
	public ArrayList<Cell> temp2 = new ArrayList<Cell>();
	public ArrayList<Cell> temp3 = new ArrayList<Cell>();

	public Cell[] cells = new Cell[CELL_COUNT];

	public Box() {
		this.setLayout(new GridLayout(3, 3));
		this.setBorder(new LineBorder(Color.BLACK, 2));
		for (int i = 0; i < CELL_COUNT; i++) {
			Cell cell = new Cell();
			cells[i] = cell;
			if (i < 3)
				temp1.add(cell);
			else if (i < 6)
				temp2.add(cell);
			else
				temp3.add(cell);
			this.add(cells[i]);
			// Cell cell = new Cell();
			// cells[i] = cell;
			// temp.add(cell);
			// this.add(cells[i]);
		}
	}

	public ArrayList<Cell> getCells1() {
		return temp1;
	}

	public ArrayList<Cell> getCells2() {
		return temp2;
	}

	public ArrayList<Cell> getCells3() {
		return temp3;
	}
}

@SuppressWarnings("serial")
class resultBox extends JPanel {

}

@SuppressWarnings("serial")
public class Board extends JFrame {
	private SudokuSolver play = new SudokuSolver();
	public final int BOX_COUNT = 9;
	public Box[] boxes = new Box[BOX_COUNT];
	public int[][] board = new int[BOX_COUNT][BOX_COUNT];
	public ArrayList<Cell> compileList = new ArrayList<Cell>();
	public JTextField solvedField;
	public JLabel solvedLabel;
	public Square solvedSquare;
	public Boolean hasBoard = false;
	public Cell row;
	public Cell col;
	public ArrayList<Cell> compileListTemp1 = new ArrayList<Cell>();
	public ArrayList<Cell> compileListTemp2 = new ArrayList<Cell>();
	public ArrayList<Cell> compileListTemp3 = new ArrayList<Cell>();

	public Board() {
		super("Sudoku");
		setSize(600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2));

		JPanel p1 = new JPanel(new GridLayout(3, 3));
		JPanel p2 = new JPanel(new GridLayout(2, 1));
		for (int a = 0; a < 3; a++) {
			for (int i = 0; i < 3; i++) {
				Box box = new Box();
				boxes[(a) * 3 + i] = box;
				for (int j = 0; j < 3; j++) {
					compileListTemp1.add(box.getCells1().get(j));
				}
				for (int x = 0; x < 3; x++) {
					compileListTemp2.add(box.getCells2().get(x));
				}
				for (int y = 0; y < 3; y++) {
					compileListTemp3.add(box.getCells3().get(y));
				}
				p1.add(boxes[(a) * 3 + i]);
			}
			compileList.addAll(compileListTemp1);
			compileList.addAll(compileListTemp2);
			compileList.addAll(compileListTemp3);
			compileListTemp1.clear();
			compileListTemp2.clear();
			compileListTemp3.clear();
		}

		this.add(p1);

		JPanel p21 = new JPanel(new FlowLayout());
		JPanel p22 = new JPanel(new FlowLayout());
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		JButton solveOneButton = new JButton("Solve Selected Cell:");
		row = new Cell(1);
		col = new Cell(1);
		JLabel rowLabel = new JLabel("Row: ");
		JLabel colLabel = new JLabel("Column: ");
		solvedLabel = new JLabel("Please input your numbers.");
		p21.add(solveButton);
		p21.add(clearButton);
		p21.add(solveOneButton);
		p21.add(rowLabel);
		p21.add(row);
		p21.add(colLabel);
		p21.add(col);
		p22.add(solvedLabel);
		p2.add(p21);
		p2.add(p22);

		this.add(p2);

		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (hasBoard) {
					showSolved();
				} else {
					compileData();
					if (check()) {
						board = play.solve(board);
						testSolved();
						showSolved();
					} else {
						testSolved();
					}
				}

				/*
				 * if (!hasBoard) { compileData(); board = play.solve(board);
				 * testSolved(); if (play.getResult()) showSolved(); } else {
				 * testSolved(); if (play.getResult()) showSolved(); }
				 * 
				 * if(!hasBoard){ compileData(); board = play.solve(board); } if
				 * (check()) { board = play.solve(board); testSolved();
				 * showSolved(); } else { testSolved(); }
				 */
			}

		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clear();
			}
		});

		solveOneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (row.getNumber()==0 || col.getNumber()==0) {
					;
				} else {
					if (hasBoard) {
						showSolvedOne();
					} else {
						compileData();
						if (check()) {
							board = play.solve(board);
							testSolved();
							showSolvedOne();
						}
					}
				}
			}
		});

	}

	protected void showSolvedOne() {
		int rowNum = Integer.parseInt(row.getText()) - 1;
		int colNum = Integer.parseInt(col.getText()) - 1;
		int z = 0;
		outer: for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == rowNum && j == colNum) {
					break outer;
				}
				z++;
			}
		}
		compileList.get(z).setNumber(board[rowNum][colNum]);
	}

	protected boolean check() {
		SudokuSolver solver = new SudokuSolver();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != 0) {
					if (!solver.correct(i, j, board[i][j], board)) {
						play.setResult(false);
						return false;
					}
				}
			}
		}
		play.setResult(true);
		return true;
	}

	public void compileData() {
		int temp;
		int z = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (compileList.get(z).getText().equals("")) {
					board[i][j] = 0;
				} else {
					temp = Integer.parseInt(compileList.get(z).getText());
					board[i][j] = temp;
					compileList.get(z).setNumber(temp);
				}
				z++;
			}
		}
		/*
		 * for (int y = 0; y < 3; y++) { for (int x = 0; x < 3; x++) { for (int
		 * i = y * 3; i < y * 3 + 3; i++) { for (int j = x * 3; j < x * 3 + 3;
		 * j++) { if (compileList.get(z).getText().equals("")) { board[i][j] =
		 * 0; } else { temp = Integer.parseInt(compileList.get(z) .getText());
		 * board[i][j] = temp; compileList.get(z).setNumber(temp); } z++; } } }
		 * }
		 */
	}
	
	public void checkNumbers() {
		
	}

	public void clear() {
		for (int i = 0; i < 81; i++) {
			compileList.get(i).setNumber(0);
		}
		solvedLabel.setText("Please input your numbers.");
		hasBoard = false;
		row.setNumber(0);
		col.setNumber(0);
	}

	public void testSolved() {
		if (play.getResult()) {
			solvedLabel.setText("The puzzle was solved!");
			// solvedLabel.set
		} else {
			solvedLabel.setText("The puzzle was not solved.");
		}
	}

	public void showSolved() {
		int z = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				compileList.get(z).setNumber(board[i][j]);
				z++;
			}
		}
		/*
		 * for (int y = 0; y < 3; y++) { for (int x = 0; x < 3; x++) { for (int
		 * i = y * 3; i < y * 3 + 3; i++) { for (int j = x * 3; j < x * 3 + 3;
		 * j++) { compileList.get(z).setNumber(board[i][j]); z++; } } } }
		 */
	}

}
