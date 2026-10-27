import javax.swing.*;
import java.awt.*;

public class NoughtsAndCrosses extends JFrame {

    private static final int[][] LINES = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},   // rows
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},   // columns
        {0, 4, 8}, {2, 4, 6}               // diagonals
    };

    private final char[] board = new char[9];
    private final JButton[] buttons = new JButton[9];
    private final JLabel status = new JLabel("", SwingConstants.CENTER);
    private final JCheckBox vsComputer = new JCheckBox("Play against computer (you are X)", true);

    private char current;
    private boolean gameOver;

    public NoughtsAndCrosses() {
        super("Noughts and Crosses");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(16, 30));

        status.setFont(new Font("SansSerif", Font.BOLD, 18));
        status.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        add(status, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 4, 4));
        grid.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        for (int i = 0; i < 9; i++) {
            final int cell = i;
            JButton b = new JButton();
            b.setFont(new Font("SansSerif", Font.BOLD, 64));
            b.setPreferredSize(new Dimension(110, 110));
            b.setFocusPainted(false);
            b.addActionListener(e -> humanClicked(cell));
            buttons[i] = b;
            grid.add(b);
        }
        add(grid, BorderLayout.CENTER);

        JButton newGame = new JButton("New game");
        newGame.addActionListener(e -> reset());
        vsComputer.addActionListener(e -> reset());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        bottom.add(vsComputer);
        bottom.add(newGame);
        add(bottom, BorderLayout.SOUTH);

        reset();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void reset() {
        java.util.Arrays.fill(board, '\0');
        current = 'X';
        gameOver = false;
        for (JButton b : buttons) {
            b.setText("");
            b.setBackground(null);
            b.setOpaque(true);
        }
        status.setText("X's turn");
    }

    private void humanClicked(int i) {
        if (gameOver || board[i] != '\0') return;
        if (vsComputer.isSelected() && current == 'O') return; // computer's turn

        place(i);

        if (!gameOver && vsComputer.isSelected()) {
            Timer t = new Timer(350, e -> computerMove());
            t.setRepeats(false);
            t.start();
        }
    }

    private void place(int i) {
        board[i] = current;
        buttons[i].setText(String.valueOf(current));
        buttons[i].setForeground(current == 'X' ? new Color(200, 40, 40) : new Color(30, 90, 200));

        char w = winner(board);
        if (w != '\0') {
            gameOver = true;
            highlightWin(w);
            status.setText(w + " wins!");
        } else if (isFull(board)) {
            gameOver = true;
            status.setText("It's a draw!");
        } else {
            current = (current == 'X') ? 'O' : 'X';
            status.setText(current + "'s turn");
        }
    }

    private void highlightWin(char w) {
        for (int[] line : LINES) {
            if (board[line[0]] == w && board[line[1]] == w && board[line[2]] == w) {
                for (int idx : line) buttons[idx].setBackground(new Color(170, 235, 170));
                return;
            }
        }
    }

    // ----- Computer player (minimax: plays O and cannot be beaten) -----

    private void computerMove() {
        if (gameOver) return;
        int bestCell = -1, bestScore = Integer.MIN_VALUE;
        char[] copy = board.clone();
        for (int i = 0; i < 9; i++) {
            if (copy[i] == '\0') {
                copy[i] = 'O';
                int score = minimax(copy, 'X', 1);
                copy[i] = '\0';
                if (score > bestScore) {
                    bestScore = score;
                    bestCell = i;
                }
            }
        }
        if (bestCell >= 0) place(bestCell);
    }

    private int minimax(char[] b, char player, int depth) {
        char w = winner(b);
        if (w == 'O') return 10 - depth;
        if (w == 'X') return depth - 10;
        if (isFull(b)) return 0;

        boolean maximizing = (player == 'O');
        int best = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (b[i] == '\0') {
                b[i] = player;
                int score = minimax(b, maximizing ? 'X' : 'O', depth + 1);
                b[i] = '\0';
                best = maximizing ? Math.max(best, score) : Math.min(best, score);
            }
        }
        return best;
    }

    // ----- Game logic helpers -----

    private static char winner(char[] b) {
        for (int[] l : LINES) {
            if (b[l[0]] != '\0' && b[l[0]] == b[l[1]] && b[l[1]] == b[l[2]]) {
                return b[l[0]];
            }
        }
        return '\0';
    }

    private static boolean isFull(char[] b) {
        for (char c : b) if (c == '\0') return false;
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NoughtsAndCrosses().setVisible(true));
    }
}
