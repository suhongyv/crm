function searchUsers() {
    $("#dg").datagrid("load",{
        userName:$("#s_userName").val(),
        trueName:$("#s_trueName").val(),
        phone:$("#s_phone").val()
    })
}
/**
 * 打开添加对话框
 */
function openUserAddDialog() {
    openDialog("dlg","用户添加");
}

/**
 * 打开修改对话框
 */
function openUserModifyDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待修改的记录!!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改!!","error");
        return;
    }
    rows[0].roleIds=rows[0].rids.split(",");
    $("#fm").form("load",rows[0]);
    openDialog("dlg","用户更新");
}

/**
 * 删除记录
 */
function deleteUser() {
    deleteConfirm("dg",ctx+"/user/delete",searchUsers);
}

/**
 * 清除对话框表单数据
 */
function  clearFormData(){
    $("#userName").val("");
    $("#email").val("");
    $("#trueName").val("");
    $("#phone").val("");
    $("input[name='id']").val("");
}
/**
 * 添加或修改用户记录
 */
function saveOrUpdateUser() {
    saveOrUpdate(ctx+"/user/save",ctx+"/user/update","dlg",searchUsers,clearFormData);
}

/**
 * 关闭对话框
 */
function closeUserDialog() {
    closeDialog("dlg");
}
