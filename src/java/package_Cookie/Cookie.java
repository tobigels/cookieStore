package package_Cookie;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "Cookie.findAll",
            query = "SELECT o FROM Cookie o"),
    @NamedQuery(name = "Cookie.find",
            query = "SELECT o FROM Cookie o WHERE o.id = :id"),
    @NamedQuery(name = "Cookie.remove",
            query = "DELETE FROM Cookie o WHERE o.id = :id")
})
public class Cookie implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotNull(message="Keks braucht einen Namen")
    private String name;
    
    @Min(value= 0, message="kein Preis angegeben")
    @Max(value= 100, message="Preis zu hoch")
    private double price;
    
    @Min(value= 0, message="keine Anzahl angegeben")
    @Max(value= 10000, message="Anzahl zu hoch")
    private int count;

    public Cookie() {
    }

    public Cookie(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }
    
    // Getter
    public int getId() {
        return id;
    } 
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getCount() {
        return count;
    }
    
    // Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
    public void decreaseCount(int count) {
        this.count -= count;
    }
    
    public void increaseCount(int count) {
        this.count += count;
    }
  

    @Override
    public String toString() {
        return "Cookie[ id=" + id + " ]";
    }
}
