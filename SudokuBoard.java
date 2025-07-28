public class SudokuBoard {
    private final SudokuCell[][] grid = new SudokuCell[9][9];

    public SudokuBoard() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                grid[i][j] = new SudokuCell();
    }

    public SudokuCell getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isValidMove(int row, int col, int val) {
        if (val < 1 || val > 9) return false;

        // linha e coluna
        for (int i = 0; i < 9; i++) {
            if (i != col && grid[row][i].getValue() == val) return false;
            if (i != row && grid[i][col].getValue() == val) return false;
        }

        // bloco 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int r = startRow; r < startRow + 3; r++)
            for (int c = startCol; c < startCol + 3; c++)
                if ((r != row || c != col) && grid[r][c].getValue() == val)
                    return false;

        return true;
    }
    
    public boolean setValue(int row, int col, int value) {
        if (isValidMove(row, col, value)) {
            grid[row][col].setValue(value);
            return true;
        }
        return false;
    }

    public boolean isCompleteAndValid() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int val = grid[r][c].getValue();
                if (val == 0 || !isValidMove(r, c, val)) return false;
            }
        return true;
    }
}