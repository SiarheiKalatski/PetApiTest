
package models;

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
public class DeletePet {

    @Expose
    private Long code;
    @Expose
    private String message;
    @Expose
    private String type;


}
