package io.github.gelx_.preciousblood;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PreciousBlood extends JavaPlugin implements Listener{

	
	private RoseRemover rr;
	
	public void onEnable(){
		rr = new RoseRemover();
		rr.runTaskTimer(this, 0L, 1L);
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent evt){
		if(evt.getEntity() instanceof LivingEntity){
			if(getRealDamage((LivingEntity) evt.getEntity(), evt.getDamage()) == 0)
				return;
			
			evt.getEntity().getWorld().playEffect( ((LivingEntity) evt.getEntity()).getEyeLocation(), Effect.STEP_SOUND, 52);
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt){
		for(int i = 0; i < 5; i++){
			rr.addRose( evt.getEntity().getWorld().dropItemNaturally(evt.getEntity().getLocation(), new ItemStack(Material.RED_ROSE)));
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent evt){
		if(rr.isRose(evt.getItem()) )
			evt.setCancelled(true);
	}

	
	private double getRealDamage(LivingEntity victim, double damage){
		if((float) victim.getNoDamageTicks() > (float) victim.getMaximumNoDamageTicks() / 2.0F){
			if(damage <= victim.getLastDamage() ){
				return 0;
			}
			double realdamage = damage - victim.getLastDamage();
			victim.setLastDamage(realdamage );
			return realdamage;
		}else{
			return damage;
		}
	}
}
