package cc.moecraft.languageapi.ConsoleLoggingMessages;

import cc.moecraft.languageapi.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Kilpikonna
 */
public class MessageLogger {
    public static void log(String s)
    {
        Bukkit.getConsoleSender().sendMessage("[Language API] " + s);
    }
    
    public static void logC(String s)
    {
        Bukkit.getConsoleSender().sendMessage("[Language API][Config] " + s);
    }
    
    public static void Debug(String s)
    {
        if (LanguageAPI.debug)
        {
            log("["+ChatColor.RED+"DEBUG"+ChatColor.RESET+"("+ChatColor.GOLD+Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+ChatColor.RESET+")] "+" "+s);
        }
    }
    
    public static void Debug(Object object, String message)
    {
        if (LanguageAPI.debug)
        {
            log("["+ChatColor.RED+"DEBUG"+ChatColor.RESET+"("+ChatColor.GOLD+Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+ChatColor.RESET+")] "+object.getClass().getSimpleName()+": "+message);
        }
    }
    
    public static void logSpam(String s, int times)
    {
        for (int i = 0; i < times; i++)
        {
            log(s);
        }
    }
    
    public static void ColorfulLogSpam(String s, int times)
    {
        for (int i = 0; i < times; i++)
        {
            if (i/2 == (double)i/2)
            {
                log(ChatColor.RED + s);
            }
            else
            {
                log(s);
            }
        }
    }
}
