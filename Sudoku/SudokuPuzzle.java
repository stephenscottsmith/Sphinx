public class SudokuPuzzle {
	private SudokuNumber [] sn;
	private final String lineDivider = " _____ _____ _____ _____ _____ _____ _____ _____ _____ ";
	private final String boxDivider =  "|     |     |     |     |     |     |     |     |     |";
	private final String boxBottom = "|_____|_____|_____|_____|_____|_____|_____|_____|_____|";

	public SudokuPuzzle (SudokuNumber [] sn) {
		this.sn = sn;
	}

	public void solve () {
		System.out.println("Solving the puzzle...");

		int index = 0;
		boolean solved = false;
		
		while (!solved) {
			boolean backtracked = false;

			if (sn[index] == null) {
				sn[index] = new SudokuNumber(1, false);

				while (!isGuessValid(index, sn[index].value)) {
					sn[index].value += 1;
					if (sn[index].value > 9) {
						index = backtrack(index);
						backtracked = true;
						break;
					}
				}

				if (!backtracked) {
					index++;
				}
			} else if (!sn[index].isPermanent) {
				sn[index].value += 1;

				if (sn[index].value > 9) {
					index = backtrack(index);
					backtracked = true;
				} else {
					while (!isGuessValid(index, sn[index].value)) {
						sn[index].value += 1;
						if (sn[index].value > 9) {
							index = backtrack(index);
							backtracked = true;
							break;
						}
					}
				}

				if (!backtracked) {
					index++;
				}
			} else {
				index++;
			}

			if (index == 81) {
				solved = true;
			}
		}
	}

	public int backtrack (int index) {
		sn[index] = null;

		do {
			index--;
		} while (sn[index].isPermanent);

		return index;
	}

	public boolean squareOfIndexIsValid (int index, int value) {
		int row = index / 9,
			col = index % 9,
			startingIndex = index - (row % 3)*9 - (col % 3);
		int [] numbers = new int [10];

		for (int i = 0; i < 3; i++) {
			int startPoint = startingIndex + (9 * i);
			for (int j = startPoint; j < (startPoint + 3); j++) {
				if (sn[j] != null) {
					numbers[sn[j].value] += 1;
					if (numbers[sn[j].value] > 1) {
						return false;
					}
				}
			}
		}

		return true;		
	}

	public boolean columnOfIndexIsValid (int index, int value) {
		int start = index % 9;
		int [] numbers = new int [10];

		for (int i = start; i < 81; i += 9) {
			if (sn[i] != null) {
				numbers[sn[i].value] += 1;
				if (numbers[sn[i].value] > 1) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean rowOfIndexIsValid (int index, int value) {
		int row = index / 9,
			start = row * 9,
			end = start + 9;
		int [] numbers = new int [10];

		for (int i = start; i < end; i++) {
			if (sn[i] != null) {
				numbers[sn[i].value] += 1;
				if (numbers[sn[i].value] > 1) {
					return false;
				}
			}	
		}

		return true;
	}

	public boolean isGuessValid (int index, int value) {
		return columnOfIndexIsValid(index, value) && 
			   rowOfIndexIsValid(index, value) &&
			   squareOfIndexIsValid(index, value);
	}

	public void printPuzzle () {
		String numberLine = "";
		System.out.println(lineDivider);

		for (int i = 0; i < sn.length; i++) {
			numberLine += "|  " + ((sn[i] == null || sn[i].value == 0) ? "_" : sn[i].value) + "  ";
			if (i % 9 == 8) {
				numberLine += "|";
				String boxes = boxDivider + "\n" + numberLine + "\n" + boxBottom;
				System.out.println(boxes);
				numberLine = "";
			}
		}
	}
}