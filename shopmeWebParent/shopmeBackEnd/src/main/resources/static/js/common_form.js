$(document).ready(function(){
	
	$("#buttonCancle").on("click"  , function(){
			window.location = mouleURL;
	});
	
	
});

$(document).ready(()=>{
	$('#fileimage').change(function(){
		const file = this.files[0];
		console.log(file);
		const size  =  file.size;
		alert("file size is "+size);
		if(size > 1048576)
		{
			 this.setCustomValidity("You must choose an image less than 1MB ! ");
			 this.reportValidity();
		}
		else
		{
		if (file)
		{
		let reader = new FileReader();
		reader.onload = function(event)
		{
			console.log(event.target.result);
			$('#thumbnail').attr('src', event.target.result);
		}
		reader.readAsDataURL(file);
		}
		}
	});
});

function showMesaageonDialog(title , message)
{
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message){
	showMesaageonDialog("Error" , message);
}
function showWarningModal(message){
	showMesaageonDialog("Warning" , message);
}

