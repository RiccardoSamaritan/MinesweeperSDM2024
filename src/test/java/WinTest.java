import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WinTest {

    @Test
    public void IfGameIsWonAllCellsAreRevealedExceptMines(){
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);

        // reveal all cells except mines
        for (int row = 0; row < minefield.getRows(); row++) {
            for (int col = 0; col < minefield.getColumns(); col++) {
                if (!game.getGrid()[row][col].hasMine()) {
                    game.revealCell(row, col);
                }
            }
        }

        assertTrue(game.checkWinCondition(), "Game should be won");
    }

}
