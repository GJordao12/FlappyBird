
/**
 *
 * @author Jordão
 */
public interface Jogo {

    String getTitulo();

    int getLargura();

    int getAltura();

    void frames(java.util.Set<String> teclas, double dt);

    void tecla(String tecla);

    void desenhar(Tela tela);
}
