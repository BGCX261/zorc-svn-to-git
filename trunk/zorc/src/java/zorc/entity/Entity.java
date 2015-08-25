package zorc.entity;

import java.io.Serializable;

/**
 * @author Genocide
 *
 * Общий класс для всех сущностей.
 */
public class Entity implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
