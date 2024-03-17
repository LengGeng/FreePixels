package cn.freepixels;

import cn.freepixels.service.PositionService;
import net.fabricmc.api.ModInitializer;

public class FreePixels implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        System.out.println("FreePixels");

        PositionService.initialize();
    }
}
