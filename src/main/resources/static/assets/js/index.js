var appServer = "http://127.0.0.1:8001/lifen/";
$(function() {
	initUserInfo();  //获取用户信息
    //路由注册
    Q.reg('home',function(){
        load('admin/home');
    }).reg('admin',function(path){
        load("admin/"+path);
    }).init({
        index: 'home'
    });
	//点击导航切换页面时不刷新导航,其他方式切换页面要刷新导航
	layui.element.on('nav(index-nav)', function(elem){
		if(document.body.clientWidth<=750){
			switchNav(true);
		}
	});
	//修改密码表单提交事件
	layui.form.on('submit(pswSubmit)', function(data){
		data.field.token = getToken();
		data.field._method = $("#pswForm").attr("method");
		layer.load(1);
		$.post("api/user/psw", data.field, function(data){
			if(data.code==200){
				layer.msg(data.msg,{icon: 1});
				setTimeout(function() {
					loginOut();
				}, 1500);
			}else{
				layer.closeAll('loading');
				layer.msg(data.msg,{icon: 2});
			}
		}, "JSON");
		return false;
	});
	layui.form.verify({
		  psw2: function(value, item){
			var newPsw1 = $("#pswForm input[name=newPsw]").val();
		    if(value!=newPsw1){
		      return '两次输入密码不一致';
		    }
		  },
		  pass: [
		    /^[\S]{6,12}$/
		    ,'密码必须6到12位，且不能出现空格'
		  ]
	});
});

//异步加载子页面
function load(path) {
	activeNav(path);
    $("#main-content").load(appServer+"/" + path +".html",function(){
        layui.element.render('breadcrumb');
        layui.form.render('select');
    });
}

//获取用户信息
function initUserInfo(){
	try {
		var user = getCurrentUser();
		//$("#userHead").attr("src", user.);
		$("#userNickName").text(user.adminAccount);
	} catch (e) {
		console.log(e.message);
	}
}

//退出登录
function loginOut(){
	localStorage.removeItem("user");
	layer.load(1);
    location.replace(appServer+"admin/login_out.html");
}

//个人信息
function myInfo(){
	var user = getCurrentUser();
	var content = '<ul class="site-dir" style="padding:25px 35px 8px 35px;"><li>账号：'+user.adminAccount+'</li><li>用户名：'+user.adminName+'</li>';
	content += '</ul>';
	layer.open({
		type: 1,
		title: '个人信息',
		area: '350px',
		offset: '120px',
		content: content,
		btn: ['关闭'],
		btnAlign: 'c'
	});
}

//显示表单弹窗
function updatePsw(){
	layer.open({
		type: 1,
		title: "修改密码",
		area: '400px',
		offset: '120px',
		content: $("#pswModel").html()
	});
	$("#pswForm")[0].reset();
	$("#pswCancel").click(function(){
		layer.closeAll('page');
	});
}