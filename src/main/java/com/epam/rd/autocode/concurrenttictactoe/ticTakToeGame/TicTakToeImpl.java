package com.epam.rd.autocode.concurrenttictactoe.ticTakToeGame;

import com.epam.rd.autocode.concurrenttictactoe.TicTacToe;

public class TicTakToeImpl implements TicTacToe {
    private final char[][] ticTakTable = new char[3][3];
    private char lastMark = ' ';
    private boolean finish;


    public TicTakToeImpl() {
        for (int i = 0; i < ticTakTable.length; i++) {
            for (int j = 0; j < ticTakTable.length; j++) {
                ticTakTable[i][j] = ' ';
            }
        }
    }

    public boolean setMarkIfPossible(int x, int y) {
        return ticTakTable[x][y] == ' ';
    }

    @Override
    public void setMark(int x, int y, char mark) {
        if (setMarkIfPossible(x, y)) {
            lastMark = mark;
            ticTakTable[x][y] = mark;
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public synchronized char[][] table() {
        char[][] ticTakTableCopy = new char[3][3];
        for (int i = 0; i < ticTakTable.length; i++) {
            for (int j = 0; j < ticTakTable.length; j++) {
                ticTakTableCopy[i][j] = ticTakTable[i][j];
            }
        }
        return ticTakTableCopy;
    }

    @Override
    public char lastMark() {
        return lastMark;
    }

    @Override
    public void finish() {
        finish = true;
    }

    @Override
    public boolean isFinished() {
        return finish;
    }
}
