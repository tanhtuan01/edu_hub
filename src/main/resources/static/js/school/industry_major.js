function changeCM(e) {
	event.preventDefault()

	if (document.querySelector(".modal")) {
		document.querySelector(".modal").classList.remove('active')
	}

	var target = e.dataset.cm

	var cm = document.querySelectorAll('[data-cm]')
	cm.forEach((c) => {
		c.classList.remove("active")
	})
	e.classList.add("active")

	var cmBox = document.querySelectorAll(".cm-right .cm-box")
	cmBox.forEach((c) => {
		c.classList.add("hidden")
	});

	var selector = `.${target}-form`
	document.querySelector(selector).classList.remove("hidden")

}
toChangeCm()
function toChangeCm() {
	if (!document.getElementById("cmAction")) {
		return
	}
	var cmActionValue = document.getElementById("cmAction").value
	console.log("cmActionValue: " + cmActionValue);
	var cm = document.querySelectorAll('[data-cm]')
	cm.forEach((c) => {
		if (c.dataset.cm == cmActionValue) {
			c.classList.add("active")
		} else {
			c.classList.remove("active")
		}
	})


	var cmBox = document.querySelectorAll(".cm-right .cm-box")
	cmBox.forEach((c) => {
		c.classList.add("hidden")
	});

	if (cmActionValue == 'edit') {
		cmActionValue = 'add'
		document.querySelector("[data-cm='add']").classList.add("active")
	}

	var selector = `.${cmActionValue}-form`
	console.log(selector)
	document.querySelector(selector).classList.remove("hidden")
}

onloadGetMajorOfIndustry()

function onloadGetMajorOfIndustry(){
	if(!document.getElementById("majorIndustry")){
		return
	}
	
	const form = document.getElementById("majorIndustry")
	const value = form.querySelector("select[name='ndt']").value

	const items = form.querySelectorAll('.majors .item-major')
	items.forEach((s) => {
		s.style.display = "none"
	})
	const majorItems = form.querySelectorAll(`.majors .item-major.major-of-industry-${value}`)
	const majors = form.querySelector(".majors")
	if(majors.querySelector(".item-major.none")){
		var child = majors.querySelector(".item-major.none")
		majors.removeChild(child)
	}
	if (majorItems.length > 0) {
	
		var option = document.createElement("option")
		option.className = "item-major none"
		option.textContent = "Chọn chuyên ngành"
		option.value = 0
		majors.appendChild(option)
		/*majors.value = option.value*/
		majorItems.forEach((m) => {
			m.style.display = "block"
			var value = m.getAttribute("value")
		
			var cndt = m.getAttribute("data-cndt")
			
			if(value == cndt){
				m.setAttribute("selected", true)
			}
		})
		
	}else{
		var option = document.createElement("option")
		option.className = "item-major none"
		option.textContent = "Không có chuyên ngành phù hợp"
		option.value = 0
		majors.appendChild(option)
		majors.value = option.value
	}
	
}

function getMajorOfIndustry(e) {
	const value = e.value
	const form = e.closest("form")

	const items = form.querySelectorAll('.majors .item-major')
	items.forEach((s) => {
		s.style.display = "none"
	})
	const majorItems = form.querySelectorAll(`.majors .item-major.major-of-industry-${value}`)
	const majors = form.querySelector(".majors")
	if(majors.querySelector(".item-major.none")){
		var child = majors.querySelector(".item-major.none")
		majors.removeChild(child)
	}
	if (majorItems.length > 0) {
	
		var option = document.createElement("option")
		option.className = "item-major none"
		option.textContent = "Chọn chuyên ngành"
		option.value = 0
		majors.appendChild(option)
		majors.value = option.value
		majorItems.forEach((m) => {
			m.style.display = "block"
		})
	}else{
		var option = document.createElement("option")
		option.className = "item-major none"
		option.textContent = "Không có chuyên ngành phù hợp"
		option.value = 0
		majors.appendChild(option)
		majors.value = option.value
	}

}
