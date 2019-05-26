function menuClick() {
    var $submenu = $('.submenu');
    var $mainmenu = $('.mainmenu');
    $submenu.hide();
    $submenu.first().delay(400).slideDown(700);
    $submenu.on('click','li', function() {
        $submenu.siblings().find('li').removeClass('chosen');
        $(this).addClass('chosen');
    });
    $mainmenu.on('click', 'li', function() {
        $(this).next('.submenu').slideToggle().siblings('.submenu').slideUp();
    });
    $mainmenu.children('li:last-child').on('click', function() {
        $mainmenu.fadeOut().delay(500).fadeIn();
    });
}
function menuOpen(url,name,index) {
    if(void 0 == url){
        return false;
    }
    var isopen = false;
    $(".J_menuTab").each(function(){
        if($(this).data("id") == url ){
            isopen = true;
            // 已打开面板
            if(!$(this).hasClass("active")){
                $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
                e(this);// 初始化位置
                $(".J_mainContent .J_box").each(function(){
                    var $this = $(this);
                    if($this.data("id") == url){
                        $this.show().siblings(".J_box").hide();
                    }
                });
            }
        }
    });
    if(!isopen){
        var s = '<a href="javascript:;" style=\"float: left\" class="active J_menuTab smartmenu" data-id="' + url + '">' + name + ' <i class="fa fa-times-circle"></i></a>';
        $(".J_menuTab").removeClass("active");
        $(".J_menuTabs .page-tabs-content").append(s);
        e($(".J_menuTab.active"));
        var r = "<div class=\"J_box smartmenu index\"  name=\"iframe0\" data-id=\""+ url+"\" style=\"height: 100%;\">";
        r += " <iframe id=\"iframepage"+index+"\" src=\""+url+"\" width=\"100%\" height=\"100%\"></iframe></div>";
        $(".J_mainContent").find("div.J_box").hide().parents(".J_mainContent").append(r);
    }
    return !1;
}
function t(t) {
    var e = 0;
    return $(t).each(function() {
        e += $(this).outerWidth(!0)
    }), e
}
function e(e) {
    var a = t($(e).prevAll()),
        i = t($(e).nextAll()),
        n = t($(".content-tabs").children().not(".J_menuTabs")),
        s = $(".content-tabs").outerWidth(!0)-n,
        r = 0;
    if ($(".page-tabs-content").outerWidth() < s) r = 0;
    else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
        if (s - $(e).next().outerWidth(!0) > i) {
            r = a;
            for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;) r -= $(o).prev().outerWidth(), o = $(o).prev()
        }
    } else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
    $(".page-tabs-content").animate({
        // marginLeft: 0 - r + "px"
        marginLeft: 20 + "px"
    }, "fast")
}

