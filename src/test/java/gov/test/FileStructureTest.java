package gov.test;

import gov.example.service.SiteParsing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)


@SpringBootTest
public class FileStructureTest {
    private final String RBK_URL = "https://sportrbc.ru/";
    //private final String MATCH = "https://matchtv.ru/";
    //private final String Champ = "https://www.mk.ru/sport/";
    @Autowired
    protected SiteParsing newsParser;

    @Test
    public void fileStructureTest() throws FileNotFoundException {
        Document siterbc = newsParser.getHtmlPage(RBK_URL);
        List<String> articlelist = newsParser.findArticles(siterbc);
        Assert.assertTrue(RBK_URL != null && !RBK_URL.isEmpty());
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:\\Practice\\A\\2019-3-level-labs\\lab_2\\src\\main\\resources\\data\\articles.json"));
            JSONObject jsonObject = (JSONObject) obj;

            Date thisdate = (Date) jsonObject.get("creationDate");
            Assert.assertNotNull(thisdate);

            String thisurl = (String) jsonObject.get("url");
            Assert.assertNotNull(thisurl);

            JSONArray tittles = (JSONArray) jsonObject.get("articles");
            Assert.assertTrue(tittles != null && tittles.length() > 0);
            for (int i = 0; i < tittles.length(); i++) {
                String art = tittles.getString(i);
                if (art.isEmpty()) {
                    fail();
                }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }}
