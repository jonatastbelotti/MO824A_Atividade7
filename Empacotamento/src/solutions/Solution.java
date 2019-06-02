package solutions;

import java.util.ArrayList;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
@SuppressWarnings("serial")
public class Solution<E> extends ArrayList<E> {

    public Double cost = Double.POSITIVE_INFINITY;

    public Solution() {
        super();
    }

    public Solution(Solution<E> sol) {
        super(sol);
        cost = sol.cost;
    }

    @Override
    public String toString() {
        return "Solution: cost=[" + cost + "], size=[" + this.size() + "], elements=" + super.toString();
    }

    public String descCompleta() {
        return toString();
    }

}
