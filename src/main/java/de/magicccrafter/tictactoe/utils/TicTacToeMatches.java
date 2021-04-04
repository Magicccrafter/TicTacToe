package de.magicccrafter.tictactoe.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TicTacToeMatches {

    private HashMap<Player, TicTacToeMatch> matches = new HashMap<Player, TicTacToeMatch>();

    public TicTacToeMatches() {
    }

    public TicTacToeMatch getPlayersTicTacToeMatch(Player player) {
        if(matches.containsKey(player)) {
            return matches.get(player);
        }
        return null;
    }

    public Boolean isInMatch(Player player) {
        if(matches.containsKey(player)) {
            return true;
        }
        return false;
    }

    public void registerTicTacToeMatch(Player player, TicTacToeMatch match) {
        if(!matches.containsKey(player)) {
            matches.put(player, match);
        }
    }

    public void unregisterTicTacToeMatch(Player player) {
        if(matches.containsKey(player)) {
            matches.remove(player);
        }
    }

}
