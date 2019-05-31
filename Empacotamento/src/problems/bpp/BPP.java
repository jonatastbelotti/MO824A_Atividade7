package problems.bpp;

import problems.Evaluator;
import solutions.Solution;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class BPP implements Evaluator<Item> {

    @Override
    public Integer getDomainSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double evaluate(Solution<Item> sol) {
        Double resp = 0D;
        Double soma = 0D;

        for (Item item : sol) {
            if (soma + item.getSize() > Mochila.CAPACIDADE) {
                soma = Double.parseDouble(item.getSize() + "");
                resp += 1D;
            } else {
                soma += Double.parseDouble(item.getSize() + "");
            }
        }

        return resp;
    }

    @Override
    public Double evaluateInsertionCost(Item elem, Solution<Item> sol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double evaluateRemovalCost(Item elem, Solution<Item> sol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double evaluateExchangeCost(Item elemIn, Item elemOut, Solution<Item> sol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
