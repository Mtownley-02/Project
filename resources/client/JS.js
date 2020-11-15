window.onscroll = function() {myFunction()};

const navbar = document.getElementById("navbar");
const sticky = navbar.offsetTop;

function myFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}
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