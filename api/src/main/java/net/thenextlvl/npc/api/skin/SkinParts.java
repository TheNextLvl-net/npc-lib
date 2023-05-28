package net.thenextlvl.npc.api.skin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkinParts implements com.destroystokyo.paper.SkinParts, Cloneable {
    private int raw;

    @Override
    public boolean hasCapeEnabled() {
        return (raw & 1) != 0;
    }

    @Override
    public boolean hasJacketEnabled() {
        return (raw & 1 << 1) != 0;
    }

    @Override
    public boolean hasLeftSleeveEnabled() {
        return (raw & 1 << 2) != 0;
    }

    @Override
    public boolean hasRightSleeveEnabled() {
        return (raw & 1 << 3) != 0;
    }

    @Override
    public boolean hasLeftPantsEnabled() {
        return (raw & 1 << 4) != 0;
    }

    @Override
    public boolean hasRightPantsEnabled() {
        return (raw & 1 << 5) != 0;
    }

    @Override
    public boolean hasHatsEnabled() {
        return (raw & 1 << 6) != 0;
    }

    public void setCapeEnabled(boolean enabled) {
        if (enabled) raw |= 1;
        else raw &= ~1;
    }

    public void setJacketEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 1;
        else raw &= ~(1 << 1);
    }

    public void setLeftSleeveEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 2;
        else raw &= ~(1 << 2);
    }

    public void setRightSleeveEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 3;
        else raw &= ~(1 << 3);
    }

    public void setLeftPantsEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 4;
        else raw &= ~(1 << 4);
    }

    public void setRightPantsEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 5;
        else raw &= ~(1 << 5);
    }

    public void setHatsEnabled(boolean enabled) {
        if (enabled) raw |= 1 << 6;
        else raw &= ~(1 << 6);
    }

    @Override
    public SkinParts clone() {
        try {
            return (SkinParts) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static SkinParts getDefault() {
        return new SkinParts(95);
    }
}
