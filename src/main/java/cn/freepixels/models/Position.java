package cn.freepixels.models;


import net.minecraft.util.math.Vec3d;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position() {
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Vec3d vec3d) {
        this.x = (int) vec3d.x;
        this.y = (int) vec3d.y;
        this.z = (int) vec3d.z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
