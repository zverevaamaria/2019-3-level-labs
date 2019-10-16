package example.govno3.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DefaultSiteParsingService {

    public static final String PATH = "src/main/resources/data/articles.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSiteParsingService.class);

    @Nullable
    public Document getHtmlPage(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Could not get the page");
            System.out.println("Cause: " + e.getMessage());
            return null;
        }
    }

    public List<String> findArticles(Document document) {
        List<String> newsList = new ArrayList<String>();
        List<Elements> elementsList = new ArrayList<>();
        elementsList.add(document.select("a[class=item-sport_big__title]"));
        elementsList.add(document.select("span[class=item-sport_medium__title]"));
        elementsList.add(document.select("span[class=item-sport_online__title]"));
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                String m = element.text();
                newsList.add(m);
            }
        }
        return newsList;
    }

    public JSONObject publishReport(String path, String url, List<String> articles) {
        JSONObject obj = new JSONObject();
        obj.put("url", url);
        obj.put("creationDate", new Date().toString());
        JSONArray jsonArticles = new JSONArray();
        jsonArticles.addAll(articles);
        obj.put("articles", jsonArticles);

        try {
            File directory = new File("src/main/resources/data/");
            if (!directory.exists()) {
                directory.mkdir();
            }
            Files.createFile(Paths.get(path));
        } catch (FileAlreadyExistsException ignore) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
