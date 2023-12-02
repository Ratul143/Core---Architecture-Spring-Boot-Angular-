package util;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternUtils {
      public static String match(String line, String pattern, int groupId) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            return emptyStringToNull(matcher.group(groupId));
        }
        return null;
        }

    public static String matchAll(String line, String pattern, String end) {
        return PatternUtils.match(line, pattern + "([\\w\\W]*)" + end, 1);
    }

    public static String spaceRemover(String line) {
        Optional st = Optional.ofNullable(line);
        if(st.isPresent()){
            String[] stArr = line.trim().split("\\s+");
            return Arrays.stream(stArr).collect(Collectors.joining(" "));
        }
        return null;
    }

    private static String emptyStringToNull(String s) {
        return s != null && s.length() == 0 ? null : s;
    }
}
