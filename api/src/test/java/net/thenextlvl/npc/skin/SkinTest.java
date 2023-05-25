package net.thenextlvl.npc.skin;

import net.thenextlvl.npc.api.skin.SkinParts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SkinTest {
    private final SkinParts parts = new SkinParts();

    @Test
    @DisplayName("skin part capes")
    public void testCape() {
        Assertions.assertFalse(parts.hasCapeEnabled());
        parts.setCapeEnabled(true);
        Assertions.assertTrue(parts.hasCapeEnabled());
    }

    @Test
    @DisplayName("skin part hat")
    public void testHat() {
        Assertions.assertFalse(parts.hasHatsEnabled());
        parts.setHatsEnabled(true);
        Assertions.assertTrue(parts.hasHatsEnabled());
    }

    @Test
    @DisplayName("skin part jacket")
    public void testJacket() {
        Assertions.assertFalse(parts.hasJacketEnabled());
        parts.setJacketEnabled(true);
        Assertions.assertTrue(parts.hasJacketEnabled());
    }

    @Test
    @DisplayName("skin part left pants")
    public void testLeftPants() {
        Assertions.assertFalse(parts.hasLeftPantsEnabled());
        parts.setLeftPantsEnabled(true);
        Assertions.assertTrue(parts.hasLeftPantsEnabled());
    }

    @Test
    @DisplayName("skin part left sleeve")
    public void testLeftSleeve() {
        Assertions.assertFalse(parts.hasLeftSleeveEnabled());
        parts.setLeftSleeveEnabled(true);
        Assertions.assertTrue(parts.hasLeftSleeveEnabled());
    }

    @Test
    @DisplayName("skin part right pants")
    public void testRightPants() {
        Assertions.assertFalse(parts.hasRightPantsEnabled());
        parts.setRightPantsEnabled(true);
        Assertions.assertTrue(parts.hasRightPantsEnabled());
    }

    @Test
    @DisplayName("skin part right sleeve")
    public void testRightSleeve() {
        Assertions.assertFalse(parts.hasRightSleeveEnabled());
        parts.setRightSleeveEnabled(true);
        Assertions.assertTrue(parts.hasRightSleeveEnabled());
    }
}
