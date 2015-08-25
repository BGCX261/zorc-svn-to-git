package zorc.entity;

/**
 * @author Genocide
 *
 * Класс для представления именованной сущности.
 */
public class NamedEntity extends Entity {

    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
