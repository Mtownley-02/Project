
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
    let url="users/attemptLogin";
    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response =>{
        return response.json()
    }).then(response =>{
        if(response.hasOwnProperty("Error")){
            alert(JSON.stringify(response));
        }else{
            window.open("/client/UserS.html")
        }
    })


}