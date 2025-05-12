document.addEventListener('DOMContentLoaded', function() {
    const input = document.getElementById('tagInput');
    const suggestionList = document.getElementById('suggestionList');

    input.addEventListener('keyup', async function() {
		console.log('入力検知:', input.value);
        const keyword = input.value.trim();
        
        // 入力が空ならサジェストをクリア
        if (keyword === '') {
            suggestionList.innerHTML = '';
            return;
        }

        try {
            // fetchでAjaxリクエスト
            const response = await fetch(`/tags/search?keyword=${encodeURIComponent(keyword)}`);
			console.log('fetch完了：', response);
            if (!response.ok) {
                console.error('通信エラー:', response.status);
                return;
            }

            const tags = await response.json();

            // サジェストリストを更新
            suggestionList.innerHTML = '';
            tags.forEach(tag => {
                const li = document.createElement('li');
                li.textContent = capitalizeFirstLetter(tag.tagName);
                li.addEventListener('click', function() {
                    input.value = li.textContent;  // クリックしたタグを入力欄にセット
                    suggestionList.innerHTML = ''; // サジェストをクリア
                });
                suggestionList.appendChild(li);
            });
        } catch (error) {
            console.error('エラー発生:', error);
        }
    });

    // 先頭文字だけ大文字にするヘルパー関数
    function capitalizeFirstLetter(str) {
        if (!str) return '';
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
});