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
	var cmActionValue = document.getElementById("cmAction").value

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

	var selector = `.${cmActionValue}-form`
	document.querySelector(selector).classList.remove("hidden")
}