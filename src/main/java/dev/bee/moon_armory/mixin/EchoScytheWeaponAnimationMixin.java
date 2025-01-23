package dev.bee.moon_armory.mixin;

import dev.bee.moon_armory.item.MoonArmoryItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class EchoScytheWeaponAnimationMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

    @Shadow
    protected abstract Arm getPreferredArm(T entity);

    @Shadow
    protected abstract ModelPart getArm(Arm arm);

    @Shadow
    protected ModelPart body;

    @Shadow
    protected ModelPart head;

    @Shadow
    private ModelPart rightArm;

    @Shadow
    private ModelPart leftArm;

    @Inject(method = "animateArms", at = @At("HEAD"), cancellable = true)
    protected void animateSwingEchoScythe(T entity, float animationProgress, CallbackInfo ci) {
        // Check if player is holding an Echo Scythe
        ItemStack mainHandStack = entity.getStackInHand(Hand.MAIN_HAND);
        if (mainHandStack.isOf(MoonArmoryItems.ECHO_SCYTHE)) {

            if (!(entity.handSwingProgress <= 0.0F)) {
                Arm arm = this.getPreferredArm(entity);
                ModelPart primaryArm = this.getArm(arm);
                ModelPart secondaryArm;

                if (arm == Arm.RIGHT) {
                    secondaryArm = this.getArm(Arm.LEFT);
                } else {
                    secondaryArm = this.getArm(Arm.RIGHT);
                }

                float f = this.handSwingProgress;
                this.body.yaw = MathHelper.sin(MathHelper.sqrt(f) * (float) (Math.PI * 2)) * 0.2F;

                // Checking for left-handed
                if (arm == Arm.LEFT) {
                    this.body.yaw *= -1.0F;
                }

                // Modifiers

                    // How far away from the body the arms are
                    float armAwayModifier = 5.0F;


                this.rightArm.pivotZ = MathHelper.sin(this.body.yaw) * armAwayModifier;
                this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * armAwayModifier;
                this.leftArm.pivotZ = -MathHelper.sin(this.body.yaw) * armAwayModifier;
                this.leftArm.pivotX = MathHelper.cos(this.body.yaw) * armAwayModifier;
                this.rightArm.yaw = this.rightArm.yaw + this.body.yaw;
                this.leftArm.yaw = this.leftArm.yaw + this.body.yaw;
                this.leftArm.pitch = this.leftArm.pitch + this.body.yaw;

                // Hand Swing progress
//                f = 1.0F - this.handSwingProgress;
//                f *= f;
//                f *= f;
//                f = 1.0F - f;
//
//
//                float g = MathHelper.sin(f * (float) Math.PI);
//                float h = MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -(this.head.pitch - 0.7F) * 0.75F;
//
//                //
                primaryArm.pitch += 3.0F * this.handSwingProgress;

                secondaryArm.pitch += 3.0F * this.handSwingProgress;
//
//                //
//                primaryArm.yaw = primaryArm.yaw + this.body.yaw * 2.0F;
//                secondaryArm.yaw = secondaryArm.yaw + this.body.yaw * 2.0F;
//
//                //
//                primaryArm.roll = primaryArm.roll + MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -0.4F;
//                secondaryArm.roll = secondaryArm.roll + MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -0.4F;

                ci.cancel();
            }
        }
    }
}
