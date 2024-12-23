import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.time.Duration;
import java.time.Instant;

public class MinesweeperGUI extends JFrame {
    private MineSweeper game;
    private JPanel gridPanel;
    private JLabel timerLabel;
    private JLabel flagCounterLabel;
    private JButton[][] cellButtons;
    private int rows;
    private int cols;
    private int mines;
    private IconWrapper mineIcon;
    private IconWrapper flagIcon;
    private IconWrapper emptyIcon;
    private IconWrapper coveredIcon;
    private IconWrapper oneIcon;
    private IconWrapper twoIcon;
    private IconWrapper threeIcon;
    private IconWrapper fourIcon;
    private IconWrapper fiveIcon;
    private IconWrapper sixIcon;
    private IconWrapper sevenIcon;
    private IconWrapper eightIcon;
    private Timer timer;
    private Instant startTime;
    private boolean isTimerRunning = false;
    private int flagCount = 0;



    public MinesweeperGUI(Minefield minefield) {
        this.rows = minefield.getRows();
        this.cols = minefield.getColumns();
        this.mines = minefield.countMines();
        this.game = new MineSweeper(minefield);
        loadIcons();
        initializeGame();
    }

    private void loadIcons() {
        try {
            BufferedImage mineImage = ImageIO.read(getClass().getResource("/images/mine.png"));
            BufferedImage flagImage = ImageIO.read(getClass().getResource("/images/flag.png"));
            BufferedImage emptyImage = ImageIO.read(getClass().getResource("/images/empty.png"));
            BufferedImage coveredImage = ImageIO.read(getClass().getResource("/images/cell.png"));
            BufferedImage oneImage = ImageIO.read(getClass().getResource("/images/1.png"));
            BufferedImage twoImage = ImageIO.read(getClass().getResource("/images/2.png"));
            BufferedImage threeImage = ImageIO.read(getClass().getResource("/images/3.png"));
            BufferedImage fourImage = ImageIO.read(getClass().getResource("/images/4.png"));
            BufferedImage fiveImage = ImageIO.read(getClass().getResource("/images/5.png"));
            BufferedImage sixImage = ImageIO.read(getClass().getResource("/images/6.png"));
            BufferedImage sevenImage = ImageIO.read(getClass().getResource("/images/7.png"));
            BufferedImage eightImage = ImageIO.read(getClass().getResource("/images/8.png"));

            mineIcon = new IconWrapper(new ImageIcon(mineImage));
            flagIcon = new IconWrapper(new ImageIcon(flagImage));
            emptyIcon = new IconWrapper(new ImageIcon(emptyImage));
            coveredIcon = new IconWrapper(new ImageIcon(coveredImage));
            oneIcon = new IconWrapper(new ImageIcon(oneImage));
            twoIcon = new IconWrapper(new ImageIcon(twoImage));
            threeIcon = new IconWrapper(new ImageIcon(threeImage));
            fourIcon = new IconWrapper(new ImageIcon(fourImage));
            fiveIcon = new IconWrapper(new ImageIcon(fiveImage));
            sixIcon = new IconWrapper(new ImageIcon(sixImage));
            sevenIcon = new IconWrapper(new ImageIcon(sevenImage));
            eightIcon = new IconWrapper(new ImageIcon(eightImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeGame() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 600);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        timerLabel = new JLabel("Time:" + game.getElapsedTime().getSeconds());
        topPanel.add(timerLabel);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> restartGame());
        topPanel.add(resetButton);

        flagCounterLabel = new JLabel("Flags: 0");
        topPanel.add(flagCounterLabel);

        add(topPanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(rows, cols));
        cellButtons = new JButton[rows][cols];
        renderGrid();

        add(gridPanel, BorderLayout.CENTER);

        game.calculateNumbers();
        setVisible(true);

    }

    private void renderGrid() {
        gridPanel.removeAll();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton button = new JButton();
                button.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        int iconWidth = button.getWidth();
                        int iconHeight = button.getHeight();
                        if (iconWidth > 0 && iconHeight > 0) {
                            Image scaledCoveredImage = coveredIcon.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                            button.setIcon(new ImageIcon(scaledCoveredImage));
                        }
                    }
                });

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

    private void startTimer() {
        startTime = Instant.now();
        timer = new Timer(1000, e -> updateTimer());
        timer.start();
    }

    private void updateTimer() {
        Duration elapsedTime = Duration.between(startTime, Instant.now());
        timerLabel.setText("Time: " + elapsedTime.getSeconds() + "s");
    }


    private void handleLeftClick(int row, int col, JButton button) {
        if (!isTimerRunning) {
            startTimer();
            isTimerRunning = true;
        }

        if (!game.revealCell(row, col)) return;

        Cell cell = game.getGrid()[row][col];

        if (cell.hasMine()) {
            int iconWidth = button.getWidth();
            int iconHeight = button.getHeight();
            Image scaledMineImage = mineIcon.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledMineImage));
            button.setBackground(Color.RED);
            endGame(false);
        } else {
            updateCellIcon(button, cell);
            checkAndUpdateRevealedCells();
        }

        if (game.checkWinCondition()) {
            endGame(true);
        }
    }

    private void updateCellIcon(JButton button, Cell cell) {
        int number = cell.getNumber();
        IconWrapper iconWrapper = null;

        switch (number) {
            case 1:
                iconWrapper = oneIcon;
                break;
            case 2:
                iconWrapper = twoIcon;
                break;
            case 3:
                iconWrapper = threeIcon;
                break;
            case 4:
                iconWrapper = fourIcon;
                break;
            case 5:
                iconWrapper = fiveIcon;
                break;
            case 6:
                iconWrapper = sixIcon;
                break;
            case 7:
                iconWrapper = sevenIcon;
                break;
            case 8:
                iconWrapper = eightIcon;
                break;
            default:
                iconWrapper = emptyIcon;
                break;
        }

        if (iconWrapper != null) {
            int iconWidth = button.getWidth();
            int iconHeight = button.getHeight();
            Image scaledImage = iconWrapper.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        }

        for (MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setEnabled(true);

    }

    private void checkAndUpdateRevealedCells() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = game.getGrid()[row][col];
                JButton button = cellButtons[row][col];

                if (cell.isRevealed() && button.isEnabled()) {
                    updateCellIcon(button, cell);
                    button.setEnabled(true);
                }
            }
        }
    }

    private void handleRightClick(int row, int col, JButton button) {
        if (!isTimerRunning) {
            startTimer();
            isTimerRunning = true;
        }

        Cell cell = game.getGrid()[row][col];
        if (game.flagCell(row, col)) {
            if (cell.isFlagged()) {
                int iconWidth = button.getWidth();
                int iconHeight = button.getHeight();
                Image scaledFlagImage = flagIcon.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledFlagImage));
                flagCount++;
            } else {
                Image scaledCoveredImage = coveredIcon.getIcon().getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledCoveredImage));
                flagCount--;
            }
            updateFlagCounter();
        }
    }

    private void updateFlagCounter() {
        flagCounterLabel.setText("Flags: " + flagCount);
    }

    private void revealAllCells() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = game.getGrid()[row][col];
                JButton button = cellButtons[row][col];

                if (cell.hasMine()) {
                    int iconWidth = button.getWidth();
                    int iconHeight = button.getHeight();
                    Image scaledMineImage = mineIcon.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledMineImage));
                } else {
                    int number = cell.getNumber();
                    IconWrapper iconWrapper = null;

                    switch (number) {
                        case 1:
                            iconWrapper = oneIcon;
                            break;
                        case 2:
                            iconWrapper = twoIcon;
                            break;
                        case 3:
                            iconWrapper = threeIcon;
                            break;
                        case 4:
                            iconWrapper = fourIcon;
                            break;
                        case 5:
                            iconWrapper = fiveIcon;
                            break;
                        case 6:
                            iconWrapper = sixIcon;
                            break;
                        case 7:
                            iconWrapper = sevenIcon;
                            break;
                        case 8:
                            iconWrapper = eightIcon;
                            break;
                        default:
                            iconWrapper = emptyIcon;
                            break;
                    }

                    if (iconWrapper != null) {
                        int iconWidth = button.getWidth();
                        int iconHeight = button.getHeight();
                        Image scaledImage = iconWrapper.getIcon().getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                        button.setIcon(new ImageIcon(scaledImage));
                    }
                }

                button.setEnabled(false);
            }
        }
    }

    private void endGame(boolean isWin) {
        if (timer != null) {
            timer.stop();
        }
        long elapsedSeconds = game.getElapsedTime().getSeconds();
        String message = isWin ? "Congratulations! You won in " + elapsedSeconds + "s!" : "Game Over! You hit a mine after " + elapsedSeconds + "s!";
        JOptionPane.showMessageDialog(this, message);
        revealAllCells();
    }

    private void restartGame() {
        if (timer != null) {
            timer.stop();
        }
        isTimerRunning = false;
        flagCount = 0;
        updateFlagCounter();
        game.resetGame();
        renderGrid();
        timerLabel.setText("Time: 0s");
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

    public IconWrapper getMineIcon() {
        return mineIcon;
    }

    public IconWrapper getFlagIcon() {
        return flagIcon;
    }

    public IconWrapper getEmptyIcon() {
        return emptyIcon;
    }

    public IconWrapper getCoveredIcon() {
        return coveredIcon;
    }

    public IconWrapper getOneIcon() {
        return oneIcon;
    }

    public IconWrapper getTwoIcon() {
        return twoIcon;
    }

    public IconWrapper getThreeIcon() {
        return threeIcon;
    }

    public IconWrapper getFourIcon() {
        return fourIcon;
    }

    public IconWrapper getFiveIcon() {
        return fiveIcon;
    }

    public IconWrapper getSixIcon() {
        return sixIcon;
    }

    public IconWrapper getSevenIcon() {
        return sevenIcon;
    }

    public IconWrapper getEightIcon() {
        return eightIcon;
    }
}