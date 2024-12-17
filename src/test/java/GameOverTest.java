import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOverTest {

    @Test
    void testGameOverWhenMineIsRevealed() {
        Minefield minefield = new Minefield(9, 9, 10);
        MineSweeper game = new MineSweeper(minefield);
        game.getGrid()[0][0].setMine(true);
        boolean hasMine = game.revealCell(0,0);
        assertAll(
                ()-> assertTrue(game.getGameOver(), "Game over should be true"),
                ()-> assertTrue(hasMine, "hasMine should be true")
        );
    }
}
