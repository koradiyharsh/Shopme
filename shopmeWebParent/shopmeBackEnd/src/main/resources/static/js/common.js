$(document).ready(function(){
		$("#dropdownid").on("click" , function(e){
				e.preventDefault();
				document.logoutForm.submit();
		});
		customizedropdownmenuItem();
});

function customizedropdownmenuItem()
{

	$(".navbar .dropdown").hover(

		function()
		{
			$(this).find('.dropdown-menu').first().stop(true , true).delay(250).slideDown();
			
			
		},
		function()
		{
			$(this).find('.dropdown-menu').first().stop(true, true).delay(150).slideUp();
			
		}
	);

	$(".dropdown > a").click(function(){
		location.href  =  this;
		
		
	});

}