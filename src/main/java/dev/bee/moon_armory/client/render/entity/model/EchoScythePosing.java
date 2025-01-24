package dev.bee.moon_armory.client.render.entity.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.realms.dto.PlayerActivities;
import net.minecraft.client.realms.dto.PlayerActivity;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.math.MathHelper;

public class EchoScythePosing {
    public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, ModelPart body, boolean rightArmed) {
        final float conversion = (float) Math.PI / 180.0F;
        float k;
        float j;

        k = (float) MinecraftClient.getInstance().player.getVelocity().horizontalLengthSquared();

        if (k > 0.25F) {
            k = 0.25F;
        } else if (k < 0.1F) {
            k = 0.1F;
        }

        j = MinecraftClient.getInstance().player.age;
        j = MathHelper.cos(j * 0.015F) - 1.0F;

        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;

        //Turn body slightly to allow better arm positioning
        body.yaw = 10.0F * conversion;

        modelPart.pivotZ = 1.0F;
        modelPart.pivotX = -4.5F;
        modelPart2.pivotZ = -2.5F;
        modelPart2.pivotX = 4.5F;

        float holdingP = 110.0F * conversion; //85
        float holdingY = 10.0F * conversion; //-70
        float holdingR = 35.0F * conversion; //65

        holdingP += (j * k);
        holdingY += (j * k);
        //holdingR += (j * k);

        float otherP = -90.0F * conversion;
        float otherY = 60.0F * conversion;
        float otherR = -45.0F * conversion;

        otherP -= (j * k);
        otherR -= (2.0F * j * k);

        modelPart.pitch = (rightArmed ? -holdingP : holdingP) /*+ (0.5F * head.yaw)*/;
        modelPart.yaw = (rightArmed ? -holdingY : holdingY);
        modelPart.roll = (rightArmed ? -holdingR : holdingR);

        modelPart2.pitch = (rightArmed ? otherP : -otherP) /*+ (0.5F * head.yaw)*/;
        modelPart2.yaw = (rightArmed ? otherY : -otherY);
        modelPart2.roll = (rightArmed ? otherR : -otherR);

    }

    public static void charge(ModelPart holdingArm, ModelPart pullingArm, LivingEntity actor, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : pullingArm;
        ModelPart modelPart2 = rightArmed ? pullingArm : holdingArm;
        modelPart.yaw = rightArmed ? -0.8F : 0.8F;
        modelPart.pitch = -0.97079635F;
        modelPart2.pitch = modelPart.pitch;
        float f = (float) CrossbowItem.getPullTime(actor.getActiveItem());
        float g = MathHelper.clamp((float)actor.getItemUseTime(), 0.0F, f);
        float h = g / f;
        modelPart2.yaw = MathHelper.lerp(h, 0.4F, 0.85F) * (float)(rightArmed ? 1 : -1);
        modelPart2.pitch = MathHelper.lerp(h, modelPart2.pitch, (float) (-Math.PI / 2));
    }
}
