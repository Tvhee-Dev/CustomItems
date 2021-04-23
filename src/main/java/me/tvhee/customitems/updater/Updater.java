package me.tvhee.customitems.updater;

import me.tvhee.api.updater.UpdateChecker;
import me.tvhee.customitems.CustomItemsPlugin;

public class Updater
{
    private UpdateChecker updateChecker;
    private static Updater updater = new Updater();
    private CustomItemsPlugin plugin = CustomItemsPlugin.getInstance();

    public Updater()
    {
        setInstance(this);
        this.updateChecker = new UpdateChecker(84434, plugin.getDescription().getVersion());
    }

    public static Updater getInstance()
    {
        return updater;
    }

    public static void setInstance(Updater updater)
    {
        Updater.updater = updater;
    }

    public String getCurrentVersion()
    {
        return plugin.getDescription().getVersion();
    }

    public String getNewestVersion()
    {
        return updateChecker.getNewestVersion();
    }

    public boolean updateAvailable()
    {
        if(updateChecker.updateAvailable())
        {
            return true;
        }
        return false;
    }
}
