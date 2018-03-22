//异步加载子页面
var appServer = "http://127.0.0.1:8001/lifen/";


function load(path) {
    location.replace(appServer+path+".html");
    activeNav(path)
}

function loadAndParameter(path,parameter) {
    location.replace(appServer+path+".html?"+parameter);
    activeNav(path)
}

//设置选中导航栏
function activeNav(path_name){
    $(".layui-container ul.layui-nav li.layui-nav-item").removeClass("layui-this");
    var $a = $(".layui-container ul.layui-nav>li.layui-nav-item>a[href='javascript:load("+path_name+")']");
    $a.addClass("layui-this");
}

//搜索
function doPojectListSearch(){
    $("#project_list_search").submit()
}
