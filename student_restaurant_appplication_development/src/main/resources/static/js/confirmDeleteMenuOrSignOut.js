document.querySelectorAll("form").forEach(form => {
    form.addEventListener("submit", function(event){


        let formName = form.getAttribute("name");


        if(formName === "menuAction"){//if delete menu
            if(!confirm("Είστε σίγουροι πως θέλετε να διαγράψετε αυτό το Μενού;")){
                event.preventDefault();
            }
        }
        else if(formName === "logoutButton"){//if sign out

            if(!confirm("Είστε σίγουροι πως θέλετε να Αποσυνδεθείτε;")){
                event.preventDefault();
            }

        }







    });






});