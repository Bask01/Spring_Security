/* Wendi Jollymore
   syst10199, Feb 2016 
   modified for prog32758, Aug 2020
   some validation for a registration form
 
Feel free to modify.
*/
 
// unobtrusive javascript!!!
document.addEventListener("DOMContentLoaded", init);
function init() {
    document.forms[0].addEventListener("submit", validate);
}
 
// not very reusable validation, but done quickly
 
// TODO: check password strength!!
 
var noerror;
 
function validate(event) {
    
    noerror = true; // assume no errors to start
    
    form = document.forms[0];
    div = document.getElementById('error');
    
    // in case browser doesn't support html5 constraint validation
    // (can test by removing required attribute)
    checkEmpty(form.elements["email"], "Email", div);
    checkEmpty(form.elements["pass"], "Password", div);
    checkEmpty(form.elements["verify"], "Verify Password", div);
    
    // password must match verify password field
    if (form.elements["pass"].value !== form.elements["verify"].value) {
        div.innerHTML = "Passwords don't match.<br>";
        noerror = false;
    }
    
    // in case browser doesn't support html5 constraint validation
    // (test by changing email type to text type)
    //https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
    let emailPatt = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (!emailPatt.test(form.elements["email"].value.toLowerCase())) {
        div.innerHTML += "Invalid email address format.<br>";
        noerror = false;
    }
    
    if (!noerror)  // don't submit if errors occurred
        event.preventDefault();
        
    return noerror;
}
 
// determines if an input field is empty and adds an error message to a div
function checkEmpty(field, fieldName, div) {
    if (field.value == null || field.value == "") {
        div.innerHTML += fieldName + " can't be empty.<br>";
        noerror = false;
    }
}