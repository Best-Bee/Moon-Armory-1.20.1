package dev.bee.moon_armory.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.math.MathHelper;

public class EchoScythePosing {
    public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, ModelPart body, boolean rightArmed) {
        final float conversion = (float) Math.PI / 180.0F;
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;

        //Turn body slightly to allow better arm positioning
        // body.yaw = 30.0F * conversion;

        if (rightArmed) {
            //Back
            otherArm.pivotZ = 1.5F;
            //Up
            otherArm.pivotY = 1.0F;
            holdingArm.pivotZ = -1.5F;
            holdingArm.pivotY = -1.0F;
        } else {
            //Forward
            otherArm.pivotZ = -1.5F;
            //Down
            otherArm.pivotY = -1.0F;
            holdingArm.pivotZ = 1.5F;
            holdingArm.pivotY = 1.0F;
        }

        float holdingP = 0.0F * conversion;
        float holdingY = 0.0F * conversion;
        float holdingR = 0.0F * conversion;

        float otherP = 0.0F * conversion;
        float otherY = 0.0F * conversion;
        float otherR = 0.0F * conversion;

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
