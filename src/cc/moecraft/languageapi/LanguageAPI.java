/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi;

import static cc.moecraft.languageapi.Config.Config.checkConfig;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.LoggingMessageDB.Plugin_FinishedLoading;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.LoggingMessageDB.Plugin_FinishedUnloading;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.LoggingMessageDB.SaveLoggingMessageDB;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.log;
import cc.moecraft.languageapi.Messages.Messages;
import cc.moecraft.languageapi.Reload.PluginUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;
import static cc.moecraft.languageapi.GeoLocation.getLocalLang;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author Kilpikonna
 */
public class LanguageAPI extends JavaPlugin implements Listener
{
    //Debug mode (boolean)
    public static boolean debug = true;
    
    //Console logging language
    public static String LoggingLanguage = "EN_US";
    
    //Database file & config
    File dbFile;
    public YamlConfiguration db;
    
    //Message loading
    Messages msg = new Messages("LanguageAPI");;
    
    public static LanguageAPI instance = null;
    
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        dbFile = new File(this.getDataFolder() + "\\Database.yml");
        db = YamlConfiguration.loadConfiguration(dbFile);
        
        instance = this;
        
        checkConfig();
        debug = getConfig().getBoolean("Debug");
        
        SaveLoggingMessageDB(LoggingLanguage);
        
        Debug("onEnable()");
        log(Plugin_FinishedLoading);
        log("Debug Mode: " + debug);
    }
    
    @Override
    public void onDisable()
    {
        log(Plugin_FinishedUnloading);
    }
    
    /**
     * Get the instance for using the un-static methods in this class.
     * @return Instance.
     */
    public static LanguageAPI getInstance()
    {
        return instance;
    }
    
    /**
     * Get the instance for using the un-static methods in this class.
     * @return Instance.
     */
    public static LanguageAPI LangAPI()
    {
        return instance;
    }
    
    public static String getPlayerDefaultLanguage()
    {
        return LangAPI().getConfig().getString("Language.DefaultPlayerLanguage");
    }
    
    /**
     * Set a player's selected language.
     * @param p The player.
     * @param lang The language that should be selected by that player.
     */
    public void setPlayerLang(Player p, String lang)
    {
        Debug("setPlayerLang()");
        db.set(p.getName(), removeColorCode(lang));
        p.sendMessage(String.format(msg.getMsg("Lang_Selected", getPlayerLang(p)), lang));
        log("Player " + p.getName() + " selected language " + lang);
        saveDatabase();
    }
    
    /**
     * Save Database.
     */
    public void saveDatabase()
    {
        Debug("saveDatabase()");
        try 
        {
            db.save(dbFile);
            Debug(this.getDataFolder() + "\\Database.yml");
            log("Database saved");
        } 
        catch (IOException ex) 
        {
            Debug("ERROR - setPlayerLang(db.save(dbFile))");
        }
    }
    
    /**
     * Get the language that a player selected.
     * @param p The player.
     * @return The language that the player p selected.
     */
    public String getPlayerLang(Player p)
    {
        if (!db.contains(p.getName()))
        {
            Debug("ERROR, DB file does not contains p.getName(): " + p.getName());
            Debug("Retruning default lang");
            return getConfig().getString("Language.DefaultLanguage");
        } 
        else
        {
            Debug("db.getString(p.getName()) = " + db.getString(p.getName()));
            if (ArrayContains((ArrayList)(LangAPI().getConfig().getList("Language.EnabledLanguage")), removeColorCode(db.getString(p.getName()))))
            {
                return db.getString(p.getName());
            }
            else
            {
                Debug("Enabled language ArrayList does not contain " + db.getString(p.getName()));
                Debug(((ArrayList)(LangAPI().getConfig().getList("Language.EnabledLanguage"))).toString());
                Debug("Retruning default lang");
                return getConfig().getString("Language.DefaultLanguage");
            }
        }
    }
    
    GUI g = new GUI();
    
    /**
     * Remove color code from a string.
     * @param s The string before removing color code.
     * @return The string after removing color code.
     */
    public static String removeColorCode(String s)
    {
        Debug("removeColorCode: From: " + s + ";");
        char[] c = s.toCharArray();
        String RebuiltTemp = "";
        for (int i = 0; i < c.length; i++)
        {
            switch (c[i]) {
                case '§':
                    i += 1;
                    break;
                case '&':
                    i += 1;
                    break;
                default:
                    RebuiltTemp += c[i];
                    break;
            }
        }
        Debug("removeColorCode: to: " + RebuiltTemp + ";");
        return RebuiltTemp;
    }
    
    private boolean ArrayContains(ArrayList a, String c)
    {
        boolean b = false;
        for (int i = 0; i < a.size(); i++)
        {
            if (a.get(i).equals(c))
            {
                b = true;
            }
        }
        return b;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("lang") || cmd.getName().equalsIgnoreCase("language"))
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;
                switch (args.length) {
                    case 0:
                        g.ShowMenu(p);
                        break;
                    case 1:
                        switch (args[0]) {
                            case "reload":
                                PluginUtil.reload(Bukkit.getPluginManager().getPlugin("LanguageAPI"));
                                msg.sendMsg("Reload_Successful", getPlayerLang(p), p);
                                break;
                            default:
                                msg.sendMsg("Help", getPlayerLang(p), p);
                                break;
                        }
                        break;
                    case 2:
                        if (args[0].equals("set"))
                        {
                            ArrayList<String> TempAL = (ArrayList<String>)getConfig().getList("Language.EnabledLanguage");
                            if (TempAL.contains(args[1]))
                            {
                                setPlayerLang(p, args[1]);
                            }
                        }
                        break;
                    default:
                        g.ShowMenu(p);
                        break;
                }
            }
            else
            {
                //不是玩家输入指令
            }
        }
        return true;
    }
    
    @EventHandler
    public void onInventoryCloseE(InventoryCloseEvent e)
    {
        if (g.menu != null)
        {
            if (g.menu.isMenuOpen())
            {
                Debug("g.menu.isMenuOpen() : " + g.menu.isMenuOpen());
                if (g.menu.ClickedButtonLore.isEmpty())
                {
                    Debug("ERROR - g.menu.ClickedButtonLore.size() == 0");
                    return;
                }
                String tempLang = g.menu.ClickedButtonLore.get(0);
                if (!(tempLang.equals("")))
                {
                    String lang = removeColorCode(tempLang);
                    g.menu.ClickedButtonName = "";
                    g.menu.ClickedButtonLore = new ArrayList();
                    if (((ArrayList)(LangAPI().getConfig().getList("Language.EnabledLanguage"))).contains(lang))
                    {
                        Debug("Array contains g.menu.ClickedButtonName : " + lang);
                        setPlayerLang((Player)e.getPlayer(), lang);
                    }
                    else
                    {
                        e.getPlayer().sendMessage(String.format(msg.getMsg("Lang_Invalid_Not_Contained", getPlayerLang((Player) e.getPlayer())), lang));
                        Debug("ERROR - onInventoryClose(Enabled lang does not contain clicked language), lang = " + lang + " Enabled list = " + LangAPI().getConfig().getList("Language.EnabledLanguage"));
                    }
                }
                else
                {
                    Debug("ERROR - onInventoryClose(No button name)");
                }
            }
            else
            {
                Debug("INFO - onInventoryClose(Inventory is still open)");
            }
        }
    }
    
    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent e)
    {
        Debug("onPlayerLoginEvent");
        Player p = e.getPlayer();
        if (!db.contains(p.getName()))
        {
            String lang = getLocalLang(p);
            db.set(p.getName(), lang);
            Debug("onPlayerLoginEvent - Player: " + p.getName() + " Lang: " + lang + " Saved");
            saveDatabase();
        }
        else
        {
            Debug("DB contains p.getName(): " + p.getName());
        }
    }
}
