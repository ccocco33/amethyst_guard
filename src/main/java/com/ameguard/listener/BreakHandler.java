package com.ameguard.listener;

import com.ameguard.AmethystGuardMod;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BreakHandler {

    public static void register() {
        // BEFORE: false를 반환하면 파괴가 취소됩니다.
        PlayerBlockBreakEvents.BEFORE.register(BreakHandler::beforeBreak);
    }

    // ※ Fabric 1.20.1의 BEFORE 콜백 시그니처에 맞춤:
    // (World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) -> boolean
    private static boolean beforeBreak(World world,
                                       PlayerEntity player,
                                       BlockPos pos,
                                       BlockState state,
                                       BlockEntity blockEntity) {
        // 서버 쪽에서만 로직 적용 (클라면 통과)
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return true;
        }

        Block b = state.getBlock();

        // 번식하는 자수정 보호
        if (b == Blocks.BUDDING_AMETHYST && AmethystGuardMod.CONFIG.protectBuddingAmethyst) {
            //deny(serverPlayer, "번식하는 자수정은 보호됩니다.");
            return false;
        }

        // 크기별 제어
        if (b == Blocks.SMALL_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowSmallBud) {
            //deny(serverPlayer, "작은 자수정 봉오리는 캘 수 없어요.");
            return false;
        }
        if (b == Blocks.MEDIUM_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowMediumBud) {
            //deny(serverPlayer, "중간 자수정 봉오리는 캘 수 없어요.");
            return false;
        }
        if (b == Blocks.LARGE_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowLargeBud) {
            //deny(serverPlayer, "큰 자수정 봉오리는 캘 수 없어요.");
            return false;
        }
        if (b == Blocks.AMETHYST_CLUSTER && !AmethystGuardMod.CONFIG.allowCluster) {
            //deny(serverPlayer, "자수정 클러스터는 캘 수 없어요.");
            return false;
        }

        return true;
    }

    private static void deny(ServerPlayerEntity player, String msg) {
        player.sendMessage(Text.literal("§d[AmethystGuard] §f" + msg), true);
    }
}
