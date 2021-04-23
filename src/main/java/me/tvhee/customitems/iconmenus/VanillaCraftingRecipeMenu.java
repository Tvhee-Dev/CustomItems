package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.ItemUtil;
import me.tvhee.customitems.util.Messages;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VanillaCraftingRecipeMenu
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static IconMenu getMenu(String recipe, int page)
    {
        IconMenu menu = new IconMenu(ChatUtils.format(Messages.craftingMenu), 27, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                Player player = e.getPlayer();
                if(e.getPosition() == 3 || e.getPosition() == 4 || e.getPosition() == 5 || e.getPosition() == 11 || e.getPosition() == 12 || e.getPosition() == 13 || e.getPosition() == 14 || e.getPosition() == 15 || e.getPosition() == 21 || e.getPosition() == 22 || e.getPosition() == 23)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewMat));
                    ItemUtil.removeInventory(player);
                    ItemUtil.putInventory(player, e.getPosition(), page, "VanillaRecipesEditorMenu");
                    ItemUtil.addSaveItem(player, recipe);

                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(e.getPosition() == 18)
                {
                    ConfigUtil.setVanillaRecipeRemoved(recipe);
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemRemoved));
                    e.openNewMenu(VanillaCraftingMenu.getMenu(page), e.getPlayer());
                    return;
                }
                else if(e.getPosition() == 26)
                {
                    e.openNewMenu(VanillaCraftingMenu.getMenu(page), e.getPlayer());
                    return;
                }
            }
        }, plugin);

        menu.setOption(3, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "1") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 1).toLowerCase(), null);
        menu.setOption(4, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "2") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 2).toLowerCase(), null);
        menu.setOption(5, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "3") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 3).toLowerCase(), null);
        menu.setOption(11, new ItemStack(Material.PAPER), ChatUtils.format(Messages.vanillaCraftingRecipesMenuName) + ChatColor.WHITE + recipe, null);
        menu.setOption(12, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "4") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 4).toLowerCase(), null);
        menu.setOption(13, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "5") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 5).toLowerCase(), null);
        menu.setOption(14, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "6") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 6).toLowerCase(), null);
        menu.setOption(15, new ItemStack(Material.valueOf(ConfigUtil.getVanillaCraftingMaterial(recipe, 0))), ChatUtils.format(Messages.vanillaCraftingRecipesMenuResult) + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 0).toLowerCase(), null);
        menu.setOption(18, new ItemStack(Material.RED_WOOL, 1), ChatUtils.format(Messages.vanillaCraftingRecipesMenuRemove), null);
        menu.setOption(21, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "7") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 7).toLowerCase(), null);
        menu.setOption(22, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "8") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 8).toLowerCase(), null);
        menu.setOption(23, new ItemStack(Material.LEGACY_WORKBENCH, 1), ChatUtils.format(Messages.craftingIngredient).replaceAll("%number%", "9") + ChatColor.WHITE + ConfigUtil.getVanillaCraftingMaterial(recipe, 9).toLowerCase(), null);
        menu.setOption(26, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);
        return menu;
    }
}
