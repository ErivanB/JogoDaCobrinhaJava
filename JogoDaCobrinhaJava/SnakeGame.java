import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe principal que inicia o jogo da cobrinha.
 */
public class SnakeGame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private GamePanel gamePanel;
    private JMenuBar menuBar;
    
    /**
     * Construtor da classe SnakeGame.
     */
    public SnakeGame() {
        this.setTitle("Jogo da Cobrinha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Cria o painel do jogo
        gamePanel = new GamePanel();
        
        // Cria a barra de menu
        createMenuBar();
        
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null); // Centraliza a janela
        this.setVisible(true);
    }
    
    /**
     * Cria a barra de menu do jogo.
     */
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        // Menu Jogo
        JMenu gameMenu = new JMenu("Jogo");
        
        JMenuItem newGameItem = new JMenuItem("Novo Jogo");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.startGame();
                gamePanel.requestFocus(); // Devolve o foco para o painel do jogo
            }
        });
        
        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        gameMenu.add(newGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        // Menu Ranking
        JMenu rankingMenu = new JMenu("Ranking");
        
        JMenuItem showRankingItem = new JMenuItem("Mostrar Ranking");
        showRankingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getRankingSystem().showRanking();
                gamePanel.requestFocus(); // Devolve o foco para o painel do jogo
            }
        });
        
        rankingMenu.add(showRankingItem);
        
        // Menu Ajuda
        JMenu helpMenu = new JMenu("Ajuda");
        
        JMenuItem instructionsItem = new JMenuItem("Instruções");
        instructionsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
                gamePanel.requestFocus(); // Devolve o foco para o painel do jogo
            }
        });
        
        JMenuItem aboutItem = new JMenuItem("Sobre");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
                gamePanel.requestFocus(); // Devolve o foco para o painel do jogo
            }
        });
        
        helpMenu.add(instructionsItem);
        helpMenu.add(aboutItem);
        
        // Adiciona os menus à barra de menu
        menuBar.add(gameMenu);
        menuBar.add(rankingMenu);
        menuBar.add(helpMenu);
        
        // Define a barra de menu
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Exibe as instruções do jogo.
     */
    private void showInstructions() {
        String instructions = 
            "Instruções do Jogo da Cobrinha:\n\n" +
            "- Use as setas do teclado para controlar a direção da cobra\n" +
            "- Coma a comida vermelha para crescer e ganhar pontos\n" +
            "- Evite colidir com as paredes ou com o próprio corpo\n" +
            "- Pressione ESPAÇO para reiniciar o jogo após o Game Over\n" +
            "- Pressione R para ver o ranking a qualquer momento";
        
        JOptionPane.showMessageDialog(this, instructions, "Instruções", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Exibe informações sobre o jogo.
     */
    private void showAbout() {
        String about = 
            "Jogo da Cobrinha\n\n" +
            "Versão 1.0\n" +
            "Desenvolvido em Java com sistema de ranking\n";
        
        JOptionPane.showMessageDialog(this, about, "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Método principal que inicia o jogo.
     * 
     * @param args Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Usa o SwingUtilities para garantir que a interface gráfica seja criada na thread de eventos
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SnakeGame();
            }
        });
    }
}