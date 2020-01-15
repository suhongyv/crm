function formatterGrade(grade) {
    if(grade==0){
        return "一级菜单";
    }else if(grade==1){
        return "二级菜单";
    }else if(grade==2){
        return "三级菜单";
    }
}


function openModuleAddDialog() {
    $("#parentId").combobox("setValue",$("#pId").val());
    openDialog("dlg","菜单添加")
}
function openModuleModifyDialog() {
    openModifyDialog("dg","fm","dlg","菜单修改");
}
function deleteModule() {
    deleteConfirm("dg",ctx+"/module/delete",searchModules);
}
function searchModules() {
    $("#dg").datagrid("load",{
        moduleName:$("#s_moduleName").val(),
        code:$("#s_code").val()
    })
}
function clearFormData() {
    $("#moduleName").val("");
    $("#moduleStyle").val("");
    $("#optValue").val("");
    $("#orders").val("");
    $("input[name='id']").val("");
}
function saveOrUpdateModule() {
    saveOrUpdate(ctx+"/module/save",ctx+"/module/update","dlg",searchModules,clearFormData);
}
function closeModuleDialog() {
    closeDialog("dlg");
}