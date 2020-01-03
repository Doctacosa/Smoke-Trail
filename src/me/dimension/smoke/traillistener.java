package me.dimension.smoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class traillistener
        implements Listener {

    public static trail plugin;
    public HashMap<Player, ArrayList<Entity>> flower = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> diamonds = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> stars = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> skulls = new HashMap<Player, ArrayList<Entity>>();
    public ArrayList<Entity> allitems = new ArrayList<Entity>();
    public ArrayList<ItemStack> allitemstacks = new ArrayList<ItemStack>();
    Random random1 = new Random();
    HashMap<UUID, Integer> IDtoTaskID = new HashMap<UUID, Integer>();
    public static final Logger log = Logger.getLogger("Minecraft");
    

    public traillistener(trail instance) {
        plugin = instance;
    }
    //<editor-fold defaultstate="collapsed" desc=" Trail Integers ">
    public int FireLow;
    public int FireHigh;
    public int SmokeLow;
    public int SmokeHigh;
    public int HeartLow;
    public int HeartHigh;
    public int EnderHigh;
    public int EnderLow;
    public int CritHigh;
    public int CritLow;
    public int SweatHigh;
    public int SweatLow;
    public int DiscoHigh;
    public int DiscoLow;
    public int MagmaHigh;
    public int MagmaLow;
    public int LetterHigh;
    public int LetterLow;
    public int SparkHigh;
    public int SparkLow;
    public int BreadHigh;
    public int BreadLow;
    public int BloodHigh;
    public int BloodLow;
    public int MagicHigh;
    public int MagicLow;
    public int MusicHigh;
    public int MusicLow;
    public int HappyHigh;
    public int HappyLow;
    public int AngerHigh;
    public int AngerLow;
    public int CloudHigh;
    public int CloudLow;

//</editor-fold>
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
    	Entity newitem = event.getItem();
    	
    	if (allitems.contains(newitem)) {
    		event.setCancelled(true);
    	}
    }

    @EventHandler
    public void onPlayerTP(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (flower.containsKey(player)) {//Check if the player leaving has any items spawned
            Iterator<?> it = flower.get(player).iterator();
            while (it.hasNext()) {//Remove each item on by one
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (stars.containsKey(player)) {//Rinse
            Iterator<?> it = stars.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (diamonds.containsKey(player)) {//Repeat
            Iterator<?> it = diamonds.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }

    }

    public void EntityDeathEvent(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        for (int i = 0; i < drops.size(); i++) {
            if (allitemstacks.contains(drops.get(i))) {
                drops.get(i).setAmount(0);
            }
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEnterEvent event) {
        Entity newitem = event.getEntity();

        if (allitems.contains(newitem)) {
            newitem.remove();
        }
    }

    @EventHandler
    public void HopperPickup(InventoryPickupItemEvent event) {
        Entity newitem = event.getItem();

        if (allitems.contains(newitem)) {
            event.setCancelled(true);//If Global list of items contains the item, prevent hopper from moving it
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (flower.containsKey(player)) {//Check if the player leaving has any items spawned
            Iterator<?> it = flower.get(player).iterator();
            while (it.hasNext()) {//Remove each item on by one
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (stars.containsKey(player)) {//Rinse
            Iterator<?> it = stars.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (diamonds.containsKey(player)) {//Repeat
            Iterator<?> it = diamonds.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }

        if (plugin.getConfig().getBoolean("DisableOnLeave") == true) {
            if (plugin.getConfig().contains("Users" + event.getPlayer().getName())) {
                plugin.getConfig().set("Users." + event.getPlayer().getName(), null);
                plugin.modelist.remove(event.getPlayer().getName());
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	if (event.getPlayer().hasPermission("smoketrail.use.off"))
    		return;
    	
    	//The player doesn't have permission, kill any trails previously set
    	plugin.modelist.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        final Player sender = event.getPlayer();
        World world = sender.getWorld();
        
        if(plugin.modelist.containsKey(sender.getName())){
        //<editor-fold defaultstate="collapsed" desc=" Smoke Trail ">
            if (plugin.modelist.get(sender.getName()).contains("smoke")) {
                if (!trailNegative(SmokeHigh, SmokeLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.SMOKE_LARGE, sender.getLocation(), random.nextInt((SmokeHigh - SmokeLow) + SmokeLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Fire Trail ">
        if (plugin.modelist.containsKey(sender.getName())) {
            if ((plugin.modelist.get(sender.getName())).contains("fire")) {
                if (!trailNegative(FireHigh, FireLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.FLAME, sender.getLocation(), random.nextInt((FireHigh - FireLow) + FireLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Ender Trail ">
		if (plugin.modelist.get(sender.getName()).contains("ender")) {
			if (!trailNegative(EnderHigh, EnderLow)) {
				Random random = new Random();
				world.spawnParticle(Particle.PORTAL, sender.getLocation(), random.nextInt((EnderHigh - EnderLow) + EnderLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0F);
			}
		}

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Hearts trail ">
            if (plugin.modelist.get(sender.getName()).contains("hearts")) {
                if (!trailNegative(HeartHigh, HeartLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.HEART, sender.getLocation(), random.nextInt((HeartHigh - HeartLow) + HeartLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 3);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Crit Trail ">
            if (plugin.modelist.get(sender.getName()).contains("crit")) {
                if (!trailNegative(CritHigh, CritLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.CRIT, sender.getLocation(), random.nextInt((CritHigh - CritLow) + CritLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Sweat Trail ">
            if (plugin.modelist.get(sender.getName()).contains("sweat")) {
                if (!trailNegative(SweatHigh, SweatLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.WATER_SPLASH, sender.getLocation(), random.nextInt((SweatHigh - SweatLow) + SweatLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 2);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Disco Trail ">
            if (plugin.modelist.get(sender.getName()).contains("disco")) {
                if (!trailNegative(DiscoHigh, DiscoLow)) {
                    Random random = new Random();
                    //world.spawnParticle(Particle.REDSTONE, sender.getLocation(), random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                    world.spawnParticle(Particle.SPELL_MOB, sender.getLocation(), random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Blood Trail ">
            if (plugin.modelist.get(sender.getName()).contains("blood")) {
                if (!trailNegative(BloodHigh, BloodLow)) {
                    /*
                	Random random = new Random();
                    world.spawnParticle(Particle.REDSTONE, sender.getLocation(), random.nextInt((BloodHigh - BloodLow) + BloodLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                    */
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Sparks Trail ">
            if (plugin.modelist.get(sender.getName()).contains("sparks")) {
                if (!trailNegative(SparkHigh, SparkLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.FIREWORKS_SPARK, sender.getLocation(), random.nextInt((SparkHigh - SparkLow) + SparkLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                    
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Breadcrumb trail ">
            if (plugin.modelist.get(sender.getName()).contains("breadcrumb")) {
                if (!trailNegative(BreadHigh, BreadLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.DRIP_LAVA, sender.getLocation(), random.nextInt((BreadHigh - BreadLow) + BreadLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Magma Trail ">
            if (plugin.modelist.get(sender.getName()).contains("magma")) {
                if (!trailNegative(MagmaHigh, MagmaLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.LAVA, sender.getLocation(), random.nextInt((MagmaHigh - MagmaLow) + MagmaLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Letters Trail ">
            if (plugin.modelist.get(sender.getName()).contains("letters")) {
                if (!trailNegative(LetterHigh, LetterLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.ENCHANTMENT_TABLE, sender.getLocation(), random.nextInt((LetterHigh - LetterLow) + LetterLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Happy Trail ">
            if (plugin.modelist.get(sender.getName()).contains("happy")) {
                if (!trailNegative(HappyHigh, HappyLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.VILLAGER_HAPPY, sender.getLocation(), random.nextInt((HappyHigh - HappyLow) + HappyLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Magic Trail ">
            if (plugin.modelist.get(sender.getName()).contains("magic")) {
                if (!trailNegative(MagicHigh, MagicLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.CRIT_MAGIC, sender.getLocation(), random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                    world.spawnParticle(Particle.SPELL_WITCH, sender.getLocation(), random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
                
            }

//</editor-fold>     
        //<editor-fold defaultstate="collapsed" desc=" Music Trail ">
            if (plugin.modelist.get(sender.getName()).contains("music")) {
                if(!trailNegative(MusicHigh,MusicLow)){
	                Random random = new Random();
	                world.spawnParticle(Particle.NOTE, sender.getLocation(), random.nextInt((MusicHigh - MusicLow) + MusicLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                }  
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Anger Trail ">
            if (plugin.modelist.get(sender.getName()).contains("anger")) {
                if (!trailNegative(AngerHigh, AngerLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.VILLAGER_ANGRY, sender.getLocation(), random.nextInt((AngerHigh - AngerLow) + AngerLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Cloud Trail ">
            if (plugin.modelist.get(sender.getName()).contains("clouds")) {
                if (!trailNegative(CloudHigh, CloudLow)) {
                    Random random = new Random();
                    world.spawnParticle(Particle.CLOUD, sender.getLocation(), random.nextInt((CloudHigh - CloudLow) + CloudLow) + 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0);
                }
                
            }

//</editor-fold>
        }
        }
    }

    public Boolean trailNegative(int max, int min) {
        return max - min <= 0;
    }
    }
