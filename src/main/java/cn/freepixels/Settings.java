package cn.freepixels;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    public static final Path ModPath = Paths.get("freepixels");

    static {
        if (Files.notExists(ModPath)) {
            try {
                Files.createDirectories(ModPath);
            } catch (IOException e) {
                System.out.println("[FreePixels] 创建目录失败!");
                throw new RuntimeException(e);
            }
        }
    }
}
