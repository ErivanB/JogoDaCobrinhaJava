# Jogo da Cobrinha (Snake Game)

Um jogo clássico da cobrinha implementado em Java com sistema de ranking para acompanhar o histórico de pontuações dos jogadores.

## Características

- Controle da cobra usando as teclas de seta
- Sistema de pontuação
- Histórico de ranking
- Interface gráfica simples e intuitiva

## Como Jogar

1. Execute o arquivo `SnakeGame.java`
2. Use as teclas de seta para controlar a direção da cobra
3. Tente comer o máximo de comida possível sem colidir com as paredes ou com o próprio corpo da cobra
4. Sua pontuação será salva automaticamente no ranking

## Estrutura do Projeto

- `SnakeGame.java`: Classe principal que inicia o jogo
- `GamePanel.java`: Painel do jogo onde a cobra se move
- `Snake.java`: Classe que representa a cobra
- `Food.java`: Classe que representa a comida
- `Direction.java`: Enum para as direções da cobra
- `RankingSystem.java`: Sistema de ranking para salvar e exibir pontuações
- `Player.java`: Classe que representa um jogador no ranking