package cn.freepixels.client;

import net.fabricmc.api.ClientModInitializer;

public class FreePixelsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        System.out.println("FreePixelsClient");
    }
}
