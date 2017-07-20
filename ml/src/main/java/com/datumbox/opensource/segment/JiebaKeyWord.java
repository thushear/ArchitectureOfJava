package com.datumbox.opensource.segment;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.List;

/**
 * Created by kongming on 2017/7/20.
 */
public class JiebaKeyWord {


    static String[] sentences =
            new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
                    "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};


    public static void main(String[] args) {

        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();

        for (String sentence : sentences) {
            List<String> segList = jiebaSegmenter.sentenceProcess(sentence);
            System.out.println("segList = " + segList);
            List<SegToken> tokenList = jiebaSegmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            System.out.println("tokenList = " + tokenList);
        }




    }

}
