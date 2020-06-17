
/**
 *
 * @author Jordão
 */
public class Cano {

    public double x, y;

    /**
     * Velocidade de movimentação dos canos, em relação ao eixo do x.
     */
    public static int xVelocidade = -100;

    /**
     * Espaço entre os canos.
     */
    public static int EspacoEntreCanos = 92;

    public boolean counted = false;

    public Hitbox boxCima;
    public Hitbox boxBaixo;

    public Cano(double x, double y) {
        this.x = x;
        this.y = y;
        this.boxCima = new Hitbox(x, y - 270 - 220, x + 52, y);
        this.boxBaixo = new Hitbox(x, y + EspacoEntreCanos, x + 52, y + EspacoEntreCanos + 442);
    }

    public void frames(double dt) {
        x += xVelocidade * dt;
        boxCima.mover(xVelocidade * dt, 0);
        boxBaixo.mover(xVelocidade * dt, 0);
    }

    public void drawItself(Tela t) {
        t.imagem("flappy.png", 660, 0, 52, 242, 0, x, y + EspacoEntreCanos); //cano virado pra cima
        t.imagem("flappy.png", 660, 42, 52, 200, 0, x, y + EspacoEntreCanos + 242); //resto do cano virado pra cima

        t.imagem("flappy.png", 604, 0, 52, 270, 0, x, y - 270); //cano virado pra baixo
        t.imagem("flappy.png", 604, 0, 52, 220, 0, x, y - 270 - 220); //resto do cano virado pra baixo
    }
}
