package cn.freepixels.repositories;

import cn.freepixels.Settings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PositionShareRepository {
    public static final Path SharesPath = Settings.ModPath.resolve("shares.json");
    private static List<String> sharePlayers = new ArrayList<>();
    private static final Gson gson = new Gson();

    /**
     * 获取共享位置的玩家列表
     *
     * @return 玩家 UUID 列表
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull ArrayList<String> getSharePlayers() {
        return new ArrayList<>(sharePlayers);
    }

    /**
     * 判断玩家是否开启共享位置
     *
     * @param uuid 玩家 UUID
     * @return 玩家是否开启共享位置
     */
    public static boolean playerIsShare(String uuid) {
        return sharePlayers.contains(uuid);
    }

    /**
     * 玩家开启位置共享
     *
     * @param uuid 玩家 UUID
     * @return 是否添加成功
     */
    public static boolean open(String uuid) {
        sharePlayers.add(uuid);
        return saveSharePlayers();
    }

    /**
     * 玩家关闭位置共享
     *
     * @param uuid 玩家 UUID
     * @return 是否删除成功
     */
    public static boolean close(String uuid) {
        boolean isRemoved = sharePlayers.removeIf(id -> id.equals(uuid));
        if (isRemoved) {
            return saveSharePlayers();
        }
        return true;
    }

    /**
     * 读取数据
     */
    public static void loadSharePlayers() {
        try {
            String jsonData = new String(Files.readAllBytes(SharesPath));
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            sharePlayers = gson.fromJson(jsonData, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存数据
     *
     * @return 是否保存成功
     */
    public static boolean saveSharePlayers() {
        String jsonData = gson.toJson(sharePlayers);
        try {
            Files.write(SharesPath, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
