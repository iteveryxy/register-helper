package com.iney.registerer;

import net.minecraft.item.Item;

public class Test {
    public static void onInit(){
        Registerer registerer = new Registerer("test");
        registerer.registerWithSetting("item1", Registerer.NORMAL_CONSTRUCTOR, new Item.Settings());
        Item item2 = registerer.register("item2");
        registerer.initItemGroup("test", "test",item2);
    }
}
