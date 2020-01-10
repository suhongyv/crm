function formatterState(value) {
    if(value==0){
        return "未分配"
    }else if(value==1){
        return "已分配"
    }else{
        return "未知"
    }
}

function formatterDevResult(value) {
    if(value==0){
        return "未开发"
    }else if(value==1){
        return "开发中"
    }else if(value==2){
        return "开发成功"
    }else if(value==3){
        return "开发失败"
    }else{
        return "未知"
    }
}
function searchSaleChance() {
    var customerName=$("#s_customerName").val();
    var createMan=$("#s_createMan").val();
    var state=$("#s_state").combobox("getValue");
    $("#dg").datagrid("load",{
        customerName:customerName,
        createMan:createMan,
        state:state
    })
}

/**
 * 打开添加对话框
 */
function openSaleChanceAddDialog() {
    $("#fm").form("reset");
    $("#dlg").dialog("open").dialog("setTitle","机会数据添加");
}

/**
 * 关闭对话框
 */
function closeSaleChanceDialog() {
    $("#dlg").dialog("close");

}

/**
 * 打开修改对话框
 */
function openSaleChanceModifyDialog() {
    //获取选择的记录,数组
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待修改的记录!!","info");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","暂不支持多条记录更新!!","info");
        return;
    }
    //把数组中的数据加载到对话框的表单中
    $("#fm").form("load",rows[0]);
    //打开对话框
    $("#dlg").dialog("open").dialog("setTitle","机会数据修改");
}
/**
 * 添加或修改记录
 */
function saveOrUpdateSaleChance() {
    var url=ctx+"/sale_chance/save";
    var id=$("#chance_id").val();
    if(!(isEmpty(id))){
        url=ctx+"/sale_chance/update"
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function(data){
            data=JSON.parse(data);
            if(data.code==200){
                closeSaleChanceDialog();
                searchSaleChance();

            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}
function deleteSaleChance() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除的记录!!","info");
        return;
    }

    $.messager.confirm("来自crm","是否确认删除记录??",function (r) {
        if(r){
            var ids="ids=";
            for(var i=0;i<rows.length;i++){
                if(i<rows.length-1){
                    ids=ids+rows[i].id+"&ids="
                }else{
                    ids=ids+rows[i].id;
                }

            }
        }
        $.ajax({
            type:"post",
            url:ctx+"/sale_chance/delete",
            datatype:"json",
            data: ids,
            success:function (data) {
                if(data.code==200){
                    searchSaleChance();
                }else{
                    $.messager.alert("来自crm",data.msg,"error");
                }
            }
        })
    })
}