package package_Cookie;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CookieService implements Serializable {
    
    @Inject
    private CookiePersistence db;
    
    //return all cookies as list
    public List<Cookie> cookies() {
        List <Cookie> erg = new ArrayList<Cookie>();
        for(Cookie c : this.db.findAllCookies()) {
            erg.add(c);
        }
        return erg;
    }
    
    public List<Cookie> ordered_cookies(int bestellung) {
        List <Cookie> erg = new ArrayList<Cookie>();
        Bestellung b = db.findBestellung(bestellung);
        for(Bestellposten bp : b.getOrdered()) {
            erg.add(db.findCookie(bp.getCookieId()));
        }
        return erg;
    }
    //add cookie with parameters
    public void addCookie(String name, double price, int count) {
        Cookie c = new Cookie();
        c.setName(name);
        c.setCount(count);
        c.setPrice(price);
        this.db.addCookie(c);
    }
    
    //delete cookie with id
    public void deleteCookie(int id) {
        this.db.removeCookie(id);
    } 
    
    //check for cookie with id
    public boolean isThereCookie(int id){
        if(this.db.findCookie(id) != null){ // TODO: test if findCookie can return null
            return true;
        }else{
            return false;
        }
    }
    
    //check if cookie name already exists
    public boolean isThereName(String name) { // TODO: is there an unique anntoation
        for(Cookie c : this.db.findAllCookies()) {
            if(c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public Cookie findCookie(int id){
        return this.db.findCookie(id);
    }
    
    //delete all cookies
    public void deleteAllCookies() {
        for(Cookie c : this.db.findAllCookies()) {
            this.db.removeCookie(c.getId());
        }
    }
    
    public void updateCookie(Cookie c) {
        this.db.merge(c);
    }
    
}