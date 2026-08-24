package com.iney.registerer.test;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, user, entity, hand);

        if (entity instanceof SheepEntity sheepEntity){
            sheepEntity.setSheared(true);
            user.giveItemStack(new ItemStack(Items.WHITE_WOOL));

            stack.damage(1,user);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;

    }
}
