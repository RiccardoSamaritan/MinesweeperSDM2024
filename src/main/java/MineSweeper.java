public class MineSweeper {
    private Minefield minefield;
    Cell[][] grid;
    private boolean gameOver;

    public MineSweeper(Minefield minefield) {
        this.minefield = minefield;
        this.grid = minefield.getGrid();
        gameOver = false;
        calculateNumbers();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void printGrid(){
        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {

                Object[] cellInfo = new Object[4];
                cellInfo[0] = grid[i][j].getNumber();
                cellInfo[1] = grid[i][j].isRevealed();
                cellInfo[2] = grid[i][j].isFlagged();
                cellInfo[3] = grid[i][j].hasMine();

                System.out.print(cellInfo[3] + " ");
            }
            System.out.println();
        }
    }

    public void calculateNumbers() {
        for (int row = 0; row < minefield.getRows(); row++) {
            for (int col = 0; col < minefield.getColumns(); col++) {
                if (!grid[row][col].hasMine()) {
                    int adjacentMines = countAdjacentMines(grid, row, col);
                    grid[row][col].setNumber(adjacentMines);
                }
            }
        }
    }

    private int countAdjacentMines(Cell[][] grid, int row, int col) {
        int mineCount = 0;
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                if (grid[newRow][newCol].hasMine()) {
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    private void revealAdjacentCells(int row, int col) {
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                if (!grid[newRow][newCol].isRevealed()) {
                    revealCell(newRow, newCol);
                }
            }
        }
    }

    public boolean revealCell(int row, int col) {
        if (gameOver || !isValidCell(row, col)) return false;

        Cell cell = grid[row][col];
        if (cell.isFlagged()) return false;

        boolean hasMine = cell.reveal(); // .reveal returns true if the cell has a mine
        if (hasMine) {
            gameOver = true;
        }

        if (cell.getNumber() == 0) {
            revealAdjacentCells(row, col);
        }
        return true; // returns true if the cell can be revealed, regardless of whether it contains a mine
    }

    public boolean flagCell(int row, int col) {
        if (gameOver || !isValidCell(row, col)) return false;

        Cell cell = grid[row][col];
        return cell.flag();
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < minefield.getRows() && col >= 0 && col < minefield.getColumns();
    }

    public void resetGame() {
        minefield.initializeGrid();
        minefield.placeMines();
        this.grid = minefield.getGrid();
        gameOver = false;
        calculateNumbers();
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean checkWinCondition() {
        for (int row = 0; row < minefield.getRows(); row++) {
            for (int col = 0; col < minefield.getColumns(); col++) {
                Cell cell = getGrid()[row][col];
                if (!cell.hasMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }
}