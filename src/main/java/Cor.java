
/**
 *
 * @author Jordão
 */
import java.util.HashMap;

/**
 * Cores em RGB.
 */
public class Cor {

    public int r;
    public int g;
    public int b;

    private static final HashMap<Integer, Cor> CORES = new HashMap<Integer, Cor>();

    /*
     * Cria uma cor dado os componentes entre 0 e 255.
     */
    private Cor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /*
     * Cria uma cor dados os componentes entre 0 e 1.
     */
    public static Cor rgb(double r, double g, double b) {
        return Cor.rgb((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static Cor rgb(int r, int g, int b) {
        int indice = (r << 16) | (g << 8) | b;
        if (!CORES.containsKey(indice)) {
            CORES.put(indice, new Cor(r, g, b));
        }
        return CORES.get(indice);
    }

    public static Cor BRANCO = Cor.rgb(1.0, 1.0, 1.0);
}
