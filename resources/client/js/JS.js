
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

}

function logsDelete() {

}

function adminDelete() {

}
function adminViewUsers() {

}



function GetLogs() {
    console.log("invoked GetLogs")
    const formData= new FormData(document.getElementById('LogUpdate'))
    let url="Logs/view"
    fetch(url,{
        method: "POST",
        body: formData,
    }).then(response=>{
        document.LogUpdate.Title.value = response[0];
    }).then(response=>{
        document.LogUpdate.Text.value = response[1];
    })
}

function goToLogsView() {
    window.open("LogsView.html");
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