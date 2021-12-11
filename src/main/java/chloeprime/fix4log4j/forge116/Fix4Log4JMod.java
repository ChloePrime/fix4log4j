package chloeprime.fix4log4j.forge116;

import chloeprime.fix4log4j.Fixer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;

/**
 * @author ChloePrime
 */
@Mod(Fix4Log4JMod.MODID)
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class Fix4Log4JMod {

    public static final String MODID = "fix4log4j";

    public Fix4Log4JMod() {
        // fix exploit
        Fixer.disableJndiManager();
        // do a test later
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onSetup);
    }

    private void onSetup(final FMLCommonSetupEvent event) {
        Fixer.doRuntimeTest(LogManager.getLogger(MODID));
    }
}
