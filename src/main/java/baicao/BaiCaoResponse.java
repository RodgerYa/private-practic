package baicao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaiCaoResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String message;

    public Boolean isSuccess() {
        final int successCode = 200;
        return this.getCode() == successCode;
    }
}
