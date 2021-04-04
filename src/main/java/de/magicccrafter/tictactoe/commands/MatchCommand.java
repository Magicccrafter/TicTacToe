package de.magicccrafter.tictactoe.commands;

import de.magicccrafter.tictactoe.TicTacToe;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class MatchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(!TicTacToe.getInstance().getMatches().isInMatch(player)) {
                if(strings.length == 1) {
                    Player player1 = Bukkit.getPlayer(strings[0]);
                    if(player1 != null) {
                        if(!player.getUniqueId().equals(player1.getUniqueId())) {
                            if(!TicTacToe.getInstance().getRequests().getRequests(player1).contains(player)) {
                                TicTacToe.getInstance().getRequests().request(player, player1);
                                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§aDie Match Anfrage an §c" + player1.getName() + " §awurde versendet");

                                TextComponent tcAccept = new TextComponent();
                                tcAccept.setText("§a§l[ANNEHMEN]");
                                tcAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptmatch " + player.getName()));

                                TextComponent tcDeny = new TextComponent();
                                tcDeny.setText(" §c§l[ABLEHNEN]");
                                tcDeny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/denymatch " + player.getName()));

                                TextComponent msg = new TextComponent();
                                msg.setText(TicTacToe.getInstance().getPrefix() + "§c" + player.getName() + " §ahat dir eine Match Anfrage gesendet. Bitte wähle eine Option: ");
                                msg.addExtra(tcAccept);
                                msg.addExtra(tcDeny);

                                player1.spigot().sendMessage(msg);
                            } else {
                                player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDu hast diesem Spieler bereits eine Match Anfrage gesendet");
                            }
                        } else {
                            player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDu kannst nicht mit dir selber interagieren");
                        }
                    } else {
                        player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cDieser Spieler ist nicht online");
                    }
                } else {
                    player.sendMessage(TicTacToe.getInstance().getPrefix() + "§cSyntax: /match <Spieler>");
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
