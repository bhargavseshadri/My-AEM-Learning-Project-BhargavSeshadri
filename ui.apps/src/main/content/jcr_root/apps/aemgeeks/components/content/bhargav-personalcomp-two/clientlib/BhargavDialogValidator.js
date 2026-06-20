/*Here the main logic we follow is We attach a validator name using granite:data. AEM renders it as a data attribute on the field.
In a clientlib, we register a validator using Granite.UI.Foundation.Registry.register(). When the author clicks Done, AEM scans fields matching
the selector, invokes the validate method, "and if the method returns a string", the dialog save is blocked and the string is displayed as the
validation message. If nothing is returned, validation passes and the dialog is saved.*/


//BhargavSeshadri - Step: 2
//Step: 1 - crate a dialog field - apps/aemgeeks/components/content/bhargav-personalcomp-two/_cq_dialog/.content.xml

/*So based of ourr code in the dialog xml, AEM now knows "Whenever validation happens, run the validator named cta-validator on these fields."
  So this file gets executed.
*/
(function (document, Granite) { //here the document is the complete dom object incase if we use it.
    "use strict";

    console.log("Seshadri - Entered in to the function");

    function getDialog(element) {   //This will give the complete markup in side the element <coral-dialog>--Everything here--</coral-dialog>
        return element.closest("coral-dialog");
    }

    function getTrimmedFieldValue(dialog, selector) {
        //Now we have a complete html markup inside "coral-dialog", now we go to our exact field element using its selector - name="./ctaLink"
        const field = dialog.querySelector(selector);

		console.log("Seshadri - getTrimmedFieldValue = field  = "+field);

        return field && field.value ? field.value.trim() : "";  // after we got hold of the element then we read the value given to it
    }

    function isOnlyOneValueFilled(firstValue, secondValue) {
        console.log("Seshadri - isOnlyOneValueFilled == "+ firstValue +" || "+secondValue );
        return (firstValue && !secondValue) || (!firstValue && secondValue);  // this is just a validation of which fields are filled.
    }

    function validateCtaFields(element) {
        const dialog = getDialog(element);
        const ctaText = getTrimmedFieldValue(dialog, "[name='./ctaText']"); //This meand - "Find an element whose name attribute equals ./ctaText." this is called attribute selector.
        const ctaLink = getTrimmedFieldValue(dialog, "[name='./ctaLink']");
        console.log("Seshadri - ctaText = "+ctaText);
        console.log("Seshadri - ctaLink = "+ctaLink);

        if (isOnlyOneValueFilled(ctaText, ctaLink)) {
        //IMP LINE : so for suppose if this string is returned then GRANITE stops our dialog to get saved and shows this string, if nothing returned then the dialog simply saves
            return "CTA Text and CTA Link must both be filled or both be empty.";
        }
    }


    /*Validator Registration - This is the most important line
     -> This tells AEM Whenever you find any field matching [data-validate='cta-validator'] execute validateCtaFields().
    */
    Granite.UI.Foundation.Registry.register("foundation.validation.validator", {
    //IMP: here we are telling AEM to find the field with data-validate='cta-validator' and execute validateCtaFields() function.
        selector: "[data-validate='cta-validator']",
        validate: validateCtaFields
    });
})(document, Granite);