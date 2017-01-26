/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi.ConsoleLoggingMessages;

import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.log;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.logSpam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Kilpikonna
 */
public class LoggingMessageDB {
    public static String Plugin_FinishedLoading;
    public static String Plugin_FinishedUnloading;
    
    public static void SaveLoggingMessageDB(String lang)
    {
        switch (lang) {
            case "EN_US":
                Plugin_FinishedLoading = "Finished loading.";
                Plugin_FinishedUnloading = "Finished unloading";
                break;
                
            case "ZH_CN":
                Plugin_FinishedLoading = "加载完成, 插件作者: kilpikonna";
                Plugin_FinishedUnloading = "卸载完成";
                break;
                
            default:
                logSpam(ChatColor.RED + "Invalid default console logging language", 5);
                Bukkit.shutdown();
                break;
        }
    }
}
