public class SudokuCell {
    private int value;
    private boolean fixed;

    public SudokuCell() {
        this.value = 0;
        this.fixed = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}