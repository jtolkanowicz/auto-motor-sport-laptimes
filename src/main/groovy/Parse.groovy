import geb.Browser
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage
import pl.tolkanowicz.ams.pages.SupertestPage

Browser.drive {
    to LoginPage

    username.value("sadrascal@mailinator.com")
    password.value("sadrascal1!")

    Navigator questionnaire = $("div", id: "nuggadButtonClose")
    if(!questionnaire.isEmpty()){
        questionnaire.click()
    }

    loginButton.click()

    waitFor(100){
        title == "Mein Profil - AUTO MOTOR UND SPORT"
        //js.exec"return document.readyState" == "complete"
    }
}
List<String> urls = SupertestPage.getSupertestUrls()
List<Car> cars = new ArrayList<>()
for(String url : urls) {
    Car car = CarPage.getCarData(url)
    if(car != null){
        cars.add(car)
    }
}
println cars
//logged = CarPage.getCarData(logged, car)