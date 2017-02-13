/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package_Cookie;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.Valid;

/**
 *
 * @author tobias
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bestellposten.findAll",
            query = "SELECT o FROM Bestellposten o"),
    @NamedQuery(name = "Bestellposten.find",
            query = "SELECT o FROM Bestellposten o WHERE o.id = :id"),
    @NamedQuery(name = "Bestellposten.remove",
            query = "DELETE FROM Bestellposten o WHERE o.id = :id")
})
class Bestellposten implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @Valid
    private Bestellung bestellung;
    
    private int cookieId;
    private int count;
    private boolean status;

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setCookieId(int cookieId) {
        this.cookieId = cookieId;
    }

    public int getCookieId() {
        return cookieId;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
}
