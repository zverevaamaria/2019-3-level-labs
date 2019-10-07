package example.govno3;

import example.govno3.model.Article;
import example.govno3.model.Articles;
import example.govno3.service.SiteParsingService;
import example.govno3.utils.AppProperty;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStructureTest {

    @Autowired
    protected SiteParsingService siteParsingService;

    @Before
    public void init() {
        String url = AppProperty.getProperty("url");
        Document site = siteParsingService.getHtmlPage(url);
        List<Article> articleList = siteParsingService.findArticles(site);
        siteParsingService.publishReport(".", articleList);
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.forceDelete(new File(".", AppProperty.getProperty("fileName")));
    }

    @Test
    public void fileStructureTest() {
        Articles articles = siteParsingService.readJsonFile("./" + AppProperty.getProperty("fileName"));
        String url = articles.getUrl();
        List<Article> articleList = articles.getArticles();
        Assert.assertTrue(url != null && !url.isEmpty());
        Assert.assertTrue(articleList != null && articleList.size() > 0);
        for (Article article : articleList) {
            if (StringUtils.isEmpty(article.getTitle())) {
                fail();
            }
        }
    }
}
