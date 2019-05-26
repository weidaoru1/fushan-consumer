
$(function () {
    $('#submit').submit(function(){
        submitFun();
        return false;
    });
});
function keyLogin(){
    var e = event ? event :(window.event ? window.event : null);
    if(e.keyCode==13){
        submitFun();
    }
}

function submitFun(){
    var userName = $('#login .white form input.userName').val();
    var password = $('#login .white form input.password').val();
    var kong=/^\s*$/;
    var kongName = kong.test(userName);
    if(kongName){
        $('#login .white form p.prompt_username').html('请输入用户账号');
        $('#login .white form input.userName').css('borderColor','#ed3e3e');
        return false;
    }else if(password==""){
        $('#login .white form p.prompt_pass').html('请输入密码');
        $('#login .white form input.password').css('borderColor','#ed3e3e');
        return false;
    }

    var pattern = /^[0-9a-zA_Z]+$/;
    var puserName = pattern.test(userName);
    if(puserName){
        $('#login .white form p.prompt_username').html('');
    }else{
        $('#login .white form p.prompt_username').html('请输入正确格式的用户账号');
        $('#login .white form input.userName').css('borderColor','#ed3e3e');
        return false;
    }

    var reg = /^\S{6,16}$/;
    var rpassword = reg.test(password);
    if(rpassword){
        $('#login .white form p.prompt_pass').html("");
    }else{
        $('#login .white form p.prompt_pass').html("请输入6-16位密码，不能使用空格！");
        $('#login .white form input.password').css('borderColor','#ed3e3e');
        return false;
    }
    $('.login_submit').attr('value',"登录中...");
    $('.login_submit').attr('disabled',true);
    $.ajax({
        url:"login/check",
        type:"POST",
        data:{
            userName : userName,
            password : password
        },
        dataType:"json",
        success:function(result){
            if(result.status == 1){
                //登录成功
                window.location.href="index";
            }else{
                $('.login_submit').attr('disabled',false);
                $('.login_submit').attr('value',"登录");
                $('#login .white form p.prompt_username').html("用户账号或者密码错误！");
            }
        },
        error:function(){
        }
    });
    return false;
}



$("input").focus(function(){
    $(this).css('borderColor','#43BC60');
    $(this).parent('div').next('p').html('');
});
/*$("input").blur(function(){
	$(this).css('borderColor','#e1e1e1');
});	*/

