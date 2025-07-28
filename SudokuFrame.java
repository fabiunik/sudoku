import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {
    private SudokuBoard board;
    private JTextField[][] fields;

    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mostrarSelecaoDificuldade();
        setVisible(true);
    }

    private void mostrarSelecaoDificuldade() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 10, 10));
        String[] niveis = { "Fácil", "Médio", "Difícil" };

        for (String nivel : niveis) {
            JButton botao = new JButton(nivel);
            botao.addActionListener(e -> iniciarJogo(nivel));
            painel.add(botao);
        }

        add(new JLabel("Escolha o nível de dificuldade:", SwingConstants.CENTER), BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);
    }

    private void iniciarJogo(String nivel) {
        getContentPane().removeAll();
        SudokuGame game = new SudokuGame();
        game.iniciarComNivel(nivel);
        this.board = game.getBoard();
        this.fields = new JTextField[9][9];
        desenharTabuleiro();
        add(botaoReiniciar(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void desenharTabuleiro() {
        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(font);
                field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                field.setBackground(Color.WHITE);

                SudokuCell cell = board.getCell(r, c);
                int value = cell.getValue();

                if (value != 0) field.setText(String.valueOf(value));

                if (cell.isFixed()) {
                    field.setEditable(false);
                    field.setBackground(Color.LIGHT_GRAY);
                } else {
                    final int row = r, col = c;
                    field.addActionListener(e -> {
                        String txt = field.getText().trim();
                        try {
                            int val = Integer.parseInt(txt);
                            if (val < 1 || val > 9) throw new NumberFormatException();

                            if (board.setValue(row, col, val)) {
                                field.setBackground(Color.WHITE);
                                if (board.isCompleteAndValid()) {
                                    JOptionPane.showMessageDialog(this,
                                        "🎉 Parabéns! Você completou o Sudoku corretamente!",
                                        "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                field.setText("");
                                field.setBackground(Color.PINK);
                                JOptionPane.showMessageDialog(this,
                                    "Valor inválido! Já existe na linha, coluna ou bloco.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            field.setText("");
                            field.setBackground(Color.PINK);
                        }
                    });
                }

                fields[r][c] = field;
                gridPanel.add(field);
            }

        add(gridPanel, BorderLayout.CENTER);
    }

    private JButton botaoReiniciar() {
        JButton restart = new JButton("🔄 Novo Jogo");
        restart.addActionListener(e -> {
            getContentPane().removeAll();
            mostrarSelecaoDificuldade();
            revalidate();
            repaint();
        });
        return restart;
    }

}