var registerJS = {};

//标记是否已获取验证码
var isGetValidateCode = 0;

//用于保存用户的邮箱
var userEamil = "";

//获取验证码
registerJS.getValidateCode = function () {
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
        url: "/user/getValidateCode",
        dataType: "json",
        data: data,
        success: function(result) {
            if(result.code == "200") {
                isGetValidateCode = 1;
                userEamil = email;
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

//注册用户
registerJS.register = function () {
    if(isGetValidateCode == 0) {
        openTips(3, "请先获取验证码");
        return false;
    }
    var email = $("#email").val().trim();
    //验证邮箱是否与获取验证码时填写的信息一致
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
    var password = $("#password").val().trim();
    if(password == "") {
        openTips(3, "密码不能为空");
        return false;
    }
    if(!checkStrLength(password, 6, 20)) {
        openTips(3, "密码长度不正确");
        return false;
    }
    var confirmPassword = $("#confirmPassword").val().trim();
    if(confirmPassword != password) {
        openTips(3, "确认密码与密码不一致");
        return false;
    }
    password = rsaEncrypt(password);
    var data = {};
    data.username = email;
    data.password = password;
    data.userValidateCode = userValidateCode;
    //发送请求
    $.ajax({
        type: "POST",
        url: "/user/register",
        dataType: "json",
        data: data,
        success: function(result) {
            if(result.code == "200") {
                openTips(1, "注册成功，请登录");
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