/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi;

import static cc.moecraft.languageapi.LanguageAPI.LangAPI;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;

/**
 *
 * @author Kilpikonna
 */
public class GUI 
{    
    public Menu menu;
    public int count;
    
    public GUI()
    {
        count += 1;
    }
    
    private Menu CreateMenu(Player p)
    {
        ArrayList LanguageAL = (ArrayList)LangAPI().getConfig().getList("Language.EnabledLanguage");
        
        if (!LangAPI().getConfig().getBoolean("GUI.UseUniqueTitle"))
        {
            if (!LangAPI().getPlayerLang(p).equals("ERROR"))
            {
                menu = new Menu(p, LangAPI().getConfig().getString("GUI.Title." + LangAPI().getPlayerLang(p)), (((LanguageAL.size() / 9) + 1) * 9));
            } 
            else
            {
                menu = new Menu(p, LangAPI().getConfig().getString("GUI.Title.Unique"), (((LanguageAL.size() / 9) + 1) * 9));
            }
        }
        else
        {
            menu = new Menu(p, LangAPI().getConfig().getString("GUI.Title.Unique"), (((LanguageAL.size() / 9) + 1) * 9));
        }
        for (int i = 0; i < LanguageAL.size(); i++)
        {
            ItemStack TempIS = (new Wool(DyeColor.RED).toItemStack());
            if (!LangAPI().getPlayerLang(p).equals("ERROR"))
            {
                if (LangAPI().getPlayerLang(p).equals((String) LanguageAL.get(i)))
                {
                    TempIS = (new Wool(DyeColor.GREEN).toItemStack());
                    TempIS.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                }
            }
            else
            {
                Debug("Read lang equals ERROR, " + LangAPI().getPlayerLang(p));
            }
            ItemMeta TempIM = TempIS.getItemMeta();
            TempIM.setDisplayName(ChatColor.GOLD + LangAPI().getConfig().getString("Language.Settings." + (String) LanguageAL.get(i) + ".Nickname"));
            ArrayList<String> TempLore = new ArrayList<>();
            TempLore.add((String) LanguageAL.get(i));
            TempIM.setLore(TempLore);
            TempIM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            TempIS.setItemMeta(TempIM);
            TempIS.setAmount(1);
            menu.setButton((i), TempIS);
        }
        return menu;
    }
    
    public void ShowMenu(Player p)
    {
        CreateMenu(p).openMenu();
    }
    
    public Menu getMenu()
    {
        return menu;
    }
}
