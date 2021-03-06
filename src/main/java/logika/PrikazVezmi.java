package logika;


/**
 * Třída představuje příkaz pro sebrání předmětu z aktuální lokace
 * a jeho vložení do batohu (inventáře) postavy.
 * 
 * @author     Jan Riha, Markéta Halíková
 * @version    LS 2016/2017
 */
public class PrikazVezmi implements IPrikaz{
    private static final String NAZEV = "vezmi";
    private HerniPlan hPlan;
    
    public PrikazVezmi(HerniPlan hPlan){
        this.hPlan = hPlan;
    }
    
    /**
     * Metoda představuje zpracování příkazu pro sebrání předmětu.
     * Nejprve zkontroluje, zda byl zadán právě jeden název jako
     * parametr, ověří, zda v aktuální lokaci je předmět s tímto
     * názvem, zda je přenositelný, zda je v lékárničce místo a
     * následně předmět odebere z lokace a vloží ho do lékárničky.
     * 
     * @param parametry pole parametrů zadaných na příkazové řádce
     * @return výsledek zpracování, tj. text, který se vypíše na konzoli
     */
    public String proved(String... parametry){        
        if (parametry.length < 1)
        {
            return "Nevím, co mám sebrat";
        }
        
        if (parametry.length > 1)
        {
            return "Tomu nerozumím, nedokážu sebrat více věcí najednou";
        }
        
        String nazevPredmetu = parametry[0];
        Lokace aktLokace = hPlan.getAktualniLokace();
        
        if (!aktLokace.obsahujePredmet(nazevPredmetu))
        {
            return "Předmět " + nazevPredmetu + " tady není";
        }
  
        Predmet predmet = aktLokace.vezmiPredmet(nazevPredmetu);
        
        if ((!predmet.isPrenositelny()) && (aktLokace.getNepritele() != null))
        {
            aktLokace.vlozPredmet(predmet);
            return  nazevPredmetu + " si vzít nemůžeš, nejdřív musíš zneškodnit nepřítele!";
        }
        if (!predmet.isPrenositelny())
        {
            aktLokace.vlozPredmet(predmet);
            return  nazevPredmetu + " si vzít nemůžeš, bez toho pacient nepřežije!";
        }
        

        Lekarnicka lekarnicka = hPlan.getLekarnicka();
        if (!lekarnicka.vlozPredmet(predmet)){
            aktLokace.vlozPredmet(predmet);
            return "V lékárniččce už nemáš volné místo, můžeš nést jen dvě věci - musíš něco zahodit";
        }
        hPlan.setAktualniLokace(aktLokace);
        return "Sebral(a) jsi předmět " + nazevPredmetu;
    }
    
    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
    public String getNazev(){
        return NAZEV;
    }
    
}
