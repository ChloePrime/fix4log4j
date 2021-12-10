package chloeprime.fix4log4j.forge;

import chloeprime.fix4log4j.Fixer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author ChloePrime
 */
@Mod(
        modid = Fix4Log4JMod.MODID,
        name = Fix4Log4JMod.NAME,
        version = Fix4Log4JMod.VERSION,
        useMetadata = true
)
public class Fix4Log4JMod {
    public static final String MODID = "fix4log4j";
    public static final String NAME = "Fix4Log4J";
    public static final String VERSION = "1.1.0";

    @EventHandler
    public void construct(FMLConstructionEvent event) {
        try {
            Fixer.disableJndiManager();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Fixer.doRuntimeTest(event.getModLog());
    }
}
