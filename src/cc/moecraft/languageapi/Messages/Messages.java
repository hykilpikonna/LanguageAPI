/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi.Messages;

import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;
import static cc.moecraft.languageapi.LanguageAPI.LangAPI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Kilpikonna
 */
public class Messages{
    //Message file & config for this plugin
    File msgFile;
    YamlConfiguration msg;
    
    public String pluginName;
    
    /**
     * Create a new Message object.
     * @param pluginname The plugin name
     */
    public Messages(String pluginname)
    {
        pluginName = pluginname;
        Debug("Created a new Message object");
    }    
    
    /**
     * Get a message of a plugin in a language. 
     * @param MsgPath The path of the message.
     * @param Lang The language.
     * @return The message of this plugin in this language.
     */
    public String getMsg(String MsgPath, String Lang)
    {
        return getMsg(MsgPath, Lang, ChatColor.RED + "Unsupported message (" + MsgPath + ")");
    }
    
    /**
     * Send a message of a plugin in a language to a player. 
     * @param MsgPath The path of the message.
     * @param Lang The language.
     * @param player The player who the message send to.
     */
    public void sendMsg(String MsgPath, String Lang, Player player)
    {
        player.sendMessage(getMsg(MsgPath, Lang, ChatColor.RED + "Unsupported message (" + MsgPath + ")"));
    }
    
    /**
     * Get a message of a plugin in a language.
     * Add the default message to the message file if can't find it. 
     * @param MsgPath The path of the message.
     * @param Lang The language.
     * @param DefaultMsg The default message that it will save if can't find it.
     * @return The message of this plugin in this language.
     */
    public String getMsg(String MsgPath, String Lang, String DefaultMsg)
    {
        Debug("getMsg(" + MsgPath + ", " + Lang + ", " + DefaultMsg + ")");
        
        if (Lang.equals(""))
        {
            Lang = "EN_US";
        }
        msgFile = new File(LangAPI().getDataFolder() + "\\Messages\\" + pluginName + "\\" + Lang + ".yml");
        msg = YamlConfiguration.loadConfiguration(msgFile);
        
        if (msg.contains(MsgPath))
        {
            return msg.getString(MsgPath);
        }
        else
        {
            msg.addDefault(MsgPath, DefaultMsg);
            save();
            return DefaultMsg;
        }
    }
    
    /**
     * Set a message of a plugin in a language.
     * @param MsgPath The path of the message.
     * @param Lang The language.
     * @param Msg The message that will be saved.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMsg(String MsgPath, String Lang, String Msg)
    {
        return addMsg(MsgPath, Lang, Msg, true);
    }
    
    /**
     * Set a message of a plugin in a language.
     * @param MsgPath The path of the message.
     * @param Lang The language.
     * @param Msg The message that will be saved.
     * @param Overwrite Overwrite it or not if it already exists.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMsg(String MsgPath, String Lang, String Msg, boolean Overwrite)
    {
        Debug("addMsg(" + MsgPath + ", " + Lang + ", " + Msg + ", " + Overwrite + ")");
        
        if (Lang.equals(""))
        {
            Lang = "Default";
        }
        msgFile = new File(LangAPI().getDataFolder() + "\\Messages\\" + pluginName + "\\" + Lang + ".yml");
        msg = YamlConfiguration.loadConfiguration(msgFile);
        
        if (msg.contains(MsgPath))
        {
            if (Overwrite)
            {
                msg.set(MsgPath, Msg);
                save();
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            msg.addDefault(MsgPath, Msg); Debug("msg.addDefault(MsgPath, Msg) MsgPath = " + MsgPath + " Msg = " + Msg);
            save();
            return true;
        }
    }
    
    private void save()
    {
        try {
            msg.options().copyDefaults(true);
            msg.save(msgFile);
            Debug("Message file saved: " + msgFile.getPath());
        } catch (IOException ex) {
            Debug("Message saving failed");
        }
    }
    
    /**
     * Set a message of a plugin in multiple languages in format.
     * Format: "[Language];;[Message]"
     * @param MsgPath The path of the message.
     * @param MultilanguageFormatedMsg Formated message ArrayList.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMultilanguageMsgFormated(String MsgPath, ArrayList<String> MultilanguageFormatedMsg)
    {
        return addMultilanguageMsgFormated(MsgPath, MultilanguageFormatedMsg, true);
    }
    
    /**
     * Set a message of a plugin in multiple languages in format (Advanced).
     * Format: "[Language];;[Message]"
     * @param MsgPath The path of the message.
     * @param MultilanguageFormatedMsg Formated message ArrayList.
     * @param Overwrite Overwrite it or not if it already exists.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMultilanguageMsgFormated(String MsgPath, ArrayList<String> MultilanguageFormatedMsg, boolean Overwrite)
    {
        Debug("addMultilanguageMsgFormated(" + MsgPath + ", " + MultilanguageFormatedMsg.toString() + ", " + Overwrite + ")");
        
        if (!MultilanguageFormatedMsg.isEmpty())
        {
            Debug("!MultilanguageFormatedMsg.isEmpty()");
            boolean tempBool = false;
            for (String ThisFormatedMsg : MultilanguageFormatedMsg) 
            {
                String[] TempSA = ThisFormatedMsg.split(";;", 1);
                if (TempSA.length == 2)
                {
                    tempBool = addMsg(MsgPath, TempSA[0], TempSA[1], Overwrite);
                }
                else
                {
                    Debug("TempSA.length != 2 (" + TempSA.length + ")");
                    return false;
                }
            }
            return tempBool;
        }
        else 
        {
            return false;
        }
    }
    
    /**
     * Set a message of a plugin in multiple languages in format (Advanced).
     * Format: "[Language];;[Message]"
     * @param MsgPath The path of the message.
     * @param MultilanguageFormatedMsg Formated message string array.
     * @param Overwrite Overwrite it or not if it already exists.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMultilanguageMsgFormated(String MsgPath, String[] MultilanguageFormatedMsg, boolean Overwrite)
    {
        Debug("addMultilanguageMsgFormated(" + MsgPath + ", " + Arrays.toString(MultilanguageFormatedMsg) + ", " + Overwrite + ")");
        
        if (MultilanguageFormatedMsg != null)
        {
            Debug("MultilanguageFormatedMsg != null");
            boolean tempBool = false;
            for (String ThisFormatedMsg : MultilanguageFormatedMsg) 
            {
                String[] TempSA = ThisFormatedMsg.split(";;");
                if (TempSA.length == 2)
                {
                    tempBool = addMsg(MsgPath, TempSA[0], TempSA[1], Overwrite);
                }
                else
                {
                    Debug("TempSA.length != 2 (" + TempSA.length + ")");
                    Debug("TempSA.toString(): " + Arrays.toString(TempSA));
                    return false;
                }
            }
            return tempBool;
        }
        else 
        {
            return false;
        }
    }
    
    /**
     * Set a message of a plugin in multiple languages (Advanced).
     * @param MsgPath The path of the message.
     * @param Lang Language
     * @param Msg Message
     * @param Overwrite Overwrite it or not if it already exists.
     * @return Saved susscessfully or not (boolean).
     */
    public boolean addMultilanguageMsgStreamed(String MsgPath, ArrayList<String> Lang, ArrayList<String> Msg, boolean Overwrite)
    {
        Debug("addMultilanguageMsgFormated(" + MsgPath + ", " + Lang.toString() + ", " + Msg.toString() + ", " + Overwrite + ")");
        
        if (!Msg.isEmpty() && !Lang.isEmpty())
        {
            Debug("!Msg.isEmpty() && !Lang.isEmpty() == true");
            if (Msg.size() == Lang.size())
            {
                boolean tempBool = false;
                for (int i = 0; i < Lang.size(); i++) 
                {
                    tempBool = addMsg(MsgPath, Lang.get(i), Msg.get(i), Overwrite);
                }
                return tempBool;
            }
            else
            {
                Debug("Msg.size() != Lang.size()");
                return false;
            }
        }
        else 
        {
            return false;
        }
    }
}
