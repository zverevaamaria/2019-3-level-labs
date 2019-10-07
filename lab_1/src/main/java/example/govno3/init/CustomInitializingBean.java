package example.govno3.init;

import example.govno3.model.Article;
import example.govno3.service.SiteParsingService;
import example.govno3.utils.AppProperty;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomInitializingBean implements InitializingBean {

    @Autowired
    protected SiteParsingService siteParsingService;

    @Override
    public void afterPropertiesSet() {
        String url = AppProperty.getProperty("url");
        Document site = siteParsingService.getHtmlPage(url);
        List<Article> articleList = siteParsingService.findArticles(site);
        siteParsingService.publishReport(AppProperty.getProperty("pathToSave"), articleList);
    }
}
