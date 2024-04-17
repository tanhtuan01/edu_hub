function changeCM(e){
	event.preventDefault()
	var target = e.dataset.cm

	var cm = document.querySelectorAll('[data-cm]')
	cm.forEach((c)=>{
		c.classList.remove("active")
	})
	e.classList.add("active")

	var cmBox = document.querySelectorAll(".cm-right .cm-box")
	cmBox.forEach((c)=>{
		c.classList.add("hidden")
	});

	var selector = `.${target}-form`
	document.querySelector(selector).classList.remove("hidden")

}
toChangeCm()
function toChangeCm(){
	if(!document.getElementById("cmAction")){
		return
	}
	var cmActionValue = document.getElementById("cmAction").value
	console.log("cmActionValue: " + cmActionValue);
	var cm = document.querySelectorAll('[data-cm]')
	cm.forEach((c)=>{
		if(c.dataset.cm == cmActionValue){
			c.classList.add("active")
		}else{
			c.classList.remove("active")
		}
	})


	var cmBox = document.querySelectorAll(".cm-right .cm-box")
	cmBox.forEach((c)=>{
		c.classList.add("hidden")
	});
	
	if(cmActionValue == 'edit'){
		cmActionValue = 'add'
		document.querySelector("[data-cm='add']").classList.add("active")
	}

	var selector = `.${cmActionValue}-form`
	console.log(selector)
	document.querySelector(selector).classList.remove("hidden")
}



