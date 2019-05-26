
$(function () {
    $("#resources_meny>li").click(function(){
        var rul = getRootPath() + location.pathname +"?pageSize="+$(this).text();
        location.assign(rul);
    });
});
function loadResources(pageNum,pageSize){
    var rul = getRootPath() + location.pathname +"?pageNum="+pageNum+"&pageSize="+pageSize;
    location.assign(rul);
}
function getRootPath() {
    var curWwwPath = location.href;
    var pathName = location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    return localhostPaht;
}


