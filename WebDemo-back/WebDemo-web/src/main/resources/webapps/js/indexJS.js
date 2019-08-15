var indexJS = {};

//校验登录信息
indexJS.check = function() {
    var username = $("#username").val().trim();
    var password = $("#password").val().trim();
    if(username == '') {
        openTips(3, "账号不能为空");
        return false;
    }
    if(password == '') {
        openTips(3, "密码不能为空");
        return false;
    }
    password = rsaEncrypt(password);
    var jsonData = {};
    jsonData.username = username;
    jsonData.password = password;
    //发送登录请求
    $.ajax({
        type: "POST",
        url: "/user/login",
        contentType:"application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(jsonData),
        success: function(result) {
            if(result.code == "200") {
                openTips(1, result.message);
                pageToWithTimeout("/user/index", "_self", 200);
            } else {
                openTips(2, result.message);
            }
        },
        error: function() {
            openTips(3, "请求失败，请稍后再试");
        }
    });
};