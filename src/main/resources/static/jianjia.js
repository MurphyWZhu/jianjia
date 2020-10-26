function tolike(buttonid, username) {
    $.post("/tolike", "likeuser=" + username, function (data, status) {
        var rr = $.parseJSON(data);
        if (rr.code == 0) {
            $("#" + buttonid).text("取消喜欢");
            $("#" + buttonid).addClass("btn-primary").removeClass("btn-danger");
            $("#" + buttonid).attr('id', 'rmlike' + username);

        }
    });
}

function rmlike(buttonid, username) {
    $.post("/rmlike", "likeuser=" + username, function (data, status) {
        var rr = $.parseJSON(data);
        if (rr.code == 0) {
            $("#" + buttonid).text("喜欢");
            $("#" + buttonid).addClass("btn-danger").removeClass("btn-primary");
            $("#" + buttonid).attr('id', 'tolike' + username);

        }
    });
}


$(document).ready(function () {
    $("button[id^='likeshow']").click(function () {
        $.post("/toshowlike", "showid=" + $(this).attr('value'), function (data, status) {
            var rr = $.parseJSON(data);
            if (status == "success") {

                $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');

            }
        })
    });

    $("#tologinbutton").click(function () {
        $.post("/tologin", "username=" + $("#username-input").val() + "&password=" + $("#password-input").val(), function (data, status) {
            var rr = $.parseJSON(data);
            if (status == "success") {
                if (!rr.code) {
                    $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                    $(window).attr('location', 'index');
                }
                else {
                    $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                }
            }
        });
    });

    $("#signinbut").click(function () {
        var reg = /^[\w]+$/;
        var pwdreg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
        //返回值为true表示不是空字符串
        value = $("#username-input-mo")[0].value;
        valuepwd = $("#password-input-mo")[0].value;
        if (reg.test(value) && pwdreg.test(valuepwd)) {
            $.post("/tosignup", "username=" + $("#username-input-mo")[0].value + "&password=" + $("#password-input-mo")[0].value, function (data, status) {
                var rr = $.parseJSON(data);
                if (status == "success") {
                    if (rr.code == 0) {
                        $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                    }
                    else {
                        $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>' + rr.info + '</div>');
                    }
                }
            });
        }
        else {
            $("#maininfobox").append('<div class="alert alert-success alert-dismissible fade show" style="position:relative;top:10px;right:10px"><button type="button" class="close" data-dismiss="alert">&times;</button>格式错误</div>');
        }
    });


    $("#userlistdiv").on("click", "button[id^='tolike']", function () { tolike($(this).attr('id'), $(this).val()) });

    $("#userlistdiv").on("click", "button[id^='rmlike']", function () { rmlike($(this).attr('id'), $(this).val()) });

    $('button').click(function () {
        if ($(this).hasClass("aaa")) {
            $.ajaxSettings.async = false;
            var likerecode;
            $.post("/rmlike", "likeuser=" + $(this)[0].value, function (data, status) {
                var rr = $.parseJSON(data);
                likerecode = rr.code;
            });
            if (likerecode == 0) {
                $(this).text("喜欢");
                $(this).addClass("bbb btn-danger").removeClass("aaa btn-primary");
            }
        }
        else if ($(this).hasClass("bbb")) {
            $.post("/tolike", "likeuser=" + $(this)[0].value, function (data, status) {
                var rr = $.parseJSON(data);
                likerecode = rr.code;
            });
            if (likerecode == 0) {
                $(this).text("取消喜欢");
                $(this).addClass("aaa btn-primary").removeClass("bbb btn-danger");
            }
        }

    });
});