package com.iney.registerer.client;


import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;

public class DataReceiver {
    public HashMap<Block,String > blockStringHashMap;
    public HashMap<Item,String > itemStringHashMap;
    public HashMap<String,String > stringStringHashMap;

    public DataReceiver(HashMap<Block,String > blocks, HashMap<Item,String> itemStringHashMap, HashMap<String,String> stringStringHashMap) {
        this.blockStringHashMap = blocks;
        this.itemStringHashMap = itemStringHashMap;
        this.stringStringHashMap = stringStringHashMap;
    }
}
