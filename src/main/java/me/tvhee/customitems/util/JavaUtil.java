package me.tvhee.customitems.util;

import me.tvhee.api.bukkit.chat.ChatUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class JavaUtil
{
    public static List<String> splitMessage(String message, String character)
    {
        List<String> function = Arrays.asList(message.split(character));
        return function;
    }

    public static boolean reverse(boolean reverse)
    {
        if(reverse)
        {
            return false;
        }
        return true;
    }

    public static int parseInt(String message, Player player)
    {
        try
        {
            int msg = Integer.parseInt(message);
            return msg;
        }
        catch(NumberFormatException ignored)
        {
            player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noIntUsed));
            return -1;
        }
    }
}
