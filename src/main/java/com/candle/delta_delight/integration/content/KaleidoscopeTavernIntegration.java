package com.candle.delta_delight.integration.content;

import com.candle.delta_delight.integration.IIntegration;
import com.candle.delta_delight.integration.Integration;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Integration(modid = "kaleidoscope_tavern")
public class KaleidoscopeTavernIntegration implements IIntegration {
    @Override
    public void initCommon(final FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(final FMLClientSetupEvent event) {
    }
}
