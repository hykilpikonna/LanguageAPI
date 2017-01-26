/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi;

import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;
import static cc.moecraft.languageapi.LanguageAPI.LangAPI;
import static cc.moecraft.languageapi.LanguageAPI.getPlayerDefaultLanguage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kilpikonna
 */
public class GeoLocation {
    /**
     * Get the country code (ISO 3166-1 alpha-2) of a player.
     * @param p The player.
     * @return The country code (ISO 3166-1 alpha-2)
     */
    public static String getCountryCode(Player p)
    {
        InetAddress SockAddr = p.getAddress().getAddress();
        String ip = SockAddr.toString();
        ip = ip.replaceAll("/", "");
        if (ip.matches("(?i).*127.0.0.1*"))
        {
            Debug(ChatColor.RED + "Failed to get player's location, PlayerName: " + p.getName() + " ip: " + ip);
            return getPlayerDefaultLanguage();
        }
        else
        {
            String data = "";
            String cc = "US";
            try
            {
                data = IOUtils.toString(new URL("http://ip-api.com/json/" + ip));
            }
            catch (IOException e)
            {
                Debug("IOException: " + e.toString());
            }
            
            try
            {
                JSONObject result = new JSONObject(data);
                if (result.get("status").equals("fail"))
                {
                    Debug(ChatColor.RED + "Failed to get player's location, PlayerName: " + p.getName() + " ip: " + ip);
                    return defaultLang();
                }
                else
                {
                    Debug("Player Name = " + p.getName());
                    Debug("IP = " + ip);
                    Debug("Country = " + result.get("country"));
                    Debug("CountryCode = " + result.get("countryCode"));
                    Debug("RegionName = " + result.get("regionName"));
                    Debug("City = " + result.get("city"));
                    Debug("Zip Code = " + result.get("zip"));
                    Debug("Time Zone = " + result.get("timezone"));
                    Debug("ISP = " + result.get("isp"));
                    return (String) result.get("countryCode");
                }
            }
            catch (JSONException e)
            {
                Debug("JSONException: " + e.toString());
                return defaultLang();
            }
        }
    }
    
    /**
     * Get the local language code of a player from the config and based on the player's IP.
     * @param p The player
     * @return The language code (returns default language code if not in enabled language setting).
     */
    public static String getLocalLang(Player p)
    {
        ArrayList<String> EnabledLang = (ArrayList<String>) LangAPI().getConfig().getList("Language.EnabledLanguage");
        for (String EnabledLang1 : EnabledLang) 
        {
            String CCtemp = LangAPI().getConfig().getString("Language.Settings." + EnabledLang1 + ".CountryCode");
            if (!CCtemp.equals("Unsupported Language"))
            {
                if(CCtemp.equals(getCountryCode(p)))
                {
                    return CCtemp;
                }
            }
        }
        return defaultLang();
    }
    
    private static String defaultLang()
    {
        Debug("Returning Default Lang");
        return getPlayerDefaultLanguage();
    }
}
