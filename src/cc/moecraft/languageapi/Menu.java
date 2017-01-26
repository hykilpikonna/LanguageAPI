package cc.moecraft.languageapi;

import static cc.moecraft.languageapi.ConsoleLoggingMessages.MessageLogger.Debug;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
/**
 * <b>
 * Menu Object:
 * </b>
 * Contains all menu-related methods
 *
 */
public class Menu implements Listener{
	private Inventory inv;
	private Player player;
	private boolean isOpen = false;
	private boolean buttonStatus[];
	private Button buttons[];
	private int slotCount = 0;
        
	/**
	 * Menu object constructor.
	 * @param p Player involved with this menu.
	 * @param name Name of the menu.
	 * @param numberOfSlots Number of slots in the menu (must be either 0 or a multiple of 9).
	 */
	public Menu(Player p, String name, int numberOfSlots/*, String inventoryType*/){
		Debug("Initiating Menu with the following variables:");
		Debug(p, p.getDisplayName());
		Debug(name, name);
		Debug(numberOfSlots, ""+numberOfSlots);
		Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("LanguageAPI"));
		player = p;
		initInv(player, name, numberOfSlots);
	}
	/**
	 * Adds a button to the menu.
	 * @param type Material used to represent the button.
	 * @param name Name of the button.
	 * @param description List used as the buttons description.
	 */
	public void addButton(Material type, String name, List<String> description){
		ItemStack item = new ItemStack(type);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(null);
		itemMeta.setLore(description);
		item.setItemMeta(itemMeta);
		addItem(item);
	}
	/**
	 * Adds a button to the menu.
	 * @param type Material used to represent the button.
	 * @param name Name of the button.
	 */
	public void addButton(Material type, String name){
		ItemStack item = new ItemStack(type);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(null);
		item.setItemMeta(itemMeta);
		addItem(item);
	}
	/**
	 * Adds a button to the menu (for advanced users).
	 * @param buttonItem ItemStack used to represent the button.
	 */
	public void addButton(ItemStack buttonItem){
		addItem(buttonItem);
	}
	/**
	 * Opens the menu (for the menu's player).
	 */
	public void openMenu(){
		player.openInventory(inv);
		isOpen = true;
	}
	/**
	 * Returns whether or not the menu is open.
	 * @return Whether the menu is open.
	 */
	public boolean isMenuOpen(){
		return isOpen;
	}
	/**
	 * Returns the menu's inventory.
	 * @return A copy of the menu's inventory.
	 */
	public Inventory getMenuInventory(){
		return inv;
	}
	/**
	 * Replaces a button in the given slot with a new one or creates a new button in the given slot.
	 * @param slot Desired slot number for the button.
	 * @param type Material used to represent the button.
	 * @param name Name of the button.
	 * @param description List used as the buttons description.
	 */
	public void setButton(int slot, Material type, String name, List<String> description){
		ItemStack item = new ItemStack(type);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(null);
		itemMeta.setLore(description);
		item.setItemMeta(itemMeta);
		if (buttonStatus[slot] == true){
			buttons[slot].setItem(item);
		}else{
			buttons[slot] = new Button(this, item, slot, false, null, false);
			buttonStatus[slot] = true;
		}
		refresh();
	}
	/**
	 * Replaces a button in the given slot with a new one or creates a new button in the given slot.
	 * @param slot Desired slot number for the button.
	 * @param type Material used to represent the button.
	 * @param name Name of the button.
	 */
	public void setButton(int slot, Material type, String name){
		ItemStack item = new ItemStack(type);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.setLore(null);
		item.setItemMeta(itemMeta);
		if (buttonStatus[slot] == true){
			buttons[slot].setItem(item);
		}else{
			buttons[slot] = new Button(this, item, slot, false, null, false);
			buttonStatus[slot] = true;
		}
		refresh();
	}
	/**
	 * Replaces a button in the given slot with a new one or creates a new button in the given slot (for advanced users).
	 * @param slot Desired slot number for the button.
	 * @param item ItemStack used to represent the button.
	 */
	public void setButton(int slot, ItemStack item){
		if (buttonStatus[slot] == true){
			buttons[slot].setItem(item);
		}else{
			buttons[slot] = new Button(this, item, slot, false, null, false);
			buttonStatus[slot] = true;
		}
		refresh();
	}
	/**
	 * Gets the number of slots currently in the menu.
	 * @return The number of slots in the menu's inventory.
	 */
	public int getNumberOfButtons(){
		return inv.getSize();
	}
	/**
	 * Closes the menu for the menu's player (if its open).
	 */
	public void closeMenu(){
		if (isOpen){
			player.closeInventory();
			isOpen = false;
		}
	}
	/**
	 * Returns the player involved in this menu.
	 * @return A copy of the player object involved with this menu.
	 */
	public Player getPlayer(){
		return player;
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		if (event.getPlayer() == player && isOpen == true){
			isOpen = false;
		}
	}
        
        
    public String ClickedButtonName = "";
    public ArrayList<String> ClickedButtonLore = new ArrayList<>();
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Debug("Menu.onInventoryClick()");
	if (event.getWhoClicked() == player && isOpen == true){
            if (event.getSlot() < inv.getSize()){
                try
                {
                    ClickedButtonName = event.getCurrentItem().getItemMeta().getDisplayName();
                    Debug("Clicked Button Name set to: " + ClickedButtonName);
                    ClickedButtonLore = (ArrayList<String>) event.getCurrentItem().getItemMeta().getLore();
                    Debug("Clicked Button Lore set to: " + ClickedButtonLore);
                    this.closeMenu();
                }
                catch (ArrayIndexOutOfBoundsException e){}
            }	
	}
    }
    
	private void refresh(){
		inv.clear();
		for (int i = 0; i < buttonStatus.length; i++){
			if (buttonStatus[i] != false){
				inv.setItem(buttons[i].getSlot(), buttons[i].getItem());
			}
		}
	}
	private void initInv(Player player, String name, int slotNum){
		if (slotNum % 9 != 0){
			Bukkit.getServer().getLogger().severe("Please tell your local plugin author: 'You must use a multiple of 9 for a slot number!'");
			slotNum = slotNum + (9 - (slotNum % 9));
		}
		boolean buttonStatus2[] = new boolean[slotNum];
		Button buttons2[] = new Button[slotNum];
		for (int i = 0; i < slotNum; i++){
			buttonStatus2[i] = false;
		}
		buttonStatus = buttonStatus2;
		buttons = buttons2;
		inv = Bukkit.createInventory(player, slotNum, name);
	}
	private void addItem(ItemStack item){
		while (buttonStatus[slotCount] != false){
			slotCount++;
		}
		inv.addItem(item);
		buttons[slotCount] = new Button(this, item, slotCount, false, null, false);
		buttonStatus[slotCount] = true;
		slotCount++;
	}
}