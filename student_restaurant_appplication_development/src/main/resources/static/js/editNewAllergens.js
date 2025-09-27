document.querySelectorAll("button").forEach(button =>{

    button.addEventListener("click",function(){

        const buttonId = this.getAttribute("id");

        if(buttonId === "addAllergenButtonId") {//create new allergen
            let wrapper = document.createElement("div");

            wrapper.className="d-flex align-items-center mx-auto w-75";

            let container = document.getElementById('allergensAdmin');

            let newInput = document.createElement("input");

            newInput.className="form-control";

            newInput.type = "text";

            newInput.name = "allergens[]";

            newInput.placeholder = "Πληκτρολογήστε το Όνομα ενός Αλλεργιογόνου"

            let remove_button = document.createElement("button");

            remove_button.type = "button";
            remove_button.textContent = "Αφαίρεση Αλλεργιογόνου";
            remove_button.className="btn btn-primary";


            remove_button.addEventListener("click", function () {
                wrapper.remove();
            });

            wrapper.appendChild(newInput);
            wrapper.appendChild(remove_button);
            container.appendChild(wrapper);
        }
    })


});