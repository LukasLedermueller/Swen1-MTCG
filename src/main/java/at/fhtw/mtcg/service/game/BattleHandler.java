package at.fhtw.mtcg.service.game;

import at.fhtw.mtcg.dal.UnitOfWork;
import at.fhtw.mtcg.dal.repository.cards.CardRepository;
import at.fhtw.mtcg.dal.repository.cards.DeckRepository;
import at.fhtw.mtcg.dal.repository.users.UserRepository;
import at.fhtw.mtcg.exception.InvalidDeckSizeException;
import at.fhtw.mtcg.exception.NoPlayerFoundException;
import at.fhtw.mtcg.exception.UserAlreadyExistsException;
import at.fhtw.mtcg.model.Card;
import at.fhtw.mtcg.model.UserStats;

import javax.lang.model.util.ElementFilter;
import java.util.*;
import java.util.concurrent.*;

public enum BattleHandler {
    INSTANCE;
    private final ConcurrentLinkedQueue<String> queue;
    private final ConcurrentHashMap<String, String> logs;

    private enum Element {
        Fire,
        Normal,
        Water
    }
    private final float[][] checkEffectiveness = {
            {1f, 2f, 0.5f},
            {0.5f, 1f, 2f},
            {2f, 0.5f, 1f}
    };

    private BattleHandler() {
        this.queue = new ConcurrentLinkedQueue<>();
        this.logs = new ConcurrentHashMap<>();
    }
    public String startBattle(UnitOfWork unitOfWork, String player, long timeout) throws Exception {
        String log = null;
        if(this.queue.size() == 0) {
            queue.add(player);
            long startTime = System.currentTimeMillis();
            int i = 0;
            while(logs.get(player) == null && System.currentTimeMillis() - startTime <= timeout) {
                i++;
            }
            log = logs.get(player);
            if (log == null) {
                queue.remove(player);
                logs.remove(player);
                throw new NoPlayerFoundException("No player found");
            } else {
                //System.out.println("game took place for " + player);
                logs.remove(player);
                queue.remove(player);
                return log;
            }
        } else {
            String player2 = queue.remove();
            //System.out.println("game took place for " + player);
            try {
                log = conductBattle(unitOfWork, player, player2);
                logs.put(player2, log);
                return log;
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    private String conductBattle(UnitOfWork unitOfWork, String player1, String player2) throws Exception {
        String log = player1 + " vs " + player2;
        if(player1.equals(player2)) {
            throw new UserAlreadyExistsException("Same player");
        }
        try {
            List<String> deckPlayer1Ids = new DeckRepository(unitOfWork).getDeckOfUser(player1);
            List<String> deckPlayer2Ids = new DeckRepository(unitOfWork).getDeckOfUser(player2);
            List<Card> deckPlayer1 = new ArrayList<>();
            List<Card> deckPlayer2 = new ArrayList<>();
            for (String id : deckPlayer1Ids) {
                deckPlayer1.add(new CardRepository(unitOfWork).getCardById(id));
                //System.out.println("ID: " + id);
            }
            for (String id : deckPlayer2Ids) {
                deckPlayer2.add(new CardRepository(unitOfWork).getCardById(id));
                //System.out.println("ID: " + id);
            }
            if(deckPlayer1.size() != 4 || deckPlayer2.size() != 4) {
                throw new InvalidDeckSizeException("Deck doesn't contain 4 cards");
            }
            Random rand = new Random();
            Card cardPlayer1;
            Card cardPlayer2;
            int winner = 0;
            int roundCounter = 1;

            while(deckPlayer1.size() != 0 && deckPlayer2.size() != 0 && roundCounter <= 100) {
                cardPlayer1 = deckPlayer1.get(rand.nextInt(deckPlayer1.size()));
                cardPlayer2 = deckPlayer2.get(rand.nextInt(deckPlayer2.size()));
                log += "\nRound " + roundCounter + ": " + cardPlayer1.getName() + "(" + cardPlayer1.getDamage()
                        + ") vs " + cardPlayer2.getName() + "(" + cardPlayer2.getDamage() + ") => ";
                winner = calculateWinner(cardPlayer1, cardPlayer2);
                if(winner == 1) {
                    log += cardPlayer1.getName() + "(" + cardPlayer1.getDamage() + ") wins";
                    deckPlayer2.remove(cardPlayer2);
                    deckPlayer1.add(cardPlayer2);
                } else if (winner == 2) {
                    log += cardPlayer2.getName() + "(" + cardPlayer2.getDamage() + ") wins";
                    deckPlayer1.remove(cardPlayer1);
                    deckPlayer2.add(cardPlayer1);
                } else {
                    log += "Draw";
                }
                roundCounter++;
            }
            if(roundCounter > 100) {
                log+= "\nGame ends in a draw!";
            } else if(deckPlayer1.size() > 0) {
                log += "\n" + player1 + " wins!";
                changeStats(unitOfWork, player1, player2);
            } else {
                log += "\n" + player2 + " wins!";
                changeStats(unitOfWork, player2, player1);
            }
            return log;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    private int calculateWinner(Card cardPlayer1, Card cardPlayer2) {
        if (cardPlayer2.getName().endsWith("Goblin") && cardPlayer1.getName().endsWith("Dragon")) {
            return 1;
        } else if (cardPlayer1.getName().endsWith("Goblin") && cardPlayer2.getName().endsWith("Dragon")) {
            return 2;
        } else if(cardPlayer1.getName().endsWith("Wizzard") && cardPlayer2.getName().endsWith("Ork")) {
            return 1;
        } else if(cardPlayer2.getName().endsWith("Wizzard") && cardPlayer1.getName().endsWith("Ork")) {
            return 2;
        } else if (cardPlayer2.getName().endsWith("Knight") && cardPlayer1.getName().equals("WaterSpell")) {
            return 1;
        } else if (cardPlayer1.getName().endsWith("Knight") && cardPlayer2.getName().equals("WaterSpell")) {
            return 2;
        } else if (cardPlayer1.getName().endsWith("Kraken") && cardPlayer2.getName().endsWith("Spell")) {
            return 1;
        } else if (cardPlayer2.getName().endsWith("Kraken") && cardPlayer1.getName().endsWith("Spell")) {
            return 2;
        } else if (cardPlayer1.getName().equals("FireElf") && cardPlayer2.getName().endsWith("Dragon")) {
            return 1;
        } else if (cardPlayer2.getName().equals("FireElf") && cardPlayer1.getName().endsWith("Dragon")) {
            return 2;
        }
        float cardDamage1 = cardPlayer1.getDamage();
        float cardDamage2 = cardPlayer2.getDamage();
        if (cardPlayer1.getName().endsWith("Spell") || cardPlayer1.getName().endsWith("Spell")) {
            Element elementCard1 = getElement(cardPlayer1.getName());
            Element elementCard2 = getElement(cardPlayer2.getName());

            float changeEffectiveness = checkEffectiveness[elementCard1.ordinal()][elementCard2.ordinal()];
            cardDamage1 *= changeEffectiveness;
            cardDamage2 /= changeEffectiveness;
        }
        if (cardDamage1 > cardDamage2) {
            return 1;
        } else if (cardDamage2 > cardDamage1) {
            return 2;
        } else {
            return 0;
        }
    }

    private Element getElement(String cardName) {
        if (cardName.contains("Fire")) {
            return Element.Fire;
        } else if (cardName.contains("Water")) {
            return Element.Water;
        } else {
            return Element.Normal;
        }
    }
    private void changeStats(UnitOfWork unitOfWork, String winner, String loser) throws Exception {
        UserStats winnerStats = new UserRepository(unitOfWork).getUserStats(winner);
        UserStats loserStats = new UserRepository(unitOfWork).getUserStats(loser);
        winnerStats.addWin();
        loserStats.addLoss();
        new UserRepository(unitOfWork).updateUserStats(winner, winnerStats);
        new UserRepository(unitOfWork).updateUserStats(loser, loserStats);
    }
}
