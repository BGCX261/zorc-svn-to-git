package zorc.entity;

import java.sql.Date;
import java.util.Objects;

/**
 * @author Genocide
 *
 * Класс для представления плана закупок.
 */
public class Plan extends NamedEntity {

    private Integer position; // Позиция закупки в общем списке
    private Production product;
    private Double price;
    private Double amount;
    private Date begin;
    private Date end;
    private Unit unit;
    private String comment;
    private PlanChange change;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Production getProduct() {
        return product;
    }

    public void setProduct(Production product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PlanChange getChange() {
        return change;
    }

    public void setChange(PlanChange change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Plan{" + "position=" + position + ", product=" + product + ", price=" + price + ", amount=" + amount + ", begin=" + begin + ", end=" + end + ", unit=" + unit + ", comment=" + comment + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.position);
        hash = 47 * hash + Objects.hashCode(this.product);
        hash = 47 * hash + Objects.hashCode(this.price);
        hash = 47 * hash + Objects.hashCode(this.amount);
        hash = 47 * hash + Objects.hashCode(this.begin);
        hash = 47 * hash + Objects.hashCode(this.end);
        hash = 47 * hash + Objects.hashCode(this.unit);
        hash = 47 * hash + Objects.hashCode(this.comment);
        hash = 47 * hash + Objects.hashCode(this.change);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Plan other = (Plan) obj;
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.begin, other.begin)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        if (!Objects.equals(this.unit, other.unit)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        return true;
    }

}
