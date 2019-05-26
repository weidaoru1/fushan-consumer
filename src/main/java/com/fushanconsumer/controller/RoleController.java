package com.fushanconsumer.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fushanapi.service.role.RoleInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RoleController {
    @Reference
    RoleInfoService roleInfoService;
    @RequestMapping("role/roleList")
    public String roleList(Model model, HttpServletRequest request){
        return "views/role/roleList";
    }
    @RequestMapping("role/roleAll")
    public @ResponseBody Object roleAll(){
        return roleInfoService.queryListAll();
    }
}
