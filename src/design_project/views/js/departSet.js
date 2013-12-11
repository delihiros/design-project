function departSet(str) {
	var doc = document.stereotype.conditions;
	if (str == "male") {
		doc.length = 2;
		doc.options[0].text = "年収";
		doc.options[1].text = "学歴";
	} else {
		doc.length = 2;
		doc.options[0].text = "容姿";
		doc.options[1].text = "教養";
	}
	doc.options[0].selected = true;
}