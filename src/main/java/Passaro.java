
/**
 *
 * @author Jordão
 */
public class Passaro {

    public double x, y, vy;
    public static double G = 1200;
    public static double FLAP = -350;
    public Hitbox box;
    public Tempo sprite_tempo;
    public int sprite_state = 0;
    public int[] sprite_states = {0, 1, 2, 1};
    public int[] sprites_x = {528, 528, 446};
    public int[] sprites_y = {128, 180, 248};

    private Acao mudasprite() {
        return new Acao() {
            @Override
            public void executa() {
                sprite_state += 1;
                sprite_state = sprite_state % sprite_states.length;
            }
        };
    }

    public Passaro(double x, double y) {
        this.x = x;
        this.y = y;
        this.vy = 0;
        this.box = new Hitbox(x, y, x + 34, y + 24);
        sprite_tempo = new Tempo(0.1, true, mudasprite());
    }

    public void update(double dt) {
        vy += G * dt;
        y += vy * dt;
        this.box.mover(0, vy * dt);
    }

    public void updateSprite(double dt) {
        sprite_tempo.frames(dt);
    }

    public void drawItself(Tela t) {
        t.imagem("flappy.png", sprites_x[sprite_states[sprite_state]], sprites_y[sprite_states[sprite_state]], 34, 24, Math.atan(vy / 200), x, y);
    }

    public void flap() {
        this.vy = FLAP;
    }
}
