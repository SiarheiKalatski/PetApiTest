
package entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Pet {
    @Expose
    private Category category;
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private List<String> photoUrls;
    @Expose
    private String status;
    @Expose
    private List<Tag> tags;
}
