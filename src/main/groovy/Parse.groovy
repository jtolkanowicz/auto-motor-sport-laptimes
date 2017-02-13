import geb.Browser
import geb.navigator.Navigator
import groovy.json.JsonBuilder
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
List<String> urls = new SupertestPage().getSupertestUrls()
List<Car> cars = new ArrayList<>()
for(String url : urls) {
    CarPage carPage = new CarPage(url: url)
    if(carPage.hasCarData()){
        cars.add(carPage.getCarData())
    }

}
new File("Cars.json").write(new JsonBuilder(cars).toPrettyString())
//logged = CarPage.getCarData(logged, car)