function attemptLogin(){
    debugger;
    console.log("invoked attemptlogin");
    const formData= new FormData(document.getElementById('Login'));
    let url="/Users/attemptlogin/";
    console.log(url);
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response =>{
        return response;
    }).then(response =>{
        if(response.hasOwnProperty("error")){
            console.log(JSON.stringify(response));

            return response;
        }else{
            let Admincheck=response;
            console.log("Success");
            console.log(Admincheck);
            if(Admincheck.hasOwnProperty("true")){
                window.open("UserA.html")
            }else{
               window.open("UserS.html");
            }
        }
    })
}
function goTocreateUser() {
    window.open("CreateUser.html")
}



function logsCreate() {
    console.log("invoked logsCreate");
    const formData = new FormData(document.getElementById('LogCreate'));
    let url = "/Logs/create/";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response;
    }).then(response => {
        if (response.hasOwnProperty("Error") ||(response.hasOwnProperty("error"))) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was added to database.");
        }
    });
}

function logsDelete(LogId) {
    console.log("invoked logsDelete");
    const url="/Logs/delete/";
    fetch(url + LogId,{
        method: "DELETE",
    }).then(response =>{
        return response;
    } ).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was removed from database.");
        }
    });
}

function adminDelete(UserID) {
    console.log("invoked adminDelete");
    let url="/Admins/delete/";
    fetch(url + UserID, {                // UserID as a path parameter
        method: "DELETE",
    }).then(response => {
        return response;                         //return response to JSON
    }).then(response => {                                   //something here
        if (response.hasOwnProperty("Error")) {         //checks if response from server has an "Error"
            alert(JSON.stringify(response));            // if it does, convert JSON object to string and alert
        } else {
            alert(JSON.stringify(response)+ "has been deleted");
        }
    });
}

function adminViewUsers(){
    console.log("Invoked listUsers() ");
    let url = "/Admins/list/";
    fetch(url, {
        method: 'GET',
    }).then(response => {
        return response.json();                 //now return that promise to JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            let UsersTable = `<div>`;
            UsersTable += `<table><th>UserId</th><th>Password</th><th>Admin</th><th>SessionToken</th>`;
            console.log(response);
            for(let r of response.Users){
                UsersTable += `<tr class="User_${r.UserId}">`
                    + `<td>${r.UserId}</td>`
                    + `<td>${r.Password}</td>`
                    + `<td>${r.Admin}</td>`
                    + `<td>${r.SessionToken}</td>`
                    + `<td><button onclick="adminDelete(${r.UserId})" class="button">Delete User</button></td>`
                    + `</tr>`;
            }
            UsersTable += `</div>`;
            document.getElementById('Users').innerHTML = UsersTable;
        }
    });
}


function adminViewLogs(){
    console.log("Invoked listLogs() ");
    let url = "/Logs/list/";
    fetch(url, {
        method: 'GET',
    }).then(response => {
        return response.json();                 //now return that promise to JSON
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            let LogsTable = `<div>`;
            LogsTable += `<table><th>LogID</th><th>Title</th><th>Text</th><th>UserId</th>`;
            console.log(response);
            for(let r of response.Logs){
                LogsTable += `<tr class="Log_${r.LogId}">`

                    + `<td>${r.LogId}</td>`
                    + `<td>${r.Title}</td>`
                    + `<td>${r.Text}</td>`
                    + `<td>${r.UserId}</td>`
                    + `<td><button onclick="logsDelete(${r.LogId})" class="button">Delete Log</button></td>`
                    + `</tr>`;
            }
            LogsTable += `</div>`;
            document.getElementById('Logs').innerHTML = LogsTable;
        }
    });
}
function goToLogsList() {
    window.open("LogList.html");
}


function GetLogs() {
    console.log("invoked GetLogs");
    const LogId= document.getElementById("getLog").value;
    console.log(LogId);
    let url="/Logs/GetText/";
    fetch(url + LogId,{
        method: "GET",
    }).then(response=>{
        return response.json();
    }).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            let LogUpdate;
            LogUpdate=response;
            console.log(LogUpdate);
            document.getElementById('TextBox').innerText = LogUpdate;
            alert("Log was fetched.");
        }
    });
}

function goToLogsView() {
    window.open("LogEdit.html");
}
function goToLogsViewAdmin() {
    window.open("LogEditAdmin.html");
}
function createUser(){
    const formData= new FormData(document.getElementById("CreateU"));
    let url="/Users/create/";
        fetch(url, {
            method: "POST",
            body: formData,
        } ).then(response=> {
            return response.json();
        } ).then(response => {
            if (response.hasOwnProperty("Error")) {
                alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
            } else {
                alert("User created, UserID:"+response.toString());
            }
        })
}

function logsUpdate(){
    console.log("invoked logsUpdate");
    const formData=new FormData(document.getElementById('LogUpdate'));
    let url="/Logs/update/";
    fetch(url, {
        method: "POST",
        body: formData
    }).then(response=>{
        return response.json();
    } ).then(response => {
        if (response.hasOwnProperty("Error")) {
            alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
        } else {
            alert("Log was updated.");
        }
    });
}
