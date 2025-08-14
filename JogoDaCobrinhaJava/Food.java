import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * Classe que representa a comida no jogo da cobrinha.
 */
public class Food {
    private Point position;
    private final int UNIT_SIZE;
    private final int GAME_UNITS_X;
    private final int GAME_UNITS_Y;
    private final Color color;
    private final Random random;
    
    /**
     * Construtor da classe Food.
     * 
     * @param unitSize Tamanho de cada unidade do jogo
     * @param width Largura do painel de jogo
     * @param height Altura do painel de jogo
     */
    public Food(int unitSize, int width, int height) {
        this.UNIT_SIZE = unitSize;
        this.GAME_UNITS_X = width / unitSize;
        this.GAME_UNITS_Y = height / unitSize;
        this.color = Color.RED;
        this.random = new Random();
        this.position = new Point();
        newFood(); // Gera a posição inicial da comida
    }
    
    /**
     * Gera uma nova posição aleatória para a comida.
     */
    public void newFood() {
        position.x = random.nextInt(GAME_UNITS_X) * UNIT_SIZE;
        position.y = random.nextInt(GAME_UNITS_Y) * UNIT_SIZE;
    }
    
    /**
     * Desenha a comida no painel de jogo.
     * 
     * @param g Objeto Graphics para desenhar
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(position.x, position.y, UNIT_SIZE, UNIT_SIZE);
    }
    
    /**
     * Retorna a posição atual da comida.
     * 
     * @return Posição da comida
     */
    public Point getPosition() {
        return position;
    }
}