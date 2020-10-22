$(document).ready(function () {
    $("a[id^='likeshow']").click(function(){
        $.post("/toshowlike","showid="+$(this).attr('value'),function(data,status){
            var rr = $.parseJSON(data);
            if(status=="success"){
                if(rr.code){
                    $(window).attr('location', 'index');
                }
            }
        })
    });

    $("#tologinbutton").click(function(){
        $.post("/tologin","username="+$("#username-input").val()+"&password="+$("#password-input").val(),function(data,status){
            var rr = $.parseJSON(data);
            if(status=="success"){
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
});