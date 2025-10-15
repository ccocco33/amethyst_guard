package com.ameguard.client;

import com.ameguard.AmethystGuardMod;
import com.ameguard.config.AmethystGuardConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuIntegration::buildScreen;
    }

    public static Screen buildScreen(Screen parent) {
        AmethystGuardConfig cfg = AmethystGuardMod.CONFIG;

        var cat = ConfigCategory.createBuilder()
                .name(Text.translatable("amethyst_guard.config.category.mining_restrict"))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.smallBud.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.smallBud.desc")))
                        .binding(cfg.allowSmallBud, () -> cfg.allowSmallBud, v -> cfg.allowSmallBud = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.mediumBud.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.mediumBud.desc")))
                        .binding(cfg.allowMediumBud, () -> cfg.allowMediumBud, v -> cfg.allowMediumBud = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.largeBud.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.largeBud.desc")))
                        .binding(cfg.allowLargeBud, () -> cfg.allowLargeBud, v -> cfg.allowLargeBud = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.cluster.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.cluster.desc")))
                        .binding(cfg.allowCluster, () -> cfg.allowCluster, v -> cfg.allowCluster = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.buddingProtect.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.buddingProtect.desc")))
                        .binding(cfg.protectBuddingAmethyst, () -> cfg.protectBuddingAmethyst, v -> cfg.protectBuddingAmethyst = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                // ✅ 추가: 화면 메시지 On/Off
                .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("amethyst_guard.config.option.showMsg.name"))
                        .description(OptionDescription.of(Text.translatable("amethyst_guard.config.option.showMsg.desc")))
                        .binding(cfg.showOnScreenMessages, () -> cfg.showOnScreenMessages, v -> cfg.showOnScreenMessages = v)
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build();

        var yacl = YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("amethyst_guard.config.title"))
                .category(cat)
                .save(cfg::save)
                .build();

        return yacl.generateScreen(parent);
    }
}
