package github.buiawpkgew1.untitled2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.UUID;

public class RandomTeleportOnDeath extends JavaPlugin implements Listener {

    private final long cooldownDuration = 600 * 1000; // 冷却时间为 10 分钟（以毫秒为单位）
    private final CooldownTracker cooldownTracker = new CooldownTracker();

    @Override
    public void onEnable() {
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // 检查玩家的冷却时间是否已经过去
        if (cooldownTracker.isOnCooldown(player)) {
            long remainingCooldown = cooldownTracker.getRemainingCooldown(player);
            player.sendMessage("你需要等待 " + formatTime(remainingCooldown) + " 才能进行随机传送。");
            return;
        }

        Location randomLocation = generateRandomLocation(player.getWorld());
        player.teleport(randomLocation);

        // 启动冷却计时器
        cooldownTracker.startCooldown(player);

        // 可选：你还可以在冷却结束后将玩家从冷却中移除
        new BukkitRunnable() {
            @Override
            public void run() {
                cooldownTracker.removeCooldown(player);
            }
        }.runTaskLater(this, cooldownDuration / 50); // 将毫秒转换为服务器Ticks
    }

    private Location generateRandomLocation(org.bukkit.World world) {
        Random random = new Random();
        int x = random.nextInt(10000) - 5000; // 在范围 -5000 到 5000 内生成随机坐标
        int z = random.nextInt(10000) - 5000;
        int y = world.getHighestBlockYAt(x, z); // 获取最高方块的Y坐标
        return new Location(world, x, y, z);
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
