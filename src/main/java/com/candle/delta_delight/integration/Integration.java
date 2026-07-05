package com.candle.delta_delight.integration;

import net.minecraftforge.api.distmarker.Dist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Integration {
    /**
     * 目標 Mod 的 ID
     */
    String modid();

    /**
     * 限制執行的物理端。
     * 預設為 Dist.CLIENT 和 Dist.DEDICATED_SERVER 皆執行（全端）。
     */
    Dist[] side() default {Dist.CLIENT, Dist.DEDICATED_SERVER};
}
