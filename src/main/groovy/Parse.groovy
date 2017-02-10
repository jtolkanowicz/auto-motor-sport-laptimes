import geb.Browser
import geb.navigator.Navigator
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage
import pl.tolkanowicz.ams.Car

Browser logged = Browser.drive {
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
    /*
    to CarPage
    Navigator carName = $("th", 1)
    String make = "";
    String model = "";
    if(!carName.isEmpty()){
        String temp = carName.text()
        make = temp.take(temp.indexOf(" "))
        model = temp.drop(temp.indexOf(" ")+1)
    }

    String time = "0:00"
    Navigator nordschleifeSection = $("td", text: "Nordschleife").parent().parent().children()
    while(nordschleifeSection.size()>0){
        if(nordschleifeSection.children().getAt(0).text().equals("Rundenzeit")){
            time = nordschleifeSection.children().getAt(1).text()
        }
        nordschleifeSection = nordschleifeSection.next()
    }
    Car car = new Car(make: make, model: model, time: time)
    print car*/
}
Car car = new Car()
logged = CarPage.getCarData(logged, car)