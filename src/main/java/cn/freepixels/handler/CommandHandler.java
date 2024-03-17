package cn.freepixels.handler;

import cn.freepixels.utils.MarkUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

public class CommandHandler {
    public static void sendFeedback(@NotNull ServerCommandSource source, Text text) {
        source.sendFeedback(() -> text, false);
    }

    public static void sendFeedback(@NotNull ServerCommandSource source, String text) {
        source.sendFeedback(() -> MarkUtils.makeText(text), false);
    }

    public static void sendFeedback(@NotNull ServerCommandSource source, String text, Formatting formatting) {
        sendFeedback(source, MarkUtils.makeText(text, Style.EMPTY.withColor(formatting)));
    }

    public static void sendSuccessFeedback(@NotNull ServerCommandSource source, String text) {
        sendFeedback(source, text, Formatting.GREEN);
    }

    public static void sendErrorFeedback(@NotNull ServerCommandSource source, String text) {
        sendFeedback(source, text, Formatting.RED);
    }

    public static void sendWarningFeedback(@NotNull ServerCommandSource source, String text) {
        sendFeedback(source, text, Formatting.GOLD);
    }
}
