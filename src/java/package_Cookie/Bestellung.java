/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package_Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author tobias
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bestellung.findAll",
            query = "SELECT o FROM Bestellung o"),
    @NamedQuery(name = "Bestellung.find", // kritisch, besser em.find()
            query = "SELECT o FROM Bestellung o WHERE o.id = :id"),
    @NamedQuery(name = "Bestellung.remove",
            query = "DELETE FROM Bestellung o WHERE o.id = :id"),
    @NamedQuery(name = "Bestellung.findBestellposten",
            query = "SELECT b FROM Bestellung s JOIN s.ordered b WHERE s.id = :id")
})
public class Bestellung implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String besteller;
    
    @OneToMany(mappedBy = "bestellung", cascade = CascadeType.PERSIST)
    private List<Bestellposten> ordered;
    
    public Bestellung() {
        this.ordered = new ArrayList<Bestellposten>();
    }
    
    public void addBestellposten(Bestellposten bp){
        this.ordered.add(bp);
    }
    
    public void removeBestellposten(Bestellposten bp){
        this.ordered.remove(bp);
    }

    public void setBesteller(String besteller) {
        this.besteller = besteller;
    }

    public void setOrdered(List<Bestellposten> ordered) {
        this.ordered = ordered;
    }

    public String getBesteller() {
        return besteller;
    }

    public List<Bestellposten> getOrdered() {
        return ordered;
    }

    public int getId() {
        return id;
    }
    
    public void addBestellPosten(Bestellposten bp) {
        this.ordered.add(bp);
    }
    
    public void removeBestellPosten(Bestellposten bp) {
        this.ordered.remove(bp);
    }

    
}
