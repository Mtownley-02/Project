
$(window).scroll(function(){
    if ($(window).scrollTop() >= 300) {
        $('navbar').addClass('fixed-header');
    }
    else {
        $('navbar').removeClass('fixed-header');
    }
});
function attemptLogin(){
    console.log("invoked attemptLogin");
    const formData= new FormData(document.getElementById('Login'));
    let url="Users/attemptLogin";
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response =>{
        if(response.hasOwnProperty("Error")){
            console.log(JSON.stringify(response));
        }else{
            window.open("UserS.html");
        }
    })
}
function goTocreateUser() {
    window.open("CreateUser.html")
}



function logsCreate() {
    console.log("invoked logsCreate");
    const formData= new FormData(document.getElementById('LogCreate'));
    let url="Logs/create";
        fetch(url, {
            method: "POST",
            body: formData,
        }).then(response =>{
            return response;
        } )
}

function logsDelete() {
    console.log("invoked logsDelete");
    const formData= new FormData(document.getElementById('LogDelete'));
    let url="Logs/delete";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response =>{
        return response;
    } )
}

function adminDelete() {
    console.log("invoked adminDelete");
    const formData= new FormData(document.getElementById('AdminDelete'));
    let url="Admins/delete";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response =>{
        return response;
    } )
}


function adminViewUsers() {
    console.log("invoked adminViewUsers");
    const url="Admins/view/";
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
    let dataHTML = "";
    for (let item of Array) {
        dataHTML += "<tr><td>" + item.UserId + "<td><td>" + item.Admin + "<td><td>" + item.SessionToken + "<tr><td>";
    }
    document.getElementById("UsersTable").innerHTML = dataHTML;
}

function goToLogsList() {
    window.open("LogList/html");
}

function adminViewLogs() {
    console.log("invoked adminViewLogs");
    const url="Admins/view/";
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
    let dataHTML = "";
    for (let item of Array) {
        dataHTML += "<tr><td>" + item.LogId + "<td><td>" + item.Title + "<td><td>" + item.Text + "<tr><td>";
    }
    document.getElementById("UsersTable").innerHTML = dataHTML;
}

function GetLogs() {
    console.log("invoked GetLogs")
    const formData= new FormData(document.getElementById('LogUpdate'))
    let url="Logs/view"
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response=>{
        document.getElementById('LogUpdate').value = response[0];
    }).then(response=>{
        document.getElementById('LogUpdate').value = response[1];
    })
}

function goToLogsView() {
    window.open("LogEdit.html");
}
function createUser(Password,Password1) {
    console.log("invoked createUser");
    const formData= new FormData(document.getElementById('Create'));
    let url="Users/create";
    if(Password===Password1){
        fetch(url, {
            method: "POST",
            body: formData,
        }).then(response =>{

            window.open("UserS.html");
            return response;
        } )
    }else {
        console.log('Passwords do not match')
    }

}