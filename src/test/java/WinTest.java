import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WinTest {

    @Test
    public void CheckAllCellsAreRevealedExceptMines(){
        WinningConditions winningconditions = new WinningConditions();
        boolean allCellsRevealed = winningconditions.AllCellsRevealed();
        assertTrue(allCellsRevealed, "All cells should be revealed except mines");
    }
}
