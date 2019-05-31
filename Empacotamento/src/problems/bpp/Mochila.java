package problems.bpp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class Mochila {

    public static int CAPACIDADE;

    private List<Item> itens;
    private int espacoResidual;

    public Mochila() {
        this.itens = new ArrayList<>();
        this.espacoResidual = Mochila.CAPACIDADE;
    }
    
    public boolean addItem(Item item) {
        if (this.espacoResidual < item.getSize()) {
            return false;
        }
        
        this.itens.add(item);
        this.espacoResidual -= item.getSize();
        return true;
    }
    
    public List<Item> getItens() {
        return this.itens;
    }
    
    

}
