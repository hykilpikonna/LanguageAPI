/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi.Config;

import static cc.moecraft.languageapi.LanguageAPI.LangAPI;
import static cc.moecraft.languageapi.Messages.MsgForThisPlugin.setupMessage;
import java.util.ArrayList;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;

/**
 *
 * @author Kilpikonna
 */
public class Config {
    public static void checkConfig()
    {
        Debug("Config.checkConfig()");
        if (LangAPI().getConfig().contains("DefaultConfig"))
        {
            if (LangAPI().getConfig().getBoolean("DefaultConfig"))
            {
                Debug("DefaultConfig == true");
                
                setupMessage();
                
                LangAPI().getConfig().addDefault("Language.DefaultPlayerLanguage", "EN_US");
                ArrayList<String> TempAL = new ArrayList<>();
                TempAL.add("ZH_CN");
                TempAL.add("EN_US");
                LangAPI().getConfig().addDefault("Language.EnabledLanguage", TempAL);
                LangAPI().getConfig().addDefault("Language.DefaultLanguage", "EN_US");
                LangAPI().getConfig().addDefault("Language.CheckEnabledLanguage", true);
                LangAPI().getConfig().addDefault("GUI.Title.EN_US", "Language Menu");
                LangAPI().getConfig().addDefault("GUI.Title.ZH_CN", "语言菜单");
                LangAPI().getConfig().addDefault("GUI.UseUniqueTitle", true);
                LangAPI().getConfig().addDefault("GUI.Title.Unique", "语言菜单 - Language Menu");
                
                LangAPI().getConfig().addDefault("Language.Settings.ZH_CN.Nickname", "简体中文");
                LangAPI().getConfig().addDefault("Language.Settings.EN_US.Nickname", "English (US)");
                
                LangAPI().getConfig().addDefault("Language.Settings.ZH_CN.CountryCode", "CN");
                LangAPI().getConfig().addDefault("Language.Settings.EN_US.CountryCode", "US");
                
                LangAPI().getConfig().set("DefaultConfig", false);
                
                LangAPI().saveConfig();
            }
            else
            {
                Debug("DefaultConfig == false");
                
                ArrayList<String> TempAL = (ArrayList<String>)LangAPI().getConfig().getList("Language.EnabledLanguage");
                for (String TempAL1 : TempAL) {
                    Debug("For loop, TempAL1 = " + TempAL1);
                    if (!LangAPI().getConfig().contains("Language.Settings." + TempAL1 + ".Nickname"))
                    {
                        LangAPI().getConfig().addDefault("Language.Settings." + TempAL1 + ".Nickname", TempAL1);
                    }
                    if (!LangAPI().getConfig().contains("Language.Settings." + TempAL1 + ".CountryCode"))
                    {
                        LangAPI().getConfig().addDefault("Language.Settings." + TempAL1 + ".CountryCode", "Unsupported Language");
                    }
                }
                
                LangAPI().saveConfig();
            }
        }
        else 
        {
            Debug("ERROR - Config.java(checkConfig()(Default config does not save))");
        }
    }
}
