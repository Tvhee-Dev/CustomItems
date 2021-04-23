package me.tvhee.customitems.listeners;

import me.tvhee.api.bukkit.items.DurabilityManager;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.ConfigUtil;

import me.tvhee.customitems.util.HashMaps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteract implements Listener
{
    private CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();

        if(HashMaps.inInventory.containsKey(player))
        {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        String itemName = null;

        if(meta == null || !meta.hasCustomModelData())
        {
            return;
        }
        else if(meta.getCustomModelData() != 0)
        {
            for(String s : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
            {
                if(item.getItemMeta().getCustomModelData() == ConfigUtil.getItemID(s))
                {
                    itemName = s;
                }
            }

            if(itemName == null)
            {
                return;
            }
        }

        String function = ConfigUtil.getItemFunctionExecutionOn(itemName);
        String action = null;

        if(function.equals("left-click-on-block"))
        {
            action = "LEFT_CLICK_BLOCK";
        }
        else if(function.equals("right-click-on-block"))
        {
            action = "RIGHT_CLICK_BLOCK";
        }
        else if(function.equals("left-click-on-air"))
        {
            action = "LEFT_CLICK_AIR";
        }
        else if(function.equals("right-click-on-air"))
        {
            action = "RIGHT_CLICK_AIR";
        }

        if(e.getAction().compareTo(Action.valueOf(action)) == 0)
        {
            DurabilityManager.setDamage(item, 1.0, player);

            if(ConfigUtil.getItemFunctionCommandEnabled(itemName))
            {
                Bukkit.dispatchCommand(player, ConfigUtil.getItemFunctionCommand(itemName).replaceAll("/", ""));
            }

            if(ConfigUtil.getItemFunctionCommandEnabled(itemName))
            {
                if(player.getInventory().getChestplate() != null)
                {
                    if(player.getInventory().getChestplate().getType() == Material.ELYTRA)
                    {
                        Location playerLocation = player.getLocation();
                        if(player.isSprinting() || player.isFlying() || playerLocation.getBlock().isEmpty())
                        {
                            double boost = ConfigUtil.getItemFunctionElytraBoost(itemName);
                            player.setVelocity(player.getVelocity().add(player.getLocation().getDirection().multiply(boost)));
                        }
                    }
                }
            }
        }
    }
}
