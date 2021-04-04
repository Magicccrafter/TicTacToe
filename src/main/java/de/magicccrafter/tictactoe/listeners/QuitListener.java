package de.magicccrafter.tictactoe.listeners;

import de.magicccrafter.tictactoe.TicTacToe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        TicTacToe.getInstance().getRequests().unregisterPlayer(event.getPlayer());
    }

}
