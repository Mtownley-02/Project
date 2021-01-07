function attemptLogin(){
    debugger;
    console.log("invoked attemptlogin");
    const formData= new FormData(document.getElementById('Login'));
    let url="/Users/attemptlogin";
    console.log(url);
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response =>{
        if(response.hasOwnProperty("error")){
            console.log(JSON.stringify(response));
        }else{
            console.log("Success");
            if(response.hasOwnProperty("True")||response.hasOwnProperty("true")||response.hasOwnProperty("1")){
                window.open("UserA.html")
            }else {
                window.open("UserS.html");
            }
        }
    })
}
function goTocreateUser() {
    window.open("CreateUser.html")
}



function logsCreate() {
    debugger;
    console.log("invoked logsCreate");
    const formData = new FormData(document.getElementById('LogCreate'));
    let url = "/Logs/create";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json();
    }).then(response => {
        if (response.hasOwnProperty("Error") ||(response.hasOwnProperty("error"))) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was added to database.");
        }
    });
}

function logsDelete() {
    console.log("invoked logsDelete");
    const LogId= document.getElementById("filler").value;
    const url="/Logs/delete/";
    fetch(url + LogId,{
        method: "DELETE",
    }).then(response =>{
        return response;
    } ).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert(JSON.stringify(response) +"Log was removed from database.");
        }
    });
}

function adminDelete() {
    debugger;
    console.log("invoked adminDelete");
    const formData= new FormData(document.getElementById('AdminDelete'));
    let url="/Admins/delete";

    fetch(url + UserID, {                // UserID as a path parameter
        method: "DELETE",
    }).then(response => {
        return response.json();                         //return response to JSON
    }).then(response => {                                   //something here
        if (response.hasOwnProperty("Error")) {         //checks if response from server has an "Error"
            alert(JSON.stringify(response));            // if it does, convert JSON object to string and alert
        } else {
            alert(JSON.stringify(response), "has been deleted");
        }
    });
}


function adminViewUsers() {
    debugger;
    console.log("invoked adminViewUsers");
    const url="/Admins/view/";
    fetch(url, {
        method: "GET",
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatUsersList(response);          //this function will create an HTML table of the data (as per previous lesson)
        }
    });
}

function formatUsersList(Array){
    debugger;
    let dataHTML = "";
    for (let item of Array) {
        dataHTML += "<tr><td>" + item.UserId + "<td><td>" + item.Admin + "<td><td>" + item.SessionToken + "<tr><td>";
    }
    document.getElementById("UsersTable").innerHTML = dataHTML;
}

function goToLogsList() {
    window.open("LogList.html");
}

function adminViewLogs() {
    debugger;
    console.log("invoked adminViewLogs");
    const url="/Admins/viewLog/";
    fetch(url, {
        method: "GET",
    }).then(response => {
        return response.json();                 //return response as JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) { //checks if response from the web server has an "Error"
            alert(JSON.stringify(response));    // if it does, convert JSON object to string and alert (pop up window)
        } else {
            formatLogsList(response);          //this function will create an HTML table of the data (as per previous lesson)
        }
    });
}

function formatLogsList(Array){
    debugger;
    let dataHTML = "";
    for (let item of Array) {
        dataHTML += "<tr><td>" + item.LogId + "<td><td>" + item.Title + "<td><td>" + item.Text + "<tr><td>";
    }
    document.getElementById("LogsTable").innerHTML = dataHTML;
}

function GetLogs() {
    debugger;
    console.log("invoked GetLogs");
    const formData= new FormData(document.getElementById('getLog'));
    let url="/Logs/view";
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response=>{
        LogUpdate.append(response[0],response[1]);
        return document.getElementById('LogUpdate').value = response;
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was fetched.");
        }
    });
}

function goToLogsView() {
    window.open("LogEdit.html");
}
function createUser(){
    debugger;
    const formData= new FormData(document.getElementById('CreateU'));
    let url="/Users/create";
        fetch(url, {
            method: "POST",
            body: formData,
        } ).then(response=> {
            return response.json();
        } ).then(response => {
            if (response.hasOwnProperty("Error")) {
                alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
            } else {
                alert("User created");
            }
        })
}

function logsUpdate(){
    debugger;
    console.log("invoked logsUpdate");
    const LogId=new FormData(document.getElementById('getLog'));
    const formData=new FormData(document.getElementById('LogUpdate'));
    let url="/Logs/update";
    fetch(url, {
        method: "POST",
        body: LogId,formData
    }).then(response=>{

        return response;
    } ).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was updated.");
        }
    });
}
