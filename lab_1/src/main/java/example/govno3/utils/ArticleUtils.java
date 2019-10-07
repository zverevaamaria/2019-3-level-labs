package example.govno3.utils;

import org.jsoup.nodes.Element;

public class ArticleUtils {

    private ArticleUtils() {
    }

    public static String getTitle(Element element) {
        if (element != null) {
            return StringUtils.cleanString(element.childNode(0).toString());
        }
        return "";
    }
}
