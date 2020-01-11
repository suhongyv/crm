function formatterOp(value,rowData) {
    var devResult = rowData.devResult;
    var href="javascript:openCusDevPlanDialog()";
    if(devResult == 2|| devResult==3){
        return "<a href='"+href+"' >查看详情</a>";
    }else{
        return "<a href='"+href+"'>开发</a>"
    }

}
function openCusDevPlanDialog() {
    
}

/**
 * 删除开发计划项
 */
function delCusDevPlan() {
    
}

/**
 * 保存开发计划项
 */
function saveCusDevPlan() {

}

/**
 * 更新营销机会开发状态
 * @param status
 */
function updateSaleChanceDevResult(status) {

}