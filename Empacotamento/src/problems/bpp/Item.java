package problems.bpp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 */
public class Item {

    private static List<Item> listaItens = null;
    private static int N;

    private int id;
    private int size;

    public Item(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public static void carregarArquivo(String nomeArquivo) {
        Item.listaItens = new ArrayList<>();

        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            Item.N = Integer.parseInt(lerArq.readLine());
            Mochila.CAPACIDADE = Integer.parseInt(lerArq.readLine());

            String linha = lerArq.readLine();
            while (linha != null) {
                Item.listaItens.add(new Item(Item.listaItens.size(), Integer.parseInt(linha)));
                linha = lerArq.readLine();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo:" + e.getMessage());
        }

    }
    
    public static int getNumItens() {
        return Item.N;
    }
    
    public static List<Item> getItens() {
        return Item.listaItens;
    }

}
