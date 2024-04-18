document.onreadystatechange = function() {
    if (document.readyState === 'loading') {
        showLoadingSpinner();
    } else {
        hideLoadingSpinner();
    }

};


function removeParamatersOnUrl() {
	var url = window.location.href;

	if (url.indexOf('?') !== -1) {
		var cleanUrl = url.substring(0, url.indexOf('?'));
		window.history.replaceState({}, document.title, cleanUrl);
	}
}

window.onload = () => {
	/*removeParamatersOnUrl()*/
}

window.addEventListener('beforeunload', function() {
      removeParamatersOnUrl()
});


function validateFormAndNoSubmit(e) {
    event.preventDefault();

    var form = e.closest("form");

    function checkInputField(selector, message) {
        if (form.querySelector(selector)) {
            var value = form.querySelector(selector).value;
            if (value.trim().length == 0) {
                form.querySelector(selector).parentElement.querySelector(".text-error").textContent = message;
                form.querySelector(selector).style.borderBottom = '1px solid red';
                return false;
            } else {
				console.log(form.querySelector("#email"))
                form.querySelector(selector).parentElement.querySelector(".text-error").textContent = '';
                form.querySelector(selector).style.borderBottom = 'none';
                return true;
            }
        }
        // Trường hợp không tồn tại selector trong form
        return true;
    }

    if (!checkInputField("#name", "Tên không được để trống")) {
        return;
    }

    if (!checkInputField("#domain", "Domain không được để trống")) {
        return;
    }

    if(!checkInputField("#email", "Email không được để trống")){
      return;
    }



    if(form.querySelector("#email")){
      var emailvalue = document.getElementById("email").value.trim()

      if(validateEmail(emailvalue)){
        form.querySelector("#email").parentElement.querySelector(".text-error").textContent = "";
        form.querySelector("#email").style.borderBottom = 'none';
      }else{
        form.querySelector("#email").parentElement.querySelector(".text-error").textContent = "Email không hợp lệ";
        form.querySelector("#email").style.borderBottom = '1px solid red';
        return
      }
    }

    if (!checkInputField("#phone", "Số điện thoại không được để trống")) {
        return;
    }

    if(form.querySelector("#phone")){
      var phoneValue = document.getElementById("phone").value

      if(phoneValue.length >= 10 && phoneValue.length <= 11 && phoneValue.startsWith("0")){
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "";
        form.querySelector("#phone").style.borderBottom = 'none';
      }else {
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "Số điện thoại không hợp lệ";
        form.querySelector("#phone").style.borderBottom = '1px solid red';
        return
      }
    }

    if (!checkInputField("#pass", "Mật khẩu không được để trống") || !checkInputField("#repass", "Bạn chưa nhập lại mật khẩu")) {
        return;
    }

    if (form.querySelector("#pass") && form.querySelector("#repass")) {
        var valuePass = form.querySelector("#pass").value.trim();
        var valueRepass = form.querySelector("#repass").value.trim();
        if (valuePass != valueRepass) {
            form.querySelector(".text-alert").classList.add("alert-warning");
            form.querySelector(".text-alert").textContent = 'Mật khẩu không khớp';
            return;
        } else {
            form.querySelector(".text-alert").classList.remove("alert-warning");
        }
    }

   	return true
}

const validateEmail = (email) => {
  return /^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(email);
};


function validateFormAndSendEmail(e){
	 var isFormValid = validateFormAndNoSubmit(e);
	 
	 if(isFormValid){
		 showLoadingSpinner()
		 e.closest("form").submit()
	 }

}

function validateFormAndSubmit(e){
	var isFormValid = validateFormAndNoSubmit(e);
	 
	 if(isFormValid){
		 e.closest("form").submit()
	 }
}






function chooseImage(e, inputName) {
		event.preventDefault()
    var fileInput = $("<input type='file' accept='image/*'>").hide();

    var uploadedImage = e.closest("form").querySelector("#uploaded-image");
    fileInput.change(function() {
        var file = this.files[0];
        var allowedTypes = ["image/jpeg", "image/png", "image/gif"];

        if (allowedTypes.includes(file.type)) {
            var reader = new FileReader();
            reader.onload = function(e) {
                // Thay đổi giá trị của thuộc tính src của hình ảnh và hiển thị hình ảnh
                uploadedImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
            
            // Gán tệp ảnh đã chọn vào trường input ẩn
            var hiddenInput = document.querySelector(`input[type='file'][name='${inputName}']`);
            hiddenInput.files = fileInput[0].files;
        } else {
            // Xử lý lỗi khi tệp không phải định dạng ảnh
            alert("Vui lòng chọn một tệp ảnh có định dạng JPEG, PNG hoặc GIF.");
        }
    });
    
    // Kích hoạt sự kiện click trên input[type=file] ẩn
    fileInput.click();
   // e.closest("#image-container").querySelector(".errorLoadImg").style.display = "none"
}
