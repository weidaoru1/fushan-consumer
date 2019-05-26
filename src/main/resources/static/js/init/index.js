$(function() {
    $.ajax({
        url:"login/menuList",
        cache:false,
        type:"GET",
        dataType:"json",
        success:function(result){
            if (result && result.length > 0){
                var ul = "<ul class=\"mainmenu\">";
                for (var i=0; i< result.length;i++){
                    var menuli = result[i];
                    ul += "<li><span>" + menuli.name + "</span></li>";
                    var children = menuli.children;
                    if (children && children.length > 0){
                        ul += "<ul class=\"submenu\"><div class=\"expand-triangle\"></div>";
                        for (var j =0;j < children.length ;j++){
                            var child = children[j];
                            ul += "<li><a onclick=\"menuOpen('"+child.url+"','" + child.name +"','" + i +"')\"><span style=\"margin-left: 50px;\">" + child.name + "</span></a></li>";
                        }
                        ul += "</ul>";
                    }
                }
                ul += "</ul>";
                $("#menuId").append(ul);
            }
        }
    });
    // var $p = $(document);
    // //自定义滚动条
    // $(".bbg,.bgg",$p).each(function(){
    //     var $this = $(this);
    //     $this.slimScroll({
    //         width: 'auto', //可滚动区域宽度
    //         height: '100%', //可滚动区域高度
    //         size: '5px', //组件宽度
    //         color: '#000', //滚动条颜色
    //         position: 'right', //组件位置：left/right
    //         distance: '0px', //组件与侧边之间的距离
    //         start: 'top', //默认滚动位置：top/bottom
    //         opacity: .4, //滚动条透明度
    //         alwaysVisible: true, //是否 始终显示组件
    //         disableFadeOut: false, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
    //         railVisible: true, //是否 显示轨道
    //         railColor: '#333', //轨道颜色
    //         railOpacity: .2, //轨道透明度
    //         railDraggable: true, //是否 滚动条可拖动
    //         // railClass: 'slimScrollRail', //轨道div类名
    //         //  barClass: 'slimScrollBar', //滚动条div类名
    //         //  wrapperClass: 'slimScrollDiv', //外包div类名
    //         allowPageScroll: true, //是否 使用滚轮到达顶端/底端时，滚动窗口
    //         wheelStep: 20, //滚轮滚动量
    //         touchScrollStep: 200, //滚动量当用户使用手势
    //         borderRadius: '7px', //滚动条圆角
    //         railBorderRadius: '7px' //轨道圆角
    //     });
    // });

    // 关闭当前
    function s() {
        var t = $(this).parents(".J_menuTab").data("id"),
            a = $(this).parents(".J_menuTab").width();
        if ($(this).parents(".J_menuTab").hasClass("active")) {
            if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
                var i = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
                $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"), $(".J_mainContent .J_box").each(function() {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                0 > n && $(".page-tabs-content").animate({
                    marginLeft: n + a + "px"
                }, "fast"), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_box").each(function() {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
            if ($(this).parents(".J_menuTab").prev(".J_menuTab").size()) {
                var i = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
                $(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"), $(".J_mainContent .J_box").each(function() {
                    return $(this).data("id") == i ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
                }), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_box").each(function() {
                    return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
                })
            }
        } else $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_box").each(function() {
            return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
        }), e($(".J_menuTab.active"));
        return !1
    }

    // 单击选项卡 切换面板
    function d() {
        if (!$(this).hasClass("active")) {
            var t = $(this).data("id");
            $(".J_mainContent .J_box").each(function() {
                return $(this).data("id") == t ? ($(this).show().siblings(".J_box").hide(), !1) : void 0
            }), $(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this)
        }
    }

    $(".J_menuTabs").on("click", ".J_menuTab i", s);
    $(".J_menuTabs").on("click", ".J_menuTab", d);
});