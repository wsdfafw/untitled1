package github.buiawpkgew1.untitled1;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class RandomTeleportOnDeath extends JavaPlugin implements Listener {
    private final long cooldownDuration = 600*1000;// 冷却为10分钟
    private final CooldownTracker cooldownTracker = new CooldownTracker();

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        //
        if(cooldownTracker.isOnCooldown(player)){
            long remainingCooldown=cooldownTracker.getRemainingCooldown(player);
            player.sendMessage("你需要等待 " + formatTime(remainingCooldown) + " 才能进行随机传送。");
            return;
        }

        Location randomLocation = generateRandomLocation(player.getWorld());
        player.teleport(randomLocation);

        //
        cooldownTracker.startCooldown(player);
    }

    private Location generateRandomLocation(org.bukkit.World world) {
        Random random = new Random();
        int x= random.nextInt(10000)-5000;
        int z= random.nextInt(10000)-5000;
        int y= world.getHighestBlockYAt(x,z);
        return new Location(world,x,y,z);
    }

    private String formatTime(long milliseconds){
        long sec=milliseconds/1000;
        long min=sec/60;
        sec %=60;
        return String.format("%02d;$02d",min,sec);
    }
}
