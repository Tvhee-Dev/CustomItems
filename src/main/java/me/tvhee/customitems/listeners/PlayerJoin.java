package me.tvhee.customitems.listeners;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.Messages;
import me.tvhee.customitems.updater.Updater;
import me.tvhee.customitems.util.ConfigUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if(ConfigUtil.getPluginResourcePack() == null)
        {
            return;
        }

        Player player = e.getPlayer();
        player.setResourcePack(ConfigUtil.getPluginResourcePack());

        if(player.hasPermission("customitems.updates"))
        {
            if(ConfigUtil.getPluginUpdateCheckerEnabled())
            {
                if(new Updater().updateAvailable())
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
                    return;
                }
                else
                {
                    if(CustomItemsPlugin.getInstance().getConfig().getBoolean("plugin.no-update-available-message-on-join"))
                    {
                        player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
                    }
                    return;
                }
            }
            else
            {
                player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
                return;
            }
        }
    }
}
