package chloeprime.fix4log4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.net.JndiManager;

import javax.naming.Context;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

/**
 * @author ChloePrime
 */
public class Fixer {
    public static void doRuntimeTest(Logger logger) {
        logger.info("Fix4Log4J loaded.");
        logger.info("If you see stacktrace below, CLOSE EVERYTHING IMMEDIATELY!");

        String someRandomString =
                RandomStringUtils.randomAlphanumeric(40)
                        + ":"
                        + RandomStringUtils.randomAlphanumeric(40);
        logger.info("Exploit Test: ${jndi:ldap://" + someRandomString + "}");
    }

    public static void disableJndiManager() {
        try {
            Fixer.disableJndiManager0();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void disableJndiManager0() {
        // Load default manager
        JndiManager.getDefaultManager();

        Class<AbstractManager> mapHolder = AbstractManager.class;
        // Find "static Map<?, ?>" fields
        Arrays.stream(mapHolder.getDeclaredFields()).filter(
                f -> (f.getModifiers() & Modifier.STATIC) > 0
        ).filter(
                f -> Map.class.isAssignableFrom(f.getType())
        ).map(
                // get the Map object
                f -> {
                    try {
                        f.setAccessible(true);
                        return (Map<?, ?>) (f.get(null));
                    } catch (IllegalAccessException e) {
                        throw new ExceptionInInitializerError(e);
                    }
                }
        ).forEach(map -> {
            if (map == null) {
                return;
            }
            // hack the Context
            map.forEach((k, v) -> {
                if (v instanceof JndiManager) {
                    try {
                        fixJndiManager((JndiManager) v);
                    } catch (ReflectiveOperationException e) {
                        throw new ExceptionInInitializerError(e);
                    }
                }
            });
        });
    }

    /**
     * set JndiManager.Context to null-pattern implementation
     */
    private static void fixJndiManager(JndiManager jndiManager) throws ReflectiveOperationException {
        // find Context field
        Arrays.stream(jndiManager.getClass().getDeclaredFields()).filter(
                f -> Context.class.isAssignableFrom(f.getType())
        ).forEach(f -> {
            try {
                // get access to it
                f.setAccessible(true);
                FieldUtils.removeFinalModifier(f);
                // replace implementation
                f.set(jndiManager, EmptyJndiContext.INSTANCE);
            } catch (IllegalAccessException e) {
                throw new ExceptionInInitializerError(e);
            }
        });
    }
    private Fixer() {}
}
