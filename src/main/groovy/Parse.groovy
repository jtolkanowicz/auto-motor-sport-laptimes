import geb.Browser
import geb.navigator.Navigator
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import pages.LoginPage
import pages.MainPage


Browser.drive {
    to LoginPage

    username.value("sadrascal@mailinator.com")
    password.value("sadrascal1!")

    Navigator questionnaire = $("div", id: "nuggadButtonClose")
    if(!questionnaire.isEmpty()){
        questionnaire.click()
    }

    loginButton.click()

}
