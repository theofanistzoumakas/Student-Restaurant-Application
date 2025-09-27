let inputsGroupBreakfast = document.querySelectorAll('li[data-role="breakfastDishes"]');
let inputsGroupLunch = document.querySelectorAll('li[data-role="lunchDishes"]');
let inputsGroupDinner = document.querySelectorAll('li[data-role="dinnerDishes"]');//select all dishes

//counters for ids
let breakfastCounter = -1;
let lunchCounter = -1;
let dinnerCounter = -1;

let breakfastCounterLabel = -1;
let lunchCounterLabel = -1;
let dinnerCounterLabel = -1;

//check if it has already dishes
if(inputsGroupBreakfast.length>0){
    breakfastCounter = inputsGroupBreakfast.length-1;
    breakfastCounterLabel = inputsGroupBreakfast.length-1;
}

if(inputsGroupLunch.length>0){
    lunchCounter = inputsGroupLunch.length-1;
    lunchCounterLabel = inputsGroupLunch.length-1;
}

if(inputsGroupDinner.length>0){
    dinnerCounter = inputsGroupDinner.length-1;
    dinnerCounterLabel = inputsGroupDinner.length-1;
}




document.querySelectorAll("button").forEach(button => {
    button.addEventListener("click",function(){

        const action = this.dataset.action;

        if(action === "addDish") {
            let targetContainerId = this.getAttribute("name");
            let targetContainer = document.getElementById(targetContainerId);

            if(targetContainerId === "breakfast"){//update ids
                breakfastCounterLabel+=1;
            }

            if(targetContainerId === "lunch"){
                lunchCounterLabel+=1;
            }

            if(targetContainerId === "dinner"){
                dinnerCounterLabel+=1;
            }

            let outer_wrapper = document.createElement("div");//create the dish inputs

            let wrapper = document.createElement("div");

            outer_wrapper.className="card mt-3 mb-3";

            wrapper.className= "card-body d-flex flex-column align-items-center gap-3 mt-4";

            let label1 = document.createElement("label");

            label1.innerText = "Όνομα Φαγητού";

            let label2 = document.createElement("label");

            label2.innerText = "Περιγραφή Φαγητού";

            let label3 = document.createElement("label");

            label3.innerText = "Vegetarian";

            let label4 = document.createElement("label");

            label4.innerText = "Vegan";


            if(targetContainerId === "breakfast"){//set labels
                label1.setAttribute("for","foodName"+breakfastCounterLabel.toString());
                label2.setAttribute("for","foodName"+breakfastCounterLabel.toString());
                label3.setAttribute("for","foodName"+breakfastCounterLabel.toString());
                label4.setAttribute("for","foodName"+breakfastCounterLabel.toString());
            }

            if(targetContainerId === "lunch"){
                label1.setAttribute("for","foodName"+lunchCounterLabel.toString());
                label2.setAttribute("for","foodName"+lunchCounterLabel.toString());
                label3.setAttribute("for","foodName"+lunchCounterLabel.toString());
                label4.setAttribute("for","foodName"+lunchCounterLabel.toString());
            }

            if(targetContainerId === "dinner"){
                label1.setAttribute("for","foodName"+dinnerCounterLabel.toString());
                label2.setAttribute("for","foodName"+dinnerCounterLabel.toString());
                label3.setAttribute("for","foodName"+dinnerCounterLabel.toString());
                label4.setAttribute("for","foodName"+dinnerCounterLabel.toString());
            }


            let newInput = document.createElement("textarea");

            newInput.className="form-control";

            newInput.rows = 4;

            newInput.name = targetContainerId + "[]";

            newInput.placeholder = "Πληκτρολογήστε ένα Όνομα για το Φαγητό"


            let newInput2 = document.createElement("textarea");

            newInput2.className="form-control";


            newInput2.rows = 4;

            newInput2.name = targetContainerId + "Description[]";

            newInput2.placeholder = "Πληκτρολογήστε μια Περιγραφή για το Φαγητό";

            let newInput3 = document.createElement("input");

            newInput3.className = "form-check-input";

            newInput3.type = "checkbox";

            newInput3.name = targetContainerId + "IsVegetarian[]";


            let newInput4 = document.createElement("input");

            newInput4.className = "form-check-input";

            newInput4.type = "checkbox";

            newInput4.name = targetContainerId + "IsVegan[]";


            if(targetContainerId === "breakfast"){
                breakfastCounter+=1;
                newInput3.value=breakfastCounter.toString();
                newInput4.value=breakfastCounter.toString();
            }

            if(targetContainerId === "lunch"){
                lunchCounter+=1;
                newInput3.value=lunchCounter.toString();
                newInput4.value=lunchCounter.toString();

            }

            if(targetContainerId === "dinner"){
                dinnerCounter+=1;
                newInput3.value=dinnerCounter.toString();
                newInput4.value=dinnerCounter.toString();
            }


            if(targetContainerId === "breakfast"){//set ids for inputs
                newInput.id="foodName"+breakfastCounterLabel.toString();
                newInput2.id="foodName"+breakfastCounterLabel.toString();
                newInput3.id="foodName"+breakfastCounterLabel.toString();
                newInput4.id="foodName"+breakfastCounterLabel.toString();
            }

            if(targetContainerId === "lunch"){
                newInput.id="foodName"+lunchCounterLabel.toString();
                newInput2.id="foodName"+lunchCounterLabel.toString();
                newInput3.id="foodName"+lunchCounterLabel.toString();
                newInput4.id="foodName"+lunchCounterLabel.toString();
            }

            if(targetContainerId === "dinner"){
                newInput.id="foodName"+dinnerCounterLabel.toString();
                newInput2.id="foodName"+dinnerCounterLabel.toString();
                newInput3.id="foodName"+dinnerCounterLabel.toString();
                newInput4.id="foodName"+dinnerCounterLabel.toString();
            }



            let remove_button = document.createElement("button");

            remove_button.className="btn btn-primary";

            remove_button.type = "button";
            remove_button.textContent = "Αφαίρεση Φαγητού";

            remove_button.addEventListener("click", function () {//if remove
                console.log(targetContainerId)


                if(targetContainerId === "breakfast"){//update counters if remove
                    breakfastCounter-=1;
                    console.log("breakfast");
                }

                if(targetContainerId === "lunch"){
                    lunchCounter-=1;
                    console.log("breakfast");

                }

                if(targetContainerId === "dinner"){
                    dinnerCounter-=1;
                    console.log("breakfast");
                }
                wrapper.remove();
                outer_wrapper.remove();
            });

            wrapper.appendChild(label1);//create
            wrapper.appendChild(newInput);

            wrapper.appendChild(label2);
            wrapper.appendChild(newInput2);

            wrapper.appendChild(label3);
            wrapper.appendChild(newInput3);

            wrapper.appendChild(label4);
            wrapper.appendChild(newInput4);

            wrapper.appendChild(remove_button);

            outer_wrapper.appendChild(wrapper);

            targetContainer.appendChild(outer_wrapper);
        }

        if(action === "removeDish"){//if remove

            let targetContainerId = this.getAttribute("name");
            let targetContainer = document.getElementById(targetContainerId);

            targetContainer.remove();

        }
    });
});

