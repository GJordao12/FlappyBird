
/**
 *
 * @author Jordão
 */
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

public final class FlappyBird implements Jogo {

    public Passaro passaro;
    /**
     * Gerar alturas para os canos;
     */
    public Random gerador = new Random();
    public ScoreNumber scoreNumber;

    public int game_state = 0; //[0->Start] [1->Get Ready] [2->Game] [3->Game Over]

    public double scenario_offset = 0;
    public double ground_offset = 0;
    public ArrayList<Cano> canos = new ArrayList<>();
    public Tempo pipeTempo;
    public Hitbox groundBox;

    public Tempo auxTempo;

    public static void main(String[] args) {
        roda();
    }

    private static void roda() {
        new Motor(new FlappyBird());
    }

    private Acao addCano() {
        return new Acao() {
            @Override
            public void executa() {
                canos.add(new Cano(getLargura(), gerador.nextInt(getAltura() - 112 - Cano.EspacoEntreCanos)));
            }
        };
    }

    private Acao proxCena() {
        return new Acao() {
            @Override
            public void executa() {
                game_state += 1;
                game_state = game_state % 4;
            }
        };
    }

    public FlappyBird() {
        passaro = new Passaro(50, getAltura() / 4);
        pipeTempo = new Tempo(2, true, addCano());
        scoreNumber = new ScoreNumber(0);
        groundBox = new Hitbox(0, getAltura() - 112, getLargura(), getAltura());

    }

    @Override
    public String getTitulo() {
        return "Flappy Bird";
    }

    public String getAuthor() {
        return "Gonçalo Jordão";
    }

    @Override
    public int getLargura() {
        return 384;
    }

    @Override
    public int getAltura() {
        return 512;
    }

    public void gameOver() {
        canos = new ArrayList<>();
        passaro = new Passaro(50, getAltura() / 4);
        proxCena().executa();
    }

    @Override
    public void frames(Set<String> keys, double dt) {
        scenario_offset += dt * 25;
        scenario_offset = scenario_offset % 288;
        ground_offset += dt * 100;
        ground_offset = ground_offset % 308;

        switch (game_state) {
            case 0: //Main Ecrã
                break;
            case 1: //Get Ready
                auxTempo.frames(dt);
                passaro.updateSprite(dt);
                break;
            case 2: //Game Ecrã
                pipeTempo.frames(dt);
                passaro.update(dt);
                passaro.updateSprite(dt);
                if (groundBox.intersecao(passaro.box) != 0) {
                    gameOver();
                    return;
                }
                if (passaro.y < -5) {
                    gameOver();
                    return;
                }
                for (Cano cano : canos) {
                    cano.frames(dt);
                    if (cano.boxCima.intersecao(passaro.box) != 0 || cano.boxBaixo.intersecao(passaro.box) != 0) {
                        if (scoreNumber.getScore() > ScoreNumber.recorde) {
                            ScoreNumber.recorde = scoreNumber.getScore();
                        }
                        gameOver();
                        return;
                    }
                    if (!cano.counted && cano.x < passaro.x) {
                        cano.counted = true;
                        scoreNumber.modificaScore(1);
                    }
                }
                if (canos.size() > 0 && canos.get(0).x < -70) {
                    canos.remove(0);
                }

                break;
            case 3: //Game Over Ecrã
                break;
        }
    }

    @Override
    public void tecla(String c) {
        switch (game_state) {
            case 0:
                if (c.equals(" ")) {
                    auxTempo = new Tempo(1.6, false, proxCena());
                    proxCena().executa();
                }
                break;
            case 1:
                break;
            case 2:
                if (c.equals(" ")) {
                    passaro.flap();
                }
                break;
            case 3:
                if (c.equals(" ")) {
                    scoreNumber.setScore(0);
                    proxCena().executa();
                }
                break;
        }
    }

    @Override
    public void desenhar(Tela t) {

        //Desenhar Background
        t.imagem("flappy.png", 0, 0, 288, 512, 0, (int) -scenario_offset, 0);
        t.imagem("flappy.png", 0, 0, 288, 512, 0, (int) (288 - scenario_offset), 0);
        t.imagem("flappy.png", 0, 0, 288, 512, 0, (int) ((288 * 2) - scenario_offset), 0);

        canos.forEach((cano) -> {
            cano.drawItself(t);
        });

        // Desenhar Ground
        t.imagem("flappy.png", 292, 0, 308, 112, 0, -ground_offset, getAltura() - 112);
        t.imagem("flappy.png", 292, 0, 308, 112, 0, 308 - ground_offset, getAltura() - 112);
        t.imagem("flappy.png", 292, 0, 308, 112, 0, (308 * 2) - ground_offset, getAltura() - 112);

        switch (game_state) {
            case 0:
                t.imagem("flappy.png", 292, 346, 192, 44, 0, getLargura() / 2 - 192 / 2, 100);
                t.imagem("flappy.png", 352, 306, 70, 36, 0, getLargura() / 2 - 70 / 2, 175);
                t.texto("Pressione espaço", 60, getAltura() / 2 - 16, 32, Cor.BRANCO);
                break;
            case 1:
                passaro.drawItself(t);
                t.imagem("flappy.png", 292, 442, 174, 44, 0, getLargura() / 2 - 174 / 2, getAltura() / 3);
                scoreNumber.escreveScore(t, 5, 5);
                break;
            case 2:
                scoreNumber.escreveScore(t, 5, 5);
                passaro.drawItself(t);
                break;
            case 3:
                //Game Over Texto
                t.imagem("flappy.png", 292, 398, 188, 38, 0, getLargura() / 2 - 188 / 2, 100);
                //Game Over Painel
                t.imagem("flappy.png", 292, 116, 226, 116, 0, getLargura() / 2 - 226 / 2, getAltura() / 2 - 116 / 2);
                if (scoreNumber.getScore() >= 0 && scoreNumber.getScore() < 25) {
                    //Medalha Bronze

                } else if (scoreNumber.getScore() >= 25 && scoreNumber.getScore() < 50) {
                    //Medalha Prata

                } else {
                    //Medalha Ouro

                }
                scoreNumber.escreveScore(t, getLargura() / 2 + 50, getAltura() / 2 - 25);
                scoreNumber.escreveRecorde(t, getLargura() / 2 + 55, getAltura() / 2 + 16);
                break;
        }
    }
}
