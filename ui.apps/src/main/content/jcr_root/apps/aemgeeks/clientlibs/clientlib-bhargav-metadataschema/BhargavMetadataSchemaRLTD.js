/*BhargavSeshadri : Step : 2 - create JS for fetch calls, eventlisteners.
                               : apps/aemgeeks/clientlibs/clientlib-bhargav-metadataschema/BhargavMetadataSchemaRLTD.js

Step : 1 - creating the clientlib we are on.
Step : 3 - com/aem/geeks/core/servlets/BhargavMetadataSchemaPathServlet.java
*/


(function () {
    "use strict";

    function initUserIdField() {

         //In AEM metadata editor, fields are identified using name.
        var userIdField = document.querySelector("[name='./jcr:content/metadata/userId']");
        console.log("Bhargav - userIdField : ", userIdField); //This will give complete element where the element have "/jcr:content/metadata/userId"

        if (!userIdField) {
            console.log("Bhargav - userIdField not found, retrying...");
            return false;
        }

        userIdField.addEventListener("change", function () {

            //the will give the id number entered in the field
            var userId = this.value.trim();
            console.log("Bhargav - userId : " + userId);

            if (!userId) {
                return;
            }

            fetch("/bhargav/userdetails?id=" + userId, { method: "GET" })
                .then(function (response) {
                      //here the response is the API headers type of things :
                     //Eg : Response{type: 'basic', url: 'http://localhost:4502/bhargav/userdetails?id=3', redirected: false, status: 200, ok: true,…}
                    if (!response.ok) {
                        throw new Error("API failed");
                    }
                    //Converts JSON response body into JS object
                    return response.json();
                })
                .then(function (data) {
                    console.log("Bhargav - API response : ", data);
                    //Data : it will give what response we are sending from out servlet -> here fullname -: name : "Sophia Brown"

                    if (data && data.name) {
                        var userNameField = document.querySelector("[name='./jcr:content/metadata/userName']"); //this will get the complete element like above.
                        if (userNameField) {
                            // and below line ,we fill that field using the above data.name as the value.
                            userNameField.value = data.name;
                            console.log("Bhargav - userName set : " + data.name);
                        }
                    }
                })
                .catch(function (error) {
                    console.error("Bhargav - Fetch error : ", error);
                });
        });
        return true;
    }

    // Try immediately — fields may already be in DOM
    if (!initUserIdField()) { //if initUserIdField() methiod is false then it enters into this.

        // Coral UI may still be initializing, so poll briefly as fallback, this fallback is important
        var attempts = 0;

        //This SetInterval repeatedly executes for every 500ms, until we stop using "clearInterval"
        var interval = setInterval(function () {
            attempts++;
            console.log("Bhargav - Retry attempt : " + attempts);

            //Here exactly we are calling the method again, if it returns false.
            var isFieldFound = initUserIdField();

            if (isFieldFound || attempts >= 10) { //enters in to this if, only if isFieldFound is true or attempts > 10
                clearInterval(interval); //This will stop repeated retrying, avoids infinite loops.
            }
        }, 500);
    }

})();