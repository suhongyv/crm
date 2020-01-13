function searchRoles() {
    $("#dg").datagrid("load",{
        roleName:$("#s_roleName").val()
    })
}
function  clearFormData(){
    $("#roleName").val("");
    $("#roleRemark").val("");
    $("input[name='id']").val("");
}
function openRoleAddDialog() {
    openDialog("dlg","角色添加");
}
function openRoleModifyDialog() {
    openModifyDialog("dg","fm","dlg","角色修改")
}
function closeRoleDialog() {
    closeDialog("dlg");
}
function deleteRole() {
    deleteConfirm("dg",ctx+"/role/delete",searchRoles);
}
function saveOrUpdateRole() {
    saveOrUpdate(ctx+"/role/save",ctx+"/role/update","dlg",searchRoles,clearFormData);
}
function openAddModuleDialog() {
    $.ajax({
        url:ctx+"/module/queryAllModules",
        type:"post",
        dataType:"json",
        success:function (data) {
            var zTreeObj;
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                check: {
                    enable: true
                }
            };
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            openDialog("module","授权");
        }
    });
}
