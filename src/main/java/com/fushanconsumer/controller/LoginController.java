package com.fushanconsumer.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fushanapi.common.utils.DataGrid;
import com.fushanapi.common.utils.UserConstants;
import com.fushanapi.model.role.RoleInfo;
import com.fushanapi.model.role.RoleUser;
import com.fushanapi.model.user.UserInfo;
import com.fushanapi.service.role.RoleInfoService;
import com.fushanapi.service.role.RoleUserService;
import com.fushanapi.service.user.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {
    //引用远程dubbo服务
    @Reference
    UserInfoService userInfoService;
    @Reference
    RoleUserService roleUserService;
    @Reference
    RoleInfoService roleInfoService;
    @RequestMapping("/user/userList")
    public String userList(Model model, HttpServletRequest request, DataGrid dataGrid)throws Exception{
        model.addAttribute("page",userInfoService.pagedQuery(dataGrid));
        return "views/user/userList";
    }

    @RequestMapping("/")
    public String loginNew(Model model, HttpServletRequest request)throws Exception{
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request)throws Exception{
        return "login";
    }
    @RequestMapping("user/queryById")
    public  @ResponseBody String queryById(HttpServletRequest request, UserInfo userInfo) throws Exception{
        JSONObject object = new JSONObject();
        UserInfo user = userInfoService.selectByPrimaryKey(userInfo.getId());
        if (user != null){
            object.put("id",user.getId());
            object.put("userName",user.getUserName());
            object.put("realName",user.getRealName());
            object.put("password",user.getPassword());
            object.put("tel",user.getTel());
            object.put("des",user.getDes());
        }
        List<RoleInfo> list =  roleInfoService.queryByUserId(userInfo.getId());
        if (list != null && list.size() > 0){
            object.put("roleId",list.get(0).getId());
            object.put("roleName",list.get(0).getRoleName());
        }
        return object.toString();
    }
    @RequestMapping("user/deleteById")
    public  @ResponseBody String deleteById(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("ids");
        if (StringUtils.isNotBlank(id)){
            String[] ids = id.split(";");
            for (String s : ids){
                roleUserService.deleteByUserId(Integer.parseInt(s));
                userInfoService.deleteByPrimaryKey(Integer.valueOf(s));
            }
        }
        jsonObject.put("msg","删除成功！");
        return jsonObject.toString();
    }
    @RequestMapping("user/save")
    public @ResponseBody String save(HttpServletRequest request, UserInfo userInfo)throws Exception{
        String roleId = request.getParameter("roleId");
        JSONObject jsonObject = new JSONObject();
        UserInfo u = new UserInfo();
        u.setUserName(userInfo.getUserName());
        List<UserInfo> user = userInfoService.queryList(u);
        if (user != null && user.size() > 0 && userInfo.getId()  == null){
            jsonObject.put("msg","该用户账号已存在！");
            jsonObject.put("status",0);
            return jsonObject.toString();
        }
        //md5加密
//        userInfo.setPassword(MD5utils.encrypt(userInfo.getPassword()));
        if (userInfo.getId() != null){
            userInfoService.updateByPrimaryKey(userInfo);
        }else{
            userInfo = userInfoService.insertSelective(userInfo);
        }
        if (StringUtils.isNotBlank(roleId) && userInfo != null){
            roleUserService.deleteByUserId(userInfo.getId());
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(Integer.parseInt(roleId));
            roleUser.setUserId(userInfo.getId());
            roleUserService.insertSelective(roleUser);
        }

        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("/login/check")
    public @ResponseBody String check(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UserInfo userInfo = userInfoService.userCheck(userName,password);
        if (userInfo == null){
            jsonObject.put("status",0);
            jsonObject.put("success",false);
            return jsonObject.toString();
        }
        request.getSession().setAttribute(UserConstants.LOGIN_USER.name(),userInfo);
        request.getSession().setAttribute(UserConstants.LOGIN_USER_NAME.name(),userInfo.getUserName());
        request.getSession().setAttribute(UserConstants.LOGIN_REAL_NAME.name(),userInfo.getRealName());
        request.getSession().setAttribute(UserConstants.LOGIN_USER_ID.name(),userInfo.getId());
        jsonObject.put("status",1);
        jsonObject.put("success",true);
        return jsonObject.toString();
    }
    @RequestMapping("/login/logout")
    public String logout(){
        return "redirect:/login";
    }
}
