package de.magicccrafter.tictactoe.api.event;

import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TicTacToeMatchStartEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private TicTacToeMatch match;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public TicTacToeMatchStartEvent(TicTacToeMatch match) {
        this.match = match;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public TicTacToeMatch getMatch() {
        return match;
    }

}
