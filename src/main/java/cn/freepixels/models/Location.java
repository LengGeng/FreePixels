package cn.freepixels.models;

import net.minecraft.util.math.Vec3d;

public class Location {
    // 名称
    private String name;
    // 描述
    private String description;
    // 所在世界
    private String world;
    // 分类
    private String category;
    // 创建者
    private String creator;
    // 创建者ID(UUID)
    private String creatorId;
    // 创建时间
    private String creatorTime;

    // 坐标位置
    private Position position;

    public Location() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(String creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y, int z) {
        this.position = new Position(x, y, z);
    }

    public void setPosition(Vec3d vec3d) {
        this.position = new Position(vec3d);
    }
}
