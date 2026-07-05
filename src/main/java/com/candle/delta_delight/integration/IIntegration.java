package com.candle.delta_delight.integration;

import net.minecraftforge.fml.event.lifecycle.*;

public interface IIntegration {
    default void initCommon(final FMLCommonSetupEvent event) {}
    default void initClient(final FMLClientSetupEvent event) {}
    default void initDedicatedServer(final FMLDedicatedServerSetupEvent event) {}
    default void constructMod(final FMLConstructModEvent event) {}
    default void loadComplete(final FMLLoadCompleteEvent event) {}
}