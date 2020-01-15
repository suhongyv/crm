function searchRoles() {
    $("#dg").datagrid("load", {
        roleName: $("#s_roleName").val()
    })
}

function clearFormData() {
    $("#roleName").val("");
    $("#roleRemark").val("");
    $("input[name='id']").val("");
}

function openRoleAddDialog() {
    openDialog("dlg", "角色添加");
}

function openRoleModifyDialog() {
    openModifyDialog("dg", "fm", "dlg", "角色修改")
}

function closeRoleDialog() {
    closeDialog("dlg");
}

function deleteRole() {
    deleteConfirm("dg", ctx + "/role/delete", searchRoles);
}

function saveOrUpdateRole() {
    saveOrUpdate(ctx + "/role/save", ctx + "/role/update", "dlg", searchRoles, clearFormData);
}


var zTreeObj;
var roleId;

function openAddModuleDialog() {
    var row = $("#dg").datagrid("getSelections");
    if (row.length == 0) {
        $.messager.alert("来自crm", "请选择待授权的角色!", "error");
        return;
    }
    if (row.length > 1) {
        $.messager.alert("来自crm", "暂不支持批量授权!!", "error");
        return;
    }
    roleId = row[0].id;
    $.ajax({
        url: ctx + "/module/queryAllModules",
        type: "post",
        data:{
            roleId:roleId
        },
        dataType: "json",
        success: function (data) {
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                check: {
                    enable: true
                },
                callback: {
                    //点击事件
                    onCheck: zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
            openDialog("module", "授权");
        }
    });
}

function zTreeOnCheck(event, treeId, treeNode) {
    var nodes = zTreeObj.getCheckedNodes(true);
    var mids="mids=";
    for (var i=0; i < nodes.length; i++) {
        if (i < nodes.length - 1) {
            mids = mids + nodes[i].id + "&mids=";
        } else {
            mids = mids + nodes[i].id;
        }
    }
    $.ajax({
        type: "post",
        url: ctx + "/role/addGrant",
        data:mids+"&roleId="+roleId,
        datatype:"json",
        success:function (data) {
            console.log(data);
        }
    })
}