package yifeng;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class DeliveryForm {
    private Integer id;
    private String partnerCode;
    private String uri;
    private String headers;
    private String query;
    private String content;
    private OffsetDateTime created;
}

