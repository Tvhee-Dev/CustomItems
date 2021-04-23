package me.tvhee.customitems.util;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.customitems.CustomItemsPlugin;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtil
{
    public static String configurationSection = "items";
    public static String configurationSectionVanillaRecipes = "vanilla.removed-recipes";

    private static CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    //Main section

    public static String getPluginResourcePack()
    {
        return plugin.getConfig().getString("plugin.resource-pack");
    }

    public static boolean getPluginRemoveAllVanillaRecipes()
    {
        return plugin.getConfig().getBoolean("plugin.remove-all-vanilla-recipes");
    }

    public static boolean getPluginUpdateCheckerEnabled()
    {
        return plugin.getConfig().getBoolean("plugin.update-check");
    }

    public static void setPluginResourcePack(String link)
    {
        plugin.getConfig().set("plugin.resource-pack", link);
        plugin.saveConfig();
    }

    //Items Section

    public static String getItemMain(String itemName)
    {
        return plugin.getConfig().getString(configurationSection + "." + itemName + ".main");
    }

    public static String getItemDisplayName(String itemName)
    {
        return ChatUtils.format(plugin.getConfig().getString(configurationSection + "." + itemName + ".displayname"));
    }

    public static int getItemID(String itemName)
    {
        return plugin.getConfig().getInt(configurationSection + "." + itemName + ".id");
    }

    public static int getItemDurability(String itemName)
    {
        return plugin.getConfig().getInt(configurationSection + "." + itemName + ".durability");
    }

    public static boolean getItemLoreEnabled(String itemName)
    {
        return plugin.getConfig().getBoolean(configurationSection + "." + itemName + ".lore.enabled");
    }

    public static List<String> getItemLore(String itemName)
    {
        List<String> formatted = new ArrayList<>();
        List<String> unformatted = plugin.getConfig().getStringList(configurationSection + "." + itemName + ".lore.text");
        for(int i = 0; i < unformatted.size(); i++)
        {
            formatted.add(ChatUtils.format(unformatted.get(i)));
        }
        return formatted;
    }

    public static List<String> getItemLoreUnformatted(String itemName)
    {
        return plugin.getConfig().getStringList(configurationSection + "." + itemName + ".lore.text");
    }

    public static boolean getItemCraftingEnabled(String itemName)
    {
        return plugin.getConfig().getBoolean(configurationSection + "." + itemName + ".crafting.enabled");
    }

    public static String getItemCraftingMaterial(String itemName, int slot)
    {
        if(slot == 0)
        {
            return plugin.getConfig().getString(configurationSection + "." + itemName + ".crafting.result");
        }
        else
        {
            return plugin.getConfig().getString(configurationSection + "." + itemName + ".crafting.slot" + slot);
        }
    }

    public static String getItemFunctionExecutionOn(String itemName)
    {
        return plugin.getConfig().getString(configurationSection + "." + itemName + ".function-on");
    }

    public static boolean getItemFunctionCommandEnabled(String itemName)
    {
        return plugin.getConfig().getBoolean(configurationSection + "." + itemName + ".function.command.enabled");
    }

    public static String getItemFunctionCommand(String itemName)
    {
        return plugin.getConfig().getString(configurationSection + "." + itemName + ".function.command.name");
    }

    public static boolean getItemFunctionElytraBoostEnabled(String itemName)
    {
        return plugin.getConfig().getBoolean(configurationSection + "." + itemName + ".function.elytra-boost.enabled");
    }

    public static double getItemFunctionElytraBoost(String itemName)
    {
        return plugin.getConfig().getDouble(configurationSection + "." + itemName + ".function.elytra-boost.boost");
    }

    public static void setItemMainValidated(String itemName, String itemMain)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".main", itemMain);
        plugin.saveConfig();
    }

    public static boolean setItemMain(String itemName, String itemMain)
    {
        try
        {
            Material.valueOf(itemMain.toUpperCase().replaceAll(" ", "_"));
            setItemMainValidated(itemName, itemMain.toUpperCase().replaceAll(" ", "_"));
            return true;
        }
        catch(IllegalArgumentException ignored)
        {
            return false;
        }
    }

    public static void setItemDisplayName(String itemName, String displayName)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".displayname", ChatUtils.format(displayName));
        plugin.saveConfig();
    }

    public static void setItemID(String itemName, int id)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".id", id);
        plugin.saveConfig();
    }

    public static void setItemDurability(String itemName, int durability)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".durability", durability);
        plugin.saveConfig();
    }

    public static void setItemLoreEnabled(String itemName, boolean itemLore)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".lore.enabled", itemLore);
        plugin.saveConfig();
    }

    public static void setItemLore(String itemName, List<String> lore)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".lore.text", lore);
        plugin.saveConfig();
    }

    public static void setItemCraftingEnabled(String itemName, boolean itemCrafting)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".crafting.enabled", itemCrafting);
        plugin.saveConfig();
    }

    public static void setItemCraftingMaterialValidated(String itemName, int slot, String craftingMaterial)
    {
        if(slot == 0)
        {
            plugin.getConfig().set(configurationSection + "." + itemName + ".crafting.result", craftingMaterial);
            plugin.saveConfig();
        }
        else
        {
            plugin.getConfig().set(configurationSection + "." + itemName + ".crafting.slot" + slot, craftingMaterial);
            plugin.saveConfig();
        }
    }

    public static boolean setItemCraftingMaterial(String itemName, int slot, String craftingMaterial)
    {
        if(plugin.getConfig().contains(configurationSection))
        {
            for(String s : plugin.getConfig().getConfigurationSection(configurationSection).getKeys(false))
            {
                if(s.equals(craftingMaterial))
                {
                    setItemCraftingMaterialValidated(itemName, slot, craftingMaterial);
                    return true;
                }
            }
        }

        try
        {
            Material.valueOf(craftingMaterial);
            setItemCraftingMaterialValidated(itemName, slot, craftingMaterial.toUpperCase().replaceAll(" ", "_"));
            return true;
        }
        catch(IllegalArgumentException ignored)
        {
            return false;
        }
    }

    public static void setItemFunctionExecutionOn(String itemName, String function)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".function-on", function);
        plugin.saveConfig();
    }

    public static void setItemFunctionCommandEnabled(String itemName, boolean commandExecution)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".function.command.enabled", commandExecution);
        plugin.saveConfig();
    }

    public static void setItemFunctionCommand(String itemName, String command)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".function.command.name", command);
        plugin.saveConfig();
    }

    public static void setItemFunctionElytraBoostEnabled(String itemName, boolean elytraBoost)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".function.elytra-boost.enabled", elytraBoost);
        plugin.saveConfig();
    }

    public static void setItemFunctionElytraBoost(String itemName, double boost)
    {
        plugin.getConfig().set(configurationSection + "." + itemName + ".function.elytra-boost.boost", boost);
        plugin.saveConfig();
    }

    public static void setItemRemoved(String itemName)
    {
        plugin.getConfig().set(configurationSection + "." + itemName, null);
        plugin.saveConfig();
    }

    public static void setItemRenamed(String oldName, String newName)
    {
        ItemUtil.copyItem(newName, oldName, true);
        plugin.saveConfig();
    }

    public static void setItemDuplicated(String oldName, String newName)
    {
        ItemUtil.copyItem(newName, oldName, false);
        plugin.saveConfig();
    }

    //Vanilla Remove Recipes Section

    public static void setVanillaCraftingMaterialValidated(String itemName, int slot, String craftingMaterial)
    {
        if(slot == 0)
        {
            plugin.getConfig().set(configurationSectionVanillaRecipes + "." + itemName + ".result", craftingMaterial);
            plugin.saveConfig();
        }
        else
        {
            plugin.getConfig().set(configurationSectionVanillaRecipes + "." + itemName + ".slot" + slot, craftingMaterial);
            plugin.saveConfig();
        }
    }

    public static boolean setVanillaCraftingMaterial(String itemName, int slot, String craftingMaterial)
    {
        try
        {
            Material.valueOf(craftingMaterial.toUpperCase().replaceAll(" ", "_"));
            setVanillaCraftingMaterialValidated(itemName, slot, craftingMaterial.toUpperCase().replaceAll(" ", "_"));
            return true;
        }
        catch(IllegalArgumentException ignored)
        {
            return false;
        }
    }

    public static void setVanillaRecipeRenamed(String oldName, String newName)
    {
        ItemUtil.copyItem(newName, oldName, true);
        plugin.saveConfig();
    }

    public static void setVanillaRecipeRemoved(String name)
    {
        plugin.getConfig().set(configurationSectionVanillaRecipes + "." + name, null);
        plugin.saveConfig();
    }

    public static String getVanillaCraftingMaterial(String itemName, int slot)
    {
        if(slot == 0)
        {
            return plugin.getConfig().getString(configurationSectionVanillaRecipes + "." + itemName + ".result");
        }
        else
        {
            return plugin.getConfig().getString(configurationSectionVanillaRecipes + "." + itemName + ".slot" + slot);
        }
    }
}
