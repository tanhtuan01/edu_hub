function checkInputNumber(event) {
    var value = event.key
    stopArrowUpDown(event)
    if (value === 'e' || value === '.' || value === '-') {
        event.preventDefault()
    }
}

function stopArrowUpDown(event) {
    if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
        event.preventDefault()
    }
}

function inputPasswordToText(e) {
    var inputType = e.parentElement.querySelector("input").type;

    e.classList.toggle("active")
    if (inputType === 'text') {
        e.parentElement.querySelector("input").type = 'password';
    } else {
        e.parentElement.querySelector("input").type = 'text';
    }
}


const validateEmails = (email) => {
  return /^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(email);
};


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

      if(validateEmails(emailvalue)){
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

      if(phoneValue.length >= 10 && phoneValue.length <= 11){
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "";
        form.querySelector("#phone").style.borderBottom = 'none';
      }else {
        form.querySelector("#phone").parentElement.querySelector(".text-error").textContent = "Số điện thoại không hợp kệ";
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

    form.submit();
}



/*INDEX*/
 getStartScore()

    function getStartScore() {
        const star = document.querySelectorAll("#khoa-hoc .vote .vote-score")
        if (star) {
            star.forEach((s) => {
                generateStars(s.innerText, s.parentElement.querySelector(".vote-star"))
            });
        }
    }


    function generateStars(score, container) {

        const votingValue = score

        const fullStars = Math.floor(votingValue);
        const decimalPart = votingValue % 1;

        const starsContainer = container;
        starsContainer.innerHTML = "";

        for (let i = 0; i < fullStars; i++) {
            const starSvg = `<svg class="star" viewBox="0 0 24 24">
                      <path d="M12 2L15.09 8.36L22 9.27L17 13.14L18.18 20L12 17.77L5.82 20L7 13.14L2 9.27L8.91 8.36L12 2Z" />
                    </svg>`;
            starsContainer.innerHTML += starSvg;
        }

        if (decimalPart > 0 && decimalPart <= 0.5) {
            const halfStarSvg = `<svg class="star half-star" viewBox="0 0 24 24">
                          <defs>
                            <mask id="half-star-mask">
                              <rect x="0" y="0" width="12" height="24" fill="white" />
                            </mask>
                          </defs>
                          <path d="M12 2L15.09 8.36L22 9.27L17 13.14L18.18 20L12 17.77L5.82 20L7 13.14L2 9.27L8.91 8.36L12 2Z" mask="url(#half-star-mask)" />
                        </svg>`;
            starsContainer.innerHTML += halfStarSvg;
        } else if (decimalPart > 0.5) {
            const fullStarSvg = `<svg class="star" viewBox="0 0 24 24">
                          <path d="M12 2L15.09 8.36L22 9.27L17 13.14L18.18 20L12 17.77L5.82 20L7 13.14L2 9.27L8.91 8.36L12 2Z" />
                        </svg>`;
            starsContainer.innerHTML += fullStarSvg;
        }
    }