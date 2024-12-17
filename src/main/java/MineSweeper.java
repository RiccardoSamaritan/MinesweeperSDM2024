import java.util.Random;

public class MineSweeper {
    private Minefield minefield;
    private Cell[][] grid;
    private boolean gameOver;
    private boolean gameWon;
    private int minesCounter;

    //creates the minefield
    public MineSweeper(Minefield minefield) {
        this.minefield = minefield;
        grid = new Cell[minefield.getRows()][minefield.getColumns()];
        gameOver = false;
        gameWon = false;
        minesCounter = minefield.countMines();
        initializeGrid(); //calls methods initializeGrid to initialize empty cells
    }

    //initializes all the cells
    private void initializeGrid() {
        for (int i = 0; i < minefield.getRows(); i++) {
            for (int j = 0; j < minefield.getColumns(); j++) {
                grid[i][j] = new Cell();
            }
        }
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

    // Reset the game
    public void resetGame() {
        initializeGrid();
        placeMines();
        gameOver = false;
        gameWon = false;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    // Game over when a mine is revealed, reveals all mines
    public void GameOver() {
        gameOver = true;

        for (int i = 0; i < minefield.getColumns(); i++) {
            for (int j = 0; j < minefield.getRows(); j++) {
                Cell cell = grid[i][j];

                if (cell.hasMine()) {
                    cell.reveal();
                }
            }
        }
    }

    public boolean getGameWon() {
        return gameWon;
    }

    public void GameWon() {
        if(gameOver){
            return;
        }
        WinningConditions winningConditions = new WinningConditions();
        if (winningConditions.AllCellsRevealed()) {
            gameWon = true;
            // Flag all the cells not already flagged
            for (int i = 0; i < minefield.getColumns(); i++) {
                for (int j = 0; j < minefield.getRows(); j++) {
                    Cell cell = grid[i][j];
                    if (!cell.isFlagged()) {
                        cell.flag();
                    }
                }
            }
        }
    }
}

