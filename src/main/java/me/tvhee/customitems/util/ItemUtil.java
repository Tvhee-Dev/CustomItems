package me.tvhee.customitems.util;

import me.tvhee.api.bukkit.crafting.CraftingManager;
import me.tvhee.api.bukkit.items.DurabilityManager;
import me.tvhee.api.bukkit.items.ItemBuilder;
import me.tvhee.customitems.CustomItemsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemUtil
{
    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public static ItemStack buildCustomItem(ItemStack main, String name, int model, List<String> lore)
    {
        return new ItemBuilder(main).setName(name).setModel(model).setLore(lore).toItemStack();
    }

    public static void setDurability(ItemStack customItem, int durability)
    {
        DurabilityManager.setDurability(durability, customItem, plugin);
    }

    public static void addSaveItem(Player player, String itemName)
    {
        HashMaps.saveItem.put(player, itemName);
    }

    public static void putInventory(Player player, int slot, int page, String menu)
    {
        HashMaps.saveMenuPage.put(player, page);
        HashMaps.clickedInvSlot.put(player, slot);
        HashMaps.inInventory.put(player, menu);
    }

    public static void removeInventory(Player player)
    {
        HashMaps.inInventory.remove(player);
        HashMaps.clickedInvSlot.remove(player);
    }

    public static int getNewID()
    {
        int id = 1;
        if(plugin.getConfig().contains(ConfigUtil.configurationSection))
        {
            Set<Integer> ids = new HashSet<>();

            for(String s : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
            {
                ids.add(ConfigUtil.getItemID(s));
            }

            boolean isNotUsed = false;
            while(!isNotUsed)
            {
                if(!ids.contains(id))
                {
                    isNotUsed = true;
                }
                else
                {
                    id++;
                }
            }
        }
        return id;
    }

    public static boolean changeNameOfItem(String oldName, String newName)
    {
        if(plugin.getConfig().contains(ConfigUtil.configurationSection + "." + oldName) && !plugin.getConfig().contains(ConfigUtil.configurationSection + "." + newName))
        {
            ConfigUtil.setItemRenamed(oldName, newName);
            return true;
        }

        return false;
    }

    public static void duplicateNewItem(String newName, String oldName)
    {
        ConfigUtil.setItemDuplicated(newName, oldName);
    }

    public static boolean checkIfNameIsAlreadyTaken(String itemName, String configSection)
    {
        if(plugin.getConfig().contains(configSection))
        {
            for(String s : plugin.getConfig().getConfigurationSection(configSection).getKeys(false))
            {
                if(s.contains(itemName))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static void createNewItem(String itemName)
    {
        ConfigUtil.setItemMainValidated(itemName, "DIAMOND_SWORD");
        ConfigUtil.setItemDisplayName(itemName, "&f" + itemName);
        ConfigUtil.setItemID(itemName, getNewID());
        ConfigUtil.setItemDurability(itemName, 2000);
        ConfigUtil.setItemLoreEnabled(itemName, true);
        ConfigUtil.setItemLore(itemName, Arrays.asList("&fCool custom item!", "&fPlugin made by tvhee!"));
        ConfigUtil.setItemCraftingEnabled(itemName, false);

        for(int i = 0; i < 10; i++)
        {
            ConfigUtil.setItemCraftingMaterial(itemName, i, "GOLD_BLOCK");
        }

        ConfigUtil.setItemFunctionCommandEnabled(itemName, false);
        ConfigUtil.setItemFunctionCommand(itemName, "ci");
        ConfigUtil.setItemFunctionElytraBoostEnabled(itemName, false);
        ConfigUtil.setItemFunctionElytraBoost(itemName, 5);
    }

    public static void copyItem(String itemName, String oldItemName, boolean removeOldItem)
    {
        ConfigUtil.setItemMainValidated(itemName, ConfigUtil.getItemMain(oldItemName));
        ConfigUtil.setItemDisplayName(itemName, ConfigUtil.getItemDisplayName(oldItemName));
        ConfigUtil.setItemID(itemName, getNewID());
        ConfigUtil.setItemDurability(itemName, ConfigUtil.getItemDurability(oldItemName));
        ConfigUtil.setItemLoreEnabled(itemName, ConfigUtil.getItemLoreEnabled(oldItemName));
        ConfigUtil.setItemLore(itemName, ConfigUtil.getItemLoreUnformatted(oldItemName));
        ConfigUtil.setItemCraftingEnabled(itemName, ConfigUtil.getItemCraftingEnabled(oldItemName));

        for(int i = 0; i < 10; i++)
        {
            ConfigUtil.setItemCraftingMaterial(itemName, i, ConfigUtil.getItemCraftingMaterial(oldItemName, i));
        }

        ConfigUtil.setItemFunctionCommandEnabled(itemName, ConfigUtil.getItemFunctionCommandEnabled(oldItemName));
        ConfigUtil.setItemFunctionCommand(itemName, ConfigUtil.getItemFunctionCommand(oldItemName));
        ConfigUtil.setItemFunctionElytraBoostEnabled(itemName, ConfigUtil.getItemFunctionElytraBoostEnabled(oldItemName));
        ConfigUtil.setItemFunctionElytraBoost(itemName, ConfigUtil.getItemFunctionElytraBoost(oldItemName));

        if(removeOldItem)
        {
            ConfigUtil.setItemRemoved(oldItemName);
        }

    }

    public static void registerItems()
    {
        if(!plugin.getConfig().contains(ConfigUtil.configurationSection) && !plugin.getConfig().contains(ConfigUtil.configurationSectionVanillaRecipes))
        {
            return;
        }

        if(plugin.getConfig().contains(ConfigUtil.configurationSection))
        {
            HashMap<String, ItemStack> registeredItems = new HashMap<>();
            for(String item : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
            {
                String main = ConfigUtil.getItemMain(item);
                String displayName = ConfigUtil.getItemDisplayName(item);
                int id = ConfigUtil.getItemID(item);
                int durability = ConfigUtil.getItemDurability(item);
                ItemStack itemMain;

                try
                {
                    itemMain = new ItemStack(Material.valueOf(main));
                }
                catch(IllegalArgumentException ignored)
                {
                    plugin.getLogger().warning("Material result from the crafting recipe from item " + item + " is not a valid material!");
                    return;
                }

                ItemStack registeredItem;

                if(ConfigUtil.getItemLoreEnabled(item))
                {
                    List<String> lore = ConfigUtil.getItemLore(item);
                    registeredItem = ItemUtil.buildCustomItem(itemMain, displayName, id, lore);
                }
                else
                {
                    registeredItem = ItemUtil.buildCustomItem(itemMain, displayName, id, null);
                }

                ItemUtil.setDurability(registeredItem, durability);
                registeredItems.put(item, registeredItem);
            }

            for(String item : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
            {
                HashMap<Integer, ItemStack> craftingIngredients = new HashMap<>();
                ItemStack result = registeredItems.get(item);

                for(int i = 1; i < 10; i++)
                {
                    ItemStack craftingIngredientAsIS;
                    String configMaterial = ConfigUtil.getItemCraftingMaterial(item, i);

                    try
                    {
                        Material mat = Material.valueOf(configMaterial);
                        craftingIngredientAsIS = new ItemStack(mat);
                    }
                    catch(IllegalArgumentException ignored)
                    {
                        if(registeredItems.containsKey(configMaterial))
                        {
                            craftingIngredientAsIS = registeredItems.get(configMaterial);
                        }
                        else if(configMaterial.equals("null"))
                        {
                            craftingIngredientAsIS = null;
                        }
                        else
                        {
                            plugin.getLogger().warning("Material" + i + " from the crafting recipe from item " + item + " is not a valid material!");
                            return;
                        }
                    }
                    craftingIngredients.put(i - 1, craftingIngredientAsIS);
                }
                CraftingManager.registerRecipe(item, result, craftingIngredients, plugin);
            }
        }

        if(plugin.getConfig().contains(ConfigUtil.configurationSectionVanillaRecipes))
        {
            for(String item : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSectionVanillaRecipes).getKeys(false))
            {
                HashMap<Integer, Material> craftingIngredients = new HashMap<>();
                String configResult = ConfigUtil.getVanillaCraftingMaterial(item, 0);
                Material result;

                try
                {
                    result = Material.valueOf(configResult);
                }
                catch(IllegalArgumentException ignored)
                {
                    plugin.getLogger().warning("Material" + configResult + " from the crafting recipe from item " + item + " is not a valid material!");
                    return;
                }

                for(int i = 1; i < 10; i++)
                {
                    Material craftingIngredientAsIS = null;
                    String configMaterial = ConfigUtil.getVanillaCraftingMaterial(item, i);

                    try
                    {
                        craftingIngredientAsIS = Material.valueOf(configMaterial);
                    }
                    catch(IllegalArgumentException ignored)
                    {
                        if(configMaterial.equals("null"))
                        {
                            craftingIngredientAsIS = null;
                        }
                        else
                        {
                            plugin.getLogger().warning("Material" + i + " from the crafting recipe from item " + item + " is not a valid material!");
                        }

                    }
                    craftingIngredients.put(i - 1, craftingIngredientAsIS);
                }
                HashMaps.removedRecipes.put(result, craftingIngredients);
            }
        }
    }
}
