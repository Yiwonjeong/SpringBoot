/**
 * 
 */
$(document).ready(function(){
				
	$('.member.list1').click(function(){
		$.ajax({
			url: '/Ch09/member',
			method: 'GET',
			dataType: 'json',
			success: function(data){
				console.log(data);
			}
		});
	});
	
	$('.member.list2').click(function(){
		
		let uid = 'u201';
		
		$.ajax({
			url: '/Ch09/member/'+uid,
			method: 'GET',
			dataType: 'json',
			success: function(data){
				console.log(data);
			}
		});	
	});
	
	$('.member.register').click(function(){
		
		let jsonData = {
			"uid": "k203",
			"name": "김유신",
			"hp": 35,
			"pos": "영업부",
			"dep": '21'
		};
		
		$.ajax({
			url: '/Ch09/member/',
			method: 'POST',
			data: jsonData,
			dataType: 'json',
			success: function(data){
				console.log(data);	
			}						
		});
		
	});
	
	
	$('.member.modify').click(function(){
		
		let jsonData = {
				"uid": "k203",
				"name": "김유신",
				"hp": 13,
				"pos": "영업부",
				"dep": '21'
			};
			
			$.ajax({
				url: '/Ch09/member/',
				method: 'PUT',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					console.log(data);	
				}						
			});
		
	});
	
	
	$('.member.delete').click(function(){
		let uid = 'k203';
		
		$.ajax({
			url: '/Ch09/member/'+uid,
			method: 'DELETE',
			dataType: 'json',
			success: function(data){
				console.log(data);	
			}						
		});
		
	});

});