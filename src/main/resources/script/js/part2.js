$(document).ready(function() {
    // register handler
    $("#createForm").onSubmit = event => {
        event.preventDefault();
    };
    $("#viewForm").onSubmit = event => {
        event.preventDefault();
    };
});
console.log("Found Me! JS File Included");