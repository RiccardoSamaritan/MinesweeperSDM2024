import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GUITest {
    private MinesweeperGUI gui;
    private Minefield minefield;

    @BeforeEach
    public void setUp() {
        minefield = new Minefield(9, 9, 10);
        gui = new MinesweeperGUI(minefield);
    }

    @Test
    void testGridRendering() {
        assertNotNull(gui.getGridPanel(), "Grid panel should not be null");
        assertEquals(gui.getRows() * gui.getCols(), gui.getGridPanel().getComponentCount(), "Grid should have the correct number of buttons");
    }

    @Test
    void testRevealCell() {
        JButton button = gui.getCellButtons()[0][0];
        button.doClick();

        Cell cell = gui.getGame().getGrid()[0][0];
        if (cell.hasMine()) {
            assertEquals(gui.getMineIcon().getIcon(), button.getIcon(), "Mine cell should display the mine icon");
        } else if (cell.getNumber() > 0) {
            assertEquals(String.valueOf(cell.getNumber()), button.getText(), "Number cell should display the correct number");
        } else {
            assertEquals("", button.getText(), "Empty cells should remain blank");
        }
    }

    @Test
    void testFlaggingCell() {
        JButton button = gui.getCellButtons()[0][0];

        // Right-click to flag
        MouseEvent rightClick = new MouseEvent(button, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), InputEvent.BUTTON3_DOWN_MASK, 0, 0, 1, false, MouseEvent.BUTTON3);
        button.dispatchEvent(rightClick);
        assertEquals(gui.getFlagIcon().getIcon().getDescription(), ((ImageIcon) button.getIcon()).getDescription(), "Flagged cell should display the flag icon");

        // Right-click again to unflag
        button.dispatchEvent(rightClick);
        assertEquals(gui.getCoveredIcon().getIcon().getDescription(), (((ImageIcon) button.getIcon()).getDescription()), "Unflagged cell should have covereed icon");

    }

    @Test
    void testRestart() {
        JButton button = gui.getCellButtons()[0][0];
        button.doClick(); // Simulate reveal

        JButton restartButton = new JButton();
        restartButton.doClick();

        for (int row = 0; row < gui.getRows(); row++) {
            for (int col = 0; col < gui.getCols(); col++) {
                JButton b = gui.getCellButtons()[row][col];
                assertEquals("", b.getText(), "Cells should be reset after restart");
                assertNull(b.getIcon(), "Cells should have no icons after restart");
                assertTrue(b.isEnabled(), "Cells should be enabled after restart");
            }
        }
    }
}