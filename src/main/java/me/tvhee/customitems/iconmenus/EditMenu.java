package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.api.bukkit.items.ItemBuilder;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.HashMaps;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.ItemUtil;
import me.tvhee.customitems.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class EditMenu
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

        int durability = ConfigUtil.getItemDurability(itemName);

        HashMap<Integer, String> materials = new HashMap<>();
        for(int i = 1; i < 10; i++)
        {
            materials.put(i, ConfigUtil.getItemCraftingMaterial(itemName, i));
        }

        IconMenu menu = new IconMenu(ChatUtils.format(Messages.editMenu), 27, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                Player player = e.getPlayer();

                if(e.getPosition() == 4 || e.getPosition() == 5 || e.getPosition() == 6 || e.getPosition() == 13 || e.getPosition() == 14 || e.getPosition() == 15 || e.getPosition() == 22 || e.getPosition() == 23 || e.getPosition() == 24)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewMat));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 8)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemName));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 9)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemName));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 10)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewId));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 11)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewLore));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 12)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewDurability));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 16)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewMat));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "CustomItemsEditMenu");
                    ItemUtil.addSaveItem(player, itemName);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 17)
                {
                    ItemUtil.removeInventory(player);
                    HashMaps.saveMenuPage.put(player, page);
                    ItemUtil.addSaveItem(player, itemName);
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(e.getPosition() == 18)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemRemoved));
                    ConfigUtil.setItemRemoved(itemName);
                    ItemUtil.removeInventory(player);

                    e.openNewMenu(MainMenu.getMenu(page), player);
                    return;
                }
                else if(e.getPosition() == 26)
                {
                    plugin.reloadConfig();
                    ItemUtil.removeInventory(player);
                    e.openNewMenu(MainMenu.getMenu(page),player);
                    return;
                }
            }
        }, plugin);

        ItemStack toSet = item;
        ItemMeta meta = toSet.getItemMeta();
        meta.setDisplayName(ConfigUtil.getItemDisplayName(itemName));
        toSet.setItemMeta(meta);

        menu.setOption(0, toSet);
        menu.setOption(8, new ItemStack(Material.NAME_TAG, 1), ChatUtils.format(Messages.editMenuItemDisplayName) + ChatColor.WHITE + ConfigUtil.getItemDisplayName(itemName), null);
        menu.setOption(9, new ItemStack(Material.PAPER, 1), ChatUtils.format(Messages.editMenuItemName) + ChatColor.WHITE + itemName, null);
        menu.setOption(10, new ItemStack(Material.DIAMOND, 1), ChatUtils.format(Messages.editMenuID) + ChatColor.WHITE + item.getItemMeta().getCustomModelData(), null);
        menu.setOption(11, new ItemStack(Material.BOOK, 1), ChatUtils.format(Messages.editMenuLore), item.getItemMeta().getLore());
        menu.setOption(12, new ItemStack(Material.LAVA_BUCKET, 1), ChatUtils.format(Messages.editMenuDurability) + ChatColor.WHITE + durability, null);
        menu.setOption(16, new ItemStack(mainMaterial, 1), ChatUtils.format(Messages.editMenuChangeMainMaterial), null);
        menu.setOption(17, new ItemStack(Material.GREEN_WOOL, 1), ChatUtils.format(Messages.editMenuFunctionsMenu), null);
        menu.setOption(18, new ItemStack(Material.RED_WOOL, 1), ChatUtils.format(Messages.editMenuRemoveItem), null);
        menu.setOption(26, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);

        menu.setOption(4, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "1") + ChatColor.WHITE + materials.get(1).toLowerCase(), null);
        menu.setOption(5, new ItemStack(Material.LEGACY_WORKBENCH, 1), null, null);
        menu.setOption(6, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "3") + ChatColor.WHITE + materials.get(3).toLowerCase(), null);
        menu.setOption(13, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "4") + ChatColor.WHITE + materials.get(4).toLowerCase(), null);
        menu.setOption(14, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "5") + ChatColor.WHITE + materials.get(5).toLowerCase(), null);
        menu.setOption(15, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "6") + ChatColor.WHITE + materials.get(6).toLowerCase(), null);
        menu.setOption(22, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "7") + ChatColor.WHITE + materials.get(7).toLowerCase(), null);
        menu.setOption(23, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "8") + ChatColor.WHITE + materials.get(8).toLowerCase(), null);
        menu.setOption(24, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "9") + ChatColor.WHITE + materials.get(9).toLowerCase(), null);
        return menu;
    }

}
