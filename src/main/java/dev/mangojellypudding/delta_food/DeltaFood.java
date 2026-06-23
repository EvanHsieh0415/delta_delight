package dev.mangojellypudding.delta_food;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(DeltaFood.MODID)
public class DeltaFood {
    public static final String MODID = "delta_food";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DeltaFood(IEventBus modEventBus, ModContainer modContainer) {
    }
}
