package com.crowdfunding.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author LiMeiyuan
 * @version v1.00
 * @date 2016/5/24 23:19
 * @description: 生成随机数工具类
 */
public class RandomUtil {
    private static List<Character> lowerList = new ArrayList<>(26);
    private static List<Character> upperList = new ArrayList<>(26);
    private static List<Character> numberList = new ArrayList<>(10);

    public enum RandomType {
        LOWER, UPPER, NUMBER, LOWER_UPPER_NUMBER, LOWER_NUMBER, UPPER_NUMBER, LOWER_UPPER
    }

    static {
        for (int index = 0; index < 26; index++) {
            upperList.add((char) (index + 65));
            lowerList.add((char) (index + 97));
        }
        for (int index = 0; index < 10; index++) {
            numberList.add((char) (index + 48));
        }
    }

    private static String genRandomChar(List<Character> keys, int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append(keys.get(random.nextInt(keys.size())));
        }
        return builder.toString();
    }

    /**
     * 生成随机位数的大小写字母加数字组合的随机字符串
     *
     * @param length 要生成字符串的长度
     * @param type   生成的随机数类型（仅数字，仅字母。。。）
     * @return 生成的字符串
     */
    public static String genRandomChar(int length, RandomType type) {
        List<Character> keys;
        if (type == RandomType.NUMBER) {
            keys = numberList;
        } else if (type == RandomType.LOWER) {
            keys = lowerList;
        } else if (type == RandomType.UPPER) {
            keys = upperList;
        } else if (type == RandomType.LOWER_NUMBER) {
            keys = new ArrayList<>(26 + 10);
            keys.addAll(numberList);
            keys.addAll(lowerList);
        } else if (type == RandomType.LOWER_UPPER) {
            keys = new ArrayList<>(26 + 26);
            keys.addAll(upperList);
            keys.addAll(lowerList);
        } else if (type == RandomType.UPPER_NUMBER) {
            keys = new ArrayList<>(26 + 10);
            keys.addAll(numberList);
            keys.addAll(upperList);
        } else {
            keys = new ArrayList<>(26 + 26 + 10);
            keys.addAll(numberList);
            keys.addAll(lowerList);
            keys.addAll(upperList);
        }
        return genRandomChar(keys, length);
    }
}
