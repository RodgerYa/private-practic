package baicao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;

@Data
@Accessors(chain = true)
public class BaiCaoSubmitDetail {

    @JsonProperty("pid")
    private String prescriptionId;

    @JsonProperty("xh")
    private Integer serial;

    @JsonProperty("ypmc")
    private String goodsName;

    @JsonProperty("ypbm")
    private String goodsCode;

    @JsonProperty("ypgg")
    private String spec;

    @JsonProperty("qf")
    private Boolean qf;

    @JsonProperty("sl")
    private String count;

    @JsonProperty("yf")
    private Optional<String> makingMethod = Optional.empty();
}
