package com.ies.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.ies.constast.SysConstast;
import com.ies.domain.User;
import com.ies.service.UserService;
import com.ies.utils.WebUtils;
import com.ies.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin() {
        return "system/main/login";
    }

    @RequestMapping("login")
    public String login(UserVo userVo, Model model) {
        Object obj = WebUtils.getHttpSession().getAttribute("code");
        if (obj == null) {
            return "system/main/login";
        } else {
            String code = obj.toString();
            if (userVo.getCode().equals(code)) {
                User user = userService.login(userVo);
                if (null != user) {
                    WebUtils.getHttpSession().setAttribute("user", user);
                    WebUtils.getHttpSession().setAttribute("userType", user.getType());
                    return "system/main/index";
                } else {
                    model.addAttribute("error", "用户名密码不正确");
                    return "system/main/login";
                }
            } else {
                model.addAttribute("error", "验证码不正确");
                return "system/main/login";
            }
        }
    }

    /**
     * 得到登陆验证码
     *
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 5);
        session.setAttribute("code", lineCaptcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
    }

}
