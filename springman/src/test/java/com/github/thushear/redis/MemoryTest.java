package com.github.thushear.redis;

import com.github.thushear.BaseTest;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by kongming on 2017/5/22.
 */
public class MemoryTest extends BaseTest {


    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    String commentWithTagsAndAfterUserCommentAndShowOrderJSON = "";

//            "{\n" +
//            "  \"nid\": 10338860399,\n" +
//            "  \"ct\": \"第一次用苹果手机，京东自营的东西，正品没问题。一次到位买的32g版本，价钱5499元确实比实体店便宜，目前用着没有任何问题，照相功能非常强大，虽然我很少照相，系统一点都不卡顿，不过出现过部分软件卡死的现象，还有一次出现过无法接收信号，就是上什么软件都连不上网，最后关机重启才好了，希望这个手机能用久一点吧，而且7p适合手大一点的人用黑色版本不用担心耐脏的问题，反正我还是挺喜欢这个黑色的苹果的，希望多用几年毕竟买一次疼几年呀。\",\n" +
//            "  \"ce\": \"2017-04-24 12:21:41\",\n" +
//            "  \"id\": \"6c46616b-d4af-4c7d-87c1-9404738b72c3\",\n" +
//            "  \"ide\": true,\n" +
//            "  \"itp\": false,\n" +
//            "  \"rid\": \"3133847\",\n" +
//            "  \"rti\": \"2017-04-18 15:23:27\",\n" +
//            "  \"rtp\": \"Product\",\n" +
//            "  \"rtd\": 0,\n" +
//            "  \"rk\": \"-20\",\n" +
//            "  \"rct\": 0,\n" +
//            "  \"se\": 4,\n" +
//            "  \"st\": 1,\n" +
//            "  \"tt\": \"\",\n" +
//            "  \"uvc\": 0,\n" +
//            "  \"ulvc\": 0,\n" +
//            "  \"pin\": \"319420944-170585\",\n" +
//            "  \"ui\": \"storage.360buyimg.com/i.imageUpload/3331393432303934342d31373035383531343837353933313036343233_sma.jpg\",\n" +
//            "  \"uip\": \"171.44.192.22\",\n" +
//            "  \"uli\": \"62\",\n" +
//            "  \"up\": \"\",\n" +
//            "  \"urt\": \"2013-09-24 12:31:03\",\n" +
//            "  \"vct\": 156,\n" +
//            "  \"oid\": 51329822239,\n" +
//            "  \"irg\": false,\n" +
//            "  \"uid\": 127943716,\n" +
//            "  \"urk\": \"懒洋洋的池塘\",\n" +
//            "  \"uct\": 0,\n" +
//            "  \"td\": 0,\n" +
//            "  \"mos\": 2,\n" +
//            "  \"did\": 200256785,\n" +
//            "  \"cts\": [\n" +
//            "    {\n" +
//            "      \"id\": 5216089,\n" +
//            "      \"name\": \"功能齐全\",\n" +
//            "      \"pin\": \"\",\n" +
//            "      \"status\": 0,\n" +
//            "      \"rid\": 15231,\n" +
//            "      \"productId\": 3133847,\n" +
//            "      \"commentId\": 10338860399,\n" +
//            "      \"created\": \"2017-04-24 12:22:13\",\n" +
//            "      \"modified\": \"2017-04-24 12:22:13\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"id\": 5216090,\n" +
//            "      \"name\": \"信号稳定\",\n" +
//            "      \"pin\": \"\",\n" +
//            "      \"status\": 0,\n" +
//            "      \"rid\": 15236,\n" +
//            "      \"productId\": 3133847,\n" +
//            "      \"commentId\": 10338860399,\n" +
//            "      \"created\": \"2017-04-24 12:22:13\",\n" +
//            "      \"modified\": \"2017-04-24 12:22:13\"\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"pcr\": \"黑色\",\n" +
//            "  \"pse\": \"\",\n" +
//            "  \"itl\": -20,\n" +
//            "  \"ird\": false,\n" +
//            "  \"uln\": \"金牌会员\",\n" +
//            "  \"af\": 1,\n" +
//            "  \"vts\": 1493113936905,\n" +
//            "  \"tgt\": 0,\n" +
//            "  \"auc\": {\n" +
//            "    \"tableNames\": {},\n" +
//            "    \"forceRead2Writer\": false,\n" +
//            "    \"id\": 13720191,\n" +
//            "    \"productId\": 3133847,\n" +
//            "    \"orderId\": 51329822239,\n" +
//            "    \"commentId\": 10338860399,\n" +
//            "    \"status\": 1,\n" +
//            "    \"pin\": \"319420944-170585\",\n" +
//            "    \"clientType\": 1,\n" +
//            "    \"ip\": \"171.44.192.22\",\n" +
//            "    \"anonymous\": 1,\n" +
//            "    \"dealt\": 0,\n" +
//            "    \"created\": \"2017-04-24 12:26:52\",\n" +
//            "    \"modified\": \"2017-04-24 12:26:52\",\n" +
//            "    \"hAfterUserComment\": {\n" +
//            "      \"rowKey\": \"6813720191\",\n" +
//            "      \"id\": 13720191,\n" +
//            "      \"content\": \"评价一下订单 51367820501这一单，对物流很不满意。系统错误分配配送站，导致收到货用了将近4天的时间，京东物流的时效性我本来还是很相信的，可是这次硬是让我大跌眼镜，而且之后京东自营也没有一个相对解决的态度，为什么前面这单物流那么快，我后面下一单就区别对待\",\n" +
//            "      \"discussionId\": 200274882\n" +
//            "    }\n" +
//            "  },\n" +
//            "  \"plusAvailable\": 103,\n" +
//            "  \"uif\": 1,\n" +
//            "  \"sas\": [\n" +
//            "    {\n" +
//            "      \"dim\": 3,\n" +
//            "      \"saleName\": \"选择容量\",\n" +
//            "      \"saleValue\": \"32GB\"\n" +
//            "    }\n" +
//            "  ]\n" +
//            "}";

    int iter = 1000000;


    Map<String, String> commentMap = Maps.newHashMap();

    {
        commentMap.put("counter", "1");
        commentMap.put("comment", commentWithTagsAndAfterUserCommentAndShowOrderJSON);

    }


    @Test
    public void oldUnCompressedComment() {
        // 10000 31M
        // 100000 302
        for (int i = 0; i < iter; i++) {
            redisTemplate.opsForValue().set("foo" + i, commentWithTagsAndAfterUserCommentAndShowOrderJSON);
        }

    }


    @Test
    public void newHashComment() {

        //10000 34.17
        // 100000 335
        for (int i = 0; i < iter; i++) {

            redisTemplate.opsForHash().putAll("has" + i, commentMap);
        }

    }


    @Test
    public void setMemoryTest() {

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        for (int i = 0; i < iter; i++) {
            setOperations.add("bigset", iter + i + "");
        }
    }


    @Test
    public void testSetCommand() {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Set<String> bigSet = setOperations.members("bigset");
        System.out.println("bigSet = " + bigSet.size());
    }


    @Test
    public void sscanSetTest() {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        ScanOptions.ScanOptionsBuilder scanOptionsBuilder = new ScanOptions.ScanOptionsBuilder();
        ScanOptions scanOptions = scanOptionsBuilder.count(3).build();

//        ScanOptions scanOptions = new ScanOptions.ScanOptionsBuilder().count(20).build();
        Cursor<String> cursor = setOperations.scan("bs3", scanOptions);

        while (cursor.hasNext()) {
            System.out.println("cursorId " + cursor.getCursorId());
            System.out.println("position" + cursor.getPosition());
            String next = cursor.next();
            System.out.println("next = " + next);
        }


    }


}
