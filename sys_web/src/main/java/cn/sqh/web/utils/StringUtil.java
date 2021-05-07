package cn.sqh.web.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public final class StringUtil {
    private StringUtil() {
    }

    private static String key = "and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+";
    private static Set<String> notAllowedKeyWords = new HashSet<String>(0);
    private static String replacedString = "INVALID";

    static {
        String[] keyStr = key.split("\\|");
        for (String str : keyStr) {
            notAllowedKeyWords.add(str);
        }
    }

    public static boolean isFileTypeCorrect(String fileName, String...suffixes) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return Arrays.stream(suffixes).anyMatch(str -> str.equals(suffix));//这里用Java8的stream流的接口加快匹配
    }

    /**
     * 防xss攻击
     */
    public static String cleanXSS(String valueBefore) {
        String valueAfter = valueBefore.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        valueAfter = valueAfter.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        valueAfter = valueAfter.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        valueAfter = valueAfter.replaceAll("'", "& #39;");
        valueAfter = valueAfter.replaceAll("eval\\((.*)\\)", "");
        valueAfter = valueAfter.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        valueAfter = valueAfter.replaceAll("script", "");
        return valueAfter;
    }

    public static void main(String[] args) {
        String fileName="hello.txt";
        final boolean flag = isFileTypeCorrect(fileName, "txt","doc","docx");
        System.out.println(flag);
    }

}
