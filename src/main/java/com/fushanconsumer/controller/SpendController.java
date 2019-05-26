package com.fushanconsumer.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fushanapi.common.utils.DataDealUtils;
import com.fushanapi.common.utils.DataGrid;
import com.fushanapi.common.utils.UserConstants;
import com.fushanapi.model.cost.SpendInfo;
import com.fushanapi.model.cost.SpendRecord;
import com.fushanapi.model.role.RoleInfo;
import com.fushanapi.service.cost.SpendInfoService;
import com.fushanapi.service.cost.SpendRecordService;
import com.fushanapi.service.role.RoleInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SpendController {
    @Reference
    RoleInfoService roleInfoService;
    @Reference
    SpendInfoService spendInfoService;
    @Reference
    SpendRecordService spendRecordService;
    @RequestMapping("cost/spendList")
    public String spendList(Model model, HttpServletRequest request, DataGrid dataGrid) throws Exception {
        List<RoleInfo> roleInfoList = roleInfoService.queryByUserId((Integer) request.getSession().getAttribute(UserConstants.LOGIN_USER_ID.name()));
        if (roleInfoList != null && roleInfoList.size() > 0) {
            model.addAttribute("role", roleInfoList.get(0));
        }
        model.addAttribute("page", spendInfoService.pagedQuery(dataGrid));
        return "views/cost/spendList";
    }
    @RequestMapping("cost/spendAdd")
    public String spendAdd(Model model, HttpServletRequest request, DataGrid dataGrid) throws Exception {
        return "views/cost/spendAdd";
    }
    @RequestMapping("spend/save")
    public @ResponseBody String save(HttpServletRequest request, SpendInfo spendInfo)throws Exception{
        JSONObject jsonObject = new JSONObject();
        spendInfoService.insertSelective(spendInfo);
        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("spend/queryById")
    public String queryById(Model model, HttpServletRequest request)throws Exception{
        String id = request.getParameter("id");
        SpendInfo spendInfo = null;
        if (StringUtils.isNotBlank(id)){
            spendInfo = spendInfoService.selectByPrimaryKey(Integer.parseInt(id));
        }
        if (spendInfo != null){
            model.addAttribute("spendInfo",spendInfo);
        }
        return "views/cost/spendEdit";
    }
    @GetMapping("spend/spendRecordList")
    public String querySpendRecord(Model model, HttpServletRequest request,DataGrid dataGrid)throws Exception{
        String spendId = request.getParameter("spendId");
        Map<String,Object> map = new HashMap<>();
        map.put("spendId",spendId);
        model.addAttribute("spendId",spendId);
        model.addAttribute("page",spendRecordService.pagedQueryByCondition(dataGrid,map));
        return "views/cost/spendRecordList";
    }

    @RequestMapping("spend/edit")
    public @ResponseBody String edit(HttpServletRequest request,SpendInfo spendInfo)throws Exception{
        JSONObject jsonObject = new JSONObject();
        SpendInfo oldData = spendInfoService.selectByPrimaryKey(spendInfo.getId());
        SpendRecord spendRecord = DataDealUtils.getSpendRecord(spendInfo,oldData);
        if (spendRecord != null){
            spendRecord.setSpendId(spendInfo.getId());
            spendRecord.setUserName((String)request.getSession().getAttribute(UserConstants.LOGIN_USER_NAME.name()));
            spendRecord.setCreateTime(new Date());
            spendRecordService.insertSelective(spendRecord);
        }
        spendInfoService.updateByPrimaryKey(spendInfo);
        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("spend/deleteById")
    public  @ResponseBody String deleteById(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("ids");
        if (StringUtils.isNotBlank(id)) {
            String[] ids = id.split(";");
            for(String s : ids){
                spendRecordService.deleteBySpendId(Integer.parseInt(s));
                spendInfoService.deleteByPrimaryKey(Integer.parseInt(s));
            }
        }
        jsonObject.put("msg","删除成功！");
        return jsonObject.toString();
    }
}
