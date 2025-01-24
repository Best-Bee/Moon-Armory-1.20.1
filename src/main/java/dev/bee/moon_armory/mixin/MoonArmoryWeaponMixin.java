package dev.bee.moon_armory.mixin;

import dev.bee.moon_armory.item.MoonArmoryItems;
import dev.bee.moon_armory.client.render.entity.model.EchoScythePosing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BipedEntityModel.class)
public abstract class MoonArmoryWeaponMixin<T extends LivingEntity> {

    @Shadow
    private ModelPart rightArm;

    @Shadow
    private ModelPart leftArm;

    @Shadow
    private ModelPart head;

    @Shadow
    private ModelPart body;

    @Shadow
    public BipedEntityModel.ArmPose leftArmPose = BipedEntityModel.ArmPose.EMPTY;

    @Shadow
    public BipedEntityModel.ArmPose rightArmPose = BipedEntityModel.ArmPose.EMPTY;

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void positionWeaponRight(T entity, CallbackInfo ci) {
        // Check if player is holding an Echo Scythe
        ItemStack mainHandStack = entity.getStackInHand(Hand.MAIN_HAND);
        if (mainHandStack.isOf(MoonArmoryItems.ECHO_SCYTHE)) {
//            float k = 1.0F;
//            if (entity.getRoll() > 4) {
//                k = (float)entity.getVelocity().lengthSquared();
//                k /= 0.2F;
//                k *= k * k;
//            }
//
//            if (k < 1.0F) {
//                k = 1.0F;
//            }

            EchoScythePosing.hold(this.rightArm, this.leftArm, this.head, this.body, true);
            ci.cancel();
        }
    }
//    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
//    private void positionWeaponLeft(T entity, CallbackInfo ci) {
//        // Check if player is holding an Echo Scythe
//        ItemStack mainHandStack = entity.getStackInHand(Hand.MAIN_HAND);
//        if (mainHandStack.isOf(MoonArmoryItems.ECHO_SCYTHE)) {
//            EchoScythePosing.hold(this.rightArm, this.leftArm, this.head, this.body, false);
//            ci.cancel();
//        }
//    }

}