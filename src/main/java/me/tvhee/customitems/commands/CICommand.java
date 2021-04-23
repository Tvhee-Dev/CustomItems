package me.tvhee.customitems.commands;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.customitems.CustomItemsPlugin;
import me.tvhee.customitems.util.Messages;
import me.tvhee.customitems.updater.Updater;
import me.tvhee.customitems.util.ConfigUtil;
import me.tvhee.customitems.iconmenus.CustomRecipesMenu;
import me.tvhee.customitems.iconmenus.MainMenu;
import me.tvhee.customitems.iconmenus.VanillaCraftingMenu;
import me.tvhee.customitems.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CICommand implements CommandExecutor
{
	private CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length == 0)
		{
			sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu1));
			sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu2).replaceAll("%version%", plugin.getDescription().getVersion()));
			sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu3));
			sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu4) + " tvhee");
			sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu5));
			return true;
		}
		else
		{
			if(args[0].equalsIgnoreCase("help"))
			{
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu1));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu2));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu3));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu4));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu5));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu6));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu7));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu8));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu9));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu10));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu11));
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu12));
				return true;
			}
			else if(args[0].equalsIgnoreCase("give"))
			{
				if(sender instanceof Player)
				{
					if(sender.hasPermission("customitems.give"))
					{
						Player player = (Player) sender;

						if(args.length == 2)
						{
							if(plugin.getConfig().contains(ConfigUtil.configurationSection))
							{
								for(String s : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
								{
									if(s.equals(args[1]))
									{
										Material mainMaterial;

										try
										{
											mainMaterial = Material.valueOf(ConfigUtil.getItemMain(s));
										}
										catch(IllegalArgumentException ignored)
										{
											plugin.getLogger().warning("Error: The material from item " + s + " does not exist!");
											return true;
										}

										ItemStack item = ItemUtil.buildCustomItem(new ItemStack(mainMaterial), ConfigUtil.getItemDisplayName(s), ConfigUtil.getItemID(s), ConfigUtil.getItemLore(s));
										player.getInventory().addItem(item);
										player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemGivenToPlayer).replaceAll("%item%", s).replaceAll("%player%", "you"));
										return true;
									}
								}
							}

							player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemNotFound).replaceAll("%item%", args[1]));
							return true;
						}
						else if(args.length == 3)
						{
							Player target = Bukkit.getPlayer(args[2]);

							if(target == null)
							{
								player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.playerNotFound).replaceAll("%player%", args[2]));
								return true;
							}

							for(String s : plugin.getConfig().getConfigurationSection(ConfigUtil.configurationSection).getKeys(false))
							{
								if(s.equals(args[1]))
								{
									Material mainMaterial;

									try
									{
										mainMaterial = Material.valueOf(ConfigUtil.getItemMain(s));
									}
									catch(IllegalArgumentException ignored)
									{
										plugin.getLogger().warning("Error: The material from item " + s + " does not exist!");
										return true;
									}

									ItemStack item = ItemUtil.buildCustomItem(new ItemStack(mainMaterial), ConfigUtil.getItemDisplayName(s), ConfigUtil.getItemID(s), ConfigUtil.getItemLore(s));
									target.getInventory().addItem(item);
									player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.itemGivenToPlayer).replaceAll("%item%", s).replaceAll("%player%", target.getName()));
									return true;
								}
							}

							player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.typeInItemNotFound).replaceAll("%item%", args[1]));
							return true;
						}
						else
						{
							player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.giveWrong));
						}
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.cmdNoPlayer));
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(sender.hasPermission("customitems.reload"))
				{
					plugin.reloadConfig();

					final PluginManager plg = Bukkit.getPluginManager();
					final Plugin plgname = plg.getPlugin(plugin.getName());
					Bukkit.clearRecipes();
					plg.disablePlugin(plgname);
					plg.enablePlugin(plgname);

					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.reload));

					if(ConfigUtil.getPluginUpdateCheckerEnabled())
					{
						if(Updater.getInstance().updateAvailable())
						{
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
							return true;
						}
						else
						{
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
							return true;
						}
					}
					else
					{
						sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
						return true;
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noPermission));
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("menu"))
			{
				if(sender instanceof Player)
				{
					if(sender.hasPermission("customitems.menu"))
					{
						Player player = (Player) sender;
						MainMenu.getMenu(1).open(player);
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.cmdNoPlayer));
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("recipes"))
			{
				if(sender instanceof Player)
				{
					if(sender.hasPermission("customitems.recipes"))
					{
						Player player = (Player) sender;
						CustomRecipesMenu.getMenu(1).open(player);
						return true;
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.cmdNoPlayer));
					return true;
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("resourcepack"))
			{
				if(sender.hasPermission("customitems.resourcepack"))
				{
					if(args.length == 3 && args[1].equalsIgnoreCase("load"))
					{
						String link = args[2];
						ConfigUtil.setPluginResourcePack(link);

						for(Player player : Bukkit.getOnlinePlayers())
						{
							player.setResourcePack(link);
						}

						sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.resourcePackLoaded));
						return true;
					}
					else if(args.length == 2 && args[1].equalsIgnoreCase("unload"))
					{
						ConfigUtil.setPluginResourcePack(null);

						for(Player player : Bukkit.getOnlinePlayers())
						{
							player.kickPlayer(ChatUtils.format(Messages.igprefix + Messages.kickResourcePackUnload));
						}

						sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.resourcePackUnloaded));
						return true;
					}
					else
					{
						sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.resourcePackWrong));
						return true;
					}
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("vanilla-recipes"))
			{
				if(sender instanceof Player)
				{
					if(sender.hasPermission("customitems.vanilla-recipes"))
					{
						if(!ConfigUtil.getPluginRemoveAllVanillaRecipes())
						{
							Player player = (Player) sender;
							VanillaCraftingMenu.getMenu(1).open(player);
							return true;
						}
						else
						{
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.recipesAlreadyRemoved));
							return true;
						}
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.cmdNoPlayer));
					return true;
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("update"))
			{
				if(sender.hasPermission("customitems.updates"))
				{
					if(ConfigUtil.getPluginUpdateCheckerEnabled())
					{
						if(Updater.getInstance().updateAvailable())
						{
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
							return true;
						}
						else
						{
							sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
							return true;
						}
					}
					else
					{
						sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
						return true;
					}
				}
				else
				{
					sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noPermission));
					return true;
				}
			}
			else
			{
				sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.cmdNotFound));
				return true;
			}
		}
	}
}
