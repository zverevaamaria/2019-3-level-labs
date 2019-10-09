package example.govno3.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import example.govno3.model.Article;
import example.govno3.model.Articles;
import example.govno3.service.SiteParsingService;
import example.govno3.utils.AppProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static example.govno3.utils.ArticleUtils.getTitle;

@Service(SiteParsingService.NAME)
public class DefaultSiteParsingService implements SiteParsingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSiteParsingService.class);

    @Override
    @Nullable
    public Document getHtmlPage(String url) {
        Preconditions.checkArgument(url != null, "Url cannot be null");
        try {
            Document document = Jsoup.connect(url).get();
            document.charset(StandardCharsets.UTF_16);
            return document;
        } catch (IOException e) {
            LOGGER.warn("Error while parsing url: '{}'", url);
            return null;
        }
    }

    @Override
    public List<Article> findArticles(Document document) {
        List<Article> newsList = new ArrayList<>();
        List<Elements> elementsList = new ArrayList<>();
        elementsList.add(document.select("a[class=item-sport_big__title]"));
        elementsList.add(document.select("span[class=item-sport_medium__title]"));
        elementsList.add(document.select("span[class=item-sport_online__title]"));
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                newsList.add(new Article(getTitle(element)));
            }
        }
        return newsList;
    }

    @Override
    public void publishReport(String path, List<Article> articleList) {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(path, AppProperty.getProperty("fileName"));
        try {
            String date = new SimpleDateFormat("YYYY.MM.dd 'at' HH:mm").format(new Date());
            Articles articles = new Articles(AppProperty.getProperty("url"), date, articleList);
            mapper.writeValue(file, articles);
        } catch (IOException e) {
            LOGGER.error("Error while writing json");
        }
    }

    @Override
    @Nullable
    public Articles readJsonFile(String pathToFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(pathToFile), Articles.class);
        } catch (IOException e) {
            LOGGER.error("Cannot find or read json file");
        }
        return null;
    }

    @Override
    @Nullable
    public Articles readJsonFile(InputStream is) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(is, Articles.class);
        } catch (IOException e) {
            LOGGER.error("Cannot find or read json file");
        }
        return null;
    }
}
