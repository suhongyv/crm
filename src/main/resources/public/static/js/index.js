function login() {
    var userName=$("#userName").val();
    var userPwd=$("#password").val();
    if(isEmpty(userName)){
        alert("请输入用户名!")
        return;
    }
    if(isEmpty(userPwd)){
        alert("请输入用户密码!")
        return;
    }
    $.ajax({
       type:"post",
       url:ctx+"/user/login",
        data:{
           userName:userName,
           userPwd:userPwd
        },
        datatype:"json",
        success:function (result) {
            if(result.code==200){
                var result=result.result;
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                $.cookie("userIdStr",result.userId);
                window.location.href=ctx+"/main";
            }else{
                alert(result.msg);
            }
        }
    });
}
function openRegisterModal() {

}
