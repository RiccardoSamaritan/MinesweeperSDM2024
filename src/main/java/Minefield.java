import java.util.Random;

public class Minefield {
    private int rows;
    private int cols;
    private int n_mine;
    private Cell[][] grid;

    public Minefield(int rows, int cols, int n_mine) {
        this.rows = rows;
        this.cols = cols;
        this.n_mine = n_mine;
        this.grid = new Cell[rows][cols];
        initializeGrid();
        placeMines();
        calculateNumbers();
    }

    private void initializeGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = new Cell();
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < n_mine) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (!grid[row][col].hasMine()) {
                grid[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!grid[row][col].hasMine()) {
                    grid[row][col].setNumber(grid, row, col);
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return cols;
    }

    public int countMines() {
        return n_mine;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean checkNumbers(){
        return true;
    }
}

