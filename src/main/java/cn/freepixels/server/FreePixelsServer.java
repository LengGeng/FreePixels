package cn.freepixels.server;

import cn.freepixels.service.PositionService;
import net.fabricmc.api.DedicatedServerModInitializer;

public class FreePixelsServer implements DedicatedServerModInitializer {
    /**
     * Runs the mod initializer on the server environment.
     */
    @Override
    public void onInitializeServer() {
        System.out.println("FreePixelsServer");

        PositionService.initialize();
    }
}
