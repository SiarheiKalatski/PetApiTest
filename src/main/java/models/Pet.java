
package models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
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
