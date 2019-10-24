package gov.example.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Map<String, Object> model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); // получили код ошибки
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString()); // превратили код ошибки в число
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.put("error", "Ошибочка " + statusCode + " . Страничка не найдена!");

            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}