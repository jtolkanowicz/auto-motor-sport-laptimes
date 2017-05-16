package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator

/**
 * Created by jacek on 13.12.16.
 */
class LoginPage extends Page {

    static url = "/community-login-2731627.html"

    static at = { title == "Login - auto motor und sport Community - AUTO MOTOR UND SPORT" }

    static content = {
        loginButton(to: ProfilePage) { $("input", type: "submit", value: "Einloggen") }
        username { $("input", name: "username") }
        password { $("input", name: "password") }
    }

    public void login(){
        Browser.drive {
            to this

            username.value("supertests1@mailinator.com")
            password.value("supertests1!")

            waitFor(30){
                loginButton.click()
                //$("div", id: "ftdiv2368015").empty
            }


            waitFor(30){
                title == "Mein Profil - AUTO MOTOR UND SPORT"
            }
        }
    }

}
