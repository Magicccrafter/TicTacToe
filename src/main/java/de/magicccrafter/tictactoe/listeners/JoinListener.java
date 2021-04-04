package de.magicccrafter.tictactoe.listeners;

import de.magicccrafter.tictactoe.TicTacToe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        TicTacToe.getInstance().getRequests().registerPlayer(event.getPlayer());
    }

}
