import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.LoginPage

/**
 * Created by jacek on 15.02.17.
 */
new LoginPage().login()
CarPage carPage = new CarPage(url: "/supertest/lamborghini-huracan-coupe-lp-610-4-supertest-11494782.html")
if(carPage.hasCarData()){
    println carPage.getCarData()
}

