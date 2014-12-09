var baseUrl = "";
function init(){
	document.getElementById("name").focus();
}

function listPerson(response, url) {
	baseUrl = url;
    var arr = JSON.parse(response);
    var table = document.getElementById("testTable");

    for(i = 0; i < arr.length; i++) {
        var tr = document.createElement('tr');

        var tdId = document.createElement('td');
        tdId.appendChild(document.createTextNode(arr[i].id));
        tdId.setAttribute('onclick', 'populatePerson('+arr[i].id+');');
        tdId.setAttribute('style', 'color:blue;cursor:pointer;');
        tr.appendChild(tdId);

        var tdName = document.createElement('td');
        tdName.appendChild(document.createTextNode(arr[i].name));
        tr.appendChild(tdName);

        var tdEmail = document.createElement('td');
        tdEmail.appendChild(document.createTextNode(arr[i].email));
        tr.appendChild(tdEmail);

        var tdDelete = document.createElement('td');
        tdDelete.appendChild(document.createTextNode("Remove"));
        tdDelete.setAttribute('style', 'color:red;cursor:pointer;');
        tdDelete.setAttribute('onclick', 'removePerson('+arr[i].id+');');
        tr.appendChild(tdDelete);

        table.appendChild(tr);

        personList.push(arr[i]);
    }
}


function populatePerson(personId){
    for(i =0; i< personList.length; i++){
        if(personId == personList[i].id){
        	console.log(personList[i].name);
            document.getElementsByName("name")[0].value = personList[i].name;
            document.getElementsByName("email")[0].value = personList[i].email;
            document.getElementsByName("id")[0].value = personList[i].id;

            var address = personList[i].address[0];
            if(address != null){
                document.getElementsByName("address")[0].value = address.address;
                document.getElementsByName("street")[0].value = address.street;
                document.getElementsByName("city")[0].value = address.city;
                document.getElementsByName("state")[0].value = address.state;
                document.getElementsByName("zip")[0].value = address.zip;
                document.getElementsByName("addType")[0].value = address.type;
                document.getElementsByName("addId")[0].value = address.addId;
            }
           
            var tel = personList[i].telPhoneNumber[0];
            if(tel != null){
                document.getElementsByName("telType")[0].value = tel.type;
                document.getElementsByName("number")[0].value = tel.telNumber;
                document.getElementsByName("telId")[0].value = tel.telId;
            }
        }
    }

}

function submitForm(){
        if(!validateForm()){
            return;
        }
        var addUrl = baseUrl;
        var personToAdd = createPerson();
        addUrl = addUrl + "/add";   
        xmlhttp.open("POST", addUrl, true);
        xmlhttp.setRequestHeader("Content-type", "application/json");
        xmlhttp.send(JSON.stringify(personToAdd));
    }

function updateForm(){
        if(!validateForm()){
            return;
        }
        var updateUrl = baseUrl;
        var personToUpdate = createPerson();
        updateUrl = updateUrl + "/update/" + personToUpdate.id;   
        console.log(updateUrl);
        xmlhttp.open("PUT", updateUrl, true);
        xmlhttp.setRequestHeader("Content-type", "application/json");
        xmlhttp.send(JSON.stringify(personToUpdate));
}

function removePerson(personId){
        var removeUrl = baseUrl;
        if(personId > 0){
            var result = confirm("Do you want to delete person with id " + personId);
            if(result != true){
                return;
            }
            removeUrl = removeUrl + "/remove/" + personId;
            console.log(removeUrl) ;
            xmlhttp.open("DELETE", removeUrl, true);
            xmlhttp.send();
        }
}

function createPerson(){
    var person = {id:"", name:"", email:"", address:[], telPhoneNumber:[]};
     person.name = document.getElementsByName("name")[0].value;
    person.email = document.getElementsByName("email")[0].value;
    person.id = document.getElementsByName("id")[0].value;

    var address = {addId:"", address:"", street:"", city:"", state:"", zip:"", type:""};
    address.address = document.getElementsByName("address")[0].value;
    address.street = document.getElementsByName("street")[0].value;
    address.city = document.getElementsByName("city")[0].value;
    address.state = document.getElementsByName("state")[0].value;
    address.zip = document.getElementsByName("zip")[0].value;
    address.type = document.getElementsByName("addType")[0].value;
    address.addId = document.getElementsByName("addId")[0].value;

    var tel = {telId:"", type:"", telNumber:""};
    tel.type = document.getElementsByName("telType")[0].value;
    tel.telNumber = document.getElementsByName("number")[0].value;
    tel.telId = document.getElementsByName("telId")[0].value;

    person.address.push(address);
    person.telPhoneNumber.push(tel);

    return person;
}

function validateForm() {
   return (isNotEmpty("name", "Please enter your name!")
        && isNumeric("zip", "Please enter a 5-digit zip code!")
        && isLengthMinMax("zip", "Please enter a 5-digit zip code!", 5, 5)
        && isPhoneNumber("number", "Please enter a valid phone number!")
        && isValidEmail("email", "Enter a valid email!"));
}

// Return true if the input value contains only digits (at least one)
function isNumeric(inputId, errorMsg) {
   var inputElement = document.getElementById(inputId);
   var errorElement = document.getElementById(inputId + "Error");
   var inputValue = inputElement.value.trim();
   var isValid = (inputValue.search(/^[0-9]+$/) !== -1) && (inputValue.length == 5);
   showMessage(isValid, inputElement, errorMsg, errorElement);
   return isValid;
}

function isPhoneNumber(inputId, errorMsg) {
   var inputElement = document.getElementById(inputId);
   var errorElement = document.getElementById(inputId + "Error");
   var inputValue = inputElement.value.trim();
   var isValid = (inputValue.search(/^[0-9]+$/) !== -1) && (inputValue.length == 10);
   showMessage(isValid, inputElement, errorMsg, errorElement);
   return isValid;
}
 
function isNotEmpty(inputId, errorMsg) {
   var inputElement = document.getElementById(inputId);
   var errorElement = document.getElementById(inputId + "Error");
   var inputValue = inputElement.value.trim();
   var isValid = (inputValue.length !== 0);  // boolean
   showMessage(isValid, inputElement, errorMsg, errorElement);
   return isValid;
}

function isLengthMinMax(inputId, errorMsg, minLength, maxLength) {
   var inputElement = document.getElementById(inputId);
   var errorElement = document.getElementById(inputId + "Error");
   var inputValue = inputElement.value.trim();
   var isValid = (inputValue.length >= minLength) && (inputValue.length <= maxLength);
   showMessage(isValid, inputElement, errorMsg, errorElement);
   return isValid;
}

function isValidEmail(inputId, errorMsg) {
   var inputElement = document.getElementById(inputId);
   var errorElement = document.getElementById(inputId + "Error");
   var inputValue = inputElement.value;
   var atPos = inputValue.indexOf("@");
   var dotPos = inputValue.lastIndexOf(".");
   var isValid = (atPos > 0) && (dotPos > atPos + 1) && (inputValue.length > dotPos + 2);
   showMessage(isValid, inputElement, errorMsg, errorElement);
   return isValid;
}

function showMessage(isValid, inputElement, errorMsg, errorElement) {
   if (!isValid) {
      // Put up error message on errorElement or via alert()
      if (errorElement !== null) {
         errorElement.innerHTML = errorMsg;
      } else {
         alert(errorMsg);
      }
      // Change "class" of inputElement, so that CSS displays differently
      if (inputElement !== null) {
         inputElement.className = "error";
         inputElement.focus();
      }
   } else {
      // Reset to normal display
      if (errorElement !== null) {
         errorElement.innerHTML = "";
      }
      if (inputElement !== null) {
         inputElement.className = "";
      }
   }
}