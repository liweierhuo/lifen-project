$(function() {
	//渲染表格
	layui.table.render({
		elem : '#table',
		url : appServer+'admin/project_list.json',
 		where: {
	  		token : getToken()
		},
		page: true,
		cols: [[
			{type:'numbers'},
			{field:'projectCode', sort: true, title: '项目编号'},
            {field:'projectName', sort: true, title: '项目名称'},
			{field:'projectType', sort: true, templet:function(d){ return d.projectType == '0'?'创业项目':d.projectType == '1'? '创新项目':"其他"; }, title: '类型'},
			{field:'projectStatus', sort: true, templet:function(d){ return d.projectStatus == 'draft'?'草稿':d.projectStatus == 'normal'? '正常':"结束"; }, title: '状态'},
			{field:'userAccount', sort: true, title: '发布者'},
			{field:'projectContent', sort: true, title: '内容'},
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
        var selectItem = "";
        if(data!=null){
            $("#editForm input[name=userId]").val(data.userId);
            $("#editForm input[name=userAccount]").val(data.userAccount);
            $("#editForm input[name=userName]").val(data.userName);
            $("#editForm input[name=userType]").val(data.userType);
            $("#editForm").attr("method","PUT");
            selectItem = data.roleId;
            if('男'==data.sex){
                $("#sexMan").attr("checked","checked");
                $("#sexWoman").removeAttr("checked");
            }else{
                $("#sexWoman").attr("checked","checked");
                $("#sexMan").removeAttr("checked");
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
		theme: '#393D49'
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
        } else if(layEvent === 'reset'){ //重置密码
            doReSet(obj.data.userId);
        }
    });
});

//搜索
function doSearch(){
	var searchDate = $("#searchDate").val().split(" - ");
	var searchAccount = $("#searchAccount").val();
	layui.table.reload('table', {where: {startDate: searchDate[0], endDate: searchDate[1], account: searchAccount}});
}
