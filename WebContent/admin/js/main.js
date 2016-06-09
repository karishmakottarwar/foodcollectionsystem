/*Author : Shrikant Ghuge*/
$(document).ready(function(){
	
	/*This checks the valid user is accessing the page*/
	if(checkLoginInfo()){
		$.ajax({
			url : "../fcs/user/checkLogin",
			type : "GET",
			data: {
				name : localStorage.getItem('username'),
				password : localStorage.getItem('password')
			},
			success : function(response){
				if(response=="false"){
					window.location.replace('../requestFC/404.html');
				}
			}
		});
	}else{
		window.location.replace('./error.html');
	}
	
	
	/*Signup the user : START*/
	$("#submitForm").click(function(){
		if(validateFields()!=undefined){
			$.ajax({
				url : "../fcs/user/signUp",
				data : {
					name : $("#uFName").val() ,
					email : $("#uEmailId").val(),
					mobile : $("#uContactNo").val(),
					password : $("#password1").val(),
					quantity : $("#uQuantity").val()
				},
				type : "GET",
				success : function(response){
					console.log(response);
					alert(response);
					window.location.replace('./signup.html');
				}
			});
		}
	});
	/*Signup the user : END*/
	
	/*List the registered user : START*/
	$("#listRegUser").click(function(){
		$.ajax({
			url : "../fcs/user/getRegistereduser",
			type : "GET",
			data : {
				
			},
			success : function(response){
				console.log(response);
				if(response==null){
					alert("There is no user registered yet!!");
				}
				$("#pageHeading").html("Collector List");
				var htmlToRender = '<div class="sign-u "><div class="sign-up1" style="width: 7%; "><h4 maxLength="10">Sr No</h4></div><div class="sign-up1 " style="width: 30%; padding-left: 5%;"><h4>Name</h4></div><div class="sign-up1 " style="width: 20%; padding-left: 5%;"><h4>Contact No</h4></div><div class="sign-up1 " style="width: 25%; padding-left: 5%;"><h4>Email Id</h4></div><div class="sign-up1 " style="width: 16%; padding-left: 5%;"><h4>Status</h4><div class="clearfix"> </div></div>';
				for(var i=0;i<response.user.length;i++){
					htmlToRender= htmlToRender +'<div class="sign-u "><div class="sign-up1" style="width: 7%;"><h4>'+(i+1)+'</h4></div><div class="sign-up1 shriUserListBorder" style="width: 30%; padding-left: 5%;"><h4>'+response.user[i].name+'</h4></div><div class="sign-up1 shriUserListBorder" style="width: 20%; padding-left: 5%;"><h4>'+response.user[i].mobile+'</h4></div><div class="sign-up1 shriUserListBorder" style="width: 25%; padding-left: 5%;"><h4>'+response.user[i].email+'</h4></div><div class="sign-up1 shriUserListBorder" style="width: 16%; padding-left: 5%;"><h4>Yet to update</h4></div><div class="clearfix"> </div></div>';
				}				
				var htmlToRender1 = '<table id="t01" class="names"><tr><th>Sr No</th><th>Name</th><th>Contact No</th><th>Email Id</th><th>Status</th><th>Edit Button</th><th>Delete Button</th></tr>';
				for(var i=0;i<response.user.length;i++){
					htmlToRender1= htmlToRender1 +'<tr><td>'+(i+1)+'</td><td>'+response.user[i].name+'</td><td>'+response.user[i].mobile+'</td><td>'+response.user[i].email+'</td><td>'+response.user[i].status+'</td><td><input type="button" value="Edit" class="EditCollectorRecord"/></td><td><input type="button" class="deleteCollectorRecord" value="Delete"/></td></tr>';
				}
				if(response.user.name!=undefined){
					htmlToRender1= htmlToRender1 +'<tr><td>'+1+'</td><td>'+response.user.name+'</td><td>'+response.user.mobile+'</td><td>'+response.user.email+'</td><td>'+response.user.status+'</td><td><input type="button" value="Edit" class="EditCollectorRecord"/></td><td><input type="button" value="Delete" class="deleteCollectorRecord"/></td></tr>';
				}
				htmlToRender1= htmlToRender1 + '</table>';
				
				$("#mainTemplateBody").html(htmlToRender1);
				$("#mainTemplateBody").css("width","100%");
				/*Delete event registered by prashant*/
				$(".deleteCollectorRecord").click(function(){
					//alert("I am goinrg right");
					$.ajax({
						url : "../fcs/user/deleteUser",
						type : "GET",
						data : {
							email : $($($($(this).parent()).parent()).children()[3]).html()
						},
						success : function(response){
							if(response =="success"){
								alert("The record has been deleted");
							}else{
								alert("Error occured, try again latter");
							}
							window.location.replace('./signup.html');
						}
					});
				});
				/*Edit Field enabling event*/
				$('.EditCollectorRecord').click(function(){
					console.log("current reference "+this);
					 if($($($($(this).parent()).parent()).children()[1]).children().html()==undefined){
						for(var i=1;i<3;i++){
							$($($($(this).parent()).parent()).children()[i]).html('<input type="text" value="'+$($($($(this).parent()).parent()).children()[i]).html()+'">');
						};
						this.value="Update";
					 }else{
						 $.ajax({
								url : "../fcs/user/updateUser",
								type : "GET",
								data : {
									email : $($($($(this).parent()).parent()).children()[3]).html(),
									name : $($($($(this).parent()).parent()).children()[1]).children().val(),
									mobile : $($($($(this).parent()).parent()).children()[2]).children().val()
								},
								success : function(response){
									if(response =="success"){
										alert("The record has been updated");
									}else{
										alert("Error occured, try again latter");
									}
									window.location.replace('./signup.html');
								}
							});						 
					 }
				});
				
			}
		});
	});
	/*List the registered user : END*/
	/*List the collection request : START*/
	$("#listCollReq").click(function(){
		$.ajax({
			url : "../fcs/user/getCollectionRequest",
			type : "GET",
			data : {
				
			},
			success : function(response){
				console.log(response);
				if(response==null){
					alert("There is no collection request yet!!");
				}
				$("#pageHeading").html("Collection Request");							
				var htmlToRender1 = '<table id="t01" class="names"><tr><th>Request No</th><th>Name</th><th>Contact No</th><th>Email Id</th><th>Quantity</th><th>Address</th><th>Status</th><th>Cancel Request</th></tr>';
				for(var i=0;i<response.user.length;i++){
					htmlToRender1= htmlToRender1 +'<tr><td>'+response.user[i].dateOfBirth+'</td><td>'+response.user[i].name+'</td><td>'+response.user[i].mobile+'</td><td>'+response.user[i].email+'</td><td>'+response.user[i].requestedQuantity+'</td><td>'+response.user[i].location+'</td><td>'+response.user[i].status+'</td><td><input type="button" class="deleteCollectorRecord" value="Cancel"/></td></tr>';
				}
				if(response.user.name!=undefined){
					htmlToRender1= htmlToRender1 +'<tr><td>'+response.user.dateOfBirth+'</td><td>'+response.user.name+'</td><td>'+response.user.mobile+'</td><td>'+response.user.email+'</td><td>'+response.user.requestedQuantity+'</td><td>'+response.user.location+'</td><td>'+response.user.status+'</td><td><input type="button" class="deleteCollectorRecord" value="Cancel"/></td></tr>';
				}
				htmlToRender1= htmlToRender1 + '</table>';
				
				$("#mainTemplateBody").html(htmlToRender1);
				$("#mainTemplateBody").css("width","100%");	
				
				$(".deleteCollectorRecord").click(function(){
					//alert("I am goinrg right");
					$.ajax({
						url : "../fcs/user/deleteFCReq",
						type : "GET",
						data : {
							request_no : $($($($(this).parent()).parent()).children()[0]).html()
						},
						success : function(response){
							if(response =="success"){
								alert("The record has been deleted");
							}else{
								alert("Error occured, try again latter");
							}
							window.location.replace('./signup.html');
						}
					});
				});
				
			}
		});
	});
	/*List the collection request : END*/
	
	/*Logout functionallaty : START*/
	$("#logOut").click(function(){
		 $.ajax({
				url : "../fcs/user/logout",
				type : "GET",
				data : {},
				success : function(response){
					if(response =="success"){
						alert("You have been logged out successfully!!");
					}else{
						alert("Error occured, try again latter");
					}
					window.location.replace('../');
				}
			});	
	});
	/*Logout functionallaty : END*/
	
});

function validateFields(){
		if($("#uFName").val()==""){
			alert("Enter First Name");
		}else
		if($("#uLName").val()==""){
			alert("Enter Last Name");
		}else
		if($("#uEmailId").val()==""){
			alert("Enter Email Id");
		}else
		if($("#uContactNo").val()==""){
			alert("Enter Contact Number");
		}else
		if($("#password1").val()==""){
			alert("Enter Password");
		}else
		if($("#password2").val()==""){
			alert("Re-Enter password");
		}else
		if($("#password1").val()!=$("#password2").val()){
			alert("Both passwords doesnot match");
		}else{
			return true;
		}
}

function checkLoginInfo(){
	return localStorage.getItem('username')!=null && localStorage.getItem('password')!=null ;
}




