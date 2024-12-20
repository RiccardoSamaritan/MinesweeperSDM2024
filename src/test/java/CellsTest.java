import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CellsTest {

    // Reveal an unrevealed cell
    @Test
    void testRevealUnrevealedCell() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        assertTrue(game.revealCell(0, 0), "Cell should be revealed");
    }

    // Reveal an already revealed cell
    @Test
    void testRevealAlreadyRevealedCell() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.revealCell(0, 0);
        assertTrue(game.revealCell(0, 0), "Cell should be revealed");
    }

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
    void testCellOutOfRowRangeShouldNotBeRevealed(){
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        assertFalse(game.revealCell(10, 0), "Reveal should not be possible");
    }

    @Test
    void testCellOutOfColRangeShouldNotBeRevealed(){
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        assertFalse(game.revealCell(0, 10), "Reveal should not be possible");
    }

    // If there's a mine in a neighbour cell, getNumber() should return 3
    @Test
    void testGetNumberWithMineInNeighbourCells() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.getGrid()[0][1].setMine(true);
        game.getGrid()[1][0].setMine(true);
        game.getGrid()[1][1].setMine(true);
        game.calculateNumbers();

        game.getGrid()[0][0].getNumber();
        assertEquals(3, game.getGrid()[0][0].getNumber(), "Number of mines around the cell should be 3");
    }

    // Only mines in neighbour cells should be counted
    @Test
    void testGetNumberShouldCountOnlyMinesInNeighbourCells() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.getGrid()[0][1].setMine(true);
        game.getGrid()[1][0].setMine(true);
        game.getGrid()[1][1].setMine(true);
        game.getGrid()[1][2].setMine(true);
        game.getGrid()[2][1].setMine(true);
        game.calculateNumbers();
        game.getGrid()[0][0].getNumber();
        assertEquals(3, game.getGrid()[0][0].getNumber(), "Number of mines around the cell should be 3");
    }

    // If there's no mine in neighbour cells, getNumber() should return 0
    @Test
    void testGetNumberWithNoMineInNeighbourCells() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.getGrid()[3][3].setMine(false);
        game.getGrid()[3][4].setMine(false);
        game.getGrid()[4][3].setMine(false);
        game.getGrid()[3][5].setMine(false);
        game.getGrid()[5][3].setMine(false);
        game.getGrid()[4][5].setMine(false);
        game.getGrid()[5][4].setMine(false);
        game.getGrid()[5][5].setMine(false);
        game.calculateNumbers();
        assertEquals(0, game.getGrid()[4][4].getNumber(), "Number of mines around the cell should be 0");
    }

    // If cell number is 0, revealCell() should call revealNeighbours()
    @Test
    void testRevealCellCallsRevealNeighbours() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.getGrid()[0][1].setMine(false);
        game.getGrid()[1][0].setMine(false);
        game.getGrid()[1][1].setMine(false);
        game.calculateNumbers();
        game.getGrid()[0][0].getNumber();
        game.revealCell(0, 0);
        assertTrue(game.getGrid()[0][1].isRevealed(), "Neighbour cell should be revealed");
        assertTrue(game.getGrid()[1][0].isRevealed(), "Neighbour cell should be revealed");
        assertTrue(game.getGrid()[1][1].isRevealed(), "Neighbour cell should be revealed");
    }

    // If a cell number is not 0, revealCell() should not call revealNeighbours()
    @Test
    void testRevealCellDoesNotCallRevealNeighbours() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        game.getGrid()[0][1].setMine(true);
        game.getGrid()[1][0].setMine(false);
        game.getGrid()[1][1].setMine(false);

        game.getGrid()[0][0].getNumber();
        game.revealCell(0, 0);
        assertFalse(game.getGrid()[0][1].isRevealed(), "Neighbour cell should not be revealed");
        assertFalse(game.getGrid()[1][0].isRevealed(), "Neighbour cell should not be revealed");
        assertFalse(game.getGrid()[1][1].isRevealed(), "Neighbour cell should not be revealed");
    }
}


