<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Person CRUD</title>

<%
    String port = request.getServerPort() == -1 ? "":":" + request.getServerPort();
    String baseUrl = request.getScheme() + "://" + request.getServerName() + port + request.getContextPath()+"/person";
%>
<style type="text/css">
    .red {  
       color: red;
    }

    input.error {  
       border: 1px red inset;
       padding: 2px;
    }
     
    table {
       border: 0;
    }
     
    td {
       margin: 0;
       padding: 3px 40px 3px 3px;
    }

</style>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<c:url value="/resources/person.js" />"></script>
<script type="text/javascript">

window.onload = init;
var xmlhttp = new XMLHttpRequest();
var baseUrl = "<%= baseUrl%>";
var url = "<%= baseUrl%>"+"/list";

var personList = [];

xmlhttp.onreadystatechange=function(){
        if(xmlhttp.readyState == 4){
            if(xmlhttp.status == 202 || xmlhttp.status == 201){
                console.log(xmlhttp.status);
                window.location = window.location.pathname;
            }else if(xmlhttp.status == 409){
                 var inputElement = document.getElementById("email");
                 var errorElement = document.getElementById("emailError");
                 errorMsg = "Duplicate email entry";
                 showMessage(false, inputElement, errorMsg, errorElement);
            }
            else if(xmlhttp.status == 200){
                listPerson(xmlhttp.responseText, baseUrl);
            }
            else{
                alert("Unable to process request. Response code returned " + xmlhttp.status);
            }
        }
} 

xmlhttp.open("GET", url, true);
xmlhttp.send();


</script>
</head>
  <body >
    <h1>Users</h1>
    <div id="id01"></div>

    <div id="mainContainer">
                <div id="leftContainer" style="float: left; width: 35%">
                    <table id="testTable"> 
                    </table>
                </div>
                <div id ="rightContainer" style="float:right; width : 65%; clear: right'">
                    <form action="" name="details">
                        <div style="border-style:light;border-width:1px;margin-bottom:20px; width:60%;">
                            <table border="0">
                                <tr>
                                    <td>Name</td>
                                    <td><input type="text" id="name" name="name" value="">
                                        <input type="hidden" name="id" value="0">
                                    </td>
                                    <td id="nameError" class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td><input type="text" id="email" name="email" value=""></td>
                                    <td id="emailError" class="red">&nbsp;</td>
                                </tr>
                            </table>
                        </div>
                        <div style="border-style:light;border-width:1px; margin-bottom:20px;width:60%;">
                            <table border="0">
                                <tr>
                                    <td>Type</td>
                                    <td><input type="text" name="addType" value="">
                                        <input type="hidden" name="addId" value="0">
                                    </td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>Address</td>
                                    <td><input type="text" name="address" value=""></td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>Street</td>
                                    <td><input type="text" name="street" value=""></td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>City</td>
                                    <td><input type="text" name="city" value=""></td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>State</td>
                                    <td><input type="text" name="state" value=""></td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>Zipcode</td>
                                    <td><input type="text" id="zip" name="zip" value=""></td>
                                    <td id="zipError" class="red">&nbsp;</td>
                                </tr>
                            </table>
                        </div>
                        <div style="border-style:light;border-width:1px;margin-bottom:20px;width:60%;">
                            <table border="0">
                                <tr>
                                    <td>Type</td>
                                    <td><input type="text" name="telType" value="">
                                        <input type="hidden" name="telId" value="0">
                                    </td>
                                    <td class="red">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>Number</td>
                                    <td><input type="text" id="number" name="number" value=""></td>
                                    <td id="numberError" class="red">&nbsp;</td>
                                </tr>
                            </table>
                        </div>
                    </form>
                    

                    <table border="0" width="50%">
                        <tr >
                            <td ALIGN="left" id="create"> <input type="button"  value="Create" onClick="submitForm()"/>
                            <td ALIGN="left" id="update"> <input type="button"  value="Update" onClick="updateForm()"/>
                        </tr>
                    </table>
                </div> <!-- End right container -->
            </div><!-- End main container -->
  </body>
</html>