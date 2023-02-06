$(document).ready(function(){
        // 댓글 수정
        $(document).on('click', '.modify', function(e){
            e.preventDefault();

            console.log('here1');
            let txt = $(this).text();
            p_tag = $(this).parent().prev();
                $(this).hide();
                $(this).next().css('display', 'inline-block');
                p_tag.attr('contentEditable', true);
                p_tag.focus();
        });
        // 댓글 수정 완료
        $(document).on('click', '.modifyComplete', function(e) {
            e.preventDefault();

            console.log('here2');
            let txt = $(this).text();

            if(txt == '등록'){
    				$(this).hide();
    				$(this).prev().show();

    				console.log('here3');

    				let no = $(this).attr('data-no');
    				let content = p_tag.text();

    				console.log(no);
    				console.log(content);

    				const jsonData = {
    						'no': no,
    						'content': content
    				};

    				console.log(jsonData);

    				$.ajax({
    					url : '/Farmstory/board/updateComment',
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

    			}
        });

        // 대댓글 작성
        $(document).on('click', '.reComment', function(e) {
            e.preventDefault();
            alert('답글 달기');

            $(this).parent().next().css('display', 'block');        // 대댓글 작성 폼 보이기
            $(this).hide();                                         //  '답글' 버튼 숨기기
            $(this).next().css('display', 'inline');                // '답글접기' 버튼 보이기
        });
        $(document).on('click', '.btnReComplete', function(e) {
            e.preventDefault();
            let parent = $(this).attr('data-parent');               // parent <- 게시글 번호
            let cno = $(this).attr('data-cno');                     // cno <- 댓글 번호
            let content = $(this).prev().val();
            let uid = $(this).prev().prev().prev().val();

            if(content == ''){ alert('내용을 입력해주세요.'); return false; }

            const jsonData = {
                'parent': parent,
                'cno': cno,
                'content': content,
                'uid': uid
            }
            console.log(jsonData);

            $.ajax({
                url: '/Farmstory/board/insertReComment',
                type: 'POST',
                data: jsonData,
                dataType: 'json',
                success: function(data){
                    console.log(data);
                    if(data.result == 1){
                        alert('등록되었습니다.');
                    }else {
                        console.log('error');
                    }
                }
            });
        });

        $(document).on('click', '.reCommentCancel', function(e) {
            e.preventDefault();
            alert('답글 접기');

            $(this).hide();
            $(this).prev().css('display', 'inline');
            $(this).parent().next().css('display', 'none');

        });

    });