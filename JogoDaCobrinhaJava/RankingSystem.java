import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável por gerenciar o sistema de ranking do jogo.
 * Permite salvar, carregar e exibir as pontuações dos jogadores.
 */
public class RankingSystem {
    private static final String RANKING_FILE = "ranking.dat";
    private List<Player> players;
    
    /**
     * Construtor da classe RankingSystem.
     * Carrega o ranking existente ou cria uma nova lista vazia.
     */
    public RankingSystem() {
        players = new ArrayList<>();
        loadRanking();
    }
    
    /**
     * Adiciona um novo jogador ao ranking.
     * 
     * @param name Nome do jogador
     * @param score Pontuação do jogador
     */
    public void addScore(String name, int score) {
        Player player = new Player(name, score);
        players.add(player);
        Collections.sort(players); // Ordena a lista de jogadores
        saveRanking(); // Salva o ranking atualizado
    }
    
    /**
     * Carrega o ranking do arquivo.
     */
    @SuppressWarnings("unchecked")
    private void loadRanking() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RANKING_FILE))) {
            players = (List<Player>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Arquivo não existe ainda, não é um erro
            System.out.println("Arquivo de ranking não encontrado. Um novo será criado.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Salva o ranking no arquivo.
     */
    private void saveRanking() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RANKING_FILE))) {
            oos.writeObject(players);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna a lista de jogadores ordenada por pontuação.
     * 
     * @return Lista de jogadores
     */
    public List<Player> getPlayers() {
        return players;
    }
    
    /**
     * Exibe o ranking em uma janela.
     */
    public void showRanking() {
        JFrame frame = new JFrame("Ranking");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Ranking de Pontuações", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Cria o modelo para a tabela de ranking
        String[] columnNames = {"Posição", "Nome", "Pontuação", "Data/Hora"};
        Object[][] data = new Object[players.size()][4];
        
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            data[i][0] = i + 1; // Posição
            data[i][1] = player.getName(); // Nome
            data[i][2] = player.getScore(); // Pontuação
            data[i][3] = player.getDateTime().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")); // Data/Hora
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setEnabled(false); // Desabilita edição
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> frame.dispose());
        panel.add(closeButton, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}