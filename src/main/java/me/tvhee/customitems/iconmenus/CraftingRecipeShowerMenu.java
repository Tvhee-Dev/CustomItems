package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.api.bukkit.items.ItemBuilder;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CraftingRecipeShowerMenu
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static IconMenu getMenu(String itemName, int page)
    {
        String material = ConfigUtil.getItemMain(itemName);
        Material mainMaterial;

        try
        {
            mainMaterial = Material.valueOf(material);
        }
        catch(IllegalArgumentException ignored)
        {
            plugin.getLogger().warning("Error: The material from item " + itemName + " does not exist!");
            return null;
        }

        ItemStack item = new ItemBuilder(mainMaterial).setName(ChatUtils.format("&f" + itemName)).setModel(ConfigUtil.getItemID(itemName)).setLore(ConfigUtil.getItemLore(itemName)).toItemStack();

        HashMap<Integer, String> materials = new HashMap<>();
        for(int i = 0; i < 9; i++)
        {
            materials.put(i, ConfigUtil.getItemCraftingMaterial(itemName, i));
        }

        IconMenu menu = new IconMenu(ChatUtils.format(Messages.craftingMenu), 27, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                if(e.getPosition() == 26)
                {
                    e.openNewMenu(CustomRecipesMenu.getMenu(page), e.getPlayer());
                    return;
                }
            }
        }, plugin);

        menu.setOption(0, item);
        menu.setOption(3, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "1") + ChatColor.WHITE + materials.get(0).toLowerCase(), null);
        menu.setOption(4, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "2") + ChatColor.WHITE + materials.get(1).toLowerCase(), null);
        menu.setOption(5, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "3") + ChatColor.WHITE + materials.get(2).toLowerCase(), null);
        menu.setOption(12, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "4") + ChatColor.WHITE + materials.get(3).toLowerCase(), null);
        menu.setOption(13, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "5") + ChatColor.WHITE + materials.get(4).toLowerCase(), null);
        menu.setOption(14, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "6") + ChatColor.WHITE + materials.get(5).toLowerCase(), null);
        menu.setOption(21, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "7") + ChatColor.WHITE + materials.get(6).toLowerCase(), null);
        menu.setOption(22, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "8") + ChatColor.WHITE + materials.get(7).toLowerCase(), null);
        menu.setOption(23, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "9") + ChatColor.WHITE + materials.get(8).toLowerCase(), null);
        menu.setOption(26, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);
        return menu;
    }
}
