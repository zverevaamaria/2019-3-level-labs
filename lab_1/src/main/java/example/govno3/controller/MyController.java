package example.govno3.controller;

import example.govno3.dao.NewsDAO;
import example.govno3.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    private NewsDAO newsDAO;


    @RequestMapping("/")
    public String handleRequest(Model model) {

        List<News> news = null;
        try {
            news = NewsDAO.getNews();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("news", news);
        return "news";
    }
}