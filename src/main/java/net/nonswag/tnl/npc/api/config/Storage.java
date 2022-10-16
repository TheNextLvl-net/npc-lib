package net.nonswag.tnl.npc.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.nonswag.core.api.file.formats.JsonFile;
import net.nonswag.tnl.listener.api.player.Skin;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Storage {

    @Nonnull
    private static final JsonFile saves = new JsonFile("plugins/NPC", "saves.json");

    @Nonnull
    public static final List<FakePlayer> NPCs = new ArrayList<>();

    public static void loadAll() {
        if (!saves.getJsonElement().isJsonArray()) saves.setJsonElement(new JsonArray());
        JsonArray root = saves.getJsonElement().getAsJsonArray();
        root.forEach(entry -> NPCs.add(parse(entry.getAsJsonObject()).register()));
    }

    @Nonnull
    private static FakePlayer parse(@Nonnull JsonObject object) {
        if (!object.has("name")) throw new NullPointerException("name not found");
        if (!object.has("location")) throw new NullPointerException("location not found");
        if (!object.has("uuid")) throw new NullPointerException("uuid not found");
        String name = object.get("name").getAsString();
        Location location = parse(object.get("location").getAsString());
        Skin skin = object.has("skin") ? parse(object.get("skin").getAsString().split(", ")) : null;
        UUID uuid = UUID.fromString(object.get("uuid").getAsString());
        return new FakePlayer(name, location, skin, uuid);
    }

    @Nonnull
    private static JsonObject parse(@Nonnull FakePlayer npc) {
        JsonObject object = new JsonObject();
        object.addProperty("name", npc.getName());
        object.addProperty("location", parse(npc.getLocation()));
        if (npc.getPlayer().getGameProfile().getSkin() != null) {
            object.addProperty("skin", parse(npc.getPlayer().getGameProfile().getSkin()));
        }
        object.addProperty("uuid", npc.getPlayer().getGameProfile().getUniqueId().toString());
        return object;
    }

    @Nonnull
    private static Location parse(@Nonnull String string) {
        String[] split = string.split(", ");
        if (split.length < 4) throw new NullPointerException("invalid location: " + string);
        World world = Bukkit.getWorld(split[0]);
        if (world == null) throw new NullPointerException("invalid world: " + split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        if (split.length < 6) return new Location(world, x, y, z);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Nonnull
    private static String parse(@Nonnull Location location) {
        if (location.getWorld() == null) throw new NullPointerException("world cannot be null");
        return "%s, %s, %s, %s, %s, %s".formatted(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Nonnull
    private static Skin parse(@Nonnull String[] strings) {
        return new Skin(strings[0], strings[1]);
    }

    @Nonnull
    private static String parse(@Nonnull Skin skin) {
        return "%s, %s".formatted(skin.getValue(), skin.getSignature());
    }

    public static void exportAll() {
        JsonArray array = new JsonArray();
        NPCs.forEach(npc -> array.add(parse(npc)));
        saves.setJsonElement(array);
        saves.save();
    }
}
