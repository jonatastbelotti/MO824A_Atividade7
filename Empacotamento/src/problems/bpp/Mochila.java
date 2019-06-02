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
    public static int N;

    private List<Item> itens;
    private int espacoResidual;

    public Mochila() {
        this.itens = new ArrayList<>();
        this.espacoResidual = Mochila.CAPACIDADE;
    }

    public boolean addItem(Item item) {
        if (!this.cabeItem(item)) {
            return false;
        }

        this.itens.add(item);
        this.espacoResidual -= item.getSize();
        return true;
    }

    public boolean cabeItem(Item item) {
        return this.espacoResidual >= item.getSize();
    }

    public List<Item> getItens() {
        return this.itens;
    }

    public Integer getEspacoResidual() {
        return espacoResidual;
    }

}
