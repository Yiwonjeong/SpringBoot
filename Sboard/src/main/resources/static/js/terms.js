/**
 * 약관 동의란 체크 검사
 */
//브라우저가 로드된 후 수행 
window.onload = function(){
	
	let checkAll = document.getElementById('checkAll');					// 모두 동의하기 
	let checkList = document.querySelectorAll('input[name=agree]');		// 각 체크박스
	let check1 = document.getElementById('chk01');						// 약관 체크박스
	let check2 = document.getElementById('chk02');						// 개인정보 체크박스
	const btnNext = document.getElementById('btnNext');					// 다음 버튼 
	const errorMsg = document.querySelector('.errorMsg');				// 에러 메시지 출력
	
	
	// 전체 동의하기 클릭 시 
    checkAll.addEventListener('change', function () {
      	check1.checked = checkAll.checked;
      	check2.checked = checkAll.checked;
    });
    
    // 각 동의하기 해제 시 - 전체 동의하기 해제 
    checkList.forEach(function (check) {
      check.addEventListener('change', function () {
        if (check1.checked && check2.checked) {
          checkAll.checked = true;
        } else {
          checkAll.checked = false;
        }
      });
    });
	
	
	// 다음 버튼 클릭 시 
	btnNext.addEventListener('click', (e)=>{
		if(check1.checked == true && check2.checked == true){
			errorMsg.style.display = 'none';
		}else if(check1.checked == false || check2.checked == false){
			errorMsg.style.display = 'block';
			e.preventDefault();
		}
	});
	

	
};