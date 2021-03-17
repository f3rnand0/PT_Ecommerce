package perfiltic.ecommerce.api.dto.external.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import perfiltic.ecommerce.api.model.Category;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quotes {

    private Double USDUSD;
    private Double USDCOP;

}
