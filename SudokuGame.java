import java.util.*;

public class SudokuGame {
    private SudokuBoard board;

    public SudokuGame() {
        board = new SudokuBoard();
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void iniciarComNivel(String nivel) {
        board = new SudokuBoard();
        gerarTabuleiroCompleto(0, 0);
        removerCelas(nivel);
    }

    private boolean gerarTabuleiroCompleto(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return gerarTabuleiroCompleto(row + 1, 0);

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) nums.add(i);
        Collections.shuffle(nums);

        for (int val : nums) {
            if (board.isValidMove(row, col, val)) {
                board.setValue(row, col, val);
                if (gerarTabuleiroCompleto(row, col + 1)) return true;
                board.setValue(row, col, 0);
            }
        }

        return false;
    }

    private void removerCelas(String dificuldade) {
    int celulasFixas;

    switch (dificuldade.toLowerCase()) {
        case "fácil":   celulasFixas = 46; break; // 81 - 35
        case "médio":   celulasFixas = 36; break; // 81 - 45
        case "difícil": celulasFixas = 26; break; // 81 - 55
        default:        celulasFixas = 46;
    }

    Set<String> escolhidas = new HashSet<>();
    Random rand = new Random();

    while (escolhidas.size() < celulasFixas) {
        int r = rand.nextInt(9);
        int c = rand.nextInt(9);
        String coord = r + "," + c;

        if (!escolhidas.contains(coord)) {
            escolhidas.add(coord);
        }
    }

    // define quais são fixas
    for (int r = 0; r < 9; r++)
        for (int c = 0; c < 9; c++) {
            String coord = r + "," + c;
            if (escolhidas.contains(coord)) {
                board.getCell(r, c).setFixed(true);
            } else {
                board.getCell(r, c).setValue(0);
                board.getCell(r, c).setFixed(false);
            }
        }
    }
}