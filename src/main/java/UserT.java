import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
class UserT {

    @NotNull
    Integer id;

    @Valid
    String name;
}