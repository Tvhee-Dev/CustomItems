package me.tvhee.customitems.listeners;

import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.ConfigUtil;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PrepareItemCraft implements Listener
{
    private CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    @EventHandler
    public void onPrepareCraftEvent(PrepareItemCraftEvent e)
    {
        if(e.getInventory().getMatrix().length < 9 || ConfigUtil.getPluginRemoveAllVanillaRecipes())
        {
            return;
        }

        ItemStack[] matrix = e.getInventory().getMatrix();

        if(plugin.getConfig().contains(ConfigUtil.configurationSectionVanillaRecipes))
        {
            for(String s : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSectionVanillaRecipes).getKeys(false))
            {
                HashMap<Integer, Material> ingredients = new HashMap<>();

                for(int i = 1; i < 10; i++)
                {
                    int toPut = i - 1;

                    try
                    {
                        ingredients.put(toPut, Material.valueOf(ConfigUtil.getVanillaCraftingMaterial(s, i)));
                    }
                    catch(IllegalArgumentException ignored)
                    {
                        plugin.getLogger().warning("Material" + i + " from item " + s + " is not an valid material! (CraftingRecipe remover)");
                    }
                }

                for(int i = 0; i < 9; i++)
                {
                    if(ingredients.containsKey(i))
                    {
                        if(matrix[i] == null || !matrix[i].getType().equals(ingredients.get(i)))
                        {
                            return;
                        }
                    }
                    else
                    {
                        if(matrix[i] != null)
                        {
                            return;
                        }
                    }
                }

                try
                {
                    Material.valueOf(ConfigUtil.getVanillaCraftingMaterial(s, 0));
                }
                catch(IllegalArgumentException ignored)
                {
                    plugin.getLogger().warning("The result material" + " from item " + s + " is not an valid material! (CraftingRecipe remover)");
                }

                if(e.getInventory().getResult().getType().equals(Material.valueOf(plugin.getConfig().getString(ConfigUtil.configurationSectionVanillaRecipes + s + ".result"))))
                {
                    e.getInventory().setResult(null);
                }
            }
        }
    }
}
