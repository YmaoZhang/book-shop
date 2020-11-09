//全局变量status，属性为注册表合法性的状态
var status={
    userStatus:false,
    pwdStatus:false,
    confirmPwdStatus:false,
    emailStatus:false,
    phoneStatus:false,
    oldPwdStatus:false,
    newPwdStatus:false,
    confirmNewPwdStatus:false
}

//验证登录用户名是否是否已经存在
function checkLoginUser(obj) {
        $.ajax({
            url:contextPath + "/user/checkUserName",
            data:{"username":obj.value},
            method:"post",
            success:function (data) {
                //不存在则显示提醒
                if(data == 101) {
                    $("#userTip").css("display","block");
                } else {
                    $("#userTip").css("display","none");
                }
            }
        })

}


//验证注册用户名是否合法并且是否已经存在
function checkRegUser(obj) {
    var inputName=/^[a-zA-Z][^\u4e00-\u9fa5]*$/;
    //var inputName=/[a-z]*/;
    if(!inputName.test(obj.value)){
        //alert("用户名不符合要求");
        $("#msgRegUser").css("display","block");
        $("#regUserTip").html("用户名不符合要求");
        $("#regUserTip").removeClass("glyphicon glyphicon-ok");
        $("#regUserTip").addClass("alert-danger");
        status.userStatus=false;
      //  $("#reg").attr("disabled",true);

    }else{
    $.ajax({
        url: contextPath + "/user/checkUserName",
        data:{"username":obj.value},
        method:"post",
        success:function (data) {
            $("#msgRegUser").css("display","block");
            if (data == 102) {
                $("#regUserTip").html("用户名已被注册");
                $("#regUserTip").removeClass("glyphicon glyphicon-ok");
                $("#regUserTip").addClass("alert-danger");
                status.userStatus=false;
                //$("#reg").attr("disabled",true);
            } else {
                $("#regUserTip").html(null);
                $("#regUserTip").removeClass("alert-danger");
                $("#regUserTip").addClass("glyphicon glyphicon-ok");
                status.userStatus=true;
            }
        }
    })
    }
}


function checkPwd(obj){
    if(obj.value.length>=6&&obj.value.length<=12)
    {
        $("#msgRegPwd").css("display","block");
        $("#regPwdTip").html(null);
        $("#regPwdTip").removeClass("alert-danger");
        $("#regPwdTip").addClass("glyphicon glyphicon-ok");
        status.pwdStatus=true;
       // $("#reg").attr("disabled",false);
    } else {
        $("#msgRegPwd").css("display","block");
        $("#regPwdTip").html("密码不符合要求");
        $("#regPwdTip").removeClass("glyphicon glyphicon-ok");
        $("#regPwdTip").addClass("alert-danger");
        status.pwdStatus=false;
        //$("#reg").attr("disabled",true);
    }

}

//判断两次密码是否一致
function confirmPwd(obj,pwd){
    //alert(pwd.value);
   if( obj.value==pwd.value)
    {
       /// alert("密码一致");
        $("#msgConfirmPwd").css("display","block");
        $("#confirmPwdTip").html(null);
        $("#confirmPwdTip").removeClass("alert-danger");
        $("#confirmPwdTip").addClass("glyphicon glyphicon-ok");
        status.confirmPwdStatus=true;
       // $("#reg").attr("disabled",false);
    } else {
        $("#msgConfirmPwd").css("display","block");
        $("#confirmPwdTip").html("两次密码不一致");
        $("#confirmPwdTip").removeClass("glyphicon glyphicon-ok");
        $("#confirmPwdTip").addClass("alert-danger");
        status.confirmPwdStatus=false;
       // $("#reg").attr("disabled",true);
    }

}

//邮箱合法性检查
function checkEmail(obj){
    var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if(pattern.test(obj.value))
    {
        $("#msgEmail").css("display","block");
        $("#emailTip").html(null);
        $("#emailTip").removeClass("alert-danger");
        $("#emailTip").addClass("glyphicon glyphicon-ok");
        status.emailStatus=true;
       // $("#reg").attr("disabled",false);
    } else {
        $("#msgEmail").css("display","block");
        $("#emailTip").html("邮箱号不合法");
        $("#emailTip").removeClass("glyphicon glyphicon-ok");
        $("#emailTip").addClass("alert-danger");
        status.emailStatus=false;
      //  $("#reg").attr("disabled",true);
    }

}

//手机号合法性检查
function checkPhone(obj){
    var pattern =  /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
    if(pattern.test(obj.value))
    {
        $("#msgPhone").css("display","block");
        $("#phoneTip").html(null);
        $("#phoneTip").removeClass("alert-danger");
        $("#phoneTip").addClass("glyphicon glyphicon-ok");
        status.phoneStatus=true;
       // $("#reg").attr("disabled",false);
    } else {
        $("#msgPhone").css("display","block");
        $("#phoneTip").html("手机号码不合法");
        $("#phoneTip").removeClass("glyphicon glyphicon-ok");
        $("#phoneTip").addClass("alert-danger");
        status.phoneStatus=false;
      //  $("#reg").attr("disabled",true);
    }

}
//用户注册
function register() {
    if(status.phoneStatus&&status.userStatus&&status.pwdStatus&&status.confirmPwdStatus&&status.emailStatus){
    var datas = $("#regForm").serialize();
    $.ajax({
        url: contextPath + "/user/register",
        data:datas,
        method:"post",
        success:function (data) {
            if(data == 'success'){
                alert("注册成功，请登录！");
                $("#register").modal('hide');
            }
        }
    })}
    else {
        alert("请正确填写后注册")
    }
}

//用户登录
function login() {
    var datas = $("#loginForm").serialize();
    $.ajax({
        url: contextPath + "/user/login",
        data:datas,
        method:"post",
        success:function (data) {
            $("#userTip").css("display","none");
            $("#pwdTip").css("display","none");
            //登陆成功跳转到index首页
            if(data == 100){
                $("#loginModal").modal('hide');
                window.location.href = contextPath + "/book/index";
            }  else if(data == 101) {
                $("#userTip").css("display","block");
            } else {
                $("#pwdTip").css("display","block");
            }
        }
    })
}
function checkOldPwd(obj) {
   $.ajax({
        url: contextPath + "/user/checkOldPwd",
        data:{oldPwd:obj.value},
        method:"post",
        success:function (data){
            if(data==102){
                $("#msgOldPwd").css("display","block");
                $("#oldPwdTip").html(null);
                $("#oldPwdTip").removeClass("alert-danger");
                $("#oldPwdTip").addClass("glyphicon glyphicon-ok");
                status.oldPwdStatus=true;
            }
            else {
                $("#msgOldPwd").css("display","block");
                $("#oldPwdTip").html("旧密码错误");
                $("#oldPwdTip").removeClass("glyphicon glyphicon-ok");
                $("#oldPwdTip").addClass("alert-danger");
                status.oldPwdStatus=false;
            }
        }

   })

}

function checkNewPwd(obj) {
    if(obj.value.length>=6&&obj.value.length<=12)
    {
        $("#msgNewPwd").css("display","block");
        $("#newPwdTip").html(null);
        $("#newPwdTip").removeClass("alert-danger");
        $("#newPwdTip").addClass("glyphicon glyphicon-ok");
        status.newPwdStatus=true;

    } else {
        $("#msgNewPwd").css("display","block");
        $("#newPwdTip").html("新密码不符和要求");
        $("#newPwdTip").removeClass("glyphicon glyphicon-ok");
        $("#newPwdTip").addClass("alert-danger");
        status.newPwdStatus=false;
    }

}
function confirmNewPwd(obj,newPwd) {
    if( obj.value==newPwd.value)
    {
        /// alert("密码一致");
        $("#msgConfirmNewPwd").css("display","block");
        $("#confirmNewPwdTip").html(null);
        $("#confirmNewPwdTip").removeClass("alert-danger");
        $("#confirmNewPwdTip").addClass("glyphicon glyphicon-ok");
        status.confirmNewPwdStatus=true;

    } else {
        $("#msgConfirmNewPwd").css("display","block");
        $("#confirmNewPwdTip").html("两次密码不一致");
        $("#confirmNewPwdTip").removeClass("glyphicon glyphicon-ok");
        $("#confirmNewPwdTip").addClass("alert-danger");
        status.confirmNewPwdStatus=false;
    }
}
function alterPwd(obj) {
    if(status.confirmNewPwdStatus&&status.oldPwdStatus&&status.newPwdStatus) {
        $.ajax({
            url: contextPath + "/user/alter",
            data: {newPwd: obj.value},
            method: "post",
            success: function (data) {
                if (data == 'success') {
                    alert("密码修改成功");
                    $("#alterPwd").modal('hide');
                }
            }
        })
    }else
    {
        alert("请正确填写");
    }

}
