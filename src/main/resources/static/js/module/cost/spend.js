
$(function () {
    $("#addWindow").click(function () {
        window.location.href="/cost/spendAdd";
    });
    $("#editWindow").click(function () {
        var obj = document.getElementsByName("check");
        var check_val = [];
        var id;
        for (var i = 0; i < obj.length; i++){
            if(obj[i].checked){
                check_val.push(obj[i].value);
                id = obj[i].value;
            }
        }
        if (check_val.length == 0){
            bootbox.alert("请选择编辑的数据记录！");
            return false;
        }else if (check_val.length > 1){
            bootbox.alert("只允许一条数据记录！");
            return false;
        }
        window.location.href="/spend/queryById?id="+id;
    });

    $("#spendSave").click(function () {
        var url = "/spend/save";
        if (form1.id.value != ""){
            url = "/spend/edit";
        }
        if(form1.spendName.value==""){
            form1.spendName.focus();
            return false;
        }
        if(form1.spendMatters.value==""){
            form1.spendMatters.focus();
            return false;
        }
        if(form1.amount.value==""){
            form1.amount.focus();
            return false;
        }
        if(form1.spendTime.value==""){
            form1.spendTime.focus();
            return false;
        }
        var spendTime = new Date(form1.spendTime.value);
        var createTime = form1.createTime.value;
        if (createTime == ""){
            createTime = new Date();
        }
        $.ajax({
            url:url,
            type:"POST",
            data:{
                spendName : form1.spendName.value,
                spendMatters : form1.spendMatters.value,
                amount : form1.amount.value,
                type : form1.type.value,
                spendTime : spendTime,
                remark : form1.remark.value,
                createTime : createTime,
                id : form1.id.value
            },
            dataType:"json",
            success:function(result){
                bootbox.alert(result.msg, function () {
                    window.location.href="/cost/spendList";
                });
            }
        });
    });
    $("#spendClose").click(function () {
        window.location.href="/cost/spendList";
    });
    $("#spendReturn").click(function () {
        window.location.href="/cost/spendList";
    });
    $("#deleteWindow").click(function () {
        if ($("#isSuper").val() != "1"){
            bootbox.alert("您没有删除权限，请联系系统管理员！");
            return;
        }
        var obj = document.getElementsByName("check");
        var check_val = [];
        for (var i = 0; i < obj.length; i++){
            if(obj[i].checked){
                check_val.push(obj[i].value);
            }
        }
        if (check_val.length == 0){
            bootbox.alert("请选择删除的数据记录！")
            return false;
        }
        bootbox.confirm("确定要删除选择的数据！", function(result){
            if(!result){
                return;
            }
            $.ajax({
                url:"/spend/deleteById",
                type:"POST",
                data:{
                    ids : check_val.join(";")
                },
                dataType:"json",
                success:function(result){
                    bootbox.alert(result.msg, function () {
                        location.assign(getRootPath() + location.pathname);
                    });
                }
            });
        });
    });
});

