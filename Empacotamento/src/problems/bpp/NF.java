package problems.bpp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class NF {
    private List<Mochila> mochilas = null;

    public NF(String nomeArquivo) {
        Item.carregarArquivo(nomeArquivo);
        this.mochilas = new ArrayList<>();
    }
    
    public void solve() {
        this.mochilas = new ArrayList<>();
        Mochila m = new Mochila();
        
        for (Item i : Item.getItens()) {
            if (!m.addItem(i)) {
                this.mochilas.add(m);
                m = new Mochila();
                m.addItem(i);
            }
        }
        
        this.mochilas.add(m);
    }
    
    @Override
    public String toString() {
        int i = 0;
        String resp = "Foram usadas " + this.mochilas.size() + " mochilas\n";
        
        for (Mochila m : this.mochilas) {
            resp += "M" + (i++) + ":";
            
            for (Item item : m.getItens()) {
                resp += " " + item.getId();
            }
            
            resp += "\n";
        }
        
        return resp;
    }
    
    public static void main(String[] args) {
        NF ff = new NF("./bpp_instances/instance0.bpp");
        
        ff.solve();
        
        System.out.println(ff);
    }
    

}
