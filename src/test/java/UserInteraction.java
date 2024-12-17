import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserInteraction {

    @Test
    public void DoNothingWhenAlreadyRevealed() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        // cell to reveal
        int row = 0;
        int col = 0;
        game.revealCell(row, col);

        // capture the cell's initial state after revealing
        Cell[][] grid = game.getGrid();
        Cell cell = grid[row][col];
        boolean initialIsRevealed = cell.isRevealed();
        boolean initialIsFlagged = cell.isFlagged();
        boolean initialHasMine = cell.hasMine();
        int initialNumber = cell.getNumber();

        // reveal cell again
        game.revealCell(row, col);

        // Assert that the cell's state remains unchanged
        assertAll(
                () -> assertEquals(initialIsRevealed, cell.isRevealed(), "isRevealed should remain unchanged"),
                () -> assertEquals(initialIsFlagged, cell.isFlagged(), "isFlagged should remain unchanged"),
                () -> assertEquals(initialHasMine, cell.hasMine(), "hasMine should remain unchanged"),
                () -> assertEquals(initialNumber, cell.getNumber(), "number should remain unchanged")
        );
    }

}
