public class Minefield {
    int x;
    int y;
    int n_mine;

    Minefield(int x, int y, int n_mine) {
        this.x = x;
        this.y = y;
        this.n_mine = n_mine;
    }

    int getRows() {
        return x;
    }

    int getColumns() {
        return y;
    }

    int countMines() {
        return n_mine;
    }

    boolean checkNumbers() {
        return true;
    }

}
