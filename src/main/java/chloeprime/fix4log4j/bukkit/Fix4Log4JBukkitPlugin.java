package chloeprime.fix4log4j.bukkit;

import chloeprime.fix4log4j.Censor;
import chloeprime.fix4log4j.Fixer;
import org.apache.logging.log4j.LogManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ChloePrime
 */
public class Fix4Log4JBukkitPlugin extends JavaPlugin implements Listener {
    private volatile boolean doCensor;

    @Override
    public void onLoad() {
        Fixer.disableJndiManager();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Fixer.doRuntimeTest(LogManager.getLogger("Fix4Log4J"));

        doCensor = getConfig().getBoolean("censor-user-input", true);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void censorCommand(PlayerCommandPreprocessEvent event) {
        if (!doCensor) {
            return;
        }
        if (Censor.isMaliciousMessage(event.getMessage())) {
            getLogger().warning("Found malicious player command sent by " + event.getPlayer().getName());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void censorChatAsync(AsyncPlayerChatEvent event) {
        if (!doCensor) {
            return;
        }
        if (Censor.isMaliciousMessage(event.getMessage())) {
            getLogger().warning("Found malicious chat message sent by " + event.getPlayer().getName());
            event.setCancelled(true);
        }
    }

}
