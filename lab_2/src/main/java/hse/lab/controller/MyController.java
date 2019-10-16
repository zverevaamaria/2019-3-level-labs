package hse.lab.controller;

import hse.lab.service.SiteParsing;
import hse.lab.service.SiteParsing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyController {

    private final String RBK_URL = "https://sportrbc.ru/";

    @Autowired
    private SiteParsing newsParser;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNews() {
        ModelAndView modelAndView = new ModelAndView("news");
        String cd = newsParser.Date();
        List<String> articles = newsParser.findArticles(newsParser.getHtmlPage(RBK_URL));
        newsParser.publishReport(SiteParsing.PATH, RBK_URL, articles, cd );
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("url", RBK_URL);
        modelAndView.addObject("date", cd);
        return modelAndView;
    }
}