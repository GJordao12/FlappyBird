
/**
 *
 * @author Jordão
 */
public final class ScoreNumber {

    public int numero;
    public String sNumero;
    public static int recorde = 0;
    public static int[][] numeroData = {{576, 200}, {578, 236}, {578, 268}, {578, 300},{574, 346}, {574, 370}, {330, 490}, {350, 490}, {370, 490}, {390, 490}};

    public ScoreNumber(int n) {
        this.numero = n;
        setSNumero();
    }

    public void setSNumero() {
        sNumero = String.valueOf(numero);
    }

    public void setScore(int n) {
        numero = n;
        setSNumero();
    }

    public int getScore() {
        return numero;
    }

    public void modificaScore(int dn) {
        numero += dn;
        setSNumero();
    }

    public void escreveScore(Tela t, int x, int y) {
        for (int i = 0; i < sNumero.length(); i++) {
            escreveNumero(t, Integer.parseInt(sNumero.substring(i, i + 1)), x + 15 * i, y);
        }
    }

    public void escreveRecorde(Tela t, int x, int y) {
        String srecord = String.valueOf(ScoreNumber.recorde);
        for (int i = 0; i < srecord.length(); i++) {
            escreveNumero(t, Integer.parseInt(srecord.substring(i, i + 1)), x + 15 * i, y);
        }
    }

    public void escreveNumero(Tela t, int number, int x, int y) {
        t.imagem("flappy.png", numeroData[number][0], numeroData[number][1], 14, 20, 0, x, y);
    }
}
