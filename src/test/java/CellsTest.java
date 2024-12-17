import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CellsTest {

    // flagged cell cannot be revealed
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

    @Test
    void testRevealedCellCannotBeRevealed() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.revealCell(0, 0);
        assertFalse(game.revealCell(0, 0), "revealCell should not be possible");
    }

    @Test
    void testFlaggedCellCannotBeFlaggedAgain() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.flagCell(0, 0);
        assertFalse(game.flagCell(0, 0), "flagCell should not be possible");
    }

}
