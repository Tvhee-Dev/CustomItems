package me.tvhee.customitems.listeners;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.customitems.iconmenus.EditMenu;
import me.tvhee.customitems.iconmenus.FunctionSetterMenu;
import me.tvhee.customitems.iconmenus.MainMenu;
import me.tvhee.customitems.iconmenus.VanillaCraftingRecipeMenu;
import me.tvhee.customitems.util.ItemUtil;
import me.tvhee.customitems.util.HashMaps;
import me.tvhee.customitems.util.JavaUtil;
import me.tvhee.customitems.util.Messages;
import me.tvhee.customitems.util.ConfigUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerChat implements Listener
{
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        String message = e.getMessage();

        if(!HashMaps.inInventory.containsKey(player))
        {
            return;
        }

        e.getRecipients().clear();

        if(message.equalsIgnoreCase("exit"))
        {
            MainMenu.getMenu(1);
            HashMaps.inInventory.remove(player);
            HashMaps.clickedInvSlot.remove(player);
            HashMaps.saveItem.remove(player);
            return;
        }

        int slot = HashMaps.clickedInvSlot.get(player);
        String inventory = HashMaps.inInventory.get(player);
        int saveMenuPage = HashMaps.saveMenuPage.get(player);
        String saveItem = HashMaps.saveItem.get(player);

        if(inventory.equals("CustomItemsMainMenu"))
        {
            List<String> splittedMsg = JavaUtil.splitMessage(message, " ");
            if(splittedMsg.size() > 1)
            {
                player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemNameNotCorrect));
                return;
            }

            String item = splittedMsg.get(0);

            if(slot == 45 || slot == 100)
            {
                if(ItemUtil.checkIfNameIsAlreadyTaken(item, ConfigUtil.configurationSection))
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.nameAlreadyTaken));
                    return;
                }

                if(slot == 45)
                {
                    ItemUtil.createNewItem(message);
                }
                else
                {
                    String saveOldItem = HashMaps.saveOldItem.get(player);
                    ItemUtil.duplicateNewItem(saveOldItem, item);
                    HashMaps.saveOldItem.remove(player);
                    HashMaps.inInventory.remove(player);
                }

                EditMenu.getMenu(item, saveMenuPage).open(player);
                HashMaps.saveMenuPage.remove(player);
                ItemUtil.addSaveItem(player, item);
                return;
            }
            else if(slot == 46)
            {
                String oldItem = item;

                if(!ItemUtil.checkIfNameIsAlreadyTaken(message, ConfigUtil.configurationSection))
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemNotFound).replaceAll("%item%", oldItem));
                    return;
                }

                HashMaps.saveOldItem.put(player, oldItem);
                player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemName));
                HashMaps.clickedInvSlot.remove(player);
                HashMaps.clickedInvSlot.put(player, 100);
                return;
            }
        }
        else if(inventory.equals("CustomItemsEditMenu"))
        {
            int square = 0;

            switch(slot)
            {
                case 4: square = 1;
                    break;
                case 5: square = 2;
                    break;
                case 6: square = 3;
                    break;
                case 8:
                    ConfigUtil.setItemDisplayName(saveItem, message);
                    break;
                case 9:
                    List<String> splittedMsg = JavaUtil.splitMessage(message, " ");
                    if(splittedMsg.size() > 1)
                    {
                        player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemNameNotCorrect));
                        return;
                    }
                    String item = splittedMsg.get(0);
                    ItemUtil.changeNameOfItem(saveItem, item);
                    ItemUtil.addSaveItem(player, message);
                    break;
                case 10:
                    int parsed = JavaUtil.parseInt(message, player);
                    if(parsed != -1)
                    {
                        ConfigUtil.setItemID(saveItem, parsed);
                    }
                    break;
                case 11:
                    if(message.equals("false"))
                    {
                        ConfigUtil.setItemLoreEnabled(saveItem, false);
                    }
                    else
                    {
                        List<String> lore = Arrays.asList(message.split("%n"));
                        ConfigUtil.setItemLore(saveItem, lore);
                    }
                    break;
                case 12:
                    int parsed2 = JavaUtil.parseInt(message, player);
                    if(parsed2 != -1)
                    {
                        ConfigUtil.setItemID(saveItem, parsed2);
                    }
                    try
                    {
                        int msg = Integer.parseInt(message);
                        ConfigUtil.setItemDurability(saveItem, msg);
                    }
                    catch(NumberFormatException ignored)
                    {
                        player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noIntUsed));
                        return;
                    }
                    break;
                case 13: square = 4;
                    break;
                case 14: square = 5;
                    break;
                case 15: square = 6;
                    break;
                case 16:
                    if(!ConfigUtil.setItemMain(saveItem, message))
                    {
                        player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noValidMaterial).replaceAll("%material%", message));
                        return;
                    }
                    break;
                case 22: square = 7;
                    break;
                case 23: square = 8;
                    break;
                case 24: square = 9;
                    break;
            }

            if(square != 0)
            {
                if(!ConfigUtil.setItemCraftingMaterial(saveItem, square, message))
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noValidMaterial).replaceAll("%material%", message));
                    return;
                }
            }

            ItemUtil.removeInventory(player);
            EditMenu.getMenu(saveItem, saveMenuPage).open(player);
            HashMaps.saveMenuPage.remove(player);
            HashMaps.saveItem.remove(player);
            return;
        }
        else if(inventory.equals("CustomItemsFunctionSetterMenu"))
        {
            if(slot == 13)
            {
                ConfigUtil.setItemFunctionCommand(saveItem, message);
            }
            else if(slot == 14)
            {
                int parsed = JavaUtil.parseInt(message, player);
                if(parsed != -1)
                {
                    ConfigUtil.setItemFunctionElytraBoost(saveItem, parsed);
                }
            }

            ItemUtil.removeInventory(player);
            FunctionSetterMenu.getMenu(saveItem).open(player);
            return;
        }
        else if(inventory.equals("VanillaRecipesEditorMenu"))
        {
            int square = 100;

            switch(slot)
            {
                case 3: square = 1;
                    break;
                case 4: square = 2;
                    break;
                case 5: square = 3;
                    break;
                case 11:
                    if(ItemUtil.checkIfNameIsAlreadyTaken(message, ConfigUtil.configurationSectionVanillaRecipes))
                    {
                        player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.nameAlreadyTaken));
                        return;
                    }

                    ConfigUtil.setVanillaRecipeRenamed(saveItem, message);
                    break;
                case 12: square = 4;
                    break;
                case 13: square = 5;
                    break;
                case 14: square = 6;
                    break;
                case 15: square = 0;
                    break;
                case 21: square = 7;
                    break;
                case 22: square = 8;
                    break;
                case 23: square = 9;
            }

            if(square != 100)
            {
                if(!ConfigUtil.setVanillaCraftingMaterial(saveItem, square, message))
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noValidMaterial).replaceAll("%material%", message));
                    return;
                }
            }

            ItemUtil.removeInventory(player);
            VanillaCraftingRecipeMenu.getMenu(message, saveMenuPage).open(player);
            HashMaps.saveMenuPage.remove(player);
            HashMaps.saveItem.remove(player);
            return;
        }
        else if(inventory.equals("CraftingRemovedMainInventory"))
        {
            if(slot == 45)
            {
                for(int i = 0; i < 10; i++)
                {
                    ConfigUtil.setVanillaCraftingMaterial(message, i, "GOLD_BLOCK");
                }

                ItemUtil.removeInventory(player);
                VanillaCraftingRecipeMenu.getMenu(message, saveMenuPage).open(player);
                HashMaps.saveMenuPage.remove(player);
                HashMaps.saveItem.remove(player);
            }
            return;
        }
    }
}
