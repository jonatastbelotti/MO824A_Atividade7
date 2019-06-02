package problems.bpp.solvers;

import java.util.ArrayList;
import java.util.List;
import problems.bpp.Item;
import problems.bpp.Mochila;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class NFD {

    protected List<Mochila> mochilas = null;

    public NFD(String nomeArquivo) {
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
        String arquivo = "./bpp_instances/instance9.bpp";

        System.out.println("Algoritmo Next Fit");
        System.out.println("Arqivo " + arquivo);

        NFD ff = new NFD(arquivo);

        ff.solve();

        System.out.println(ff);
    }

}
