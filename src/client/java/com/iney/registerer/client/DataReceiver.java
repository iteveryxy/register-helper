package com.iney.registerer.client;


import java.util.HashMap;

public class DataReceiver {
    /** 语言代码 -> (翻译键 -> 翻译文本) */
    public HashMap<String, HashMap<String, String>> languageEntries = new HashMap<>();

    /** 获取指定语言的翻译条目，无数据时返回空映射 */
    public HashMap<String, String> getLanguage(String languageCode) {
        return languageEntries.getOrDefault(languageCode, new HashMap<>());
    }
}
