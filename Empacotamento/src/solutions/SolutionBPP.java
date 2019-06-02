package solutions;

import problems.bpp.Item;
import problems.bpp.Mochila;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class SolutionBPP extends Solution<Item> {

    public SolutionBPP() {
        super();
    }

    public SolutionBPP(Solution<Item> sol) {
        super(sol);
    }

    @Override
    public String toString() {
        String resp = "Solution: cost=[" + cost + "], size=[" + this.size() + "], elements=[";
        String itens = "";

        for (Item item : this.subList(0, this.size())) {
            if (!itens.isEmpty()) {
                itens += ", ";
            }

            itens += item.getId();
        }

        resp += itens + "]";

        return resp;
    }

    @Override
    public String descCompleta() {
        String resp;
        int soma;
        int m = 0;

        resp = "Número de mochilas: " + this.cost;
        resp += "\nM" + (m++) + ": " + this.get(0).getId();
        soma = this.get(0).getSize();

        for (Item item : this.subList(1, this.size())) {
            if (soma + item.getSize() > Mochila.CAPACIDADE) {
                soma = item.getSize();
                resp += "\nM" + (m++) + ": " + item.getId();
            } else {
                soma += item.getSize();
                resp += ", " + item.getId();
            }
        }

        return resp;
    }

}
