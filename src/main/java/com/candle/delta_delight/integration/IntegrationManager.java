package com.candle.delta_delight.integration;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrationManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, IIntegration> ACTIVE_INTEGRATIONS = new HashMap<>();

    // 你的模組 ID
    private static final String MOD_ID = "yourmod";

    public static void init() {
        // 取得 Forge 在啟動時已經掃描好的類別資料
        ModFileScanData scanData = ModList.get().getModFileById(MOD_ID).getFile().getScanResult();
        String annotationName = Integration.class.getName();

        // 遍歷所有帶有我們自訂註解的類別
        for (ModFileScanData.AnnotationData data : scanData.getAnnotations()) {
            if (annotationName.equals(data.annotationType().getClassName())) {
                String className = data.clazz().getClassName();
                try {
                    // 使用 Class.forName 載入類別
                    Class<?> clazz = Class.forName(className);

                    if (IIntegration.class.isAssignableFrom(clazz)) {
                        Integration anno = clazz.getAnnotation(Integration.class);
                        if (anno != null) {
                            processIntegration(anno, (Class<? extends IIntegration>) clazz);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error("Failed to load integration class: {}", className, e);
                }
            }
        }
    }

    private static void processIntegration(Integration anno, Class<? extends IIntegration> clazz) {
        String targetModId = anno.modid();
        List<Dist> allowedSides = Arrays.asList(anno.side());
        Dist currentSide = FMLEnvironment.dist;

        // 檢查 1：目標模組是否有安裝
        if (!ModList.get().isLoaded(targetModId)) {
            LOGGER.debug("Skipping integration for '{}': Mod not installed.", targetModId);
            return;
        }

        // 檢查 2：當前的物理端 (Client/Server) 是否符合註解設定
        if (!allowedSides.contains(currentSide)) {
            LOGGER.debug("Skipping integration for '{}': Current side [{}] is not supported.", targetModId, currentSide);
            return;
        }

        // 條件皆符合，進行實例化
        try {
            IIntegration instance = clazz.getDeclaredConstructor().newInstance();
            ACTIVE_INTEGRATIONS.put(targetModId, instance);
            LOGGER.info("Successfully activated integration for mod: {} [{}]", targetModId, currentSide);
        } catch (Exception e) {
            LOGGER.error("Failed to instance integration class: {}", clazz.getName(), e);
        }
    }

    // --- 生命週期分發 ---
    public static void performCommonSetup(final FMLCommonSetupEvent event) {
        ACTIVE_INTEGRATIONS.values().forEach(i -> i.initCommon(event));
    }

    public static void performClientSetup(final FMLClientSetupEvent event) {
        ACTIVE_INTEGRATIONS.values().forEach(i -> i.initClient(event));
    }
}
