package de.magicccrafter.tictactoe.listeners;

import de.magicccrafter.tictactoe.TicTacToe;
import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void PlayerCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if(TicTacToe.getInstance().getMatches().isInMatch(player)) {
            TicTacToeMatch match = TicTacToe.getInstance().getMatches().getPlayersTicTacToeMatch(player);

            TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(match.getPlayerA());
            TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(match.getPlayerB());

            match.getPlayerA().closeInventory();
            match.getPlayerB().closeInventory();

            match.getPlayerA().sendMessage(TicTacToe.getInstance().getPrefix() + "§cDas Match wurde abgebrochen da ein Spieler das Inventar oder das Spiel verlassen hat");
            match.getPlayerB().sendMessage(TicTacToe.getInstance().getPrefix() + "§cDas Match wurde abgebrochen da ein Spieler das Inventar oder das Spiel verlassen hat");

            match.getPlayerA().playSound(match.getPlayerA().getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
            match.getPlayerB().playSound(match.getPlayerB().getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);

            match = null;
        }
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(TicTacToe.getInstance().getMatches().isInMatch(player)) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() == Material.WHITE_CONCRETE) {
                TicTacToeMatch match = TicTacToe.getInstance().getMatches().getPlayersTicTacToeMatch(player);
                if(match.getCurrentPlayer().getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                    match.changeTicTacToeBlock(event.getSlot(), match.getPlayersField(player));
                    match.getCurrentPlayer().playSound(match.getCurrentPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    match.nextRound();
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                }
            }
        } else if(event.getView().getTitle().equalsIgnoreCase("§2§lTIC TAC TOE")) {
            event.setCancelled(true);
        }
    }

}
