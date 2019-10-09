package example.govno3.controller;

import example.govno3.model.Articles;
import example.govno3.service.SiteParsingService;
import example.govno3.utils.AppProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @Autowired
    protected SiteParsingService siteParsingService;

    @RequestMapping("/")
    public String handleRequest(Model model) {
        Articles articles = siteParsingService.readJsonFile(
                AppProperty.getProperty("pathToSave") + AppProperty.getProperty("fileName"));
        model.addAttribute("news", articles.getArticles());
        model.addAttribute("creationDate", articles.getCreationDate());
        model.addAttribute("url", articles.getUrl());
        return "news";
    }

}