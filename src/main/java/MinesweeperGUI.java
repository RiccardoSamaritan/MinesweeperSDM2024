import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI extends JFrame {
    private MineSweeper game;
    private JPanel gridPanel;
    private JButton[][] cellButtons;
    private int rows;
    private int cols;
    private int mines;

    private ImageIcon mineIcon = new ImageIcon("mine.png");
    private ImageIcon flagIcon = new ImageIcon("flag.png");

    public MinesweeperGUI(Minefield minefield) {
        this.rows = minefield.getRows();
        this.cols = minefield.getColumns();
        this.mines = minefield.countMines();
        this.game = new MineSweeper(minefield);
        initializeGame(); // Ensure this is called after setting rows and cols
    }

    private void initializeGame() {
        // Setup GUI
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 600);

        // Create grid panel
        gridPanel = new JPanel(new GridLayout(rows, cols));
        cellButtons = new JButton[rows][cols];
        renderGrid();

        // Restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());

        add(gridPanel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void renderGrid() {
        gridPanel.removeAll();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setName("cell-" + row + "-" + col);

                // Mouse listener for left/right clicks
                int finalRow = row;
                int finalCol = col;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            handleLeftClick(finalRow, finalCol, button);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            handleRightClick(finalRow, finalCol, button);
                        }
                    }
                });

                cellButtons[row][col] = button;
                gridPanel.add(button);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void handleLeftClick(int row, int col, JButton button) {
        if (!game.revealCell(row, col)) return;

        Cell cell = game.getGrid()[row][col];

        if (cell.hasMine()) {
            button.setIcon(mineIcon);
            button.setBackground(Color.RED);
            endGame(false);
        } else {
            button.setText(cell.getNumber() > 0 ? String.valueOf(cell.getNumber()) : "");
            button.setEnabled(false);
        }

        if (checkWinCondition()) {
            endGame(true);
        }
    }

    private void handleRightClick(int row, int col, JButton button) {
        if (game.flagCell(row, col)) {
            button.setIcon(button.getIcon() == null ? flagIcon : null);
        }
    }

    private void revealAllCells() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = game.getGrid()[row][col];
                JButton button = cellButtons[row][col];

                if (cell.hasMine()) {
                    button.setIcon(mineIcon);
                } else if (cell.getNumber() > 0) {
                    button.setText(String.valueOf(cell.getNumber()));
                }

                button.setEnabled(false);
            }
        }
    }

    private boolean checkWinCondition() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = game.getGrid()[row][col];
                if (!cell.hasMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame(boolean isWin) {
        String message = isWin ? "Congratulations! You won!" : "Game Over! You hit a mine!";
        JOptionPane.showMessageDialog(this, message);
        revealAllCells();
    }

    private void restartGame() {
        initializeGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Minefield minefield = new Minefield(9, 9, 10);
            new MinesweeperGUI(minefield);
        });
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public JButton[][] getCellButtons() {
        return cellButtons;
    }

    public MineSweeper getGame() {
        return game;
    }

    public ImageIcon getMineIcon() {
        return mineIcon;
    }

    public ImageIcon getFlagIcon() {
        return flagIcon;
    }
}