import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
public class Observations {

    @XmlAttribute
    private Long timestamp;

    @XmlElement(name = "station")
    private List<Station> stations;

    // Getters and setters
}
