package pages

import geb.Page

/**
 * Created by jacek on 13.12.16.
 */
class MainPage extends Page {

    static at = { title == "AUTO MOTOR UND SPORT | Tests - Erlkönige - Autokauf - Formel 1"}

    static content = {
        //manualsMenu { $("#header-content ul li", 0).module(MenuModule) }

        loginLink {$("a", text: "Login")}
    }


}
