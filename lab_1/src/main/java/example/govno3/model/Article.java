package example.govno3.model;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {
    private static final long serialVersionUID = 2811122909526792796L;

    private String title;

    public Article() {
    }

    public Article(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article news = (Article) o;
        return Objects.equals(title, news.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "News{" +
                ", title='" + title + '\'' +
                '}';
    }
}

