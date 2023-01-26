/**
 * 
 */
//수정 텍스트란 클릭 -> 편집 모드 변환  
$(document).on('click', '.modify', function(e){
	e.preventDefault();
	
	let txt = $(this).text();
	$(this).attr('contentEditable', true);
	$(this).focus();
	console.log(txt);
}); 

// 우측 수정 (modify) 버튼 클릭 
$(document).on('click', '.modifyComplete', function(e){
	e.preventDefault();
	let no = $(this).attr('data-no');
	let content_ = $(this).parent().prev().children();
	let content = content_.text();
	
	console.log(no);
	console.log('content: '+content);
	
	const jsonData = {
			'no': no,
			'content': content
	};
	
	$.ajax({
		url: '/todoapp/modify',
		type: 'POST',
		data: jsonData,
		dataType: 'json',
		success: function(data){
			console.log(data);
			if(data.result == 1){
				alert('수정되었습니다.');
			}else {
				alert('error');
			}
		}
	});
});

// 미완료 -> 완료(체크박스) 체크 표시 
$(document).on('change', '#check1', function(e){
	e.preventDefault();
	
	let no = $(this).attr('data-no');
	let content_ = $(this).parent().next().next().children();
	let content = content_.text();
	
	console.log(no);
	console.log('content: '+content);
	
	const jsonData = {
		'no': no,
		'content': content
	};
	
	$.ajax({
		url: '/todoapp/completed',
		type: 'POST',
		data: jsonData,	
		dataType: 'json',	
		success: function(data){
			$(location).prop("href", location.href);
		}
		
	});
	
});

// 완료 -> 미완료 
$(document).on('change', '#check2', function(e){
	e.preventDefault();
	
	let no = $(this).attr('data-no');
	let content_ = $(this).parent().next().next().children();
	let content = content_.text();
	
	console.log(no);
	console.log('content: '+content);
	
	const jsonData = {
		'no': no,
		'content': content
	};
	
	$.ajax({
		url: '/todoapp/incompleted',
		type: 'POST',
		data: jsonData,	
		dataType: 'json',	
		success: function(data){
			if(data.result == 1){
				$(location).prop("href", location.href);
			}else {
				alert('error');
			}
		}
		
	});
});
