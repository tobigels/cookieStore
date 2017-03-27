package package_Cookie;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersistenceProducer {
    @Produces
    @PersistenceContext(unitName = "Kbse_2017_Luto_PU")
    private EntityManager em;
}
