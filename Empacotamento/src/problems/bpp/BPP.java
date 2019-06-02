package problems.bpp;

import problems.Evaluator;
import solutions.Solution;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class BPP implements Evaluator<Item> {

    public BPP(String nomeArquivo) {
        Item.carregarArquivo(nomeArquivo);
    }

    @Override
    public Integer getDomainSize() {
        return Item.getNumItens();
    }

    @Override
    public Double evaluate(Solution<Item> sol) {
        Mochila m = new Mochila();
        Double numMochilas = 1D;

        if (sol.isEmpty()) {
            return 0D;
        }

        for (Item item : sol) {
            if (!m.addItem(item)) {
                m = new Mochila();
                m.addItem(item);
                numMochilas += 1D;
            }
        }

        sol.cost = numMochilas;
        return sol.cost;
    }

    @Override
    public Double evaluateInsertionCost(Item elem, Solution<Item> sol) {
        Mochila m = new Mochila();

        for (Item item : sol) {
            if (!m.addItem(item)) {
                m = new Mochila();
                m.addItem(item);
            }
        }

        if (m.addItem(elem)) {
            return m.getEspacoResidual().doubleValue();
        }

        return -1D;
    }

    @Override
    public Double evaluateRemovalCost(Item elem, Solution<Item> sol) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Double evaluateExchangeCost(Item elemIn, Item elemOut, Solution<Item> sol) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Double evaluateChangeCost(Item elem1, Item elem2, Solution<Item> sol) {
        Solution<Item> solAux = new Solution<>(sol);
        Integer indice1, indice2;
        Double resp, espaco = Double.MIN_VALUE;

        indice1 = solAux.indexOf(elem1);
        indice2 = solAux.indexOf(elem2);

        solAux.set(indice1, elem2);
        solAux.set(indice2, elem1);

        solAux.cost = this.evaluate(solAux);

        resp = solAux.cost - sol.cost;

        if (resp.equals(0D)) {
            Mochila m = new Mochila(), m1 = new Mochila(), m2 = new Mochila();

            for (Item item : solAux) {
                if (!m.addItem(item)) {
                    m = new Mochila();
                    m.addItem(item);
                }

                if (item.equals(elem1)) {
                    m1 = m;
                }

                if (item.equals(elem2)) {
                    m2 = m;
                }
            }

            resp -= (Math.max(m1.getEspacoResidual().doubleValue(), m2.getEspacoResidual().doubleValue())) / Mochila.CAPACIDADE;
        }

        return resp;
    }

}
