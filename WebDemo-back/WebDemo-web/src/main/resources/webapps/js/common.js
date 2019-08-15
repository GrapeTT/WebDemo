//检查字符串是否为数字或英文
function isNumberOrEnglish(str) {
    var reg = /^[0-9a-zA-Z]+$/;
    if(reg.test(str)) {
        return true;
    } else {
        return false;
    }
}

//检查字符串长度是否符合要求
function checkStrLength(str, min, max) {
    if(str.length < min || str.length > max) {
        return false;
    }
    return true;
}

//校验邮箱格式是否正确
function checkEmail(email) {
    var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(emailReg.test(email)) {
        return true;
    } else {
        return false;
    }
}

//回车键响应事件，id：按钮id
function enterEvent(id) {
    if(event.keyCode == 13) {
        $("#" + id).click();
    }
}

//RSA加密
function rsaEncrypt(value) {
    var crypt = new JSEncrypt();
    var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDImmbdWBfTN67u7vTLUWGkSUrL\n" +
        "7ANBBmLWvyeYsafIRKNJMU1aSQat5jStLEjGwG8rz94wDy2gSoZCzBw6YEwuH665\n" +
        "m4Oiuk3pjpd98l9bauSxNRVB60K9y20okixpgiarJgOnuGaVK4NMo9gGacsCiw9C\n" +
        "qqJXtjoxq5Z09nJNcwIDAQAB";
    crypt.setPublicKey(publicKey);
    return crypt.encrypt(value);
}

//页面跳转
function pageTo(url, target) {
    //可选。指定target属性或窗口的名称。支持以下值：
    //_blank - URL加载到一个新的窗口。这是默认
    //_parent - URL加载到父框架
    //_self - URL替换当前页面
    //_top - URL替换任何可加载的框架集
    //name - 窗口名称
    window.open(url, target);
}

//页面跳转，可设置延时(毫秒)
function pageToWithTimeout(url, target, timeout) {
    setTimeout(pageTo, timeout, url, target);
}

//刷新问号提示图片
function refreshQuestionTipsImg(target) {
    if(target == 1) {
        $("#question_tips").attr("src", "/resources/icon/tips/question_circle.png");
    }
    if(target == 2) {
        $("#question_tips").attr("src", "/resources/icon/tips/question_circle_light.png");
    }
}

//弹出问号提示框
function openQuestionTips() {
    openTips(4, "若未收到验证码，请去检查邮件垃圾箱");
}

// 打开提示框
function openTips(type, message) {
    var img = '<img src="/resources/icon/tips/';
    if(type == 1) {
        img += 'right_circle';
    } else if(type == 2) {
        img += 'error_circle';
    } else if(type == 3) {
        img += 'warning_circle';
    } else if(type == 4) {
        img += 'info_circle';
    }
    img += '.png">&nbsp;&nbsp;';
    $("#tips").html(img + message);
    $("#tips").show().delay(1000).fadeOut();
}

//绘制进度圆形图
function pieChart(id, percentage, isFinish) {
    var color;
    if(isFinish == 0) {
        color = '#7CF29C';
    } else {
        color = '#4680ff';
    }
    var surplus = 100 - percentage;
    new Chart($("#" + id), {
        type: 'doughnut',
        options: {
            cutoutPercentage: 60,
            legend: {
                display: false
            }
        },
        data: {
            labels: [
                "进行至",
                "还剩余"
            ],
            datasets: [{
                data: [percentage, surplus],
                borderWidth: [0, 0],
                backgroundColor: [
                    color,
                    "#eee",
                ],
                hoverBackgroundColor: [
                    color,
                    "#eee",
                ]
            }]
        }
    });
}

//绘制比例饼图
function ratioPieChart(id, firstData, secondData) {
    var ratioPieChart = new Chart($("#" + id), {
        type: 'pie',
        data: {
            labels: [
                "学生",
                "教师"
            ],
            datasets: [
                {
                    data: [firstData, secondData],
                    borderWidth: 0,
                    backgroundColor: [
                        "#99FFFF",
                        "#33CCCC"
                    ],
                    hoverBackgroundColor: [
                        "#99FFFF",
                        "#33CCCC"
                    ]
                }]
        }
    });
    
    var ratioPieChart = {
        responsive: true
    };
}

//绘制空心圆比例图
function spaceRatioPieChart(id, firstLabel, secondLabel, firstData, secondData, firstColor, secondColor) {
    var spaceRatioPieChart = new Chart($("#" + id), {
        type: 'doughnut',
        options: {
            cutoutPercentage: 80,
        },
        data: {
            labels: [
                firstLabel,
                secondLabel
            ],
            datasets: [
                {
                    data: [firstData, secondData],
                    borderWidth: 0,
                    backgroundColor: [
                        firstColor,
                        secondColor
                    ],
                    hoverBackgroundColor: [
                        firstColor,
                        secondColor
                    ]
                }]
        }
    });
    var spaceRatioPieChart = {
        responsive: true
    };
}

//展示表格（索引列号从0开始）
function showTable(id, first, last) {
    if(first == null) {
        if(last == null) {
            $("#" + id).DataTable({
                "pagingType": "full_numbers",
                "language": {
                    "url": "/js/dataTables.Chinese.lang"
                }
            });
        } else {
            $("#" + id).DataTable({
                "pagingType": "full_numbers",
                "columnDefs": [
                    // 索引第last列，不进行排序
                    {
                        "targets": last,
                        "orderable": false
                    }
                ],
                "language": {
                    "url": "/js/dataTables.Chinese.lang"
                }
            });
        }
    } else {
        if(last == null) {
            $("#" + id).DataTable({
                "pagingType": "full_numbers",
                "columnDefs": [
                    // 索引第first列，不进行排序
                    {
                        "targets": first,
                        "orderable": false
                    }
                ],
                "language": {
                    "url": "/js/dataTables.Chinese.lang"
                }
            });
        } else {
            $("#" + id).DataTable({
                "pagingType": "full_numbers",
                "columnDefs": [
                    // 索引第first列和第last列，不进行排序
                    {
                        "targets": [first, last],
                        "orderable": false
                    }
                ],
                "language": {
                    "url": "/js/dataTables.Chinese.lang"
                }
            });
        }
    }
}