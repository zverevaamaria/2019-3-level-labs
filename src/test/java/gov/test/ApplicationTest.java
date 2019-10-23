package gov.test;

import gov.example.service.SiteParsing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;
import static org.junit.Assert.fail;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    private final String RBK_URL = "https://sportrbc.ru/";

    @Autowired
    protected SiteParsing newsParser;

    public ApplicationTest() throws FileNotFoundException {
    }

    @Test//проверка на то, забирает ли он инфу с нашего урла
    public void getHtmlPageTest() {
        Document doc = newsParser.getHtmlPage(RBK_URL);
        Assert.assertNotNull(doc);
    }
    @Test//тест на то, получиться ли у него забрать инфу с несуществующей страницы, которая похожа на урл
    public void getHtmlPageExpectedNullTest() {
        Document doc = newsParser.getHtmlPage("https://url.com");
        Assert.assertNull(doc);
    }
    @Test//тест на то, получиться ли у него забрать инфу с несуществующей страница, которая даже не похожа на урл
    public void getHtmlPageExpectedExceptionTest() {
        try {
            newsParser.getHtmlPage("url");
            fail();
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
    @Test
    //тест на то, совпадает ли JSON файл и выводимые нами заголовки
    public void testPageIsValidTest() throws FileNotFoundException {
        //Считывание с JSON
        JSONParser parser = new JSONParser();
        List<String> newsListFromjson = new ArrayList<String>();
    try{
        Object obj = parser.parse(new FileReader("C:\\Practice\\A\\2019-3-level-labs\\lab_2\\src\\test\\resources\\static\\articles.json"));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray tittles = (JSONArray) jsonObject.get("articles");
        for (int i = 0; i < tittles.length(); i++) {
            newsListFromjson.add(tittles.getString(i));
        }} catch (ParseException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }

    //Считывание с HTML
        FileReader fr = new FileReader("C:\\Practice\\A\\2019-3-level-labs\\lab_2\\src\\test\\resources\\static\\fromthepage.html");
        File file = new File(valueOf(fr));
        Document doc = new Document(valueOf(file));
        List<String> newsListFromHtml= getArticles(doc);
    //Сравнение HTML и JSON
Assert.assertEquals(newsListFromHtml.size(), newsListFromjson.size());
Assert.assertArrayEquals(new List[]{newsListFromHtml}, new List[]{newsListFromjson});
 }
 private List<String> getArticles(Document document) {
        List<String> articles = new ArrayList<>();
        Elements elements = document.select("li");
        for (Element element : elements) {
            articles.add(element.text());
        }
        return articles;
    }
}