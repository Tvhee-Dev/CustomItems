package me.tvhee.customitems.iconmenus;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.gui.IconMenu;
import me.tvhee.api.bukkit.items.ItemBuilder;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.ItemUtil;
import me.tvhee.customitems.util.Messages;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomRecipesMenu
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static IconMenu getMenu(int page)
    {
        int items = 0;

        if(plugin.getConfig().contains(ConfigUtil.configurationSection))
        {
            items = plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false).size();
        }

        int finalItems = items;

        IconMenu menu = new IconMenu(ChatUtils.format(Messages.menu), 54, new IconMenu.OptionClickEventHandler()
        {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent e)
            {
                Player player = e.getPlayer();
                int position = e.getPosition();

                e.setWillDestroy(false);

                if(position < 45)
                {
                    if(e.getClick().isLeftClick())
                    {
                        Object[] itemsByName = plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false).toArray();

                        if(itemsByName[position + (page - 1) * 45] != null)
                        {
                            String name = itemsByName[position + (page - 1) * 45].toString();
                            e.openNewMenu(CraftingRecipeShowerMenu.getMenu(name, page), player);
                            return;
                        }
                        else
                        {
                            e.setWillClose(false);
                            e.setWillDestroy(false);
                        }
                    }
                }
                else if(position == 48)
                {
                    if(page != 1)
                    {
                        e.openNewMenu(MainMenu.getMenu(page - 1), player);
                        return;
                    }

                    e.setWillClose(false);
                    e.setWillDestroy(false);
                }
                else if(position == 49)
                {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                }
                else if(position == 50)
                {
                    if(page * 45 < finalItems + 1)
                    {
                        e.openNewMenu(MainMenu.getMenu(page + 1), player);
                        return;
                    }
                }
                else if(position == 53)
                {
                    ItemUtil.removeInventory(e.getPlayer());
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                    return;
                }
            }
        }, plugin);

        if(plugin.getConfig().contains(ConfigUtil.configurationSection))
        {
            for(int i = 0; i < 45; i++)
            {
                Object[] itemsByName = plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false).toArray();

                if(i + (page - 1) * 45 < itemsByName.length)
                {
                    String name = ChatUtils.format("&f" + itemsByName[i + (page - 1) * 45].toString());
                    int id = ConfigUtil.getItemID(itemsByName[i + (page - 1) * 45].toString());
                    String material = ConfigUtil.getItemMain(itemsByName[i + (page - 1) * 45].toString());
                    List<String> lore = ConfigUtil.getItemLore(itemsByName[i + (page - 1) * 45].toString());
                    Material mainMaterial;

                    try
                    {
                        mainMaterial = Material.valueOf(material);
                    }
                    catch(IllegalArgumentException ignored)
                    {
                        plugin.getLogger().warning("Error: The material from item " + itemsByName[i + (page - 1) * 45].toString() + " does not exist!");
                        return null;
                    }

                    ItemStack item = new ItemBuilder(mainMaterial).setModel(id).toItemStack();
                    menu.setOption(i, item, name, lore);
                }
                else
                {
                    break;
                }
            }
        }

        menu.setOption(48, new ItemStack(Material.RED_WOOL, 1), ChatUtils.format(Messages.previousButton), null);
        menu.setOption(49, new ItemStack(Material.CLOCK, 1), ChatUtils.format(Messages.currentPage).replaceAll("%page%", String.valueOf(page)) + page, null);
        menu.setOption(50, new ItemStack(Material.GREEN_WOOL, 1), ChatUtils.format(Messages.nextButton), null);
        menu.setOption(53, new ItemStack(Material.BARRIER, 1), ChatUtils.format(Messages.closeMenu), null);
        return menu;
    }
}
