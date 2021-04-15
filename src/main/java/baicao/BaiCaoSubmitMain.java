package baicao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;

@Data
@Accessors(chain = true)
public class BaiCaoSubmitMain {

    @JsonProperty("pid")
    private String prescriptionId;

    @JsonProperty("cfh")
    private String prescriptionNo;

    @JsonProperty("shdh")
    private String auditOrderNo;

    @JsonProperty("mznx")
    private String mznx = "MZ";

    @JsonProperty("zd")
    private String diagnosis;

    @JsonProperty("yybh")
    private String hospitalId;

    @JsonProperty("kfrq")
    private String created;

    @JsonProperty("xm")
    private String personName;

    @JsonProperty("xb")
    private Optional<String> sex = Optional.empty();

    @JsonProperty("nl")
    private Optional<String> age = Optional.empty();

    @JsonProperty("lxdh")
    private String phone;

    @JsonProperty("psdz")
    private String deliverPlace;

    @JsonProperty("psfs")
    private String deliverType = "DS";

    @JsonProperty("yzsm")
    private String useSuggest;

    @JsonProperty("mtfs")
    private Integer packets;

    @JsonProperty("ts")
    private Integer count;

    @JsonProperty("ysxm")
    private String doctorName;

    @JsonProperty("bz")
    private String notes;

    @JsonProperty("mxsl")
    private Integer medicineNum;

    @JsonProperty("yfnx")
    private String useMethod;

    @JsonProperty("cfnx")
    private String prescriptionType;
}
