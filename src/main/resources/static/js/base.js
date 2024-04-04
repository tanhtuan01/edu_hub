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
	removeParamatersOnUrl()
}



function validateForm(e) {
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
        form.querySelector("#email").parentElement.querySelector(".text-error").textContent = "Email không hợp kệ";
        form.querySelector("#email").style.borderBottom = '1px solid red';
        return
      }
    }

    if (!checkInputField("#phone", "Số điện thoại không được để trống")) {
        return;
    }

    if(form.querySelector("#phone")){
      var phoneValue = document.getElementById("phone").value

      if(phoneValue.length >= 10 && phoneValue.length <= 11){
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "";
        form.querySelector("#phone").style.borderBottom = 'none';
      }else {
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "Số điện thoại không hợp kệ";
        form.querySelector("#phone").style.borderBottom = '1px solid red';
        return
      }
    }

    if (!checkInputField("#pass", "Mật khẩu không được để trống") || !checkInputField("#repass", "Mật khẩu không được để trống")) {
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

    form.submit();
}

const validateEmail = (email) => {
  return /^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(email);
};
