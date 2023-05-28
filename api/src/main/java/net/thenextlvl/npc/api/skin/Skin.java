package net.thenextlvl.npc.api.skin;

import org.jetbrains.annotations.Nullable;

public record Skin(SkinParts parts, String value, @Nullable String signature) implements Cloneable {
    public Skin(String value, @Nullable String signature) {
        this(SkinParts.getDefault(), value, signature);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Skin clone() {
        return new Skin(parts.clone(), value, signature);
    }
}
