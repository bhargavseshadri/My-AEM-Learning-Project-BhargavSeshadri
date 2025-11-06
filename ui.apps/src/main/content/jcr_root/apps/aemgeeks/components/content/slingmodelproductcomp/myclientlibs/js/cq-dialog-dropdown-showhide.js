(function ($, $document) {
    "use strict";

    $document.on("dialog-ready", function () {

        $(".cq-dialog-dropdown-showhide").each(function () {
            var $select = $(this);
            var target = $select.data("cq-dialog-dropdown-showhide-target");

            function showHideFields() {
                var value = $select.val();
                $(target).each(function () {
                    var $field = $(this);īō
                    if ($field.data("showhidetargetvalue") === value) {
                        $field.show();
                    } else {
                        $field.hide();
                    }
                });
            }

            // Initial call
            showHideFields();

            // On change
            $select.on("change", showHideFields);
        });
    });
})(Granite.$, jQuery(document));
