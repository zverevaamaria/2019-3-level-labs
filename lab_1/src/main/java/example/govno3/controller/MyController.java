package example.govno3.controller;

import example.govno3.service.impl.DefaultSiteParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyController {
    public static final String url = "https://sportrbc.ru/";
    @Autowired
    protected DefaultSiteParsingService siteParsingService;

    @RequestMapping(value = "/news", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getNews() {
        ModelAndView modelAndView = new ModelAndView("Page");
        List<String> articles = siteParsingService.findArticles(siteParsingService.getHtmlPage(url));
        siteParsingService.publishReport(siteParsingService.PATH, url, articles);
        modelAndView.addObject("articles", articles);
        return modelAndView;
    }
}