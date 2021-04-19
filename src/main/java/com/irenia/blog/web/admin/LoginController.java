package com.irenia.blog.web.admin;

import com.irenia.blog.prototype.User;
import com.irenia.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;//service 注入

    @GetMapping
    public String loginPage() {
        return "admin/admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        //使用service实现用户的查询
        User user = userService.checkUsers(username, password);

        if(user != null) {
            user.setPassword(null);//在session中不传入密码
            session.setAttribute("user", user);
            return "/admin/admin-successLogin";
        } else {
            attributes.addAttribute("message", "用户名或密码错误");
            return "redirect:/admin";//通过重定向方式回到登录页面，路由和文件url是不相同的
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");//注销的操作，删除session中的user
        return "redirect:/admin";
    }

}
