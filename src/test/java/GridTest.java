import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GridTest {

    // test 1: grid must be 9x9
    @Test
    void gridShouldBe9x9() {
        Minefield minefield = new Minefield(9,9,10);
        int rows = minefield.getRows();
        int columns = minefield.getColumns();
        assertAll(
                () -> assertEquals(9, rows, "Rows should be 9"),
                () -> assertEquals(9, columns, "Columns should be 9")
        );
    }

    // test 2: grid must contain exactly 10 mines
    @Test
    void gridShouldContain10Mines() {
        Minefield minefield = new Minefield(9,9, 10);
        int mines = minefield.countMines();
        assertEquals(10, mines, "Mines should be exactly 10");
    }

    // test 3: numbers inside the cell must be correct
    @Test
    void numbersInCellsShouldBeCorrect() {
        Minefield minefield = new Minefield(9,9,10);
        boolean numbersCorrect = minefield.checkNumbers();
        assertTrue(numbersCorrect, "Numbers in cells should be correct");
    }
}