package de.magicccrafter.tictactoe.commands;

import de.magicccrafter.tictactoe.TicTacToe;
import de.magicccrafter.tictactoe.utils.TicTacToeMatch;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MatchQueueCommand implements CommandExecutor {

    private static Player currentQueuePlayer = null;
    private Integer scheduler;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(TicTacToe.getInstance().getMatches().isInMatch(player)) {
                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDu nimmst bereits an einem Match teil");
                return true;
            }
            if(currentQueuePlayer == null) {
                currentQueuePlayer = player;
                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§aDu hast die Warteschlange betreten");
                return true;
            } else {
                if(!currentQueuePlayer.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                    TicTacToeMatch match = new TicTacToeMatch(player, currentQueuePlayer);
                    match.setupInventory();
                    TicTacToe.getInstance().getMatches().registerTicTacToeMatch(player, match);
                    TicTacToe.getInstance().getMatches().registerTicTacToeMatch(currentQueuePlayer, match);
                    player.sendMessage(TicTacToe.getInstance().getPrefix() + "§aEin Match wurde gefunden");
                    currentQueuePlayer.sendMessage(TicTacToe.getInstance().getPrefix() + "§aEin Match wurde gefunden");

                    currentQueuePlayer = null;

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
                            match.getPlayerA().sendMessage(message);
                            match.getPlayerB().sendMessage(message);
                            match.getPlayerA().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            match.getPlayerB().playSound(match.getPlayerB().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            seconds--;
                        }
                    }, 0, 20);
                } else {
                    player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDu hast die Warteschlange verlassen");
                    currentQueuePlayer = null;
                }
                return true;
            }
        }
        return false;
    }

}
