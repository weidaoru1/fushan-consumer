package com.fushanconsumer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogController {
    @RequestMapping("/log/logList")
    public String logList(Model model, HttpServletRequest request)throws Exception{
        return "views/log/logList";
    }

}
