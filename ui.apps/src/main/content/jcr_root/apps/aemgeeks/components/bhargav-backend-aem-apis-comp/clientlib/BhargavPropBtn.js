/*BhargavSeshadri : STEP : 2 - Here this js will look for our button click and, is clicked, it will execute our servlet. and after servlet execution,
                               it will get the response from it.
* For Step: 3 - com/aem/geeks/core/servlets/BhargavPropBtnPathServlet.java

* Main Flow of this JS file: User clicks button
                                ↓
                             Get resource path
                                ↓
                             Fetch CSRF token
                                ↓
                             Send POST request to servlet
                                ↓
                             Servlet updates JCR property
                                ↓
                             Servlet returns JSON
                                ↓
                             UI updates message

*
*/


document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".add-property-btn").forEach(button => {

        button.addEventListener("click", function () {   //When user clicks this function runs

            //dataset.resourcePath comes from HTL - so it will give resource path of the current node.
            const resourcePath = this.dataset.resourcePath;

            //This will get the next immediate Element to show status message
            const statusEl = this.nextElementSibling;

            /*This calls AEM endpoint that returns the CSRF token, then uses that token to make a POST request to our custom servlet to add the property,
              and finally updates the status message based on the response.*/
            fetch("/libs/granite/csrf/token.json")

            // this will Converts the received HTTP response → JSON object
              .then(res => res.json())

              //now data.token contains your CSRF token
              .then(data => {

              //this line Calls your servlet
                return fetch("/bhargav/addProperty", {
                  method: "POST",

                  //These headers are the metadata we send along with our request to the servlet. like instructions given to servlet
                  headers: {
                    "CSRF-Token": data.token,

                    //Tells server how data is formatted, You're sending form-style data (key=value)
                    "Content-Type": "application/x-www-form-urlencoded"
                  },

                  //Sends data to servlet, encodeURIComponent ensures safe URL encoding
                  body: "resourcePath=" + encodeURIComponent(resourcePath)
                });
              })

              //This handles the response from your servlet, converting it to JSON
              .then(res => res.json())

              //Now we check  data.status in the response and update the status message accordingly
              .then(data => {

              /*textContent : This sets the text inside an HTML element.
                Before : <div class="status"></div>
                If statusEl.textContent = "Added successfully",then the above div will look like below
                After : <div class="status">Added successfully</div>
              */
                statusEl.textContent =
                  data.status === "success" ? "Added successfully" : "Error";
              })
              .catch(() => {
                statusEl.textContent = "Request failed";
              });

        });

    });

});