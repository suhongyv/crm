function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/**
 * 用户退出
 */
function logout() {
    $.messager.confirm("来自crm","确定退出系统",function(r){
        if(r){
            //移除cookie
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.removeCookie("userIdStr");
            $.messager.alert("来自crm","系统在三秒后自动退出!!!","info")
            //设置三秒后跳到登录页面
            setTimeout(function () {
                window.location.href=ctx+"/index";
            },3000);
        }
    })
}

/**
 * 打开密码修改框
 */
function openPasswordModifyDialog() {
    $("#dlg").dialog("open").dialog("setTitle","密码修改");
}

/**
 * 关闭密码修改对话框
 */
function closePasswordModifyDialog() {
    $("#dlg").dialog("close");
}
/**
 * 修改密码
 */
function modifyPassword() {
    $("#fm").form("submit",{
        url:ctx+"/user/updatePassword",
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                closePasswordModifyDialog()
                //移除cookie
                $.removeCookie("userName");
                $.removeCookie("trueName");
                $.removeCookie("userIdStr");
                $.messager.alert("来自crm","系统在五秒后自动退出!!!","info")
                //设置三秒后跳到登录页面
                setTimeout(function () {
                    window.location.href=ctx+"/index";
                },5000);
            }else {
                $.messager.alert("来自crm",data.msg,"error")
            }
        }
    })
}