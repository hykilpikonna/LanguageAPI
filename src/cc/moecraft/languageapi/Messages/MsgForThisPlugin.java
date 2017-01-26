/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.moecraft.languageapi.Messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author Kilpikonna
 */
public class MsgForThisPlugin {
    public static void setupMessage()
    {
        Messages MessageTemp = new Messages("LanguageAPI");
        MessageTemp.addMultilanguageMsgFormated(
            "Reload_Successful", new String[] {
                "ZH_CN;;" + ChatColor.GREEN + "重载成功",
                "EN_US;;" + ChatColor.GREEN + "Reloaded successfully"
            }, true
        );
        MessageTemp.addMultilanguageMsgFormated(
            "Help", new String[] {
                "ZH_CN;;" + ChatColor.GREEN + "---------" + ChatColor.GRAY + "[" + ChatColor.GOLD + "语言帮助" + ChatColor.GRAY + "]" + ChatColor.GREEN + "---------\n"
                         + ChatColor.YELLOW + "  - /lang        " + ChatColor.GRAY + ":" + ChatColor.GOLD + " 打开语言选择菜单\n"
                         + ChatColor.YELLOW + "  - /lang reload " + ChatColor.GRAY + ":" + ChatColor.GOLD + " 重新加载此插件\n"
                         + ChatColor.YELLOW + "  - /lang set    " + ChatColor.GRAY + ":" + ChatColor.GOLD + " 手动设置语言\n",
                "EN_US;;" + ChatColor.GREEN + "---------" + ChatColor.GRAY + "[" + ChatColor.GOLD + "LanguageAPI Help" + ChatColor.GRAY + "]" + ChatColor.GREEN + "---------\n"
                         + ChatColor.YELLOW + "  - /lang        " + ChatColor.GRAY + ":" + ChatColor.GOLD + " Open language selection menu\n"
                         + ChatColor.YELLOW + "  - /lang reload " + ChatColor.GRAY + ":" + ChatColor.GOLD + " Reload this plugin\n"
                         + ChatColor.YELLOW + "  - /lang set    " + ChatColor.GRAY + ":" + ChatColor.GOLD + " Set language manually\n",
            }, true
        );
        MessageTemp.addMultilanguageMsgFormated(
            "Lang_Selected", new String[] {
                "ZH_CN;;" + ChatColor.GREEN + "已选择语言: %s",
                "EN_US;;" + ChatColor.GREEN + "Language selected: %s"
            }, true
        );
        MessageTemp.addMultilanguageMsgFormated(
            "Lang_Invalid_Not_Contained", new String[] {
                "ZH_CN;;" + ChatColor.GREEN + "语言数据库不包含语言: %s",
                "EN_US;;" + ChatColor.GREEN + "Enabled language main database does not contain: %s"
            }, true
        );
    }
}
