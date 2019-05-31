package problems.bpp;

import java.util.ArrayList;

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
        this.mochilas = new ArrayList<>();
    }

    public static void main(String[] args) {
        String arquivo = "./bpp_instances/instance0.bpp";

        System.out.println("Algoritmo Best Fit");
        System.out.println("Arqivo " + arquivo);

        BF ff = new BF(arquivo);

        ff.solve();

        System.out.println(ff);
    }

}
