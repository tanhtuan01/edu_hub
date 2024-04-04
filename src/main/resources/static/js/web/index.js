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