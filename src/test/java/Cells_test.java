import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Cells_test {
    //test 1: game over when reveling a mined cell
    @Test
    void testGameOverWhenMineIsRevealed() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);
        game.getGrid()[0][0].setMine(true);
        boolean hasMine = game.revealCell(0,0);
        assertAll(
                ()-> assertTrue(game.isGameOver(), "Game over should be true"),
                ()-> assertTrue(hasMine, "hasMine should be true")
        );
    }

    //test 2: flagged cell cannot be reveled
    @Test
    void testFlaggedCellCannotBeRevealed() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.flagCell(0, 0);
        assertFalse(game.revealCell(0, 0), "revealCell should not be possible");
    }

    @Test
    void testRevealedCellCannotBeFlagged() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.revealCell(0, 0);
        assertFalse(game.flagCell(0, 0), "flagCell should not be possible");
    }
}
