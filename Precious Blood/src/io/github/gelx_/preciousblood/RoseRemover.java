package io.github.gelx_.preciousblood;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class RoseRemover extends BukkitRunnable{

	
	public static final int LIFETICKS = 5 * 20; 
	
	private List<Item> roses = new ArrayList<>();
	
	@Override
	public void run() {
		for(Item rose : roses){
			if(rose.getTicksLived() > LIFETICKS){
				rose.remove();
				roses.remove(rose);
			}
		}	
	}
	
	public void addRose(Item rose){
		roses.add(rose);
	}	
	
	public boolean isRose(Item item){
		return roses.contains(item);
	}
}
