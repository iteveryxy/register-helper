package com.iney.registerer.test;

import com.iney.registerer.ItemRegisterHandler;
import com.iney.registerer.Registerer;
import net.minecraft.item.Item;

public class Test {
    public static void onInit(){
        Registerer registerer = new Registerer("test");
        registerer.itemHandler.registerWithSetting("item1", "测试物品1", ItemRegisterHandler.NORMAL_CONSTRUCTOR, new Item.Settings());
        Item item2 = registerer.itemHandler.register("item2", "测试物品2");
        registerer.blockHandler.register("block1", "测试方块1");
        registerer.itemHandler.registerWithSetting("shear","测试剪刀",TestItem::new, new Item.Settings().maxCount(1).maxDamage(100));


        //最后一个注册，确保所有物品已经加载
        registerer.initItemGroup("测试物品组", "test", item2);
    }
}
