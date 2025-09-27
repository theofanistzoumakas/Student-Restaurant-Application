let allButtons = document.querySelectorAll('.btn');
//for style
allButtons.forEach(oneButton => {
    oneButton.addEventListener('click',function(){
        oneButton.style.backgroundColor = 'brown';
        oneButton.style.color = 'azure';
        oneButton.style.border = 'brown';
    });

    oneButton.addEventListener('mousedown',function(){
        oneButton.style.backgroundColor = 'brown';
        oneButton.style.color = 'azure';
        oneButton.style.border = 'brown';
    });

    oneButton.addEventListener('mouseup',function(){
        oneButton.style.backgroundColor = 'brown';
        oneButton.style.color = 'azure';
        oneButton.style.border = 'brown';
    });

    oneButton.addEventListener('mouseleave',function(){
        oneButton.style.backgroundColor = 'brown';
        oneButton.style.color = 'azure';
        oneButton.style.border = 'brown';
    });
});