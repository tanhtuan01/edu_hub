
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


function transferTextAreaToDiv(textAreaID, divID){
	if(!document.getElementById(textAreaID) || !document.getElementById(divID)){
		return
	}
	var textAreaId = document.getElementById(textAreaID).value
	document.getElementById(textAreaID).style.display = 'none'
	var divId = document.getElementById(divID)
	
	divId.innerHTML = textAreaId
}