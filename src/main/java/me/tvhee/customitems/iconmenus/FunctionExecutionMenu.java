package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.Messages;
import me.tvhee.customitems.util.ConfigUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class FunctionExecutionMenu
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static IconMenu getMenu(String itemName)
    {
        IconMenu menu = new IconMenu(ChatUtils.format(Messages.editMenu), 9, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                int slot = e.getPosition();
                Player player = e.getPlayer();

                if(slot == 2)
                {
                    ConfigUtil.setItemFunctionExecutionOn(itemName, "left-click-on-block");

                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 3)
                {
                    ConfigUtil.setItemFunctionExecutionOn(itemName,"left-click-on-air");
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 4)
                {
                    ConfigUtil.setItemFunctionExecutionOn(itemName,"right-click-on-block");
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 5)
                {
                    ConfigUtil.setItemFunctionExecutionOn(itemName,"right-click-on-air");
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 8)
                {
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
            }
        }, plugin);

        menu.setOption(2, new ItemStack(Material.BLUE_WOOL), ChatColor.AQUA + "on-left-click-on-block", Collections.singletonList(ChatUtils.format(Messages.functionExecutionMenuSet)));
        menu.setOption(3, new ItemStack(Material.LIGHT_BLUE_WOOL), ChatColor.AQUA + "on-left-click-on-air", Collections.singletonList(ChatUtils.format(Messages.functionExecutionMenuSet)));
        menu.setOption(4, new ItemStack(Material.GREEN_WOOL), ChatColor.AQUA + "on-right-click-on-block", Collections.singletonList(ChatUtils.format(Messages.functionExecutionMenuSet)));
        menu.setOption(5, new ItemStack(Material.LIME_WOOL), ChatColor.AQUA + "on-right-click-on-air", Collections.singletonList(ChatUtils.format(Messages.functionExecutionMenuSet)));
        menu.setOption(8, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);
        return menu;
    }
}
