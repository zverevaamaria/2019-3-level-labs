package example.govno3;

import example.govno3.model.Article;
import example.govno3.model.Articles;
import example.govno3.service.SiteParsingService;
import example.govno3.utils.AppProperty;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static example.govno3.utils.ArticleUtils.getTitle;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Govno3ApplicationTests {

    @Autowired
    protected SiteParsingService siteParsingService;

    @Test//проверка на то, забирает ли он инфу с нашего урла
    public void getHtmlPageTest() {
        Document doc = siteParsingService.getHtmlPage(AppProperty.getProperty("url"));
        Assert.assertNotNull(doc);
    }

    @Test//тест на то, получиться ли у него забрать инфу с несуществующей страницы, которая похожа на урл
    public void getHtmlPageExpectedNullTest() {
        Document doc = siteParsingService.getHtmlPage("https://url.com");
        Assert.assertNull(doc);
    }

    @Test//тест на то, получиться ли у него забрать инфу с несуществующей страница, которая даже не похожа на урл
    public void getHtmlPageExpectedExceptionTest() {
        try {
            siteParsingService.getHtmlPage("url");
            fail();
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    //тест на то, совпадает ли JSON файл и выводимые нами заголовки
    public void testPageIsValidTest() {
        try (InputStream htmlSteam = this.getClass().getResourceAsStream("/test.html");
             InputStream jsonStream = this.getClass().getResourceAsStream("/articles.json")) {
            String html = IOUtils.toString(htmlSteam, StandardCharsets.UTF_8);
            Assert.assertNotNull(html);

            Document document = Jsoup.parse(html);
            Assert.assertNotNull(document);

            List<String> articlesFromHtml = getArticles(document);
            Assert.assertNotNull(articlesFromHtml);
            Assert.assertFalse(articlesFromHtml.isEmpty());

            Articles articles = siteParsingService.readJsonFile(jsonStream);
            Assert.assertNotNull(articles);

            List<Article> tempArticles = articles.getArticles();
            Assert.assertNotNull(tempArticles);
            Assert.assertFalse(tempArticles.isEmpty());

            List<String> articlesFromJson = tempArticles.stream().map(Article::getTitle).collect(Collectors.toList());
            Assert.assertEquals(articlesFromHtml.size(), articlesFromJson.size());

            Iterator jsonIter = articlesFromJson.iterator();
            Iterator htmlIter = articlesFromHtml.iterator();
            while (jsonIter.hasNext() && htmlIter.hasNext()) {
                if (!jsonIter.next().equals(htmlIter.next())) {
                    fail();
                }
            }
        } catch (IOException e) {
            fail();
        }
    }

    private List<String> getArticles(Document document) {
        List<String> articles = new ArrayList<>();
        Elements elements = document.select("li");
        for (Element element : elements) {
            articles.add(getTitle(element));
        }
        return articles;
    }

}
