import groovy.json.JsonBuilder
import pl.tolkanowicz.ams.pages.CarPage
import pl.tolkanowicz.ams.pages.SupertestPage

/**
 * Created by jacek on 13.02.17.
 */
List<String> urls = new SupertestPage().getSupertestUrls()
new File("Links.json").write(new JsonBuilder(urls).toPrettyString())