package net.nonswag.tnl.npc.api.messages;

import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.MessageKey;

@FieldsAreNonnullByDefault
public final class Messages {
    public static final MessageKey INVALID_SELECTION = new MessageKey("invalid-selection");
    public static final MessageKey NOTHING_CHANGED = new MessageKey("nothing-selected");
    public static final MessageKey CHANGED_SKIN = new MessageKey("changed-skin");
    public static final MessageKey UNSELECTED = new MessageKey("unselected");
    public static final MessageKey SELECTED = new MessageKey("selected");
    public static final MessageKey DELETED = new MessageKey("deleted");
    public static final MessageKey RENAMED = new MessageKey("renamed");

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
        Message.getEnglish().setDefault(RENAMED, "%prefix% §aRenamed the npc to §r%name%");
        Message.getEnglish().save();
    }

    private static void loadGerman() {
        Message.getGerman().setDefault(INVALID_SELECTION, "%prefix% §cDu musst zuerst einen npc auswählen");
        Message.getGerman().setDefault(NOTHING_CHANGED, "%prefix% §cEs konnte nichts geändert werden");
        Message.getGerman().setDefault(CHANGED_SKIN, "%prefix% §aChanged the skin to §6%skin% §8(§7Restart required§8)");
        Message.getGerman().setDefault(UNSELECTED, "%prefix% §aDeine auswahl wurde gelöscht");
        Message.getGerman().setDefault(SELECTED, "%prefix% §aDeine auswahl wurde getroffen");
        Message.getGerman().setDefault(DELETED, "%prefix% §aDer NPC wurde gelöscht");
        Message.getGerman().setDefault(RENAMED, "%prefix% §aDer NPC wurde zu §r%name%§a umbenannt");
        Message.getGerman().save();
    }
}
