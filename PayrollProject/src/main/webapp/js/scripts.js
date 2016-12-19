function showElementsOfRole(role) {
	var roleClass = role.toLowerCase();
	var divs = document.getElementsByClassName(roleClass);
	for(var i=0; i<divs.length; i++) { 
	  divs[i].style.display='block'
	}
}