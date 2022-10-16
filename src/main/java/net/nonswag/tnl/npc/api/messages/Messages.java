package net.nonswag.tnl.npc.api.messages;

import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.MessageKey;

import javax.annotation.Nonnull;

public final class Messages {

    @Nonnull
    public static final MessageKey INVALID_SELECTION = new MessageKey("invalid-selection");
    @Nonnull
    public static final MessageKey NOTHING_CHANGED = new MessageKey("nothing-selected");
    @Nonnull
    public static final MessageKey CHANGED_SKIN = new MessageKey("changed-skin");
    @Nonnull
    public static final MessageKey UNSELECTED = new MessageKey("unselected");
    @Nonnull
    public static final MessageKey SELECTED = new MessageKey("selected");
    @Nonnull
    public static final MessageKey DELETED = new MessageKey("deleted");

    private Messages() {
    }

    public static void loadAll() {
        loadEnglish();
        loadGerman();
    }

    private static void loadEnglish() {
        Message.getEnglish().setDefault(INVALID_SELECTION, "%prefix% §cYou have to select an npc first");
        Message.getEnglish().setDefault(NOTHING_CHANGED, "%prefix% §cNothing could be changed");
        Message.getEnglish().setDefault(CHANGED_SKIN, "%prefix% §aChanged the skin to §6%skin% §8(§7Restart required§8)");
        Message.getEnglish().setDefault(UNSELECTED, "%prefix% §aCleared your selection");
        Message.getEnglish().setDefault(SELECTED, "%prefix% §aSelection has been made");
        Message.getEnglish().setDefault(DELETED, "%prefix% §aNPC got deleted");
        Message.getEnglish().save();
    }

    private static void loadGerman() {
        Message.getGerman().setDefault(INVALID_SELECTION, "%prefix% §cDu musst zuerst einen npc auswählen");
        Message.getGerman().setDefault(NOTHING_CHANGED, "%prefix% §cEs konnte nichts geändert werden");
        Message.getGerman().setDefault(CHANGED_SKIN, "%prefix% §aChanged the skin to §6%skin% §8(§7Restart required§8)");
        Message.getGerman().setDefault(UNSELECTED, "%prefix% §aDeine auswahl wurde gelöscht");
        Message.getGerman().setDefault(SELECTED, "%prefix% §aDeine auswahl wurde getroffen");
        Message.getGerman().setDefault(DELETED, "%prefix% §aDer NPC wurde gelöscht");
        Message.getGerman().save();
    }
}
