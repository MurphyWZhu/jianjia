$(document).ready(function () {
    $("#search1").click(function () {
        $.get("/subsearchbox", "", function (data, status) {
            $("#maindiv").html(data);
        });
    });
    $("#userselfspacebutton").click(function () {
        $.get("/userselfspace", "", function (data, status) {
            $("#maindiv").html(data);
            $("#updateselfuserinfobutton").click(function () {
                $.get("/subuserinfo", "", function (data, status) {
                    $("#userslefspacemodalmaindiv").html(data);
                    $("#subuserinfosubut").click(function () {
                        $.post("/updateuserinfo", "age=" + $("#validationTooltip03").val() + "&nikename=" + $("#validationTooltip02").val() + "&sex=" + $("#validationTooltip04").val() + "&department=" + $("#validationTooltip05").val() + "&profilepicture=https://aaa.a.png" + "&key=" + $("#validationTooltip06").val() + "&like=" + $("#validationTooltip07").val(), function (data, status) {
                            var rr = $.parseJSON(data);
                            if (status == "success") {
                                if (rr.code == 0) {
                                    //$("#div2").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                                }
                            }
                        });
                    });
                });
            });
        });
    });
    $("#loginbutton").click(function () {
        $.get("/sublogin", "", function (data, status) {
            $("#maindiv").html(data);
            $("#signinbut").click(function () {
                var reg = /^\s*$/;
                //返回值为true表示不是空字符串
                value = $("#username-input-mo")[0].value;
                if (value != null && value != undefined && !reg.test(value)) {
                    $.post("/tosignup", "username=" + $("#username-input-mo")[0].value + "&password=" + $("#password-input-mo")[0].value, function (data, status) {
                        var rr = $.parseJSON(data);
                        if (status == "success") {
                            if (rr.code == 0) {
                                $("#signininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                            }
                            else {
                                $("#signininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                            }
                        }
                    });
                }
                else {
                    $("#signininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>格式错误</div>');
                }
            });
        })
    });
    $("#maindiv").on("click", "#tologinbutton", function () {
        $.post("/tologin", "username=" + $("#username-input")[0].value + "&password=" + $("#password-input")[0].value, function (data, status) {
            var rr = $.parseJSON(data);
            if (status == "success") {
                if (rr.code == 0) {
                    $("#div1").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                    $(window).attr('location', '/index');
                }
                else {
                    $("#div1").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                }
            }
        });


    });
    $("#maindiv").on("click", "#searchboxbutton", function () {
        $.get("/tosearch", "key=" + $("#key").val() + "&sexfilter=" + $("#sexfilter").val() + "&departmentfilter=" + $("#departmentfilter").val(), function (data, status) {
            $("#searchboxmaindiv").html(data);
            $(".userinfodiv").click(function () {
                $.post("/userspace", "username=" + $(this).attr('id'), function (data, status) {
                    $("#maindiv").html(data);
                });
            });
            $('button').click(function () {
                if ($(this).hasClass("aaa")) {
                    $.post("/rmlike", "likeuser=" + $(this)[0].value, function (data, status) {
                        //var rr = $.parseJSON(data);
                    });
                    $(this).text("喜欢");
                    $(this).addClass("bbb btn-danger").removeClass("aaa btn-primary");
                }
                else if ($(this).hasClass("bbb")) {
                    $.post("/tolike", "likeuser=" + $(this)[0].value, function (data, status) {
                        //var rr = $.parseJSON(data);
                    });
                    $(this).text("取消喜欢");
                    $(this).addClass("aaa btn-primary").removeClass("bbb btn-danger");
                }

            });
        });
    });
    $("#usershows").click(function () {
        $.post("/getallusershows", "", function (data, status) {
            $("#maindiv").html(data);
            $(".showcomment-button").click(function () {
                $.post("/getshowcomments", "showid=" + $(this).val(), function (data, status) {
                    $("#shwocomments-modal").html(data);
                    $("#postshowcommentbutton").click(function () {
                        $.post("/postcomment", "showid=" + $(this).val() + "&comment=" + $("#commentdatabox").val(), function (data, status) {
                            if (status == "success") {
                                $('#myModal-showcomment').modal('hide');
                            }
                        });
                    });
                });
            });
        });
    });
    $("#indexpostshowbutton").click(function () {
        $.post("/postshowbox", "", function (data, status) {
            $("#maindiv").html(data);
        });
    });
    $("#maindiv").on("click", ".postshowbutton", function () {
        $.post("/postshow", "showdata=" + $("#showdata").val() + "&ispublic=" + $("#ispublic").is(':checked'), function (data, status) {
            //
        });
    });
});