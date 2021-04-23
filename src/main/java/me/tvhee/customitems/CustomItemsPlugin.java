package me.tvhee.customitems;

import me.tvhee.customitems.commands.CICommand;
import me.tvhee.customitems.listeners.PrepareItemCraft;
import me.tvhee.customitems.listeners.EntityDamageByEntity;
import me.tvhee.customitems.listeners.PlayerChat;
import me.tvhee.customitems.listeners.PlayerInteract;
import me.tvhee.customitems.listeners.PlayerJoin;
import me.tvhee.customitems.util.ConfigFiles;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomItemsPlugin extends JavaPlugin
{
	private static CustomItemsPlugin instance;

	public void onEnable()
	{
		if(Bukkit.getServer().getPluginManager().getPlugin("tvheeAPI") == null)
		{
			getLogger().warning("Error: Could not find API, disabling...");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		setInstance(this);
		ConfigFiles.saveDefaultMessagesConfig();
		Bukkit.getServer().getPluginCommand("ci").setExecutor(new CICommand());

		this.saveDefaultConfig();

		if(ConfigUtil.getPluginRemoveAllVanillaRecipes())
		{
			Bukkit.clearRecipes();
		}
		else
		{
			Bukkit.resetRecipes();
		}

		ItemUtil.registerItems();

		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PrepareItemCraft(), this);

		getLogger().info("has been enabled!");
		getLogger().info("made by " + getDescription().getAuthors());
	}

	public static void setInstance(CustomItemsPlugin instance)
	{
		CustomItemsPlugin.instance = instance;
	}

	public static CustomItemsPlugin getInstance()
	{
		return instance;
	}

	@Override
	public void onDisable()
	{
		Bukkit.clearRecipes();

		getLogger().info("made by " + getDescription().getAuthors());
		getLogger().info("has been disabled!");
	}
}