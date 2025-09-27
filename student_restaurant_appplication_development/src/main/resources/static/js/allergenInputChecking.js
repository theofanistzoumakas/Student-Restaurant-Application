if(document.getElementById("allergenForm")) {
    document.getElementById("allergenForm").addEventListener("submit", function (event) {

        checkEmptyInputs(event, "allergenForm");

    });
}

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

    if(!checked){

        event.preventDefault();
        alert("Παρακαλώ δείτε ξανά τη Φόρμα!");

    }
}