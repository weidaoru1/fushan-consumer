package com.fushanconsumer.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fushanapi.common.utils.DataDealUtils;
import com.fushanapi.common.utils.DataGrid;
import com.fushanapi.common.utils.UserConstants;
import com.fushanapi.model.cost.PaymentInfo;
import com.fushanapi.model.cost.PaymentRecord;
import com.fushanapi.model.role.RoleInfo;
import com.fushanapi.service.cost.PaymentInfoService;
import com.fushanapi.service.cost.PaymentRecordService;
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
public class PaymentController {
    @Reference
    PaymentInfoService paymentInfoService;
    @Reference
    PaymentRecordService paymentRecordService;
    @Reference
    RoleInfoService roleInfoService;
    @RequestMapping("cost/paymentList")
    public String PaymentList(Model model, HttpServletRequest request, DataGrid dataGrid)throws Exception{
        List<RoleInfo> roleInfoList = roleInfoService.queryByUserId((Integer)request.getSession().getAttribute(UserConstants.LOGIN_USER_ID.name()));
        if (roleInfoList != null && roleInfoList.size() > 0){
            model.addAttribute("role",roleInfoList.get(0));
        }
        model.addAttribute("page",paymentInfoService.pagedQuery(dataGrid));
        return "views/cost/paymentList";
    }
    @GetMapping("cost/paymentRecordList")
    public String paymentRecord(Model model, HttpServletRequest request, DataGrid dataGrid)throws Exception{
        String paymentId = request.getParameter("paymentId");
        Map<String,Object> map = new HashMap<>();
        map.put("paymentId",paymentId);
        model.addAttribute("paymentId",paymentId);
        model.addAttribute("page",paymentRecordService.pagedQueryByCondition(dataGrid,map));
        return "views/cost/paymentRecordList";
    }

    @RequestMapping("cost/paymentAdd")
    public String payment(Model model, HttpServletRequest request)throws Exception{
        return "views/cost/paymentAdd";
    }
    @RequestMapping("cost/paymentEdit")
    public String paymentEdit(Model model, HttpServletRequest request)throws Exception{
        String id = request.getParameter("id");
        PaymentInfo paymentInfo = null;
        if (StringUtils.isNotBlank(id)){
            paymentInfo = paymentInfoService.selectByPrimaryKey(Integer.parseInt(id));
        }
        if(paymentInfo != null){
            model.addAttribute("paymentInfo",paymentInfo);
        }
        return "views/cost/paymentEdit";
    }
    @RequestMapping("payment/save")
    public @ResponseBody String save(HttpServletRequest request, PaymentInfo paymentInfo)throws Exception{
        JSONObject jsonObject = new JSONObject();
        paymentInfoService.insertSelective(paymentInfo);
        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("payment/edit")
    public @ResponseBody String edit(HttpServletRequest request,PaymentInfo paymentInfo)throws Exception{
        JSONObject jsonObject = new JSONObject();
        PaymentInfo oldData = paymentInfoService.selectByPrimaryKey(paymentInfo.getId());
        PaymentRecord paymentRecord = DataDealUtils.getPaymentRecord(paymentInfo,oldData);
        if(paymentRecord != null){
            paymentRecord.setPaymentId(paymentInfo.getId());
            paymentRecord.setUserName((String)request.getSession().getAttribute(UserConstants.LOGIN_USER_NAME.name()));
            paymentRecord.setCreateTime(new Date());
            paymentRecordService.insertSelective(paymentRecord);
        }
        paymentInfoService.updateByPrimaryKey(paymentInfo);
        jsonObject.put("status",1);
        jsonObject.put("msg","保存成功！");
        return jsonObject.toString();
    }
    @RequestMapping("payment/deleteById")
    public  @ResponseBody String deleteById(HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("ids");
        if (StringUtils.isNotBlank(id)) {
            String[] ids = id.split(";");
            for(String s : ids){
                paymentRecordService.deleteByPaymentId(Integer.parseInt(s));
                paymentInfoService.deleteByPrimaryKey(Integer.parseInt(s));
            }
        }
        jsonObject.put("msg","删除成功！");
        return jsonObject.toString();
    }
}
