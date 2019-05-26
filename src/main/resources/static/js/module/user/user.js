
$(function () {
    $("#userDelete").click(function () {
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
                url:"/user/deleteById",
                type:"POST",
                data:{
                    ids : check_val.join(";")
                },
                dataType:"json",
                success:function(result){
                    bootbox.alert(result.msg, function () {
                        $('#myModal').modal('hide');
                        location.assign(getRootPath() + location.pathname);
                    });
                }
            });
        })
    });

    $("#userWindow").click(function () {
        //加载下拉选框
        querySelect();
        $('#userName').val("");
        $('#realName').val("");
        $('#des').val("");
        $('#tel').val("");
        $('#userId').val("");
        $('#password').val("");
        $("#myModalLabel").text("用户信息添加");
        $('#myModal').modal('show');
    });
    $("#userEdit").click(function () {
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
            bootbox.alert("请选择编辑的数据记录！")
            return false;
        }else if (check_val.length > 1){
            bootbox.alert("只允许一条数据记录！")
            return false;
        }
        //加载下拉选框
        querySelect();

        $.ajax({
            url:"/user/queryById",
            type:"POST",
            data:{
                id : id
            },
            dataType:"json",
            success:function(result){
                $('#role').val(result.roleId);
                $('#userName').val(result.userName);
                $('#realName').val(result.realName);
                $('#des').val(result.des);
                $('#tel').val(result.tel);
                $('#password').val(result.password);
                $('#userId').val(result.id);
            }
        });
        $("#myModalLabel").text("用户信息编辑");
        $('#myModal').modal('show');
    });

    $("#userSave").click(function () {
        var userName = $('#userName').val();
        var realName = $('#realName').val();
        var password = $('#password').val();
        var userId = $("#userId").val();
        var des = $("#des").val();
        var tel = $('#tel').val();
        var roleId = $("#role").val();
        if (userName == ""){
            $("#userName").css('llborderColor','#ed3e3e');
            return false;
        }
        if (realName == ""){
            $('#realName').css('borderColor','#ed3e3e');
            return false;
        }
        if (password == ""){
            $('#password').css('borderColor','#ed3e3e');
            return false;
        }
        var pattern = /^[0-9a-zA_Z]+$/;
        var puserName = pattern.test(userName);
        if (!puserName){
            bootbox.alert("请输入正确格式的用户账号!");
        }
        var reg = /^\S{6,16}$/;
        var rpassword = reg.test(password);
        if (!rpassword){
            bootbox.alert("请输入6-16位密码，不能使用空格！");
        }
        var patrn = /^0?1[358]\d{9}$/;
        if (tel != ""){
            var pa = patrn.test(tel);
            if (!pa){
                bootbox.alert("请输入正确的手机号码格式！");
            }
        }
        $.ajax({
            url:"/user/save",
            type:"POST",
            data:{
                userName : userName,
                realName : realName,
                password : password,
                tel : tel,
                id : userId,
                des : des,
                roleId : roleId
            },
            dataType:"json",
            success:function(result){
                bootbox.alert(result.msg, function () {
                    $('#myModal').modal('hide');
                    location.assign(getRootPath() + location.pathname);
                });

            }
        });

    });
    function querySelect() {
        $.ajax({
            url:"/role/roleAll",
            type:"POST",
            dataType:"json",
            success:function(result){
                if (result && result.length > 0){
                    var url = "";
                    for (var i=0; i< result.length;i++){
                        var role = result[i];
                        url += "<option value=\"" + role.id + "\">" + role.roleName +"</option>";
                    }
                    $("#role").empty();
                    $("#role").append(url);
                }
            }
        });
    }
});


