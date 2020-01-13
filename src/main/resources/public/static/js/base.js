/**
 * 打开对话框
 */
function openDialog(dlgId,title) {
    $("#"+dlgId).dialog("open").dialog("setTitle",title);
}
/**
 * 关闭对话框
 */
function closeDialog(dlgId) {
    $("#"+dlgId).dialog("close");
}
/**
 *
 * @param dataGridId  表格id
 * @param formId       表单id
 * @param dlgId         对话框id
 * @param title             对话框标题
 */
function openModifyDialog(dataGridId,formId,dlgId,title) {
    var rows=$("#"+dataGridId).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待修改的记录!!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量修改!!","error");
        return;
    }
    $("#"+formId).form("load",rows[0]);
    openDialog(dlgId,title);
}


/**
 * 打开删除对话框
 */
function deleteConfirm(dataGrid,deleteUrl,search) {
    var rows=$("#"+dataGrid).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除的记录!!","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持批量删除!!","error");
        return;
    }
    $.messager.confirm("来自crm","是否确认删除记录??",function (r) {
        $.ajax({
            type:"post",
            url:deleteUrl,
            datatype:"json",
            data: {
                id:rows[0].id
            },
            success:function (data) {
                if(data.code==200){
                    search();
                }else{
                    $.messager.alert("来自crm",data.msg,"error");
                }
            }
        })
    })
}
/**
 * 添加或修改
 */
function saveOrUpdate(saveUrl,updateUrl,dlgId,search,clear) {
    var url=saveUrl;
    if(!(isEmpty($("input[name='id']").val()))){
        url = updateUrl;
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data =JSON.parse(data);
            if(data.code==200){
                closeDialog(dlgId);
                search();
                clear();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    });
}

