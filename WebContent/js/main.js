$(document).ready(function(){
	$("#loginButton").click(function(){
		$.ajax({
			url:"fcs/user/login",
			type: 'GET',
			data : {
				name : $("#username").val(),
				password : $("#password").val()
			},
			dataType : 'json',
			success: function(result){
				console.log(result);
				if(result.password!=-1){
					localStorage.setItem("username",result.name);
					localStorage.setItem("password",result.password);
					window.location.replace('./admin/signup.html');
				}else{
					alert("Please enter valid credentials");
				}
				
			}
		});
	});
});

function checkLoginInfo(){
	return localStorage.getItem('username')!=null && localStorage.getItem('password')!=null ;
}

