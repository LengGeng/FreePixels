package cn.freepixels.utils;

import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    // 空字符串
    public static final String Empty = "";
    // 单个空格
    public static final String Space = " ";
    // 制表符
    public static final String Tab = "\t";
    // 换行符（对于Windows系统）
    public static final String NewLineWin = "\r\n";
    // 换行符（对于Unix/Linux系统）
    public static final String NewLineUnix = "\n";
    // 换行符（跨平台兼容）
    public static final String NewLineCrossPlatform = System.lineSeparator();

    public static String GetServerTime() {
        // 获取当前的日期时间
        LocalDateTime now = LocalDateTime.now();
        // 定义日期时间的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前日期时间
        return now.format(formatter);
    }
}
