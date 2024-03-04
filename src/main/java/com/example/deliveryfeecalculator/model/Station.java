import javax.xml.bind.annotation.*;

@XmlRootElement(name = "station")
@XmlAccessorType(XmlAccessType.FIELD)
public class Station {

    private String name;
    private String wmocode;
    private Double airtemperature;
    private Double windspeed;
    private String phenomenon;
    @XmlAttribute
    private Long timestamp;

    public Double getAirtemperature() {
        return airtemperature;
    }

    public void setAirtemperature(Double airtemperature) {
        this.airtemperature = airtemperature;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWmocode() {
        return wmocode;
    }

    public void setWmocode(String wmocode) {
        this.wmocode = wmocode;
    }
}
