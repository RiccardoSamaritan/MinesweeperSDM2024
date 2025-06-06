import java.time.Duration;
import java.time.Instant;

public class MineSweeper {
    private Minefield minefield;
    private Cell[][] grid;
    private boolean gameOver;
    private Instant startTime;
    private Instant endTime;

    public MineSweeper(Minefield minefield) {
        this.minefield = minefield;
        this.grid = minefield.getGrid();
        gameOver = false;
        startTime = null;
        endTime = null;
        calculateNumbers();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void printGrid() {
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
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                Cell cell = grid[row][col];
                if (cell.hasMine()) continue;

                int count = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) continue; // ✅ Skip the cell itself
                        int r = row + dr;
                        int c = col + dc;
                        if (isValidCell(r, c) && grid[r][c].hasMine()) {
                            count++;
                        }
                    }
                }
                cell.setNumber(count);
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

        if (cell.isRevealed()) return true; // allow re-reveal
        if (cell.isFlagged()) return false;

        if (startTime == null) {
            startTime = Instant.now();
        }

        boolean hasMine = cell.reveal(); // this now always returns hasMine
        if (hasMine) {
            gameOver = true;
            endTime = Instant.now();
        }

        if (cell.getNumber() == 0) {
            revealAdjacentCells(row, col);
        }

        if (checkWinCondition()) {
            gameOver = true;
            endTime = Instant.now();
        }

        return true;
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
        startTime = null;
        endTime = null;
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

    public Duration getElapsedTime() {
        if (startTime == null) {
            return Duration.ZERO;
        }
        if (endTime == null) {
            return Duration.between(startTime, Instant.now());
        }
        return Duration.between(startTime, endTime);
    }
}
