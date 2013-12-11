function confirm() {
	//ボタンを押した際の確認ダイアログ
	myret = confirm('登録情報を削除してもよろしいですか？');
	if(myret == true) {
		//OKをしたときの処理
		//削除した事を知らせる画面に遷移
		location.href = "students.html";
	} else {
		//キャンセルしたときの処理
		window.alert('削除をキャンセルしました。');
	}
}
