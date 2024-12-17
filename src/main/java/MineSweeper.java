import java.util.Random;

public class MineSweeper {
    private Minefield minefield;
    private Cell[][] grid;
    private boolean gameOver;

    //creates the minefield
    public MineSweeper(Minefield minefield) {
        this.minefield = minefield;
        this.grid = new Cell[minefield.getRows()][minefield.getColumns()];
        this.gameOver = false;

        initializeGrid(); //calls methods initializeGrid to initialize empty cells
    }

    //initializes all the cells
    private void initializeGrid() {
        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {
                grid[i][j] = new Cell();
            }
        }
        placeMines();
        // Set the number of mines around each cell
        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {
                grid[i][j].setNumber(grid, i, j);
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }


    // places mines randomly in the grid
    private void placeMines() {
        Random rand = new Random();
        int minesToPlace = minefield.countMines();

        // Place the mines randomly in the grid
        while (minesToPlace > 0) {
            int row = rand.nextInt(minefield.getRows());  // Random row index
            int col = rand.nextInt(minefield.getColumns());  // Random column index

            Cell cell = grid[row][col];
            if (!cell.hasMine()) {  // if the cell doesn't already have a mine
                cell.setMine(true);  // set the cell to have a mine
                minesToPlace--;  // decrease the remaining mines to place
            }
        }
    }


    public boolean revealCell(int row, int col) {
        if (gameOver || !isValidCell(row, col)) return false;

        Cell cell = grid[row][col];
        if (cell.isFlagged()) return false;

        boolean hasMine = cell.reveal(); //.reveal returns true if the cell has a mine
        if (hasMine) {
            gameOver = true;
        }
        return true; // returns true if the cell can be reveled, regardless of whether it contains a mine
    }

    public boolean flagCell(int row, int col) {
        if (gameOver || !isValidCell(row, col)) return false;

        Cell cell = grid[row][col];
        return cell.flag();
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < minefield.getRows() && col >= 0 && col < minefield.getColumns();
    }

    public boolean isGameOver() {
        return gameOver;
    }

}

