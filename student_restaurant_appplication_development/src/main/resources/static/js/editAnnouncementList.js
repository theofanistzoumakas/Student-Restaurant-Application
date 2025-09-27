document.querySelectorAll("button").forEach(button =>{

    button.addEventListener("click",function(){

        const buttonId = this.getAttribute("id");

        if(buttonId === "addAnnouncementButtonId") {//create an announcement
            let outer_wrapper = document.createElement("div");

            outer_wrapper.className="card mx-auto w-75";

            let wrapper = document.createElement("div");

            wrapper.className="card-body d-flex flex-column align-items-center gap-3 mt-4";

            let container = document.getElementById('announcementsAdmin');

            let newInput = document.createElement("input");

            newInput.className="form-control";

            newInput.type = "textarea";

            newInput.name = "announcementTitle";

            newInput.placeholder = "Πληκτρολογήστε το Όνομα της Νέας Ανακοίνωσης"

            let newInput2 = document.createElement("textarea");

            newInput2.className="form-control";


            newInput2.name = "announcementDescription";

            newInput2.placeholder = "Πληκτρολογήστε την Περιγραφή της Νέας Ανακοίνωσης"

            let remove_button = document.createElement("button");

            remove_button.type = "button";
            remove_button.textContent = "Αφαίρεση Ανακοίνωσης";
            remove_button.className="btn btn-primary";


            remove_button.addEventListener("click", function () {
                wrapper.remove();
                outer_wrapper.remove();
            });

            wrapper.appendChild(newInput);
            wrapper.appendChild(newInput2);
            wrapper.appendChild(remove_button);
            outer_wrapper.appendChild(wrapper)
            container.appendChild(outer_wrapper);
        }
    })


});