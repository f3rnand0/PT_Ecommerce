package perfiltic.ecommerce.api.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDto {

    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private Long categoryId;
    @NotNull
    private String categoryName;
    @NotNull
    private String name;
    private String description;
    private BigDecimal weight;
    private BigDecimal price;

}
