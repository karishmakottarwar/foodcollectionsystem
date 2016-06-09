﻿function isEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}

	$(document).ready(function(){		
		$(".selectBox").click(function(){			
			if($(this).is(':checked')){
				selectedRecord = this.id;
				for(var i=1;i<=recordCount;i++){
					if(this.id!=i){
						$("#tableId"+i).css('display','none');
						  $("#"+i).attr("checked", false);
					}else{
						$("#tableId"+i).css('display','block');
					}				
				}
			}
		});	
		
		
		$(".confirmBooking").click(function(){
			console.log("shrikant ghuge")
			var resultArr = [];
				for(var i=0; i<$($(this.parentElement.parentElement.parentElement.parentElement).find("label")).length ;i++){
				console.log($($(this.parentElement.parentElement.parentElement.parentElement).find("label")[i]).html());
				resultArr.push($($(this.parentElement.parentElement.parentElement.parentElement).find("label")[i]).html());	
			}
			for(var j=1;j<4;j++){
				resultArr.push($($(this.parentElement.parentElement.parentElement.parentElement).find("input")[j]).val());
			}
			resultArr.push($(this.parentElement.parentElement.parentElement.parentElement).find("select").val());
			$.ajax({
				url : "contactUs",
				data : {
					data : resultArr
				},
				type : "POST",
				success : function(response){
					if(response==""){
						alert("Sorry for inconvenience, The Service is Down. Try After Some Time. ")
					}else{
							alert("Dear "+$("#userName").val()+" oue representive will contact you soon");
							//location.replace("index.html");
					}				
				},
				error : function(xhr, status, errorThrown){
					alert("There is an error in request!!")
					
				}
			});
		});
		
		
		/*to fetch 	request information:START*/
		
		$("#getRequestStatus").click(function(){
			$.ajax({
				url : "../fcs/user/fetchRequestStatus",
				data : {
					requestId : $("#requestId").val()
				},
				type : "GET",
				success : function(response){
					if(response==""){
						alert("Sorry for inconvenience, The Service is Down. Try After Some Time. ")
					}else{
						$("#requestStatus").html(response);
					}				
				},
				error : function(xhr, status, errorThrown){
					alert("There is an error in request!!")
					
				}
			});
		});
		/*to fetch 	request information:END*/
		
	});
	
	/*To Store the Enquiry Information: START*/
	function contactUSFun(){	
		if($('#userName').val()==''){
			alert('Please Enter name');
			return;
		}
		if($('#userEmail').val()=='' || !isEmail($('#userEmail').val())){
			alert('Enter Valid Email');
			return;
		}		
		if($('#userPhone').val()=='' || $('#userPhone').val().length!=10){
			alert('Enter valid Mobile Numebr');
			return;
		}		
		/*if($('#userMsg').val()==''){
			alert('Querry cannot be blank');
			return;
		}*/
		$.ajax({
			url : 'https://maps.googleapis.com/maps/api/geocode/json?address='+$($("#address").children()[1]).children()[1].value+' '+$($("#address").children()[2]).children()[1].value+' '+$($("#address").children()[3]).children()[1].value+'&key=AIzaSyD_7jZK5UXt287KSmlC6Fga1_RTavxUX1M',
			data : {
									
					},
			type : 'GET',
			success : function(response){
				if(response==""){
					alert("Sorry for inconvenience, The Service is Down. Try After Some Time. ")
				}else{
					$.ajax({
						url : "../fcs/user/theCollectionRequest",
						data : {
							name 		 : $('#userName').val(),
							requestQuantity  : $('#userQuantity').val(),
							mobileNumber : $('#userPhone').val(),
							emailId  	 : $('#userEmail').val(),							
							address : $($("#address").children()[0]).children()[1].value +" , "+$($("#address").children()[1]).children()[1].value +" , "+$($("#address").children()[2]).children()[1].value+ " , " +$($("#address").children()[3]).children()[1].value +" , Pin :" + $($("#address").children()[4]).children()[1].value,
							location : response.results[0].geometry.location.lat+","+response.results[0].geometry.location.lng 
							
						},
						type : "GET",
						success : function(response){
							if(response=="" || response=="-1"){
								alert("Sorry for inconvenience, The Service is Down. Try After Some Time. ");
							}else if(response=="collectorCannotAssigned"){
								alert("All the resources are busy right now, we cannot process you request");
							}								
							else{
									alert("Dear "+$("#userName").val()+" your Request number is : "+response +". Please keep it safe for further enquiry");
									//location.replace("index.html");
							}
							location.replace('./contact.html');
						},
						error : function(xhr, status, errorThrown){
							alert("There is an error in request!!")
							
						}
					});	
					
				}					
			},
			error : function(xhr, status, errorThrown){
				alert('There is an error in request!!')
				
			}
		});
		
		console.log('entered');
		return false;
	}
	/*To Store the Enquiry Information: END
	*/
	

	
	
	