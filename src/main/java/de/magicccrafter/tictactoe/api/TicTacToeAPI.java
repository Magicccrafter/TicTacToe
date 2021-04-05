package de.magicccrafter.tictactoe.api;

import de.magicccrafter.tictactoe.TicTacToe;
import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.entity.Player;

public class TicTacToeAPI {

    public TicTacToeMatch createAndStartMatch(Player player1, Player player2) {
        TicTacToeMatch match = new TicTacToeMatch(player1, player2);
        match.setupInventory();
        match.openInventory();
        match.startMatch();
        TicTacToe.getInstance().getMatches().registerTicTacToeMatch(player1, match);
        TicTacToe.getInstance().getMatches().registerTicTacToeMatch(player2, match);
        return match;
    }

    public void stopMatch(TicTacToeMatch match) {
        match.getPlayerA().closeInventory();
        match.getPlayerB().closeInventory();
        TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(match.getPlayerA());
        TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(match.getPlayerB());
    }

    public TicTacToeMatch getPlayersMatch(Player player) {
        return TicTacToe.getInstance().getMatches().getPlayersTicTacToeMatch(player);
    }



}
