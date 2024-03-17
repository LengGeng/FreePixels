package cn.freepixels.repositories;

import cn.freepixels.Settings;
import cn.freepixels.models.Location;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepository {
    public static final Path LocationsPath = Settings.ModPath.resolve("locations.json");
    private static List<Location> locations = new ArrayList<>();
    private static final Gson gson = new Gson();

    /**
     * 获取位置数据
     *
     * @return 位置列表
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull ArrayList<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    /**
     * 通过位置名称查找位置
     *
     * @param name 位置名称
     * @return 位置数据
     */
    public static Location getLocationByName(String name) {
        Optional<Location> location = locations.stream().filter(l -> l.getName().equals(name)).findFirst();
        return location.orElse(null);
    }

    /**
     * 添加位置数据
     *
     * @param location 位置数据
     * @return 是否添加成功
     */
    public static boolean addLocation(Location location) {
        locations.add(location);
        return saveLocations();
    }

    /**
     * 删除位置数据
     *
     * @param name 位置名称
     * @return 是否删除成功
     */
    public static boolean deleteLocation(String name) {
        boolean isRemoved = locations.removeIf(location -> location.getName().equals(name));
        if (isRemoved) {
            return saveLocations();
        }
        return true;
    }

    /**
     * 读取位置数据
     */
    public static void loadLocations() {
        try {
            String jsonData = new String(Files.readAllBytes(LocationsPath));
            Type listType = new TypeToken<ArrayList<Location>>() {
            }.getType();
            locations = gson.fromJson(jsonData, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存位置数据
     *
     * @return 是否保存成功
     */
    public static boolean saveLocations() {
        String jsonData = gson.toJson(locations);
        try {
            Files.write(LocationsPath, jsonData.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
