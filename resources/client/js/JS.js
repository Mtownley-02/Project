
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
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response =>{
        return response.json()
    }).then(response =>{
        if(response.hasOwnProperty("Error")){
            console.log(JSON.stringify(response));
        }else{
            window.open("/client/UserS.html")
        }
    })
}
function goTocreateUser() {
    window.open("/client.CreateUser.html")
}
function createUser(Password,Password1) {
    console.log("invoked createUser");
    const formData= new FormData(document.getElementById('Create'));
    let url="Users/create";
    if(Password==Password1){
        fetch(url, {
            method: "POST",
            body: formData,
        }).then(response =>{
            return response.json()
        } )
    }else {
        console.log('Passwords do not match')
    }

}