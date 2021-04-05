package de.magicccrafter.tictactoe.utils;

import de.magicccrafter.tictactoe.TicTacToe;
import de.magicccrafter.tictactoe.api.event.TicTacToeMatchEndEvent;
import de.magicccrafter.tictactoe.api.event.TicTacToeMatchStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class TicTacToeMatch {

    private Player playerA;
    private Player playerB;

    private Player currentPlayer;
    private Player winner;

    private Inventory playerAInventory;
    private Inventory playerBInventory;

    private HashMap<Integer, TicTacToeField> fields;

    public TicTacToeMatch(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;

        this.playerAInventory = Bukkit.createInventory(null, 9*6, "§2§lTIC TAC TOE");
        this.playerBInventory = Bukkit.createInventory(null, 9*6, "§2§lTIC TAC TOE");

        this.fields = new HashMap<Integer, TicTacToeField>();

        fields.put(21, TicTacToeField.FREE);
        fields.put(22, TicTacToeField.FREE);
        fields.put(23, TicTacToeField.FREE);
        fields.put(30, TicTacToeField.FREE);
        fields.put(31, TicTacToeField.FREE);
        fields.put(32, TicTacToeField.FREE);
        fields.put(39, TicTacToeField.FREE);
        fields.put(40, TicTacToeField.FREE);
        fields.put(41, TicTacToeField.FREE);
    }

    public void openInventory() {
        playerA.openInventory(playerAInventory);
        playerB.openInventory(playerBInventory);

        reloadInventory();
    }

    public void setupInventory() {
        this.playerAInventory.setItem(4, TicTacToe.getInstance().getItemUtils().getPlayerHead(playerB.getName(), "§aDein Gegner: §c" + playerB.getName()));
        this.playerBInventory.setItem(4, TicTacToe.getInstance().getItemUtils().getPlayerHead(playerA.getName(), "§aDein Gegner: §c" + playerA.getName()));
        
        ItemStack placeholderBlue = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemStack placeholderRed = new ItemStack(Material.RED_STAINED_GLASS_PANE);

        this.playerAInventory.setItem(0, placeholderBlue);
        this.playerAInventory.setItem(8, placeholderBlue);
        this.playerAInventory.setItem(9, placeholderBlue);
        this.playerAInventory.setItem(17, placeholderBlue);
        this.playerAInventory.setItem(18, placeholderBlue);
        this.playerAInventory.setItem(26, placeholderBlue);
        this.playerAInventory.setItem(27, placeholderBlue);
        this.playerAInventory.setItem(35, placeholderBlue);
        this.playerAInventory.setItem(36, placeholderBlue);
        this.playerAInventory.setItem(44, placeholderBlue);
        this.playerAInventory.setItem(45, placeholderBlue);
        this.playerAInventory.setItem(53, placeholderBlue);

        this.playerBInventory.setItem(0, placeholderRed);
        this.playerBInventory.setItem(8, placeholderRed);
        this.playerBInventory.setItem(9, placeholderRed);
        this.playerBInventory.setItem(17, placeholderRed);
        this.playerBInventory.setItem(18, placeholderRed);
        this.playerBInventory.setItem(26, placeholderRed);
        this.playerBInventory.setItem(27, placeholderRed);
        this.playerBInventory.setItem(35, placeholderRed);
        this.playerBInventory.setItem(36, placeholderRed);
        this.playerBInventory.setItem(44, placeholderRed);
        this.playerBInventory.setItem(45, placeholderRed);
        this.playerBInventory.setItem(53, placeholderRed);

        reloadInventory();
    }

    public void reloadInventory() {
        ItemStack freeField = new ItemStack(Material.WHITE_CONCRETE);
        ItemMeta freeFieldMeta = freeField.getItemMeta();
        freeFieldMeta.setDisplayName("§rFreies Feld");
        freeField.setItemMeta(freeFieldMeta);

        ItemStack blueField = new ItemStack(Material.BLUE_CONCRETE);
        ItemMeta blueFieldMeta = blueField.getItemMeta();
        blueFieldMeta.setDisplayName("§9Blaues Feld");
        blueField.setItemMeta(blueFieldMeta);

        ItemStack redField = new ItemStack(Material.RED_CONCRETE);
        ItemMeta redFieldMeta = redField.getItemMeta();
        redFieldMeta.setDisplayName("§cRotes Feld");
        redField.setItemMeta(redFieldMeta);

        for(Integer slot : fields.keySet()) {
            TicTacToeField field = fields.get(slot);

            if(field.equals(TicTacToeField.FREE)) {
                playerAInventory.setItem(slot, freeField);
                playerBInventory.setItem(slot, freeField);
            } else if(field.equals(TicTacToeField.BLUE)) {
                playerAInventory.setItem(slot, blueField);
                playerBInventory.setItem(slot, blueField);
            } else if(field.equals(TicTacToeField.RED)) {
                playerAInventory.setItem(slot, redField);
                playerBInventory.setItem(slot, redField);
            }
        }

        if(currentPlayer != null) {
            playerAInventory.setItem(49, TicTacToe.getInstance().getItemUtils().getPlayerHead(currentPlayer.getName(), "§c" + currentPlayer.getName() + " §aist am Zug"));
            playerBInventory.setItem(49, TicTacToe.getInstance().getItemUtils().getPlayerHead(currentPlayer.getName(), "§c" + currentPlayer.getName() + " §aist am Zug"));
        }
        if(winner != null) {
            playerAInventory.setItem(49, TicTacToe.getInstance().getItemUtils().getPlayerHead(winner.getName(), "§c" + winner.getName() + " §ahat das Spiel gewonnen"));
            playerBInventory.setItem(49, TicTacToe.getInstance().getItemUtils().getPlayerHead(winner.getName(), "§c" + winner.getName() + " §ahat das Spiel gewonnen"));
        }

        playerA.updateInventory();
        playerB.updateInventory();

    }

    public void startMatch() {
        this.currentPlayer = this.playerA;

        this.playerA.playSound(playerA.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        this.playerB.playSound(playerB.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        Bukkit.getPluginManager().callEvent(new TicTacToeMatchStartEvent(this));

        reloadInventory();
    }

    public void changeTicTacToeBlock(Integer slotBlock, TicTacToeField field) {
        this.fields.replace(slotBlock, field);
        reloadInventory();
    }

    public TicTacToeField getPlayersField(Player player) {
        if(player.getUniqueId().toString().equalsIgnoreCase(playerA.getUniqueId().toString())) {
            return TicTacToeField.BLUE;
        } else if(player.getUniqueId().toString().equalsIgnoreCase(playerB.getUniqueId().toString())) {
            return TicTacToeField.RED;
        }
        return null;
    }

    public void nextRound() {
        checkForWin();
        if(winner != null) return;
        if(this.currentPlayer.getUniqueId().toString().equalsIgnoreCase(playerA.getUniqueId().toString())) {
            this.currentPlayer = playerB;
            reloadInventory();
        } else if(this.currentPlayer.getUniqueId().toString().equalsIgnoreCase(playerB.getUniqueId().toString())) {
            this.currentPlayer = playerA;
            reloadInventory();
        }
    }

    public void checkForWin() {
        if(fields.get(21).equals(TicTacToeField.BLUE) && fields.get(22).equals(TicTacToeField.BLUE) && fields.get(23).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 21, 22, 23);
        } else if(fields.get(30).equals(TicTacToeField.BLUE) && fields.get(31).equals(TicTacToeField.BLUE) && fields.get(32).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 30, 31, 32);
        } else if(fields.get(39).equals(TicTacToeField.BLUE) && fields.get(40).equals(TicTacToeField.BLUE) && fields.get(41).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 39, 40, 41);
        } else if(fields.get(21).equals(TicTacToeField.BLUE) && fields.get(30).equals(TicTacToeField.BLUE) && fields.get(39).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 21, 30, 39);
        } else if(fields.get(22).equals(TicTacToeField.BLUE) && fields.get(31).equals(TicTacToeField.BLUE) && fields.get(40).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 22, 31, 40);
        } else if(fields.get(23).equals(TicTacToeField.BLUE) && fields.get(32).equals(TicTacToeField.BLUE) && fields.get(41).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 23, 32, 41);
        } else if(fields.get(21).equals(TicTacToeField.BLUE) && fields.get(31).equals(TicTacToeField.BLUE) && fields.get(41).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 21, 31, 41);
        } else if(fields.get(23).equals(TicTacToeField.BLUE) && fields.get(31).equals(TicTacToeField.BLUE) && fields.get(39).equals(TicTacToeField.BLUE)) {
            winGame(playerA, 23, 31,39);
        } else if(fields.get(21).equals(TicTacToeField.RED) && fields.get(22).equals(TicTacToeField.RED) && fields.get(23).equals(TicTacToeField.RED)) {
            winGame(playerB, 21, 22, 23);
        } else if(fields.get(30).equals(TicTacToeField.RED) && fields.get(31).equals(TicTacToeField.RED) && fields.get(32).equals(TicTacToeField.RED)) {
            winGame(playerB, 30, 31, 32);
        } else if(fields.get(39).equals(TicTacToeField.RED) && fields.get(40).equals(TicTacToeField.RED) && fields.get(41).equals(TicTacToeField.RED)) {
            winGame(playerB, 39, 40, 41);
        } else if(fields.get(21).equals(TicTacToeField.RED) && fields.get(30).equals(TicTacToeField.RED) && fields.get(39).equals(TicTacToeField.RED)) {
            winGame(playerB, 21, 30, 39);
        } else if(fields.get(22).equals(TicTacToeField.RED) && fields.get(31).equals(TicTacToeField.RED) && fields.get(40).equals(TicTacToeField.RED)) {
            winGame(playerB, 22, 31, 40);
        } else if(fields.get(23).equals(TicTacToeField.RED) && fields.get(32).equals(TicTacToeField.RED) && fields.get(41).equals(TicTacToeField.RED)) {
            winGame(playerB, 23, 32, 41);
        } else if(fields.get(21).equals(TicTacToeField.RED) && fields.get(31).equals(TicTacToeField.RED) && fields.get(41).equals(TicTacToeField.RED)) {
            winGame(playerB, 21, 31, 41);
        } else if(fields.get(23).equals(TicTacToeField.RED) && fields.get(31).equals(TicTacToeField.RED) && fields.get(39).equals(TicTacToeField.RED)) {
            winGame(playerB, 23, 31, 39);
        } else {
            for(Integer slot : fields.keySet()) {
                TicTacToeField field = fields.get(slot);
                if(field.equals(TicTacToeField.FREE)) {
                    return;
                }
            }

            TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(this.playerA);
            TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(this.playerB);

            this.getPlayerA().closeInventory();
            this.getPlayerB().closeInventory();

            this.getPlayerA().sendMessage(TicTacToe.getInstance().getPrefix() + "§cDas Match ist unentschieden ausgegangen");
            this.getPlayerB().sendMessage(TicTacToe.getInstance().getPrefix() + "§cDas Match ist unentschieden ausgegangen");

            Bukkit.getPluginManager().callEvent(new TicTacToeMatchEndEvent(null, null, this, true));
        }
    }
    
    public void winGame(Player player, Integer slot1, Integer slot2, Integer slot3) {
        this.winner = player;
        Player loser = null;
        if(player.getUniqueId().toString().equals(playerA.getUniqueId().toString())) {
            loser = playerB;
        } else if(player.getUniqueId().toString().equals(playerB.getUniqueId())) {
            loser = playerA;
        }
        TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(this.playerA);
        TicTacToe.getInstance().getMatches().unregisterTicTacToeMatch(this.playerB);

        ItemStack wonItem = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta wonItemMeta = wonItem.getItemMeta();
        wonItemMeta.setDisplayName("§c" + player.getName() + " §ahat hiermit das Match gewonnen");
        wonItem.setItemMeta(wonItemMeta);

        reloadInventory();
        playerAInventory.setItem(slot1, wonItem);
        playerAInventory.setItem(slot2, wonItem);
        playerAInventory.setItem(slot3, wonItem);
        playerBInventory.setItem(slot1, wonItem);
        playerBInventory.setItem(slot2, wonItem);
        playerBInventory.setItem(slot3, wonItem);
        playerA.updateInventory();
        playerB.updateInventory();

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.sendTitle("§a§lMatch gewonnen", "§7[§aTicTacToe§7]", 15, 80, 15);

        loser.playSound(playerA.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
        loser.sendTitle("§c§lMatch verloren", "§7[§aTicTacToe§7]", 15, 80, 15);

        this.getPlayerA().sendMessage(TicTacToe.getInstance().getPrefix() + "§c" + player.getName() + " §ahat das Match gewonnen");
        this.getPlayerB().sendMessage(TicTacToe.getInstance().getPrefix() + "§c" + player.getName() + " §ahat das Match gewonnen");

        Bukkit.getPluginManager().callEvent(new TicTacToeMatchEndEvent(player, loser, this, false));

        Bukkit.getScheduler().runTaskLater(TicTacToe.getInstance(), new Runnable() {
            @Override
            public void run() {
                playerA.closeInventory();
                playerB.closeInventory();
            }
        }, 40);
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public HashMap<Integer, TicTacToeField> getFields() {
        return fields;
    }

    public Inventory getPlayerAInventory() {
        return playerAInventory;
    }

    public Inventory getPlayerBInventory() {
        return playerBInventory;
    }

}
