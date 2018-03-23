$(function() {
	//渲染表格
	layui.table.render({
		elem : '#table',
		url : appServer+'admin/get_teacherList.json',
 		where: {
		},
		page: true,
		cols: [[
			{type:'numbers'},
			{field:'userAccount', sort: true, title: '用户账号'},
			{field:'userName', sort: true, title: '用户姓名'},
			{field:'userType', sort: true, templet:function(d){ return d.userType == '1'?'教师':'学生'; }, title: '用户类型'},
			{field:'userTitle', sort: true, title: '职称'},
			{field:'mobile', sort: true, title: '手机号'},
			/*{field:'email', sort: true, title: '邮箱'},*/
			{field:'gender', sort: true, title: '性别'},
			{field:'loginNum', sort: true, title: '登录次数'},
			{field:'createTime', sort: true, templet:function(d)
			{ return layui.util.toDateString(d.createTime); }, title: '创建时间'},
            {align:'center',width:'20%', toolbar: '#barTpl', title: '操作'}
    	]]
	});

    //添加按钮点击事件
    $("#addBtn").click(function(){
        showEditModel(null);
    });


    //表单提交事件
    layui.form.on('submit(btnSubmit)', function(data) {
        data.field.token = getToken();
        data.field._method = $("#editForm").attr("method");
        layer.load(1);
        $.post(appServer+"uc/add_user.json", data.field, function(data){
            layer.closeAll('loading');
            if(data.code==200){
                layer.msg(data.msg,{icon: 1});
                layer.closeAll('page');
                layui.table.reload('table', {});
            }else{
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

    //显示表单弹窗
    function showEditModel(data){
        layer.open({
            type: 1,
            title: data==null?"添加用户":"修改用户",
            area: '650px',
            offset: '120px',
            content: $("#addModel").html()
        });
        $("#editForm")[0].reset();
        $("#editForm").attr("method","POST");
        if(data!=null){
            $("#editForm input[name=userId]").val(data.userId);
            $("#editForm input[name=userAccount]").val(data.userAccount);
            $("#editForm input[name=userName]").val(data.userName);
            $("#editForm input[name=userType]").val(data.userType);
            $("#editForm input[name=mobile]").val(data.mobile);
            $("#editForm input[name=userTitle]").val(data.userTitle);
            $("#editForm input[name=email]").val(data.email);
            if('男'==data.gender){
                $("#genderMan").attr("checked","checked");
                $("#genderWoman").removeAttr("checked");
            }else{
                $("#genderWoman").attr("checked","checked");
                $("#genderMan").removeAttr("checked");
            }
            layui.form.render('radio');
        }
        $("#btnCancel").click(function(){
            layer.closeAll('page');
        });
    }
	//时间范围
	layui.laydate.render({
		elem: '#searchDate',
		type: 'date',
		range: true,
		theme: '#393D49',
        format:'yyyy-MM-dd'
	});
	
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	});


    //工具条点击事件
    layui.table.on('tool(table)', function(obj){
        var data = obj.data;
        var layEvent = obj.event;

        if(layEvent === 'edit'){ //修改
            showEditModel(data);
        } else if(layEvent === 'del'){ //删除
            doDelete(obj);
        } /*else if(layEvent === 'reset'){ //重置密码
            doReSet(obj.data.userId);
        }*/
    });
});

//搜索
function doSearch(){
	var searchDate = $("#searchDate").val().split(" - ");
	var userAccount = $("#userAccount").val();
	layui.table.reload('table', {where: {startTime: searchDate[0], endTime: searchDate[1], userAccount: userAccount}});
}

//删除
function doDelete(obj){
    layer.confirm('确定要删除吗？', function(index){
        layer.close(index);
        layer.load(1);
        $.ajax({
            url: appServer+"/uc/deleteUser/"+obj.data.userId+".json",
            type: "POST",
            dataType: "JSON",
            success: function(data){
                layer.closeAll('loading');
                if(data.code==200){
                    layer.msg(data.msg,{icon: 1});
                    obj.del();
                }else{
                    layer.msg(data.msg,{icon: 2});
                }
            }
        });
    });
}
