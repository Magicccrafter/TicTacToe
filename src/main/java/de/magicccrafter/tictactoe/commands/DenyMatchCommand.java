package de.magicccrafter.tictactoe.commands;

import de.magicccrafter.tictactoe.TicTacToe;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DenyMatchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(!TicTacToe.getInstance().getMatches().isInMatch(player)) {
                if(strings.length == 1) {
                    Player player1 = Bukkit.getPlayer(strings[0]);
                    if(player1 != null) {
                        List<Player> requests = TicTacToe.getInstance().getRequests().getRequests(player);
                        if(requests.contains(player1)) {
                            if(!TicTacToe.getInstance().getMatches().isInMatch(player1)) {
                                TicTacToe.getInstance().getRequests().removeRequest(player1, player);
                                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDie Match Anfrage wurde abgelehnt");
                            } else {
                                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDas Match konnte nicht angenommen werden, da dein Gegner aktuell in einem anderen Match ist");
                            }
                        } else {
                            player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDieser Spieler hat dir keine Match Anfrage gesendet");
                        }
                    } else {
                        player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDieser Spieler ist nicht online");
                    }
                } else {
                    player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cSyntax: /denymatch <Spieler>");
                }
            } else {
                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDu nimmst bereits an einem Match teil");
            }
        } else {
            commandSender.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDazu musst du ein Spieler sein");
        }
        return false;
    }

}
