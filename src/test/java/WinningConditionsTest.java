import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class WinningConditionsTest {

    @Test
    public void CheckAllCellsAreRevealedExceptMines(){
        WinningConditions winningconditions = new WinningConditions();
        boolean allCellsRevealed = winningconditions.checkAllCellsRevealed();
        assertTrue(allCellsRevealed, "All cells should be revealed except mines");
    }
}
