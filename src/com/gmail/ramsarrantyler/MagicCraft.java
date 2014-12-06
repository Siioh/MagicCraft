package com.gmail.ramsarrantyler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public final class MagicCraft extends JavaPlugin {
	
	final ItemStack apple = new ItemStack(Material.APPLE, 1);
    final ItemMeta apple_meta = apple.getItemMeta();
    final ArrayList<String> apple_lore = new ArrayList<String>();
	
	@Override
	public void onEnable(){
		new MagicCraftListener(this);
	    this.getServer().getPluginManager().registerEvents(new MagicCraftListener(this), this);
	    			
		apple_lore.add(ChatColor.DARK_PURPLE + "A mystical item which you can");
		apple_lore.add(ChatColor.DARK_PURPLE + "instantly eat to restore health.");
		apple_meta.setDisplayName("Mystical Apple");
		apple_meta.setLore(apple_lore);
		apple_meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
		apple.setItemMeta(apple_meta);
		
		ShapedRecipe magic_apple = new ShapedRecipe(apple);
		magic_apple.shape("BCB", "CAC", "BCB");
		magic_apple.setIngredient('A', Material.APPLE);
		magic_apple.setIngredient('B', Material.GOLD_INGOT);
		magic_apple.setIngredient('C', Material.IRON_INGOT);
		getServer().addRecipe(magic_apple);
		
		getLogger().info("MagicCraft has been successfully enabled!");
	}
	
	public class MagicCraftListener implements Listener{
		public MagicCraft plugin;
		
		public MagicCraftListener(MagicCraft plugin) {
			this.plugin = plugin;
		}
		
		@EventHandler
		public void onPlayerEat(PlayerInteractEvent evt){
			Player player = evt.getPlayer();
			ItemStack in_hand = player.getItemInHand();
			if(in_hand.getTypeId() != 0){
				if(in_hand.getItemMeta().getDisplayName() == "Mystical Apple"){
					if(evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
						int hp = (int) player.getHealth();
						if(hp > 0 && hp < 20){
							if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE){
								player.setHealth(hp + player.getMaxHealth() - hp);
								player.sendMessage(ChatColor.AQUA + "Mmm, that was great!");
								if(in_hand.getAmount() == 1){
									in_hand.setAmount(0);
								}
								in_hand.setAmount(in_hand.getAmount() - 1);
								player.updateInventory();
							}
						} else {
							player.sendMessage(ChatColor.AQUA + "Um, you can't use it if your health is full or are in creative.");
							evt.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void onDisable(){
		ShapedRecipe magic_apple = new ShapedRecipe(apple);
		magic_apple.shape("CCC", "CCC", "CCC");
		magic_apple.setIngredient('C', Material.AIR);
		getServer().addRecipe(magic_apple);
		getLogger().info("MagicCraft has been successfully disabled!");
	}
}
