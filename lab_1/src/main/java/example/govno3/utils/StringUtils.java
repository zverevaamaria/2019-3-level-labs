package example.govno3.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String cleanString(String input) {
        return input.replace("&nbsp;", " ");
    }
}
