package de.magicccrafter.tictactoe.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicTacToeRequests {

    private HashMap<Player, List<Player>> requests = new HashMap<Player, List<Player>>();

    public TicTacToeRequests() {
    }

    public void request(Player requester, Player requested) {
        List<Player> playerRequests = requests.get(requested);
        playerRequests.add(requester);
        requests.replace(requested, playerRequests);
    }

    public void removeRequest(Player requester, Player requested) {
        List<Player> playerRequests = requests.get(requested);
        playerRequests.remove(requester);
        requests.replace(requested, playerRequests);
    }

    public Boolean hasAlreadyRequested(Player requester, Player requested) {
        List<Player> playerRequests = requests.get(requested);
        if(playerRequests.contains(requester)) {
            return true;
        }
        return false;
    }

    public List<Player> getRequests(Player requested) {
        return requests.get(requested);
    }

    public void registerPlayer(Player player) {
        requests.put(player, new ArrayList<Player>());
    }

    public void unregisterPlayer(Player player) {
        requests.remove(player);
    }


}
