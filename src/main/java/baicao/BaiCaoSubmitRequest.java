package baicao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BaiCaoSubmitRequest {

    @JsonProperty("pspMain")
    private BaiCaoSubmitMain main;

    @JsonProperty("pspDetail")
    private List<BaiCaoSubmitDetail> detail;
}
