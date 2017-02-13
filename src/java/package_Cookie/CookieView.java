package package_Cookie;
 
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("CookieView")
@SessionScoped
public class CookieView implements Serializable {
    
    private String toAddName;
    private double toAddPrice;
    private int toAddCount;
    private int idToDelete;
    private int orderCount;
    
    private Bestellung order;
    
    
    @Inject
    private CookieService cs;
    
    @Inject
    private BestellService bs;

    @PostConstruct
    public void init() {
        cs.deleteAllCookies();
        bs.deleteAllBestellungen();
        
        
        cs.addCookie("Schoko", 1.99, 64);
        cs.addCookie("Halbkorn", 2.49, 64);
        cs.addCookie("Osmania", 2.99, 64);
        cs.addCookie("Schokomilch", 1.49, 64);
        cs.addCookie("Vollkorn", 0.99, 64);
        
        order = new Bestellung();
        bs.addBestellung(order);
        orderCount = 0;
    }

    //Buttons in main.xhtml
    
    
    /*
    Note: habe ich auskommentiert, da hier neue Cookies erstellt werden, die auch neue Ids bekommen
    -> man kann bestellung nicht mehr eindeutig über id referenzieren
    */
    
    public void orderCookieButton(int toOrderId) {
        if(cs.findCookie(toOrderId).getCount() < orderCount){
            addMessage("Vorrat reicht nicht aus");
        }else if(orderCount <= 0){
            addMessage("Bitte Menge angeben");
        }else{
            bs.addBestellposten(order.getId(), toOrderId, orderCount);
            addMessage("Zur Bestellung hinzugefügt");
            orderCount = 0;
        }  
    }
    
    public void addCookieButton() {
        if(!toAddName.equals(null) && toAddPrice != 0 && toAddCount != 0){
            cs.addCookie(toAddName, toAddPrice, toAddCount);
            addMessage("Cookie hinzugefügt");
        }else{
            addMessage("Angaben unvollständig");
        }
    }
    
    public void deleteCookieButton() {
        if(cs.isThereCookie(idToDelete)){
            cs.deleteCookie(idToDelete);            
            addMessage("Cookie gelöscht");
        }else{
            addMessage("Cookie nicht gefunden");
        }
    }
    
    //Buttons in final.xhtml
    public void orderDeleteCookieButton(int toOrderId) {
        //TODO delete cookie from order
    }
    public void confirmOrderButton() {

        for(Bestellposten bp : bs.allBestellposten(order.getId())) {
            if(!cs.isThereCookie(bp.getCookieId())) {
                addMessage("Cookie "+bp.getCookieId()+" existiert nicht mehr");
                rewind();
                break;
            } else {
                if(!(bp.getCount() <= cs.findCookie(bp.getCookieId()).getCount())) {
                    addMessage("Cookie "+bp.getCookieId()+" existiert nicht mehr in der Stückzahl");
                    rewind();
                    break;
                } else {
                    //bestellposten ausführen
                    addMessage("DEBUG: "+bp.getCount()+"|"+bp.getCookieId());
                    Cookie c = cs.findCookie(bp.getCookieId());
                    c.setCount(c.getCount() - bp.getCount());
                    cs.updateCookie(c);
                    
                    //bestellstatus auf positiv
                    bp.setStatus(true);
                    bs.updateBestellposten(bp);
                }
            }
        }
        
        //aufräumen
        addMessage("Bestellung erfolgreich");
        orderCount = 0;
        order = new Bestellung();
        bs.addBestellung(order);
    }
    
    //Bestellung bei Fehler wieder rückgängig machen
    public void rewind() {
        for(Bestellposten bp : bs.allBestellposten(order.getId())) {
            if(bp.isStatus() == true) {
                
                //bearbeitete Posten wieder hochzählen
                Cookie c = cs.findCookie(bp.getCookieId());
                c.setCount(c.getCount() + bp.getCount());
                cs.updateCookie(c);
                
                //Status des Bestellpostens zurücksetzen
                bp.setStatus(false);
                bs.updateBestellposten(bp);
            }
        }

    }
    
    //Functionality
    
    public int getBestellungCount(int id) {
        Bestellposten bp = bs.findBestellpostenByCookie(id);
        return bp.getCount();
    }
    
    public double getSummedPrice(int id) {
        Bestellposten bp = bs.findBestellpostenByCookie(id);
        return  bp.getCount() * cs.findCookie(id).getPrice();
    }
    
    public List<Cookie> cookies() {
        return cs.cookies();
    }
    
    public List<Cookie> ordered_cookies() {
        return cs.ordered_cookies(order.getId());
    }
    
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public int getOrderCount() {
        return orderCount;
    } 

    public CookieService getCs() {
        return cs;
    } 
     
     public String getToAddName() {
        return toAddName;
    }

    public double getToAddPrice() {
        return toAddPrice;
    }

    public int getToAddCount() {
        return toAddCount;
    }

    public int getIdToDelete() {
        return idToDelete;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
    
    public void setToAddName(String toAddName) {
        this.toAddName = toAddName;
    }

    public void setToAddPrice(double toAddPrice) {
        this.toAddPrice = toAddPrice;
    }

    public void setToAddCount(int toAddCount) {
        this.toAddCount = toAddCount;
    }

    public void setIdToDelete(int idToDelete) {
        this.idToDelete = idToDelete;
    }
}