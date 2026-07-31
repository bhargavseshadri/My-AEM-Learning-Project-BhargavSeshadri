document.addEventListener("DOMContentLoaded", function () {

    const button = document.getElementById("btnSearch");
    const input = document.getElementById("firstName");
    const result = document.getElementById("result");

    button.addEventListener("click", function () {

        const firstName = input.value.trim();

        if (!firstName) {
            result.innerHTML = "<span style='color:red'>Please enter a name.</span>";
            return;
        }

        const resourcePath = button.dataset.resourcePath;


        fetch("/libs/granite/csrf/token.json")
            .then(res => res.json())
            .then(data => {
               return fetch(resourcePath + ".userlookup.json", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "CSRF-Token": data.token
                    },
                    body: "firstName=" + encodeURIComponent(firstName)
                })
                })
        .then(function (response) {

            if (!response.ok) {
                throw new Error("HTTP Error : " + response.status);
            }

            return response.json();
        })
        .then(function (data) {

            if (data.email) {
                result.innerHTML = `
                    <div class="success">
                        <h3>Email Found</h3>
                        <p>${data.email}</p>
                    </div>
                `;

            } else if (data.message) {

                result.innerHTML = `
                    <div class="error">
                        ${data.message}
                    </div>
                `;

            } else {

                result.innerHTML = `
                    <div class="error">
                        Something went wrong.
                    </div>
                `;
            }

        })
        .catch(function (error) {

            console.error(error);

            result.innerHTML = `
                <div class="error">
                    Error while calling servlet.
                </div>
            `;
        });

    });

});