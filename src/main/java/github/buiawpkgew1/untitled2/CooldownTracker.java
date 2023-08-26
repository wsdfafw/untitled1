package github.buiawpkgew1.untitled2;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownTracker {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public boolean isOnCooldown(Player player) {
        return cooldowns.containsKey(player.getUniqueId());
    }

    public long getRemainingCooldown(Player player) {
        long cooldownEnd = cooldowns.get(player.getUniqueId());
        long currentTime = System.currentTimeMillis();
        return Math.max(0, cooldownEnd - currentTime);
    }

    public void startCooldown(Player player) {
        long cooldownEnd = System.currentTimeMillis() + cooldownDuration;
        cooldowns.put(player.getUniqueId(), cooldownEnd);
    }

    public void removeCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
    }

    private final long cooldownDuration = 600 * 1000; // 冷却时间为 10 分钟（以毫秒为单位）
}
