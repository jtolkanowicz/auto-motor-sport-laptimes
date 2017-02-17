package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car

/**
 * Created by jacek on 10.02.17.
 */
class SupertestPage {

    //ids from article url

    private url = "/supertests/?p=0"

    private List<String> urls = new ArrayList<>()

    public List<String> getSupertestUrls() {
        Browser.drive {
            go url
            Navigator next = $("a", text: "weiter")
            getUrls()
            while (!next.empty) {
                go next.@href
                getUrls()
                next = $("a", text: "weiter")
            }
        }
        return urls;
    }

    private void getUrls() {
        Browser.drive {
            Navigator links = $("a", class: "a125 f")
            for (int i = 0; i < links.size(); i++) {
                String urlPath = new URL(links.getAt(i).@href).getPath()
                if (urlPath.contains("/supertest/")) {
                    urls.add(urlPath)
                }
            }
        }
    }

}
