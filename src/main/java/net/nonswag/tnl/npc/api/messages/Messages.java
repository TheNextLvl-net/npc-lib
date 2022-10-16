package net.nonswag.tnl.npc.api.messages;

import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.MessageKey;

import javax.annotation.Nonnull;

public final class Messages {

    @Nonnull
    public static final MessageKey INVALID_SELECTION = new MessageKey("invalid-selection");

    private Messages() {
    }

    public static void loadAll() {
        loadEnglish();
        loadGerman();
    }

    private static void loadEnglish() {
        Message.getEnglish().setDefault(INVALID_SELECTION, "%prefix% §cYou have to select an npc first");
        Message.getEnglish().save();
    }

    private static void loadGerman() {
        Message.getGerman().setDefault(INVALID_SELECTION, "%prefix% §cDu musst zuerst einen npc auswählen");
        Message.getGerman().save();
    }
}
