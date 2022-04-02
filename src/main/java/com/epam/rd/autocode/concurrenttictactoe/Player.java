package com.epam.rd.autocode.concurrenttictactoe;

import com.epam.rd.autocode.concurrenttictactoe.ticTakToeGame.PlayerImpl;

public interface Player extends Runnable {
    static Player createPlayer(final TicTacToe ticTacToe, final char mark, PlayerStrategy strategy) {
        return new PlayerImpl(ticTacToe, strategy, mark);
    }

}
