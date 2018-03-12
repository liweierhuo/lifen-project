var appServer = "http://127.0.0.1:8001/lifen/";
layui.use(['form'], function() {
	var form = layui.form;
	
	checkLogin();
	//提交
	form.on('submit(LAY-user-login-submit)', function(obj) {
		layer.load(1);
		$.post(appServer+"admin/do_login", obj.field, function(data) {
			if (data.code == 200) {
				layer.msg(data.msg,{icon: 1});
				localStorage.setItem("user", JSON.stringify(data.user));
				setTimeout(function() {
					location.replace(appServer+"admin/index.html");
				}, 2000);
			} else {
				layer.closeAll('loading');
				layer.msg(data.msg,{icon: 2});
			}
		}, "json");
	});
});


//检查是否登录
function checkLogin(){
	var tempUser = JSON.parse(localStorage.getItem("user"));
	if (tempUser != null) {
		location.replace(appServer+"admin/index.html");
	}
}

//生成uuid
function guid() {
    function S4() {
       return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    }
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}