package com.ameguard;

import com.ameguard.config.AmethystGuardConfig;
import com.ameguard.listener.BreakHandler;
import net.fabricmc.api.ModInitializer;

public class AmethystGuardMod implements ModInitializer {
    public static final String MOD_ID = "amethyst_guard";
    public static AmethystGuardConfig CONFIG;

    @Override
    public void onInitialize() {
        // 설정 로드 (없으면 기본값 생성)
        CONFIG = AmethystGuardConfig.loadOrCreate();

        // 블록 파괴 이벤트 등록
        BreakHandler.register();
    }
}
