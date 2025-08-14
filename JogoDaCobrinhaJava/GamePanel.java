import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Painel principal do jogo onde a cobra se move e interage com a comida.
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    // Constantes do jogo
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 20; // Tamanho de cada unidade do jogo
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100; // Velocidade do jogo (menor = mais rápido)
    
    // Componentes do jogo
    private Snake snake;
    private Food food;
    private Timer timer;
    private RankingSystem rankingSystem;
    
    // Estado do jogo
    private boolean running = false;
    private boolean gameOver = false;
    
    /**
     * Construtor da classe GamePanel.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        // Inicializa o sistema de ranking
        rankingSystem = new RankingSystem();
        
        startGame();
    }
    
    /**
     * Inicia o jogo.
     */
    public void startGame() {
        // Inicializa a cobra no centro da tela
        snake = new Snake(UNIT_SIZE, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        
        // Inicializa a comida
        food = new Food(UNIT_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        // Inicia o timer
        timer = new Timer(DELAY, this);
        timer.start();
        
        running = true;
        gameOver = false;
    }
    
    /**
     * Método chamado pelo timer para atualizar o estado do jogo.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            // Move a cobra
            snake.move();
            
            // Verifica colisão com a comida
            if (snake.checkFoodCollision(food)) {
                snake.grow();
                food.newFood();
            }
            
            // Verifica colisão com as paredes
            if (snake.checkWallCollision(SCREEN_WIDTH, SCREEN_HEIGHT)) {
                gameOver();
            }
            
            // Verifica colisão com o próprio corpo
            if (snake.checkSelfCollision()) {
                gameOver();
            }
        }
        
        // Redesenha o painel
        repaint();
    }
    
    /**
     * Desenha os componentes do jogo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    /**
     * Método auxiliar para desenhar os componentes do jogo.
     */
    private void draw(Graphics g) {
        if (running) {
            // Desenha a grade (opcional)
            drawGrid(g);
            
            // Desenha a comida
            food.draw(g);
            
            // Desenha a cobra
            snake.draw(g);
            
            // Desenha a pontuação
            drawScore(g);
        } else if (gameOver) {
            drawGameOver(g);
        }
    }
    
    /**
     * Desenha a grade do jogo (opcional).
     */
    private void drawGrid(Graphics g) {
        g.setColor(new Color(20, 20, 20)); // Cinza escuro
        
        // Linhas horizontais
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        
        // Linhas verticais
        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }
    }
    
    /**
     * Desenha a pontuação atual.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String scoreText = "Pontuação: " + (snake.getSize() - 3); // -3 porque a cobra começa com 3 segmentos
        g.drawString(scoreText, (SCREEN_WIDTH - metrics.stringWidth(scoreText)) / 2, g.getFont().getSize());
    }
    
    /**
     * Desenha a tela de game over.
     */
    private void drawGameOver(Graphics g) {
        // Desenha a pontuação final
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        String gameOverText = "Game Over";
        g.drawString(gameOverText, (SCREEN_WIDTH - metrics1.stringWidth(gameOverText)) / 2, SCREEN_HEIGHT / 2 - 50);
        
        // Desenha a pontuação
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        String scoreText = "Pontuação Final: " + (snake.getSize() - 3);
        g.drawString(scoreText, (SCREEN_WIDTH - metrics2.stringWidth(scoreText)) / 2, SCREEN_HEIGHT / 2);
        
        // Instruções para reiniciar
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        String restartText = "Pressione ESPAÇO para jogar novamente";
        g.drawString(restartText, (SCREEN_WIDTH - metrics3.stringWidth(restartText)) / 2, SCREEN_HEIGHT / 2 + 50);
    }
    
    /**
     * Finaliza o jogo e salva a pontuação no ranking.
     */
    private void gameOver() {
        running = false;
        gameOver = true;
        timer.stop();
        
        // Solicita o nome do jogador e salva a pontuação
        String playerName = JOptionPane.showInputDialog(this, "Digite seu nome para o ranking:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        
        if (playerName != null && !playerName.trim().isEmpty()) {
            rankingSystem.addScore(playerName, snake.getSize() - 3);
            
            // Pergunta se o jogador quer ver o ranking
            int option = JOptionPane.showConfirmDialog(this, "Deseja ver o ranking?", "Ranking", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                rankingSystem.showRanking();
            }
        }
    }
    
    /**
     * Reinicia o jogo.
     */
    private void restartGame() {
        if (gameOver) {
            startGame();
        }
    }
    
    /**
     * Classe interna para tratar os eventos de teclado.
     */
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    snake.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_SPACE:
                    restartGame();
                    break;
                case KeyEvent.VK_R:
                    // Mostra o ranking quando o jogador pressiona R
                    if (!running || gameOver) {
                        rankingSystem.showRanking();
                    }
                    break;
            }
        }
    }
    
    /**
     * Retorna o sistema de ranking.
     * 
     * @return Sistema de ranking
     */
    public RankingSystem getRankingSystem() {
        return rankingSystem;
    }
}