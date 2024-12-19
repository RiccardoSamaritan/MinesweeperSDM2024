public class Cell {
    private boolean hasMine; //boole for mine
    private boolean isRevealed; //boole for revealed
    private boolean isFlagged; //boole for flag
    private int number; //number of mines around the cell

    //default cell empty
    public Cell() {
        this.hasMine = false;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    //Check if the cell already has a mine
    public boolean hasMine() {
        return hasMine;
    }

    // Set the mine in the cell
    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    // Check if the cell is revealed
    public boolean isRevealed() {
        return isRevealed;
    }

    // Check if the cell is flagged
    public boolean isFlagged() {
        return isFlagged;
    }

    // Get the number of mines around the cell
    public int getNumber() {
        return number;
    }


    public boolean reveal() {
        if (!isFlagged && !isRevealed) { //can't reveal a flagged cell or an already revealed cell
            isRevealed = true;
            return hasMine; // True if the cell has a mine
        }
        return false;
    }

    public boolean flag() {
        if (!isRevealed) {
            isFlagged = !isFlagged; // Inverts the cell state of 'isFlagged' (true -> false or false -> true) (if the cell was flagged it becomes unflagged)
            return true;
        }
        return false;
    }

    public void setNumber(Cell[][] grid, int row, int col) {

        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};
        int mineCount = 0;

        for (int i = 0; i < 8; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            // Check if the new indices are within the grid boundaries
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                if (grid[newRow][newCol].hasMine()) {
                    mineCount++;
                }
            }
        }

        this.number = mineCount;
    }
}