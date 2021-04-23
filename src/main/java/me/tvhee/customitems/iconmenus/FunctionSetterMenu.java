package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.HashMaps;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.ItemUtil;
import me.tvhee.customitems.util.JavaUtil;
import me.tvhee.customitems.util.Messages;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionSetterMenu
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static IconMenu getMenu(String itemName)
    {
        boolean craftingEnabled = ConfigUtil.getItemCraftingEnabled(itemName);
        List<String> crafting = new ArrayList<>();
        if(craftingEnabled)

        {
            crafting.add(ChatUtils.format(Messages.functionSetterMenuEnabled));
        }
        else
        {
            crafting.add(ChatUtils.format(Messages.functionSetterMenuDisabled));
        }

        boolean commandExecutionEnabled = ConfigUtil.getItemFunctionCommandEnabled(itemName);
        List<String> commandExecution = new ArrayList<>();

        if(commandExecutionEnabled)
        {
            commandExecution.add(ChatUtils.format(Messages.functionSetterMenuEnabled));
        }
        else
        {
            commandExecution.add(ChatUtils.format(Messages.functionSetterMenuDisabled));
        }

        boolean elytraEnabled = ConfigUtil.getItemFunctionElytraBoostEnabled(itemName);
        List<String> elytra = new ArrayList<>();

        if(elytraEnabled)
        {
            elytra.add(ChatUtils.format(Messages.functionSetterMenuEnabled));
        }
        else
        {
            elytra.add(ChatUtils.format(Messages.functionSetterMenuDisabled));
        }

        IconMenu menu = new IconMenu(ChatUtils.format(Messages.editMenu), 18, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                plugin.reloadConfig();
                int slot = e.getPosition();
                Player player = e.getPlayer();

                if(slot == 0)
                {
                    e.openNewMenu(FunctionExecutionMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 3)
                {
                    ConfigUtil.setItemCraftingEnabled(itemName, JavaUtil.reverse(craftingEnabled));
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 4)
                {
                    ConfigUtil.setItemFunctionCommandEnabled(itemName, JavaUtil.reverse(commandExecutionEnabled));
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 5)
                {
                    ConfigUtil.setItemFunctionElytraBoostEnabled(itemName, JavaUtil.reverse(elytraEnabled));
                    e.openNewMenu(FunctionSetterMenu.getMenu(itemName), player);
                    return;
                }
                else if(slot == 12)
                {
                    e.setWillDestroy(false);
                    e.setWillClose(false);
                }
                else if(slot == 13)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewCommand));
                    ItemUtil.putInventory(player, slot, HashMaps.saveMenuPage.get(player), "CustomItemsFunctionSetterMenu");
                    ItemUtil.addSaveItem(player, itemName);
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(slot == 14)
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInNewBoost));
                    ItemUtil.putInventory(player, slot, HashMaps.saveMenuPage.get(player), "CustomItemsFunctionSetterMenu");
                    ItemUtil.addSaveItem(player, itemName);
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
                else if(slot == 17)
                {
                    e.openNewMenu(EditMenu.getMenu(itemName, HashMaps.saveMenuPage.get(player)), player);
                    return;
                }

            }
        }, plugin);

        menu.setOption(0, new ItemStack(Material.BLUE_WOOL), ChatUtils.format(Messages.functionSetterMenuFunctionOn), Collections.singletonList(ChatUtils.format("&f" + ConfigUtil.getItemFunctionExecutionOn(itemName))));
        menu.setOption(3, new ItemStack(Material.LEGACY_WORKBENCH), ChatUtils.format(Messages.functionSetterMenuCrafting), crafting);
        menu.setOption(4, new ItemStack(Material.BRICK), ChatUtils.format(Messages.functionSetterMenuCommandExecution), commandExecution);
        menu.setOption(5, new ItemStack(Material.ELYTRA), ChatUtils.format(Messages.functionSetterMenuElytraBoosting), elytra);

        if(craftingEnabled)
        {
            menu.setOption(12, new ItemStack(Material.LEGACY_WORKBENCH), ChatUtils.format(Messages.functionSetterMenuCrafting), null);
        }

        if(commandExecutionEnabled)
        {
            menu.setOption(13, new ItemStack(Material.BRICK), ChatUtils.format(Messages.functionSetterMenuCommandExecution), Collections.singletonList(ChatUtils.format("&f" + ConfigUtil.getItemFunctionCommand(itemName))));
        }

        if(elytraEnabled)
        {
            menu.setOption(14, new ItemStack(Material.ELYTRA), ChatUtils.format(Messages.functionSetterMenuElytraBoosting), Collections.singletonList(ChatUtils.format("&f" + ConfigUtil.getItemFunctionElytraBoost(itemName))));
        }

        menu.setOption(17, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);
        return menu;
    }
}
