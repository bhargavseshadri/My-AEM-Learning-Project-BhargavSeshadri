document.addEventListener("dialog-ready", function () {
  const dropdowns = document.querySelectorAll(".cq-dialog-dropdown-showhide");

  dropdowns.forEach((dropdown) => {
    const targetSelector = dropdown.dataset.cqDialogDropdownShowhideTarget;

    function updateVisibility() {
      const value = dropdown.value;
      const targets = document.querySelectorAll(targetSelector);

      targets.forEach((field) => {
        const showValue = field.dataset.showhidetargetvalue;
        if (showValue === value) {
          field.style.display = "block";
        } else {
          field.style.display = "none";
        }
      });
    }

    // Run on load
    updateVisibility();

    // Listen for changes
    dropdown.addEventListener("change", updateVisibility);
  });
});