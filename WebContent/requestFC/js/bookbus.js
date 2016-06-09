$(document).ready(function(){
	$(".confirmBooking").unbind('click');
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
});
