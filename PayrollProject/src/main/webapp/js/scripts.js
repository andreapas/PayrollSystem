function showElementsOfRole(role) {
	var roleClass = role.toLowerCase();
	var divs = document.getElementsByClassName(roleClass);
	for(var i=0; i<divs.length; i++) { 
		divs[i].style.display='block';
	}
}

function hideDivsByClass(divClass) {
	var divs = document.getElementsByClassName(divClass);
	for(var i=0; i<divs.length; i++) { 
		divs[i].style.display='none';
	}
}

function showDivsByClass(divClass) {
	var divs = document.getElementsByClassName(divClass);
	for(var i=0; i<divs.length; i++) { 
		divs[i].style.display='block';
	}
}

function showOnlyDivByClass(divClass, excludeClass) {
	hideDivsByClass(excludeClass);
	showDivsByClass(divClass);
}
