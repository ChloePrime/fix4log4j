package chloeprime.fix4log4j.fabric116;

import chloeprime.fix4log4j.Fixer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author ChloePrime
 */
public class Fix4Log4JMod implements ModInitializer {
    /**
     * Nullify the JndiManager,
     * and do a simple test.
     */
    @Override
    public void onInitialize() {
        Fixer.disableJndiManager();

        Logger testLogger = LogManager.getLogger(getClass().getSimpleName());
        Fixer.doRuntimeTest(testLogger);
    }
}
