var retrievePasswordJS = {};

//标记是否已获取验证码
var isGetValidateCode = 0;

//用于保存需要找回密码的用户的邮箱
var userEamil = "";

//用于保存需要找回密码的用户的UID
var userUID = "";

//获取验证码
retrievePasswordJS.getValidateCode = function () {
    var email = $("#email").val().trim();
    if(email == '') {
        openTips(3, "邮箱不能为空");
        return false;
    }
    if(!checkEmail(email)) {
        openTips(3, "邮箱格式错误");
        return false;
    }
    var data = {};
    data.email = email;
    //发送获取验证码请求
    $.ajax({
        type: "POST",
        url: "/password/getValidateCode",
        dataType: "json",
        data: data,
        success: function(result) {
            if(result.code == "200") {
                isGetValidateCode = 1;
                userEamil = email;
                userUID = result.data;
                openTips(1, result.message);
            } else {
                openTips(3, result.message);
            }
        },
        error: function() {
            openTips(3, "请求失败，请稍后再试");
        }
    });
};

//重置密码
retrievePasswordJS.resetPassword = function () {
    if(isGetValidateCode == 0) {
        openTips(3, "请先获取验证码");
        return false;
    }
    var email = $("#email").val().trim();
    //验证信息是否与获取验证码时填写的信息一致
    if(userEamil != email) {
        openTips(3, "邮箱与获取验证码时填写的不一致");
        return false;
    }
    var userValidateCode = $("#userValidateCode").val().trim();
    if(userValidateCode == '') {
        openTips(3, "验证码不能为空");
        return false;
    }
    if(userValidateCode.length != 4 || isNaN(userValidateCode)) {
        openTips(3, "验证码错误");
        return false;
    }
    var newPassword = $("#newPassword").val().trim();
    if(newPassword == '') {
        openTips(3, "新密码不能为空");
        return false;
    }
    if(!checkStrLength(newPassword, 6, 20)) {
        openTips(3, "新密码长度不正确");
        return false;
    }
    var confirmPassword = $("#confirmPassword").val().trim();
    if(confirmPassword == '') {
        openTips(3, "确认密码不能为空");
        return false;
    }
    if(confirmPassword != newPassword) {
        openTips(3, "确认密码与新密码不一致");
        return false;
    }
    newPassword = rsaEncrypt(newPassword);
    var data = {};
    data.email = email;
    data.userValidateCode = userValidateCode;
    data.uid = userUID;
    data.newPassword = newPassword;
    //发送重置密码请求
    $.ajax({
        type: "POST",
        url: "/password/reset",
        dataType: "json",
        data: data,
        success: function(result) {
            if(result.code == "200") {
                openTips(1, result.message);
                // 跳转登录页面
                pageToWithTimeout("/", "_self", 200);
            } else {
                openTips(3, result.message);
            }
        },
        error: function() {
            openTips(3, "请求失败，请稍后再试");
        }
    });
};