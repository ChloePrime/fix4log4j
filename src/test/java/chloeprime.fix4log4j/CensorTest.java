package chloeprime.fix4log4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CensorTest {
    @Test
    void censor() {
        String virus = "I'm Bob, ${jndi:ldap://}";
        String virus2 = "${jndi:ldarg://}, Goodbye Bob~";
        String virus3 = "${jndi:}";
        String virus4 = "$§c{§4§Lj§kn§fd§ai§f:§7}";
        String virus5 = "§c$§c{§4§Lj§kn§fd§ai§f:§7}§r";

        String innocent = "{just non dimensional interface : }";
        String innocent2 = "${just non dimensional interface : ";

        assertTrue(Censor.isMaliciousMessage(virus));
        assertTrue(Censor.isMaliciousMessage(virus2));
        assertTrue(Censor.isMaliciousMessage(virus3));
        assertTrue(Censor.isMaliciousMessage(virus4));
        assertTrue(Censor.isMaliciousMessage(virus5));

        assertFalse(Censor.isMaliciousMessage(innocent));
        assertFalse(Censor.isMaliciousMessage(innocent2));
    }
}
