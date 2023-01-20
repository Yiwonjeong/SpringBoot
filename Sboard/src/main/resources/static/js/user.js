/**
 * 순수 자바스크립트로 ajax 처리
 */

// 데이터 검증에 사용하는 정규표현식
let reUid   = /^[a-z0-9_-]{4,20}$/; // 소문자 + 숫자 + 언더바/하이픈 허용 4~20자리
let rePass  = /(?=.*[a-zA-ZS])(?=.*?[#?!@$%^&*-]).{6,24}/; // 문자와 특수문자 조합의 6~24 자리
let reName  = /^[가-힣]+$/; // 한글만
//let reNick  = /^[\w\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$/;	// 닉네임 (글자수만 제한 2~20)	
let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
let reAuth  = /^[0-9]+$/;

// 폼 데이터 검증 결과 상태변수
let isUidok = false;
let isPassok = false;
let isNameok = false;
let isNickok = false;
let isEmailok = false;
let isHpok = false;

//브라우저가 로드된 후 수행 
 window.onload = function(){
    		
    // 아이디 유효성 검사 
	const btnCheckUid = document.getElementById('btnCheckUid');
	
	btnCheckUid.addEventListener('click', ()=>{
		
		let uid = document.querySelector('input[name=uid]').value;
		const resultUid = document.querySelector('.resultUid');
		
		// 아이디 미입력 후 중복확인 버튼 클릭 
		if(uid == null){
			alert("아이디를 입력하세요.");
			document.querySelector('input[name=uid]').focus();	// focus: 커서가 깜빡임, blur: 커서가 사라짐
			return false;
		}
		
		//아이디 정규식 검사 
		if(!reUid.test(uid)){
			resultUid.textContent = '4~20자 영문자 또는 숫자여야 합니다.';
			resultUid.style.color = 'red';
			document.querySelector('input[name=uid]').focus();
			return false;
		}
		
		// AJAX 전송 
		const xhr = new XMLHttpRequest();
		xhr.open("GET", "/Sboard/user/checkUid?uid="+uid);
		xhr.responseType = "json";
		xhr.send();
		
		// callback 함수 (success)
		xhr.onreadystatechange = function(){
			
			if(xhr.readyState == XMLHttpRequest.DONE){
				if(xhr.status == 200){ /*200: 성공코드*/
					const data = xhr.response;
					console.log(data);
					
					//const resultUid = document.querySelector('.resultUid');
					
					if(data.result == 1){
						resultUid.textContent = '이미 사용 중인 아이디입니다.';
						resultUid.style.color = 'red';
					}else{
						isUidok = true;
						resultUid.textContent = '사용 가능한 아이디입니다.';
						resultUid.style.color = 'green';	
					}
					
					
					
				}else{
					alert("Request fail...");
				}
			}else{
				
			}
			
		}
		
		
	});	
	
	
	// 비밀번호 유효성 검사 
	let pass1 = document.querySelector('input[name=pass1]');
	let pass2 = document.querySelector('input[name=pass2]');
	const resultPass = document.querySelector('.resultPass');
	const resultPass2 = document.querySelector('.resultPass2');
	
	pass1.addEventListener('focusout', ()=>{
		if(!rePass.test(pass1.value)){
			resultPass.textContent = '문자와 특수문자 조합의 6~24자리';
			resultPass.style.color = 'red';
			pass1.focus();
			return false;
		}else{
			resultPass.style.display = 'none';
		}
	});
	
	pass2.addEventListener('focusout', ()=>{
		if(pass1.value != pass2.value){
			isPassok = true;
			resultPass2.textContent = '비밀번호가 일치하지 않습니다.';
			resultPass2.style.color = 'red';
			pass2.focus();
			return false;
		}else{
			isPassok = true;
			resultPass2.style.display = 'none';
		}
	});
	
	
	// 이름 유효성 검사
	let name = document.querySelector('input[name=name]');
	let resultName = document.querySelector('.resultName');
	
	name.addEventListener('focusout', ()=>{
		if(!reName.test(name.value)){
			resultName.textContent = '유효한 이름이 아닙니다.';
			resultName.style.color = 'red';
			resultName.focus();
			return false;
		}else{
			isNameok = true;
			resultName.style.display = 'none';
		}
	});
	
	/*
	// 닉네임 유효성 검사
	const btnCheckNick = document.getElementById('btnCheckNick');
	
	btnCheckNick.addEventListener('click', ()=>{
		
		let nick = document.querySelector('input[name=nick]');
		let resultNick = document.querySelector('.resultNick');
		
		// 별명 미입력 후 중복확인 버튼 클릭 
		if(nick.value == ''){
			resultNick.textContent = '별명을 입력하세요.';
			resultNick.style.color = 'red';
			resultNick.focus();
			return false;
		}
		
		// 별명 정규식 검사
		if(!reNick.test(nick.value)){
			resultNick.textContent = '한글, 영문, 숫자 2~20자리';
			resultNick.style.color = 'red';
			resultNick.focus();
			return false;
		}
		
		// AJAX 전송
	 	const xhr2 = new XMLHttpRequest();
	 	xhr2.open("GET", "/Sboard/user/checkNick?nick="+nick);
	 	xhr2.reponseType = "json";
	 	xhr2.send();
	 	
	 	xhr2.onreadystatechange = function(){
			 if(xhr2.status == 200){
				 const data2 = xhr2.response;
				 console.log(data2);
				 
				 if(data2.result == 1){
					 resultNick.textContent = '이미 사용 중인 별명입니다.';
					 resultNick.style.color = 'red';
					 resultNick.focus();
				 }else{
					 isNickok = true;
					 resultNick.style.display = 'none';
				 }
			 }else{
				 alert("Reuqest faill...");
			 }
		 }
		
	});
	*/
	
	
	// 이메일 유효성 검사
	let email = document.querySelector('input[name=email]');
	let resultEmail = document.querySelector('.resultEmail');
	
	email.addEventListener('focusout', ()=>{
		if(!reEmail.test(email.value)){
			resultEmail.textContent = '유효하지 않은 이메일입니다.';
			resultEmail.style.color = 'red';
			resultEmail.focus();
			return false;
		}else{
			isEmailok = true;
			resultEmail.style.display = 'none';
		}
	});
	
	// 휴대폰 유효성 검사
	let hp = document.querySelector('input[name=hp]');
	let resultHp = document.querySelector('.resultHp');
	
	hp.addEventListener('focusout', ()=>{
		if(!reHp.test(hp.value)){
			resultHp.textContent = '-포함 13자리 입력';
			resultHp.style.color = 'red';
			resultHp.focus();
			return false;
		}else{
			isHpok = true;
			resultHp.style.display = 'none';
		}
	});
	
	
	
	
	
	
	
	
}