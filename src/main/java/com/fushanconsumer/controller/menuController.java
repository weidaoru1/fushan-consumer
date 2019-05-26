package com.fushanconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fushanapi.common.utils.DataGrid;
import com.fushanapi.model.menu.MenuInfo;
import com.fushanapi.service.menu.MenuInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class menuController {
    @Reference
    MenuInfoService menuInfoService;
    @RequestMapping("/menu/menuList")
    public String menuList(Model model, HttpServletRequest request, DataGrid dataGrid)throws Exception{
        model.addAttribute("page",menuInfoService.pagedQuery(dataGrid));
        return "views/menu/menuList";
    }
    @RequestMapping("menu/menuAdd")
        public String menuAdd(Model model, HttpServletRequest request, MenuInfo menuInfo)throws Exception{
        System.out.println("。。。。");
        return "views/menu/menuAdd";
    }
}
