package me.tvhee.customitems.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityDamageByEntity implements Listener
{
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e)
    {
        Entity entity = e.getDamager();

        if(entity instanceof Player)
        {
            Player player = (Player) entity;
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if(meta == null || !meta.hasCustomModelData())
            {
                return;
            }

            if(item.getType().equals(Material.DIAMOND_SWORD) && meta.getCustomModelData() != 0)
            {
                e.setDamage(1);
            }
        }
    }
}
