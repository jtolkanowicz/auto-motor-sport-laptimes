package pl.tolkanowicz.ams

import java.time.YearMonth

/**
 * Created by jacek on 06.02.17.
 */
class Car {

    public Car(String url){
        int lastDashId = url.lastIndexOf("-")
        int lastDotId = url.lastIndexOf(".")
        id = Integer.parseInt(url.substring(lastDashId+1, lastDotId))
        this.url = url
    }

    Integer id

    String make, model, productionYears, nordschleifeTime, hockenheimTime, url
    YearMonth testDate
    Integer weight, power, torque
    Float time100, time200
    //\d* PS
    //tires

}
