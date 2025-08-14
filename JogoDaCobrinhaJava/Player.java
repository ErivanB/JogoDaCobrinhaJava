import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um jogador no sistema de ranking.
 * Implementa Serializable para permitir a persistência dos dados.
 * Implementa Comparable para permitir a ordenação dos jogadores por pontuação.
 */
public class Player implements Serializable, Comparable<Player> {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int score;
    private LocalDateTime dateTime;
    
    /**
     * Construtor da classe Player.
     * 
     * @param name Nome do jogador
     * @param score Pontuação do jogador
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        this.dateTime = LocalDateTime.now();
    }
    
    /**
     * Retorna o nome do jogador.
     * 
     * @return Nome do jogador
     */
    public String getName() {
        return name;
    }
    
    /**
     * Retorna a pontuação do jogador.
     * 
     * @return Pontuação do jogador
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Retorna a data e hora em que a pontuação foi registrada.
     * 
     * @return Data e hora do registro
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    /**
     * Retorna uma representação em string do jogador.
     * 
     * @return String no formato "Nome: Pontuação - Data/Hora"
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return name + ": " + score + " - " + dateTime.format(formatter);
    }
    
    /**
     * Compara este jogador com outro jogador com base na pontuação.
     * Usado para ordenar os jogadores em ordem decrescente de pontuação.
     * 
     * @param other Outro jogador a ser comparado
     * @return Um valor negativo se este jogador tiver pontuação maior,
     *         zero se as pontuações forem iguais,
     *         um valor positivo se este jogador tiver pontuação menor
     */
    @Override
    public int compareTo(Player other) {
        // Ordem decrescente (maior pontuação primeiro)
        return Integer.compare(other.score, this.score);
    }
}