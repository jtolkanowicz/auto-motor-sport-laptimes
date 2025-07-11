package pl.tolkanowicz.ams

/**
 * Created by jacek on 21.02.17.
 */
class TestLink {

    public TestLink(String url){
        int lastDashId = url.lastIndexOf("-")
        int lastDotId = url.lastIndexOf(".")

        id = Integer.parseInt(url.substring(lastDashId+1, lastDotId))
        this.url = url
    }

    Integer id

    String url

    Boolean verified = false

    Boolean hasCarData = false

}
