/**
 * Created by Vishal on 3/25/2017.
 */
function check() {

    if (document.forms["checkForm"]["newpassword"].value != document.forms["checkForm"]["retyepassword"].value) {
        document.getElementById("demo").innerHTML = "Password Not Matching";
        return false;
    }
}