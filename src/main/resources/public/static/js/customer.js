function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#name").val(),
        cusNo:$("#khno").val(),
        myd:$("#myd").combobox("getValue"),
        level:$("#level").combobox("getValue")
    })
}
function clearFormData() {

}
function openCustomerAddDialog() {
    openDialog("dlg","客户添加");
}
function openCustomerModifyDialog() {
    openModifyDialog("dg","fm","dlg","客户数据修改");
}
function closeCustomerDialog() {
    closeDialog("dlg");
}
function saveOrUpdateCustomer() {
    saveOrUpdate(ctx+"/customer/save",ctx+"/customer/update","dlg",searchCustomersByParams,clearFormData);
}
function deleteCustomer() {
    deleteConfirm("dg",ctx+"/customer/delete",searchCustomersByParams);
}
