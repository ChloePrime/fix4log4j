package chloeprime.fix4log4j.bukkit;

import chloeprime.fix4log4j.Fixer;
import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ChloePrime
 */
public class Fix4Log4JBukkitPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        Fixer.disableJndiManager();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Fixer.doRuntimeTest(LogManager.getLogger("Fix4Log4J"));
    }
}
