package pl.tolkanowicz.ams.pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import pl.tolkanowicz.ams.Car

/**
 * Created by jacek on 10.02.17.
 */
class SupertestPage extends Page {

    static basicUrl = "/supertests/?p=0"

    public static List<String> getSupertestUrls() {
        List<String> urls = new ArrayList<>();
        Browser.drive {
            go basicUrl
            urls.addAll(getUrls())
            Navigator next = $("a", text: "weiter")
            while(!next.empty){
                go next.@href
                urls.addAll(getUrls())
                next = $("a", text: "weiter")
            }
        }
        return urls;
    }

    private static List<String> getUrls(){
        List<String> urls = new ArrayList<>()
        Browser.drive {
            Navigator links = $("a", class: "a125 f")
            for (int i = 0; i < links.size(); i++) {
                String urlPath = new URL(links.getAt(i).@href).getPath()
                urls.add(urlPath)
            }
        }
        return urls;
    }

}
