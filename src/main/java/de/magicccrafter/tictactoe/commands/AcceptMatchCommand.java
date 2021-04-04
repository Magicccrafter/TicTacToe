package de.magicccrafter.tictactoe.commands;

import de.magicccrafter.tictactoe.TicTacToe;
import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AcceptMatchCommand implements CommandExecutor {

    private Integer scheduler;

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
                                TicTacToeMatch match = new TicTacToeMatch(player, player1);
                                match.setupInventory();
                                TicTacToe.getInstance().getMatches().registerTicTacToeMatch(player, match);
                                TicTacToe.getInstance().getMatches().registerTicTacToeMatch(player1, match);
                                player1.sendMessage(TicTacToe.getInstance().getPrefix() + "§c" + player.getName() + " §ahat deine Match Anfrage angenommen");
                                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§aDie Match Anfrage von §c" + player1.getName() + " §awurde angenommen");

                                scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(TicTacToe.getInstance(), new Runnable() {
                                    Integer seconds = 3;
                                    @Override
                                    public void run() {
                                        if(seconds == 0) {
                                            match.openInventory();
                                            match.startMatch();
                                            Bukkit.getScheduler().cancelTask(scheduler);
                                        }
                                        String message = TicTacToe.getInstance().getPrefix() + "§aDas Match startet in §6" + seconds + " Sekunden";
                                        if(seconds == 1) {
                                            message = TicTacToe.getInstance().getPrefix() + "§aDas Match startet in §6" + seconds + " Sekunde";
                                        }
                                        player.sendMessage(message);
                                        player1.sendMessage(message);
                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                        player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                        seconds--;
                                    }
                                }, 0, 20);

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
                    player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cSyntax: /acceptmatch <Spieler>");
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
