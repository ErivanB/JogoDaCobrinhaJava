import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a cobra no jogo.
 */
public class Snake {
    private List<Point> body;
    private Direction direction;
    private boolean growing;
    private final int UNIT_SIZE;
    private final Color HEAD_COLOR = new Color(0, 153, 0); // Verde escuro para a cabeça
    private final Color BODY_COLOR = new Color(0, 204, 0); // Verde mais claro para o corpo
    
    /**
     * Construtor da classe Snake.
     * 
     * @param unitSize Tamanho de cada unidade do jogo
     * @param startX Posição inicial X da cabeça da cobra
     * @param startY Posição inicial Y da cabeça da cobra
     */
    public Snake(int unitSize, int startX, int startY) {
        this.UNIT_SIZE = unitSize;
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT; // Direção inicial
        this.growing = false;
        
        // Inicializa a cobra com 3 segmentos
        body.add(new Point(startX, startY)); // Cabeça
        body.add(new Point(startX - UNIT_SIZE, startY)); // Corpo
        body.add(new Point(startX - 2 * UNIT_SIZE, startY)); // Cauda
    }
    
    /**
     * Move a cobra na direção atual.
     */
    public void move() {
        // Se a cobra estiver crescendo, não remove a cauda
        if (!growing) {
            body.remove(body.size() - 1); // Remove a cauda
        } else {
            growing = false; // Reseta a flag de crescimento
        }
        
        // Obtém a posição atual da cabeça
        Point head = body.get(0);
        Point newHead = new Point(head);
        
        // Move a cabeça na direção atual
        switch (direction) {
            case UP:
                newHead.y -= UNIT_SIZE;
                break;
            case DOWN:
                newHead.y += UNIT_SIZE;
                break;
            case LEFT:
                newHead.x -= UNIT_SIZE;
                break;
            case RIGHT:
                newHead.x += UNIT_SIZE;
                break;
        }
        
        // Adiciona a nova cabeça no início da lista
        body.add(0, newHead);
    }
    
    /**
     * Faz a cobra crescer no próximo movimento.
     */
    public void grow() {
        growing = true;
    }
    
    /**
     * Verifica se a cobra colidiu com a comida.
     * 
     * @param food Objeto Food para verificar colisão
     * @return true se houve colisão, false caso contrário
     */
    public boolean checkFoodCollision(Food food) {
        Point head = body.get(0);
        return head.equals(food.getPosition());
    }
    
    /**
     * Verifica se a cobra colidiu com as paredes.
     * 
     * @param width Largura do painel de jogo
     * @param height Altura do painel de jogo
     * @return true se houve colisão, false caso contrário
     */
    public boolean checkWallCollision(int width, int height) {
        Point head = body.get(0);
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }
    
    /**
     * Verifica se a cobra colidiu com o próprio corpo.
     * 
     * @return true se houve colisão, false caso contrário
     */
    public boolean checkSelfCollision() {
        Point head = body.get(0);
        // Começa do índice 1 para ignorar a cabeça
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Desenha a cobra no painel de jogo.
     * 
     * @param g Objeto Graphics para desenhar
     */
    public void draw(Graphics g) {
        // Desenha a cabeça com uma cor diferente
        g.setColor(HEAD_COLOR);
        Point head = body.get(0);
        g.fillRect(head.x, head.y, UNIT_SIZE, UNIT_SIZE);
        
        // Desenha o corpo
        g.setColor(BODY_COLOR);
        for (int i = 1; i < body.size(); i++) {
            Point segment = body.get(i);
            g.fillRect(segment.x, segment.y, UNIT_SIZE, UNIT_SIZE);
        }
    }
    
    /**
     * Define a direção da cobra.
     * Impede que a cobra faça um movimento de 180 graus.
     * 
     * @param newDirection Nova direção
     */
    public void setDirection(Direction newDirection) {
        // Impede que a cobra faça um movimento de 180 graus
        if ((direction == Direction.UP && newDirection == Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection == Direction.UP) ||
            (direction == Direction.LEFT && newDirection == Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }
        
        this.direction = newDirection;
    }
    
    /**
     * Retorna o tamanho atual da cobra.
     * 
     * @return Número de segmentos da cobra
     */
    public int getSize() {
        return body.size();
    }
}