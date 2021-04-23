package me.tvhee.customitems.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HashMaps
{
    public static HashMap<Player, String> saveItem = new HashMap<>();
    public static HashMap<Player, String> saveOldItem = new HashMap<>();
    public static HashMap<Player, Integer> saveMenuPage = new HashMap<>();
    public static HashMap<Player, String> inInventory = new HashMap<>();
    public static HashMap<Player, Integer> clickedInvSlot = new HashMap<>();
    public static HashMap<Material, HashMap<Integer, Material>> removedRecipes = new HashMap<>();
}
