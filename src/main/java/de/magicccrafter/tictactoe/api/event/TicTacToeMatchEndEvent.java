package de.magicccrafter.tictactoe.api.event;

import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TicTacToeMatchEndEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private Player winner;
    private Player loser;
    private TicTacToeMatch match;
    private Boolean isTie;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public TicTacToeMatchEndEvent(Player winner, Player loser, TicTacToeMatch match, Boolean isTie) {
        this.winner = winner;
        this.loser = loser;
        this.match = match;
        this.isTie = isTie;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public TicTacToeMatch getMatch() {
        return match;
    }

    public Boolean isTie() {
        return isTie;
    }
}
