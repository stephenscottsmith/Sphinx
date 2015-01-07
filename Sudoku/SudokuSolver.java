public class SudokuSolver {
	public static void main(String[] args) {
		if (args[0].length() == 81) {
			SudokuNumber [] sn = createSudokuArray(args[0]);
			SudokuPuzzle puzzle = new SudokuPuzzle(sn);
			puzzle.solve();
			puzzle.printPuzzle();
		} else {
			System.out.println("The sudoku you entered did not have 81 slots!");
		}
	}

	private static SudokuNumber [] createSudokuArray (String sudokuInput) {
		SudokuNumber [] sn = new SudokuNumber[81];

		for (int i = 0; i < sudokuInput.length(); i++) {
			try {
				String numbersString = "" + sudokuInput.charAt(i);
				sn[i] = new SudokuNumber(Integer.parseInt(numbersString), true);
			} catch (IllegalArgumentException e) {
				sn[i] = null;
			}
		}

		return sn;
	}
}