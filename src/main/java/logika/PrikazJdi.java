/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Lubos Pavlicek, Jan Riha, Markéta Halíková
 * @version    LS 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;

   /**
    * Konstruktor třídy PrikazJdi
    *
    * @param    plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadané lokace. Pokud lokace
     * existuje, vstoupí se do nového lokace. Pokud zadaná sousední lokace
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param     parametry jako parametr obsahuje jméno lokace (východu), do kterého se má jít.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousední lokace
        Lokace sousedniLokace = plan.getAktualniLokace().vratSousedniLokaci(smer);

        if (sousedniLokace == null) {
            return "Tam se odsud jít nedá!";
        }
        else {
            if(sousedniLokace.jeZamceno()) {
                return "lokace "+sousedniLokace.getNazev()
                     +" je zamčená! Musíš ji odemknout";
            }
            
            if(sousedniLokace.getNepritele()!= null){
                
                if(!sousedniLokace.getNepritele().isZivy()){
                    plan.setAktualniLokace(sousedniLokace);
                    return sousedniLokace.dlouhyPopis();
                }
                plan.setAktualniLokace(sousedniLokace);
                return sousedniLokace.dlouhyPopis()+ "\n"+ "\n"
                    +" POZOR!!!POZOR!!! narazil/a jsi na nepritele " + sousedniLokace.getNepritele().getNazev()+ ".\n"
                    +" Aby si mohl sebrat predmety, ktere chces, musis nejdrive nepritele zneškodnit" + ".\n"
                    +" Parazita můžeš prozkoumat, aby si zjistil/a, jak na něj.";
            
            }
            
            plan.setAktualniLokace(sousedniLokace);
            return sousedniLokace.dlouhyPopis();
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
