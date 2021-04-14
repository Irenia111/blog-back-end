package com.irenia.blog.web;


// import com.irenia.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

    @GetMapping("/{id}/{path}")
    public String index(@PathVariable Integer id, @PathVariable String path) {
        // throw new NotFoundException("页面未找到");
        System.out.println("------index------");
        return "index";
    }
}
