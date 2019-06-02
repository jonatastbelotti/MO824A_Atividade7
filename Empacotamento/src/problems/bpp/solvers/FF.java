package problems.bpp.solvers;

import java.util.ArrayList;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class FF extends NF {

    public FF(String nomeArquivo) {
        super(nomeArquivo);
    }

    @Override
    public void solve() {
        this.mochilas = new ArrayList<>();
    }

    public static void main(String[] args) {
        String arquivo = "./bpp_instances/instance0.bpp";

        System.out.println("Algoritmo First Fit");
        System.out.println("Arqivo " + arquivo);

        FF ff = new FF(arquivo);

        ff.solve();

        System.out.println(ff);
    }

}
