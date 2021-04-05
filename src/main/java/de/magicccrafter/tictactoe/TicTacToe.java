package de.magicccrafter.tictactoe;

import de.magicccrafter.tictactoe.api.TicTacToeAPI;
import de.magicccrafter.tictactoe.commands.AcceptMatchCommand;
import de.magicccrafter.tictactoe.commands.DenyMatchCommand;
import de.magicccrafter.tictactoe.commands.MatchCommand;
import de.magicccrafter.tictactoe.commands.MatchQueueCommand;
import de.magicccrafter.tictactoe.listeners.InventoryListener;
import de.magicccrafter.tictactoe.listeners.JoinListener;
import de.magicccrafter.tictactoe.listeners.QuitListener;
import de.magicccrafter.tictactoe.utils.ItemUtils;
import de.magicccrafter.tictactoe.utils.TicTacToeMatches;
import de.magicccrafter.tictactoe.utils.TicTacToeRequests;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TicTacToe extends JavaPlugin {

    private static TicTacToe instance;

    private String prefix;
    private ItemUtils itemUtils;
    private TicTacToeMatches matches;
    private TicTacToeRequests requests;
    private TicTacToeAPI ticTacToeAPI;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "§7[§aTicTacToe§7] §a";
        itemUtils = new ItemUtils();
        matches = new TicTacToeMatches();
        requests = new TicTacToeRequests();

        getCommand("match").setExecutor(new MatchCommand());
        getCommand("acceptmatch").setExecutor(new AcceptMatchCommand());
        getCommand("denymatch").setExecutor(new DenyMatchCommand());
        getCommand("matchqueue").setExecutor(new MatchQueueCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onDisable() {

    }

    public static TicTacToe getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    public ItemUtils getItemUtils() {
        return itemUtils;
    }

    public TicTacToeMatches getMatches() {
        return matches;
    }

    public TicTacToeRequests getRequests() {
        return requests;
    }

    public TicTacToeAPI getTicTacToeAPI() {
        return ticTacToeAPI;
    }
}
