package zorc.entity;

import java.util.Objects;

/**
 * @author Genocide
 *
 * Класс для представления единиц измерения.
 */
public class Unit extends NamedEntity {

    // Символическое обозначение единиц измерения
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Unit [name=" + this.getName() + " ; symbol=" + symbol + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.symbol);
        return hash;
    }
    
}
