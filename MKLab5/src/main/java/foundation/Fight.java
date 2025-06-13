package foundation;

import additions.ChangeTexts;
import additions.Items;
import additions.Result;
import characters.Human;
import characters.Player;
import characters.ShaoKahn;
import fabrics.EnemyFabric;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import static java.lang.Math.max;

/**
 * Класс для управления боевой механикой игры.
 * Обрабатывает ходы, атаки, завершение раундов и обновление интерфейса.
 */
public class Fight {

    private static final int[] EXPERIENCES = {40, 90, 180, 260, 410};
    private final ChangeTexts textUpdater = new ChangeTexts();
    private final EnemyFabric enemyFabric = new EnemyFabric();
    private int[] attackSequence = {0};
    int currentTurn = 1;
    private int attackIndex = -1;
    int stun = 0;
    double v = 0.0;
    private JFrames gameInterface;

    /**
     * Выполняет ход в бою, обновляя здоровье и статусы игроков.
     *
     * @param p1 игрок который ходит первый
     * @param p2 игрок который ходит вторым
     * @param l  информация о ходе боя
     * @param l2 важные сообщения
     */
    public void performMove(Player p1, Player p2, JLabel l, JLabel l2) {
        // Update weaken duration for both players
        if (p1.isWeakened() && p1.getWeakenDuration() > 0) {
            p1.setWeakenDuration(p1.getWeakenDuration() - 1);
            if (p1.getWeakenDuration() == 0) {
                p1.setWeakened(false);
                l.setText(p1.getName() + "'s weaken debuff has ended");
            }
        }
        if (p2.isWeakened() && p2.getWeakenDuration() > 0) {
            p2.setWeakenDuration(p2.getWeakenDuration() - 1);
            if (p2.getWeakenDuration() == 0) {
                p2.setWeakened(false);
                l2.setText(p2.getName() + "'s weaken debuff has ended");
            }
        }
        
        if (stun == 1){
            p1.setAttack(-1);
        }
        
        // Adjust damage based on weaken debuff
        int p1Damage = p1.isWeakened() ? (int)(p1.getDamage() * 0.5) : p1.getDamage();
        int p2Damage = p2.isWeakened() ? (int)(p2.getDamage() * 0.5) : p2.getDamage();

        switch (Integer.toString(p1.getAttack()) + Integer.toString(p2.getAttack())) {
            case "10": // Атака vs Блок
                v = Math.random();
                if (p1 instanceof ShaoKahn && v < 0.15) {
                    p2.setHealth(-(int) (p1Damage * 0.5));
                    l2.setText("Your block is broken");
                } else {
                    p1.setHealth(-(int) p2Damage);
                    l2.setText(p2.getName() + " counterattacked");
                }
                break;
            case "11": // Атака vs Атака
                p2.setHealth(-(int)(p1Damage * (p2.isWeakened() ? 1.25 : 1.0)));
                l2.setText(p1.getName() + " attacked");
                break;
            case "00": // Блок vs Блок
                v = Math.random();
                if (v <= 0.5) {
                    stun = 1;
                    l.setText(p2.getName() + " was stunned");
                }
                l2.setText("Both defended themselves");
                break;
            case "01": // Блок vs Атака
                l2.setText(p1.getName() + " didn't attack");
                break;
            case "-10": // Оглушение vs Блок
                stun = 0;
                l2.setText(p2.getName() + " didn't attack");
                break;
            case "-11": // Оглушение vs Атака
                p1.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 : 1.0)));
                stun = 0;
                l2.setText(p2.getName() + " attacked");
                break;
            case "-12": // Оглушение vs Ослабление
                p1.setWeakened(true);
                p1.setWeakenDuration(max(1, p2.getLevel()));
                stun = 0;
                l2.setText(p1.getName() + " is weakened");
                break;
            case "-13": // Оглушение vs Регенерация
                if (p2 instanceof ShaoKahn) {
                    int healthToRegen = (p2.getMaxHealth() - p2.getHealth()) / 2;
                    p2.setHealth(healthToRegen);
                    stun = 0;
                    l.setText(p2.getName() + " regenerated " + healthToRegen + " health");
                }
                break;
            case "20": // Ослабление vs Блок
                v = Math.random();
                if (v < 0.75) {
                    p2.setWeakened(true);
                    p2.setWeakenDuration(max(1, p1.getLevel()));
                    l2.setText(p2.getName() + " is weakened");
                } else {
                    l2.setText(p2.getName() + " resisted weaken");
                }
                break;
            case "21": // Ослабление vs Атака
                p1.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 * 1.15 : 1.15)));
                l.setText(p1.getName() + "'s weaken failed, damage increased by 15%");
                break;
            case "02": // Блок vs Ослабление
                v = Math.random();
                if (v < 0.75) {
                    p1.setWeakened(true);
                    p1.setWeakenDuration(p2.getLevel());
                    l2.setText(p1.getName() + " is weakened");
                } else {
                    l2.setText(p1.getName() + " resisted weaken");
                }
                break;
            case "12": // Атака vs Ослабление
                p2.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 * 1.15 : 1.15)));
                l.setText(p2.getName() + "'s weaken failed, damage increased by 15%");
                break;
            case "22": // Ослабление vs Ослабление
                l2.setText("Both attempted to weaken");
                break;
            case "23": // Ослабление vs Регенерация
                if (p2 instanceof ShaoKahn) {
                    int healthToRegen = (p2.getMaxHealth() - p2.getHealth()) / 2;
                    p2.setHealth(healthToRegen);
                    v = Math.random();
                    if (v < 0.75) {
                        p2.setWeakened(true);
                        p2.setWeakenDuration(max(1, p1.getLevel()));
                        l2.setText(p2.getName() + " is weakened");
                    } else {
                        l2.setText(p2.getName() + " resisted weaken");
                    }
                    l.setText(p2.getName() + " regenerated " + healthToRegen + " health");
                }
                break;
            case "30": // Регенерация vs Блок
                if (p1 instanceof ShaoKahn) {
                    int healthToRegen = (p1.getMaxHealth() - p1.getHealth()) / 2;
                    p1.setHealth(healthToRegen);
                    l.setText(p1.getName() + " regenerated " + healthToRegen + " health");
                }
                break;
            case "31": // Регенерация vs Атака
                if (p1 instanceof ShaoKahn) {
                    p1.setHealth(-(int)(p2Damage * 2.0 * (p1.isWeakened() ? 1.25 : 1)));
                    l2.setText(p1.getName() + "'s regeneration was interrupted, took double damage!");
                }
                break;
            case "03": // Блок vs Регенерация
                if (p2 instanceof ShaoKahn) {
                    int healthToRegen = (p2.getMaxHealth() - p2.getHealth()) / 2;
                    p2.setHealth(healthToRegen);
                    l.setText(p2.getName() + " regenerated " + healthToRegen + " health");
                }
                break;
            case "13": // Атака vs Регенерация
                if (p2 instanceof ShaoKahn) {
                    p2.setHealth(-(int)(p1Damage * 2.0 * (p2.isWeakened() ? 1.25 : 1)));
                    l2.setText(p2.getName() + "'s regeneration was interrupted, took double damage!");
                }
                break;
            case "32": // Регенерация vs Ослабление
                if (p1 instanceof ShaoKahn) {
                    int healthToRegen = (p1.getMaxHealth() - p1.getHealth()) / 2;
                    p1.setHealth(healthToRegen);
                    v = Math.random();
                    if (v < 0.75) {
                        p1.setWeakened(true);
                        p1.setWeakenDuration(max(1, p2.getLevel()));
                        l2.setText(p1.getName() + " is weakened");
                    } else {
                        l2.setText(p1.getName() + " resisted weaken");
                    }
                    l.setText(p1.getName() + " regenerated " + healthToRegen + " health");
                }
                break;
        }
    }

    /**
     * Обрабатывает атаку игрока, обновляет состояние боя и интерфейс.
     *
     * @param human         игрок
     * @param enemy         враг
     * @param attackType    тип атаки игрока
     * @param enemyHealthLabel метка для здоровья врага
     * @param playerHealthLabel метка для здоровья игрока
     * @param endRoundDialog диалог окончания раунда
     * @param resultLabel   метка для результата раунда
     * @param action        экземпляр CharacterAction
     * @param playerHealthBar шкала здоровья игрока
     * @param enemyHealthBar шкала здоровья врага
     * @param victoryDialog диалог победы
     * @param noTopDialog   диалог без топ-10
     * @param mainFrame     главное окно игры
     * @param results       список результатов
     * @param victoryLabel  метка для текста победы
     * @param noTopLabel    метка для текста без топ-10
     * @param turnLabel     метка для текущего хода
     * @param stunLabel     метка для состояний
     * @param messageLabel  метка для сообщений
     * @param items         массив предметов
     * @param itemButton    радиокнопка для предмета
     * @param game          экземпляр игры
     * @param weakenStatusLabel метка для статуса ослабления
     */
    public void performAttack(Human human, Player enemy, int attackType, JLabel enemyHealthLabel,
                              JLabel playerHealthLabel, JDialog endRoundDialog, JLabel resultLabel,
                              CharacterAction action, JProgressBar playerHealthBar, JProgressBar enemyHealthBar,
                              JDialog victoryDialog, JDialog noTopDialog, JFrame mainFrame,
                              ArrayList<Result> results, JLabel victoryLabel, JLabel noTopLabel,
                              JLabel turnLabel, JLabel stunLabel, JLabel messageLabel, Items[] items, JRadioButton itemButton,
                              Game game, JLabel weakenStatusLabel) {
        stunLabel.setText("");
        
        human.setAttack(attackType);

        if (attackIndex < attackSequence.length - 1) {
            attackIndex++;
        } else {
            attackSequence = action.chooseBehavior(enemy, action);
            attackIndex = 0;
        }
        enemy.setAttack(attackSequence[attackIndex]);

        if (currentTurn % 2 == 1) {
            performMove(human, enemy, stunLabel, messageLabel);
        } else {
            performMove(enemy, human, stunLabel, messageLabel);
        }

        currentTurn++;
        textUpdater.updateRoundText(human, enemy, enemyHealthLabel, playerHealthLabel, currentTurn,
                                   turnLabel, weakenStatusLabel);
        action.updateHealthBar(human, playerHealthBar);
        action.updateHealthBar(enemy, enemyHealthBar);

        if (human.getHealth() <= 0 && items[2].getCount() > 0) {
            human.setNewHealth((int) (human.getMaxHealth() * 0.05));
            items[2].setCount(-1);
            action.updateHealthBar(human, playerHealthBar);
            playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
            itemButton.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
            messageLabel.setText("Вы воскресли");
        }

        if (human.getHealth() <= 0 || enemy.getHealth() <= 0) {
            if (human.getWin() == 11) {
                endFinalRound(human, action, results, victoryDialog, noTopDialog, mainFrame,
                              victoryLabel, noTopLabel);
            } else {
                endRound(human, enemy, endRoundDialog, resultLabel, action, items, game,
                         victoryDialog, noTopDialog, mainFrame, victoryLabel, noTopLabel);
            }
        }
    }

    /**
     * Завершает раунд, обновляя состояние игры и интерфейс.
     *
     * @param human         игрок
     * @param enemy         враг
     * @param endRoundDialog диалог окончания раунда
     * @param resultLabel   метка для результата раунда
     * @param action        экземпляр CharacterAction
     * @param items         массив предметов
     * @param game          экземпляр игры
     * @param victoryDialog диалог победы
     * @param noTopDialog   диалог без топ-10
     * @param mainFrame     главное окно игры
     * @param victoryLabel  метка для текста победы
     * @param noTopLabel    метка для текста без топ-10
     */
    public void endRound(Human human, Player enemy, JDialog endRoundDialog, JLabel resultLabel,
                         CharacterAction action, Items[] items, Game game, JDialog victoryDialog,
                         JDialog noTopDialog, JFrame mainFrame, JLabel victoryLabel, JLabel noTopLabel) {
        endRoundDialog.setVisible(true);
        endRoundDialog.setBounds(300, 150, 700, 600);
        human.plusWin();

        if (human.getHealth() > 0) {
            resultLabel.setText("You win");
            if (enemy instanceof ShaoKahn) {
                action.addItems(38, 23, 8, items);
                action.addPointsBoss(human, action.getEnemies(), gameInterface);
                game.nextLocation();
                if (game.isLastLocation()) {
                    endFinalRound(human, action, game.getResults(), victoryDialog, noTopDialog,
                                  mainFrame, victoryLabel, noTopLabel);
                    return;
                }
                human.setWin(0);
            } else {
                action.addItems(25, 15, 5, items);
                action.addPoints(human, action.getEnemies(), gameInterface);
            }
            human.setNewHealth(human.getMaxHealth());
        } else {
            resultLabel.setText(enemy.getName() + " win");
            human.setNewHealth(human.getMaxHealth());
            if (enemy instanceof ShaoKahn) {
                game.nextLocation();
                if (game.isLastLocation()) {
                    endFinalRound(human, action, game.getResults(), victoryDialog, noTopDialog,
                                  mainFrame, victoryLabel, noTopLabel);
                    return;
                }
                human.setWin(0);
            }
        }

        currentTurn = 1;
        attackIndex = -1;
        attackSequence = resetAttack();
        human.setWeakened(false);
        human.setWeakenDuration(0);
        enemy.setWeakened(false);
        enemy.setWeakenDuration(0);
    }

    /**
     * Завершает финальный раунд, отображая результат игры.
     *
     * @param human         игрок
     * @param action        экземпляр CharacterAction
     * @param results       список результатов
     * @param victoryDialog диалог победы
     * @param noTopDialog   диалог без топ-10
     * @param mainFrame     главное окно игры
     * @param victoryLabel  метка для текста победы
     * @param noTopLabel    метка для текста без топ-10
     */
    public void endFinalRound(Human human, CharacterAction action, ArrayList<Result> results,
                              JDialog victoryDialog, JDialog noTopDialog, JFrame mainFrame,
                              JLabel victoryLabel, JLabel noTopLabel) {
        String resultText = "Победа не на вашей стороне";
        if (human.getHealth() > 0) {
            human.setWin(12);
            action.addPoints(human, action.getEnemies(), gameInterface);
            resultText = "Победа на вашей стороне! Игра пройдена!";
        }

        boolean isTopPlayer = results == null || results.size() < 10;
        if (results != null) {
            int lowerScores = 0;
            for (Result result : results) {
                if (human.getPoints() < result.getPoints()) {
                    lowerScores++;
                }
            }
            if (lowerScores < 10) {
                isTopPlayer = true;
            }
        }

        if (isTopPlayer) {
            victoryDialog.setVisible(true);
            victoryDialog.setBounds(150, 150, 600, 500);
            victoryLabel.setText(resultText);
        } else {
            noTopDialog.setVisible(true);
            noTopDialog.setBounds(150, 150, 470, 360);
            noTopLabel.setText(resultText);
        }
        mainFrame.dispose();
    }

    /**
     * Сбрасывает последовательность атак врага.
     *
     * @return массив с начальным действием {0}
     */
    public int[] resetAttack() {
        return new int[]{0};
    }

    /**
     * Начинает новый раунд, выбирая врага и обновляя интерфейс.
     *
     * @param human           игрок
     * @param enemyImageLabel метка для изображения врага
     * @param playerHealthBar шкала здоровья игрока
     * @param enemyHealthBar  шкала здоровья врага
     * @param enemyInfoLabel  метка для информации о враге
     * @param damageLabel     метка для урона врага
     * @param healthLabel     метка для здоровья врага
     * @param action          экземпляр CharacterAction
     * @param game            экземпляр игры
     * @return выбранный враг
     */
    public Player startNewRound(Player human, JLabel enemyImageLabel, JProgressBar playerHealthBar,
                                JProgressBar enemyHealthBar, JLabel enemyInfoLabel, JLabel damageLabel,
                                JLabel healthLabel, CharacterAction action, Game game) {
        Human player = (Human) human;
        int enemiesInLocation = action.getEnemiesCountForLocation(player.getLevel(), game.getCurrentLocation());
        int currentEnemyNumber = player.getWin() + 1;
        boolean isBossFight = currentEnemyNumber >= enemiesInLocation;
        Player enemy;
        if (isBossFight) {
            enemy = action.chooseBoss(enemyImageLabel, enemyInfoLabel, damageLabel, healthLabel, game.getCurrentLocation());
            enemyInfoLabel.setText("Босс локации " + game.getCurrentLocation() + " (" + currentEnemyNumber + "/" + enemiesInLocation + ")");
        } else {
            enemy = action.chooseEnemy(enemyImageLabel, enemyInfoLabel, damageLabel, healthLabel);
            enemyInfoLabel.setText(enemy.getName() + " (" + currentEnemyNumber + "/" + enemiesInLocation + ")");
        }
        playerHealthBar.setMaximum(human.getMaxHealth());
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
        enemy.setNewHealth(enemy.getMaxHealth());
        action.updateHealthBar(human, playerHealthBar);
        action.updateHealthBar(enemy, enemyHealthBar);
        return enemy;
    }

    /**
     * Обновляет статус ослабления для игрока.
     *
     * @param player      игрок
     * @param messageLabel метка для сообщений
     */
    private void updateWeakenStatus(Player player, JLabel messageLabel) {
        if (player.isWeakened() && player.getWeakenDuration() > 0) {
            player.setWeakenDuration(player.getWeakenDuration() - 1);
            if (player.getWeakenDuration() == 0) {
                player.setWeakened(false);
                messageLabel.setText(player.getName() + "'s weaken debuff has ended");
            }
        }
    }
}