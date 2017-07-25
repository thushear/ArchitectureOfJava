package com.datumbox.opensource.segment;

import com.google.common.base.Joiner;
import com.hankcs.hanlp.HanLP;

import java.util.List;

/**
 * Created by kongming on 2017/7/21.
 */
public class HanLPKeyWord {


    static Joiner SPACE_JOINER = Joiner.on(" ");

    public static List<String> extractKeyWord(String content,Integer topN){

        return HanLP.extractKeyword(content,topN);
    }



    public static String extractKeyWordToText(String content,Integer topN){
        content = content.replaceAll("\\s+","");
        List<String> keyWordList = extractKeyWord(content, topN);
        return SPACE_JOINER.join(keyWordList);
    }


}
