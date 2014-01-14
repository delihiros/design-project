# design-project

FIXME

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## View and its Routing
:any
index.html

:student
Stutop.html
profile.html
eventN.html
profileedit.html

:university
:graduate

evedetails.html
event.html
explain.html
schooltop.html
studetails.html
syomeisyo.html
edit.html
evenewentry.html
eventN.html
index.html
logout.html
profileedit.html
students.html
stunewentry.html

## License

Copyright © 2013 FIXME

## 各種ページの説明
- schooltop.html
権限が学校としてlogin後、一番始めに表示されるページ。必要な機能は特になし。

-students.html
schooltop.htmlなどから遷移するページ。ここでは学生の検索、検索結果の表示、新規学生登録への遷移が行える。必要な機能として、検索結果をリストで一覧表示する機能、検索にヒットした数を表示する機能、selectを動的に作成する機能が必要。

-studetails.html
students.htmlの検索結果をクリックすると遷移する。学生の詳細情報を閲覧するページ。学生の写真や基本情報、在学生情報、説明会の参加者情報を表示する機能が必要。また編集ボタンを押すと編集ページへ遷移し、その際に既に入力されている情報を編集ページに引き継ぐ。削除ボタンを押すとデータが削除される機能が必要。

-stunewentry.html
学生を新規登録するページ。students.htmlの新規学生登録から遷移。写真を登録する場合、選択したファイルのサムネイルを表示する機能が必要。また各入力欄で不正な値が入力されていないかチェックすることが必要。入力した情報を登録する機能が必要。

-studelete.html,evedelete.html
データを削除した際に遷移するページ。特に必要はないので使いたかったら使う程度で。

-event.html
schooltop.htmlなどから遷移するページ。ここではイベントの検索、検索結果の表示、新規イベントの登録への遷移が行える。必要な機能として、検索結果をリストで一覧表示する機能、検索にヒットした数を表示する機能、selectを動的に作成する機能が必要。

-evedetails.html
event,htmlの検索結果をクリックすると遷移する。イベントの詳細情報を閲覧するページ。イベントに必要な情報が必要(開催日、時間など)。またアクセスマップを登録しているが、面倒ならば消します。編集ボタンを押すと編集ページへ遷移しその際に既に入力されている情報を編集ページに引き継ぐ。削除ボタンを押すとデータが削除される機能が必要。

-evenewentry.html
イベントを新規登録するページ。event.htmlの新規イベント登録から遷移。各入力欄で不正な値が入力されていないかチェックすることが必要。google map埋め込みようURLはアクセスマップを使用するならば、ここにurlを入力することでアクセスマップを登録できるようにする機能が必要。

-編集ページ eveedit.html(仮),stuedit.html(仮)
studetails.htmlやevedetails.htmlから遷移するページ。まだ作っていないのですぐ作ります。
必要な機能として既存の項目を編集することを目的としているので、既存の項目を各入力欄に引き継ぐ機能が欲しい。
編集が完了したら登録する機能が欲しい。



# MySQLのもじこーど
	sudo vim /etc/mysql/my.cnf 

	[mysqld]
	character-set-server = utf8

	sudo /etc/init.d/mysql restart

