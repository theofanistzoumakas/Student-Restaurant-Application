document.getElementById("addMenuForm").addEventListener("submit",function(event){

    checkEmptyInputs(event,"addMenuForm");

});

function checkEmptyInputs(event,formId){
    let form = document.getElementById(formId);

    let inputs = form.querySelectorAll("input");

    let checked = true;

    inputs.forEach(input => {

        if(input.value.trim() === ""){

            checked = false;
            input.style.border = "2px solid red";
        } else {
            input.style.border = "";
        }


    });


    let textareas = form.querySelectorAll("textarea");

    textareas.forEach(textarea => {

        if(textarea.value.trim() === ""){

            checked = false;
            textarea.style.border = "2px solid red";
        } else {
            textarea.style.border = "";
        }


    });

    if(!checked){

        event.preventDefault();
        alert("Παρακαλώ δείτε ξανά τη Φόρμα!");

    }
}