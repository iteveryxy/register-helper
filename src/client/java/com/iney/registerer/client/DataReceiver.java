package com.iney.registerer.client;


import it.unimi.dsi.fastutil.Hash;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.List;

public class DataReceiver {
    public HashMap<Block,String > blockStringHashMap;
    public HashMap<Item,String > itemStringHashMap;

    public DataReceiver(HashMap<Block,String > blocks, HashMap<Item,String> itemStringHashMap) {
        this.blockStringHashMap = blocks;
        this.itemStringHashMap = itemStringHashMap;
    }
}
