package example.govno3.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.govno3.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

@Repository
public class NewsDAO extends News{

    public static List<News> getNews() throws IOException
    {
        int thcounter = 0;
        String thtittle;
        Document meduzapage = Jsoup.connect("https://meduza.io/")
                .get();
        Elements Tittles = meduzapage.select("span[class = BlockTitle-first]");
       News[] arr = new News[100];
        for (Element Tittle : Tittles)
        {
            for( int i=1; i<100; i++ ){
                arr[i] = new News();
                thcounter += 1;
                thtittle = Tittle.text();
                arr[thcounter] = new News(thcounter, thtittle);
            }
        }
        List<News> list = new ArrayList<News>();
    for(int k=1; k<=thcounter; k++)
    {
        list.add(arr[k]);
    }
            return list;
        }

    }

