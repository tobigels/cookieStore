/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package_Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author tobias
 */
 @Dependent
public class BestellService implements Serializable{

    @Inject
    private CookiePersistence db;

    //--------------------- Bestellung --------------------------
    public void addBestellung() {
        Bestellung b = new Bestellung();
        this.db.addBestellung(b);
    }
    
    public void addBestellung(Bestellung b) {
        this.db.addBestellung(b);
    }

    public List<Bestellung> allBestellungen() {

        List<Bestellung> erg = new ArrayList<Bestellung>();

        for (Bestellung b : this.db.findAllBestellungen()) {
            erg.add(b);
        }

        return erg;
    }

    public void deleteBestellung(int id) {
        this.db.removeBestellung(id);
    }
    
    public Bestellung findBestellung(int id) {
        Bestellung b = this.db.findBestellung(id);
        return b;
    }
    
    public void deleteAllBestellungen() {
        for(Bestellung b : allBestellungen()) {
            deleteBestellung(b.getId());
        }
    }
    
    //----------------------- Bestellposten -------------------------
    public void addBestellposten(int bestellungId, int cookieId, int count) {
        
        //is there already an order with this cookietype?
        Bestellposten bp = findBestellpostenByCookie(cookieId, bestellungId);
        if(bp == null) {
            bp = new Bestellposten();
            bp.setCount(count);
            bp.setCookieId(cookieId);
            bp.setStatus(false);
            this.db.addBestellposten(bestellungId, bp);
        } else {
            bp.setCount(count);
            updateBestellposten(bp);
        }
    }
    
    public List<Bestellposten> allBestellposten(int bestellungId) {

        List<Bestellposten> erg = new ArrayList<Bestellposten>();
        Bestellung b = db.findBestellung(bestellungId);
        for (Bestellposten bp : b.getOrdered()) {
            erg.add(bp);
        }

        return erg;
    }
    
    public List<Bestellposten> allBestellposten() {

        List<Bestellposten> erg = new ArrayList<Bestellposten>();

        for (Bestellposten bp : this.db.findAllBestellposten()) {
            erg.add(bp);
        }

        return erg;
    }
    
    public Bestellposten findBestellposten(int id) {
        Bestellposten bp = this.db.findBestellposten(id);
        return bp;
    }
    
    public Bestellposten findBestellpostenByCookie(int cid, int bid) {
        Bestellposten bpp = null;
        Bestellung b = this.db.findBestellung(bid);
        for(Bestellposten bp : b.getOrdered()) {
            if(bp.getCookieId() == cid) {
                bpp = bp;
            }
        }
        return bpp;
    }
    

    public void deleteBestellposten(int id,Bestellposten bp) {
        this.db.removeBestellposten(id,bp);
    }

    public void updateBestellposten(Bestellposten bp) {
        this.db.merge(bp);
    }
    
    public void updateBestellung(Bestellung b) {
        this.db.merge(b);
    }
    
    public double getBestellungPrice(int id) {
        double erg = 0;
        Bestellung b = db.findBestellung(id);
        for(Bestellposten bp : b.getOrdered()) {
            Cookie c = db.findCookie(bp.getCookieId());
            erg += (c.getPrice() * bp.getCount());
        }
        return erg;
    }
}
