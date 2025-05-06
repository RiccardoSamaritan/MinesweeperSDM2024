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
        if (isRevealed) {
            return hasMine; // Reveal is idempotent
        }
        if (isFlagged) {
            return false; // Can't reveal flagged cell
        }
        isRevealed = true;
        return hasMine;
    }


    public boolean flag() {
        if (!isRevealed) {
            isFlagged = !isFlagged; // Inverts the cell state of 'isFlagged' (true -> false or false -> true) (if the cell was flagged it becomes unflagged)
            return true;
        }
        return false;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}