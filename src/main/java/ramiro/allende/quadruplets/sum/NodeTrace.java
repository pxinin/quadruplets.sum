package ramiro.allende.quadruplets.sum;

import java.util.ArrayList;
import java.util.List;

public class NodeTrace {
    private int x;
    private int y;
    private List<Integer> numbers;
    private int whatsLeft;

    public NodeTrace(int x, int y, int numberIndex, int whatsLeft) {
        this.x = x;
        this.y = y;

        List<Integer> numbers = new ArrayList<>(4);
        numbers.add(numberIndex);
        this.numbers = numbers;

        this.whatsLeft = whatsLeft;
    }

    public NodeTrace(int x, int y, List<Integer> numbers, int whatsLeft) {
        this.x = x;
        this.y = y;

        this.numbers = numbers;

        this.whatsLeft = whatsLeft;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int getWhatsLeft() {
        return whatsLeft;
    }

    public void setWhatsLeft(int whatsLeft) {
        this.whatsLeft = whatsLeft;
    }
}
