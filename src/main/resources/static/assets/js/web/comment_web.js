//异步加载子页面
var appServer = "http://127.0.0.1:8001/lifen/";
function load(path) {
    location.replace(appServer+path+".html");
}
