package example.govno3.service;

import example.govno3.model.Article;
import example.govno3.model.Articles;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.util.List;

public interface SiteParsingService {

    String NAME = "SiteParsingService";

    Document getHtmlPage(String url);

    List<Article> findArticles(Document document);

    void publishReport(String path, List<Article> articles);

    Articles readJsonFile(String pathToFile);

    Articles readJsonFile(InputStream is);

}
