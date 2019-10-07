package example.govno3.model;

import java.io.Serializable;
import java.util.List;

public class Articles implements Serializable {
    private static final long serialVersionUID = 2126083097270421917L;

    private String url;
    private String creationDate;
    private List<Article> articles;

    public Articles() {
    }

    public Articles(String url, String creationDate, List<Article> articles) {
        this.url = url;
        this.creationDate = creationDate;
        this.articles = articles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
