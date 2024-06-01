var urlOrigin = window.location.origin
scrollInViewTP()

function scrollInViewTP() {
	if (document.querySelector(".content-ctdt")) {
		const container = document.querySelector('.content-ctdt');
		const sections = Array.from(container.querySelectorAll('.eh-header-title.sub'));
		container.addEventListener('scroll', function() {
			const containerScrollTop = container.scrollTop;
			const containerHeight = container.offsetHeight;
			const visibleSection = sections.find(function(section) {
				const sectionOffsetTop = section.offsetTop;
				const sectionHeight = section.offsetHeight;
				return (
					sectionOffsetTop >= containerScrollTop &&
					sectionOffsetTop + sectionHeight <= containerScrollTop + containerHeight);
			});
			if (visibleSection) {
				const sectionId = visibleSection.getAttribute('id');
				const url = new URL(window.location.href);
				url.hash = sectionId;
				const menu = document.querySelectorAll('a[href^="#" ] .i')
				menu.forEach((m) => {
					m.classList.remove("active")
				})

				window.history.replaceState({}, '', url);
				var selector = `a[href^="#${sectionId}"] .i`
				document.querySelector(selector).classList.add("active")
			}
		});
	}
}

transferTextAreaToDiv("TXT-MUCTIEUCHUNG", "CONTENT-MUCTIEUCHUNG")
transferTextAreaToDiv("TXT-MUCTIEUCUTHE", "CONTENT-MUCTIEUCUTHE")
transferTextAreaToDiv("TXT-CHUANDAURA", "CONTENT-CHUANDAURA")
transferTextAreaToDiv("TXT-COHOINGHENGHIEP", "CONTENT-COHOINGHENGHIEP")
transferTextAreaToDiv("TXT-DOITUONGTUYENSINH", "CONTENT-DOITUONGTUYENSINH")
transferTextAreaToDiv("TXT-QUYTRINHDAOTAO", "CONTENT-QUYTRINHDAOTAO")
transferTextAreaToDiv("TXT-DOITUONGTOTNGHIEP", "CONTENT-DOITUONGTOTNGHIEP")


function transferTextAreaToDiv(textAreaID, divID) {
	if (!document.getElementById(textAreaID) || !document.getElementById(divID)) {
		return
	}
	var textAreaId = document.getElementById(textAreaID).value
	document.getElementById(textAreaID).style.display = 'none'
	var divId = document.getElementById(divID)

	divId.innerHTML = textAreaId
}


window.onload = function() {

	showListMajorByIndustry()

};


function showListMajorByIndustry() {

	try {
		var industryItems = document.getElementsByClassName('item-industry');
		var majorItems = document.getElementsByClassName('item-major');

		for (var i = 0; i < industryItems.length; i++) {
			industryItems[i].addEventListener('click', function() {
				// Xóa lớp 'active' khỏi tất cả các danh mục
				for (var j = 0; j < industryItems.length; j++) {
					industryItems[j].classList.remove('active');
				}

				this.classList.add('active');

				for (var k = 0; k < majorItems.length; k++) {
					majorItems[k].style.display = 'none';
					majorItems[k].classList.remove("active")
				}

				var selectedIndustryId = this.getAttribute('data-industry');
				var selectedMajor = document.getElementsByClassName('major-of-industry-' + selectedIndustryId);
				for (var l = 0; l < selectedMajor.length; l++) {
					selectedMajor[l].style.display = 'block';
				}

				var idIndustry = this.getAttribute("data-industry")
				var href = `${urlOrigin}/uc/api/training-program/industry/${idIndustry}`

				var xmlHttp = new XMLHttpRequest()
				xmlHttp.onreadystatechange = function() {
					if (this.readyState == 4) {
						if (this.status == 200) {
							var responseData = JSON.parse(this.responseText)

							parseResponseDataToView("#boxListTp .list", responseData)
						}
					}
				}
				xmlHttp.open("GET", href, true)
				xmlHttp.send()
			});
		}
	} catch (error) {
		/*console.error(error);*/
	}


}

function getModuleByMajor(e) {
	var idMajor = e.getAttribute('data-major')

	var majorItems = document.querySelectorAll('.item-major');
	majorItems.forEach((s) => {
		s.classList.remove("active")
	})
	e.classList.add("active")

	var idMajor = e.getAttribute("data-major")

	var href = `${urlOrigin}/uc/api/training-program/major/${idMajor}`

	var xmlHttp = new XMLHttpRequest()
	xmlHttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				var responseData = JSON.parse(this.responseText)

				parseResponseDataToView("#boxListTp .list", responseData)
			}
		}
	}
	xmlHttp.open("GET", href, true)
	xmlHttp.send()
}

function parseResponseDataToView(selector, responseData) {

	var box = document.querySelector(selector)
	document.querySelector(`${selector}`).innerHTML = ""
	if(document.querySelector(".p-mess")){
		document.querySelector(".p-mess").remove()
	}
	if (responseData.length == 0) {
		var pMess = document.createElement("p")
		pMess.className = "p-mess text-alert text-center"
		pMess.textContent = "Không tìm thấy chương trình đào tạo phù hợp"
		document.querySelector("#boxListTp").appendChild(pMess)
		console.log(document.querySelector("#boxListTp"))
	}
	
	document.querySelector("#boxListTp").style.display = "block"

	responseData.forEach((i) => {
		var item = document.createElement("div")
		item.className = "item"
		var name = document.createElement("div")
		name.className = "name"
		name.textContent = i.name

		var level = document.createElement("div")
		level.className = "level"
		var levelText;
		switch (i.level) {
			case "level_undergraduate":
				levelText = "Đại học";
				break;
			case "level_master_degree":
				levelText = "Thạc sĩ";
				break;
			case "level_doctorate":
				levelText = "Tiến sĩ";
				break;
		}
		level.textContent = "Trình độ đào tạo: " + levelText

		var cohort = document.createElement("div")
		cohort.className = "cohort"
		cohort.textContent = `Khóa đào tạo: ${i.cohort}`

		var time = document.createElement("div")
		time.className = "time"
		time.textContent = `Thời gian đào tạo: ${i.duration} năm`

		var button = document.createElement("div")
		button.className = "button"

		var btnView = document.createElement("div")

		btnView.innerHTML = `<a class="btn btn-view" href="${i.domain}/chuong-trinh-dao-tao?ct=${i.name}&id=${i.id}"><i class="fa-solid fa-eye"></i> Xem</a>`

		button.appendChild(btnView)

		item.appendChild(name)
		item.appendChild(level)
		item.appendChild(cohort)
		item.appendChild(time)
		item.appendChild(button)
		box.appendChild(item)
	})
}