/*
 * 이메일 인증
 */
 //브라우저가 로드된 후 수행

$(function(){
//window.onload = function(){

    let preventDoubleClick = false;
    let isEmailAuthOk = false;

	// 이메일 인증코드 발송 클릭
	$('#btnEmail').click(function(){

	    $(this).hide();
	    let email = $('input[name=email]').val();

        if(email == ''){
            alert('이메일을 입력 하세요.');
            return;
        }

        if(preventDoubleClick){
            return;
        }

        preventDoubleClick = true;

        $('.resultEmail').text('인증코드 전송 중 입니다. 잠시만 기다리세요...');

        setTimeout(function(){

            $.ajax({
                url: '/Farmstory/login/mailConfirm',
                method: 'GET',
                data: {"email": email},
                dataType: 'json',
                success: function(data){
                    console.log('here5 : ' + JSON.stringify(data));
                    console.log('here6 : ' + data.result);

                    if(data != null){
                        // 메일전송 성공
                        $('.resultEmail').text('이메일을 확인 후 인증코드를 입력하세요.');
                        $('.auth').show();
                        receivedCode = data.result;

                    }else{
                        // 메일전송 실패
                        alert('메일전송이 실패 했습니다.\n다시 시도 하시기 바랍니다.');
                    }
                }
            });
        }, 1000);
	});

    // 이메일 인증코드 확인 버튼
	$('#btnEmailConfirm').click(function(){

		let code = $('input[name=auth]').val();

		if(code == ''){
			alert('이메일 확인 후 인증코드를 입력하세요.');
			return;
		}

		if(code == receivedCode){
			isEmailAuthOk = true;
			$('input[name=email]').attr('readonly', true);
			$('.resultEmail').text('이메일이 인증 되었습니다.');
			$('.auth').hide();
		}else{
			isEmailAuthOk = false;
			alert('인증코드가 틀립니다.\n다시 확인 하십시요.');
		}
	});


//};
});
