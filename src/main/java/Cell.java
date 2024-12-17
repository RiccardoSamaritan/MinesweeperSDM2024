public class Cell {
    private boolean hasMine; //boole for mine
    private boolean isRevealed; //boole for revealed
    private boolean isFlagged; //boole for flag

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

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean reveal() {
        if (!isFlagged) { //can't reveal a flagged cell
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
}

