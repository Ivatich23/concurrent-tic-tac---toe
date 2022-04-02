package com.epam.rd.autocode.concurrenttictactoe.ticTakToeGame;

import com.epam.rd.autocode.concurrenttictactoe.Move;
import com.epam.rd.autocode.concurrenttictactoe.Player;
import com.epam.rd.autocode.concurrenttictactoe.PlayerStrategy;
import com.epam.rd.autocode.concurrenttictactoe.TicTacToe;

public class PlayerImpl implements Player {
    private final TicTacToe ticTacToe;
    private final PlayerStrategy playerStrategy;
    private final char mark;
    private final static int GAME_SIZE = 9;

    public PlayerImpl(TicTacToe ticTacToe, PlayerStrategy playerStrategy, char mark) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void run() {
        synchronized (ticTacToe) {
            for (int i = 0; i < GAME_SIZE; i++) {
                waitIfNotFirstTurnMark();
                if (ticTacToe.isFinished()) {
                    return;
                }
                if (ticTacToe.lastMark() == mark) {
                    try {
                        ticTacToe.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Move move = playerStrategy.computeMove(mark, ticTacToe);
                    ticTacToe.setMark(move.getRow(), move.getColumn(), mark);
                    if (isWin()) {
                        ticTacToe.finish();
                        ticTacToe.notifyAll();
                        return;
                    }
                    ticTacToe.notifyAll();
                }
            }
        }
    }

    private boolean isWin() {
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[i][0] == mark && ticTacToe.table()[i][1] == mark && ticTacToe.table()[i][2] == mark)
                return true;
            if (ticTacToe.table()[0][i] == mark && ticTacToe.table()[1][i] == mark && ticTacToe.table()[2][i] == mark)
                return true;
        }
        if (ticTacToe.table()[0][0] == mark && ticTacToe.table()[1][1] == mark && ticTacToe.table()[2][2] == mark) {
            return true;
        } else {
            return ticTacToe.table()[0][2] == mark && ticTacToe.table()[1][1] == mark && ticTacToe.table()[2][0] == mark;
        }
    }

    private void waitIfNotFirstTurnMark() {
        if (isNotFirstTurnMark()) {
            try {
                ticTacToe.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNotFirstTurnMark() {
        return mark != 'X' && ticTacToe.lastMark() == ' ';
    }


}
