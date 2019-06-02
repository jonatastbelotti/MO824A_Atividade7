package problems.bpp.solvers;

import java.util.ArrayList;
import problems.bpp.Item;
import problems.bpp.Mochila;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class BF extends NF {

    public BF(String nomeArquivo) {
        super(nomeArquivo);
    }

    @Override
    public void solve() {
        Mochila m;
        this.mochilas = new ArrayList<>();

        for (Item item : Item.getItens()) {
            m = null;

            for (Mochila mochila : this.mochilas) {
                if (mochila.cabeItem(item) && (m == null || (m != null && mochila.getEspacoResidual() > m.getEspacoResidual()))) {
                    m = mochila;
                }
            }

            if (m == null) {
                m = new Mochila();
                this.mochilas.add(m);
            }

            m.addItem(item);

        }
    }

    public static void main(String[] args) {
        String arquivo = "./bpp_instances/instance9.bpp";

        System.out.println("Algoritmo Best Fit");
        System.out.println("Arqivo " + arquivo);

        BF ff = new BF(arquivo);

        ff.solve();

        System.out.println(ff);
    }

}
