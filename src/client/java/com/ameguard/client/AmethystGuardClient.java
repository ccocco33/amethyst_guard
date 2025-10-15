package com.ameguard.client;

import com.ameguard.AmethystGuardMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class AmethystGuardClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 좌클릭으로 블록 캐기 시작하기 직전에 호출됨. FAIL 반환하면 모션 자체가 시작되지 않음.
        AttackBlockCallback.EVENT.register(this::onAttackBlock);
    }

    private ActionResult onAttackBlock(PlayerEntity player, World world, net.minecraft.util.Hand hand,
                                       BlockPos pos, Direction direction) {
        if (world == null || !world.isClient()) {
            // 클라이언트에서만 취급
            return ActionResult.PASS;
        }

        Block b = world.getBlockState(pos).getBlock();

        // 번식하는 자수정 보호
        if (b == Blocks.BUDDING_AMETHYST && AmethystGuardMod.CONFIG.protectBuddingAmethyst) {
            denyClient(player, "amethyst_guard.msg.blocked.budding");
            return ActionResult.FAIL; // ← 캐는 모션 자체가 시작되지 않음
        }

        // 크기별 제어
        if (b == Blocks.SMALL_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowSmallBud) {
            denyClient(player, "amethyst_guard.msg.blocked.small");
            return ActionResult.FAIL;
        }
        if (b == Blocks.MEDIUM_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowMediumBud) {
            denyClient(player, "amethyst_guard.msg.blocked.medium");
            return ActionResult.FAIL;
        }
        if (b == Blocks.LARGE_AMETHYST_BUD && !AmethystGuardMod.CONFIG.allowLargeBud) {
            denyClient(player, "amethyst_guard.msg.blocked.large");
            return ActionResult.FAIL;
        }
        if (b == Blocks.AMETHYST_CLUSTER && !AmethystGuardMod.CONFIG.allowCluster) {
            denyClient(player, "amethyst_guard.msg.blocked.cluster");
            return ActionResult.FAIL;
        }

        return ActionResult.PASS; // 허용: 정상 진행
    }

    private void denyClient(PlayerEntity player, String key) {
        // ✅ 새 옵션: 화면 메시지를 끄면 아무 메시지도 띄우지 않음
        if (AmethystGuardMod.CONFIG.showOnScreenMessages) {
            player.sendMessage(Text.translatable(key), true); // 액션바
        }
        // 팔 휘두르기/크랙은 FAIL 반환으로 이미 차단됨
    }
}
