"use strict"

function chuanhoa(x) {
	if (x < 10) x = "0" + x;
	return x;
}

function getDate1() {
	var dayName = ["CN", "Thu2", "Thu3", "Thu4", "Thu5", "Thu6", "Thu7"];
	var yy, mo, dd, hh, mm, ss;
	var d = new Date();
	yy = d.getFullYear();
	mo = d.getMonth() + 1;
	dd = d.getDate();
	hh = d.getHours();
	mm = d.getMinutes();
	ss = d.getSeconds();
	mo = chuanhoa(mo);
	mm = chuanhoa(mm);
	ss = chuanhoa(ss);
	hh = chuanhoa(hh);
	dd = chuanhoa(dd);
	var day = d.getDay();
	var s = new String(dayName[day] + " " + dd + "/" + (mo) + "/" + yy + " " + hh + ":" + mm + ":" + ss);
	return s;
}
setInterval(function() {
	document.getElementById("demo").innerHTML = getDate1();
}, 1000);