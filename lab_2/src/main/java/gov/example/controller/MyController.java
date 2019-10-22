package gov.example.controller;

import gov.example.service.SiteParsing;
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
    private final String MATCH = "https://matchtv.ru/";
    private final String Champ = "https://www.mk.ru/sport/";


    @Autowired
    private SiteParsing newsParser;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNewsRBC() {
        ModelAndView modelAndView = new ModelAndView("news");
        String cd = newsParser.Date();
        List<String> articles = newsParser.findArticles(newsParser.getHtmlPage(RBK_URL));
        newsParser.publishReport(SiteParsing.PATH, RBK_URL, articles, cd );
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("url", RBK_URL);
        modelAndView.addObject("date", cd);
        return modelAndView;
    }
    @RequestMapping(value = "/match", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNewsMatch() {
        ModelAndView modelAndView = new ModelAndView("news");
        String cd = newsParser.Date();
        List<String> articles = newsParser.findArticles(newsParser.getHtmlPage(MATCH));
        newsParser.publishReport(SiteParsing.PATH, MATCH, articles, cd );
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("url", MATCH);
        modelAndView.addObject("date", cd);
        return modelAndView;
    }
    @RequestMapping(value = "/ramb", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNewsTASS() {
        ModelAndView modelAndView = new ModelAndView("news");
        String cd = newsParser.Date();
        List<String> articles = newsParser.findArticles(newsParser.getHtmlPage(Champ));
        newsParser.publishReport(SiteParsing.PATH, Champ, articles, cd );
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("url", Champ);
        modelAndView.addObject("date", cd);
        return modelAndView;
    }
}