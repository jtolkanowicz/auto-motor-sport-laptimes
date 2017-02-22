package pl.tolkanowicz.ams

/**
 * Created by jacek on 21.02.17.
 */
class TestLink {

    TestLink(String url){
        int lastDashId = url.lastIndexOf("-")
        int lastDotId = url.lastIndexOf(".")

        id = Integer.parseInt(url.substring(lastDashId+1, lastDotId))
        this.url = url
    }

    TestLink(Integer id, String url, Boolean verified, Boolean hasCarData) {
        this.id = id
        this.url = url
        this.verified = verified
        this.hasCarData = hasCarData
    }
    Integer id

    String url

    Boolean verified = false

    Boolean hasCarData = false

}
