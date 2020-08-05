var main = {
    init : function(){
        var _this = this; //this는 main 변수.
        $('#btn-save').on('click', function(){
            _this.save(); //save()는 아래에 정의한 save 함수
        });
    },
    save : function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(err){
            alert(JSON.stringify(err));
        });
    }
};

main.init();