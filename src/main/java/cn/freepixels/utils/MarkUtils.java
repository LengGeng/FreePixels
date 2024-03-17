package cn.freepixels.utils;

import cn.freepixels.models.Location;
import cn.freepixels.models.Position;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MarkUtils {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText makeText(String text) {
        return Text.literal(text);
    }

    public static MutableText makeText(String text, Style style) {
        return Text.literal(text).setStyle(style);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markWarp(String text) {
        return Text.literal(text + Utils.NewLineUnix);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markSpace(@NotNull MutableText text) {
        return text.append(Utils.Space);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markTab(String text) {
        return Text.literal(text + Utils.Tab);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markTab(@NotNull MutableText text) {
        return text.append(Utils.Tab);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markWarp(@NotNull MutableText text) {
        return text.append(Utils.NewLineUnix);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText markSpace(String text) {
        return Text.literal(text + Utils.Space);
    }

    public static MutableText markPosition(@NotNull Position position) {
        // highlightCommand 是小地图高亮
        String highlightCommand = String.format("/highlightWaypoint %d %d %d", position.getX(), position.getY(), position.getZ());

        return Text.literal(String.format("[%d,%d,%d]", position.getX(), position.getZ(), position.getY()))
                .styled(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, highlightCommand))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("点击高亮坐标(Xaero's Minimap)")))
                )
                .formatted(Formatting.RED, Formatting.BOLD);
    }

    public static MutableText markLocation(@NotNull Location location) {
        return markSpace(location.getWorld()).formatted(Formatting.GREEN)
                .append(markSpace(location.getName()).formatted(Formatting.YELLOW))
                .append(markSpace(markPosition(location.getPosition())))
                .append(markSpace(location.getCreator()).formatted(Formatting.AQUA));
    }
}
