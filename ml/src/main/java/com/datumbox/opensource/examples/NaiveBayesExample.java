/* 
 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.datumbox.opensource.examples;

import com.datumbox.opensource.classifiers.NaiveBayes;
import com.datumbox.opensource.dataobjects.NaiveBayesKnowledgeBase;
import com.datumbox.opensource.segment.HanLPKeyWord;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 * @see <a href="http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/">http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/</a>
 */
public class NaiveBayesExample {


    static int topN = 20;

    /**
     * Reads the all lines from a file and places it a String array. In each 
     * record in the String array we store a training example text.
     * 
     * @param url
     * @return
     * @throws IOException 
     */
    public static String[] readLines(URL url) throws IOException {

        Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
        List<String> lines;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines.toArray(new String[lines.size()]);
    }




    public static String[] extractKeyWordFromText(URL url) throws IOException {

        File fileDir = new File(url.getPath());
        File[] listFiles = fileDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".txt");
            }
        });

        List<String> listString = new ArrayList<>();
        for (File file : listFiles) {
            StringBuffer text = new StringBuffer();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
            while ((line = bufferedReader.readLine())!= null){
                text.append(line);
            }

            String filterText  = text.toString().replaceAll("\\s+","").replaceAll("[^\\u4e00-\\u9fa5]"," ");
            List<String> keyWords = HanLPKeyWord.extractKeyWord(filterText,calTopN(text.length()));
            listString.addAll(keyWords);
        }

        return listString.toArray(new String[listString.size()]);
    }


    public static int calTopN(int length){

        return length / 20 > 20 ? length / 20 : 30;
    }




    
    /**
     * Main method
     * 
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Map<String,String[]> spamTrainExamples = new HashMap<>();
        spamTrainExamples.put("0",extractKeyWordFromText(NaiveBayesExample.class.getResource("/spam/0")));
        spamTrainExamples.put("1",extractKeyWordFromText(NaiveBayesExample.class.getResource("/spam/1")));

        // train naive bayes
        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.setChisquareCriticalValue(0);
        naiveBayes.train(spamTrainExamples);

        NaiveBayesKnowledgeBase naiveBayesKnowledgeBase = naiveBayes.getKnowledgeBase();
        naiveBayes = new NaiveBayes(naiveBayesKnowledgeBase);

        String testSpamText1 = "这个商品不错,很实用功能";

        String testSpamText2 = "查询【O24-3lO5-O76O】（ 　人 工 接 听 专 用 　 ）～～为 保 证 过 程 无（ 损 失 ）我 们 将 通 过 【O24-3lO5-O76O】（ 全 程 录 音） . ——————————————————————————————————————————————————————————————————————摩拜单车将与富士康携手整合产业上下游资源，遴选全球优质供应商合作伙伴，并通过富士康遍布海内外的数十家工厂，专门开辟摩拜单车生产线，预计年产能可达560万辆。官方表示，在牵手富士康后，摩拜单车的车辆生产能力将在原有自有产能基础上翻倍，总产能将超1000万辆／年。\n" +
                "在牵手富士康后，查询【O24-31O5-O76O】摩拜单车的车辆生产能力将在原有自有产能基础上翻倍，总产能将超1000万辆/年，产能领先优势进一步扩大，车辆成本将进一步降低，为未来进入更多国内外城市、继续快速扩张提供了车源供给。\n" +
                "富士康计划在位于国内及海外的多家工厂分别开设摩拜单车生产线，通过工业互联网智能制造技术协助摩拜单车进一步优化车辆投放流程、加快投放速度，降低新车跨地区运输投放成本，还能够尽可能缩短城市运营团队的响应时间，提升加车效率，满足快速增长的用户需求。摩拜单车联合创始人兼CEO王晓峰表示，2017年，公司计划将智能共享单车带到国内外百座城市，富士康将依靠自己的工业设计制造和全球供应链整合优势，推动摩拜单车快速成长。\n" +
                "2017年1月21日，摩拜单车宣布进入珠海，这是继上海、北京、广州、深圳、成都、宁波、厦门、佛山、武汉、昆明、南京、东莞之后，摩拜单车进入的第13座城市。\n" +
                "另一共享单车ofo则以”一天一城“的速度，进入了全国33座城市。从投放的城市数据来看，ofo几乎是摩拜单车覆盖城市数量的2倍有余。据公开数据显示，ofo每辆单车成本在200元左右，摩拜一台车3000元左右。这也就意味着摩拜单车每投入一辆，ofo则可以投放15辆车，这样的数据对比背后，ofo单车在投放规模上就占据着极大的优势。摩拜单车此次牵手富士康也是为大举向各大城市迈进做铺垫。\n" +
                "\n";
        String isSpam1 = naiveBayes.predict(HanLPKeyWord.extractKeyWordToText(testSpamText1,calTopN(testSpamText1.length())));
        System.out.println("isSpam1 = " + isSpam1);

        String isSpam2 = naiveBayes.predict(HanLPKeyWord.extractKeyWordToText(testSpamText2,calTopN(testSpamText2.length())));
        System.out.println("isSpam2 = " + isSpam2);

//
//        //map of dataset files
//        Map<String, URL> trainingFiles = new HashMap<>();
//        trainingFiles.put("English", NaiveBayesExample.class.getResource("/datasets/training.language.en.txt"));
//        trainingFiles.put("French", NaiveBayesExample.class.getResource("/datasets/training.language.fr.txt"));
//        trainingFiles.put("German", NaiveBayesExample.class.getResource("/datasets/training.language.de.txt"));
//
//        //loading examples in memory
//        Map<String, String[]> trainingExamples = new HashMap<>();
//        for(Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
//            trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
//        }
//
//        //train classifier
//        NaiveBayes nb = new NaiveBayes();
//        nb.setChisquareCriticalValue(6.63); //0.01 pvalue
//        nb.train(trainingExamples);
//
//        //get trained classifier knowledgeBase
//        NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();
//
//        nb = null;
//        trainingExamples = null;
//
//
//        //Use classifier
//        nb = new NaiveBayes(knowledgeBase);
//        String exampleEn = "I am English";
//        String outputEn = nb.predict(exampleEn);
//        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleEn, outputEn);
//
//        String exampleFr = "Je suis Français";
//        String outputFr = nb.predict(exampleFr);
//        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleFr, outputFr);
//
//        String exampleDe = "Ich bin Deutsch";
//        String outputDe = nb.predict(exampleDe);
//        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleDe, outputDe);
        

    }
    
}
