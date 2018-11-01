package org.sunyata.game.edy.interact;

import java.io.Serializable;

/**
 * @author leo
 */
public class RoundResult implements Serializable {
    public boolean getResult() {
        return result;
    }

    public RoundResult setResult(boolean result) {
        this.result = result;
        return this;
    }

    private boolean result;

    public int getBombNumbers() {
        return bombNumbers;
    }

    public RoundResult setBombNumbers(int bombNumbers) {
        this.bombNumbers = bombNumbers;
        return this;
    }

    private int bombNumbers;
}
