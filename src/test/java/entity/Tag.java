
package entity;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Tag {
    @Expose
    private Long id;
    @Expose
    private String name;
}
