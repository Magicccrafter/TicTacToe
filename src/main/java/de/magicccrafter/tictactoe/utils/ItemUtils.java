package de.magicccrafter.tictactoe.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

    public ItemStack getPlayerHead(String playerName, String displayName) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(playerName);
        skullMeta.setDisplayName(displayName);
        skull.setItemMeta(skullMeta);
        return skull;
    }

}
