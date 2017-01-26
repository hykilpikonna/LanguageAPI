package cc.moecraft.languageapi;

import org.bukkit.inventory.ItemStack;
/**
 * <b>
 * Button Object:
 * </b>
 * For internal use only!
 */
public class Button {
	private ItemStack item, toggleItem;
	private boolean isToggle, isToggled;
	private int slot;
	private Menu menu;
	/**
	 * Button object constructor.
	 * <b>
	 * FOR INTERNAL API USE ONLY, DO NOT USE
	 * </b>
	 * 
	 * @param buttonMenu
	 * @param itemDisplay
	 * @param slot2
	 * @param isTogglable
	 * @param togglableItem
	 * @param toggled
	 */
	public Button(Menu buttonMenu, ItemStack itemDisplay, int slot2, boolean isTogglable, ItemStack togglableItem, boolean toggled){
		item = itemDisplay;
		isToggle = isTogglable;
		toggleItem = togglableItem;
		slot = slot2;
		menu = buttonMenu;
	}
	public ItemStack getItem(){
		return item;
	}
	public boolean canToggle(){
		return isToggle;
	}
	public ItemStack getToggleItem(){
		return toggleItem;
	}
	public int getSlot(){
		return slot;
	}
	public void setItem(ItemStack item2){
		item = item2;
	}
	public void setToggleItem(ItemStack item2){
		toggleItem = item2;
	}
	public void setToggle(boolean toggle){
		isToggle = toggle;
	}
	public Menu getMenu(){
		return menu;
	}
	public void toggle(){
		if (isToggle){
			ItemStack temp;
			temp = item;
			item = toggleItem;
			toggleItem = temp;
			isToggled = !isToggled;
		}
	}
	public boolean isToggled(){
		return isToggled;
	}
}