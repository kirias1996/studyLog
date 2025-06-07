document.addEventListener('DOMContentLoaded', function() {
    const input = document.getElementById('iconImage');
    const errorMsg = document.getElementById('fileSizeError');
	errorMsg.textContent = '';

    input.addEventListener('change', function (event) {
		
		// すべての エラーメッセージを消去（テキストを空にする）
	    document.querySelectorAll('.alert.alert-error').forEach(el => {
	      el.textContent = '';
	    });
	
		const file = event.target.files[0];
	    const maxSizeInBytes = 5 * 1024 * 1024; // 5MB
		if (!file) {
			return;
		}

	    if (file.size > maxSizeInBytes) {
	      errorMsg.textContent = 'ファイルサイズは5MB未満にしてください。';
	      event.target.value = ''; // ファイル選択をクリア
	    }
	  
		
    });
});