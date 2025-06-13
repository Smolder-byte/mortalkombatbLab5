package additions;

import additions.Items;
import additions.Items;
import characters.Human;
import characters.Player;
import static java.lang.Math.max;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 * Класс для управления текстовым содержимым интерфейса игры.
 * Обновляет отображаемые данные о персонажах, предметах и состоянии боя.
 */
public class ChangeTexts {

    /**
     * Обновляет текст интерфейса при начале нового раунда боя.
     * Устанавливает значения очков, опыта, уровней, здоровья, урона и статуса хода.
     *
     * @param human          игрок
     * @param enemy          противник
     * @param playerHealthBar шкала здоровья игрока
     * @param enemyHealthBar  шкала здоровья противника
     * @param pointsLabel    метка для очков игрока
     * @param experienceLabel метка для опыта игрока
     * @param playerLevelLabel метка для уровня игрока
     * @param enemyLevelLabel метка для уровня противника
     * @param playerHealthLabel метка для здоровья игрока
     * @param enemyHealthLabel метка для здоровья противника
     * @param playerDamageLabel метка для урона игрока
     * @param turnLabel       метка для текущего хода
     * @param messageLabel    метка для сообщений
     * @param turnNumber      номер текущего хода
     * @param items           массив предметов игрока
     * @param itemButton1     радиокнопка для первого предмета
     * @param itemButton2     радиокнопка для второго предмета
     * @param itemButton3     радиокнопка для третьего предмета
     * @param weakenStatusLabel метка для статуса ослабления
     */
    public void updateNewRoundText(
            Player human, Player enemy, JProgressBar playerHealthBar, JProgressBar enemyHealthBar,
            JLabel pointsLabel, JLabel experienceLabel, JLabel playerLevelLabel, JLabel enemyLevelLabel,
            JLabel playerHealthLabel, JLabel enemyHealthLabel, JLabel playerDamageLabel,
            JLabel turnLabel, JLabel messageLabel, int turnNumber, Items[] items,
            JRadioButton itemButton1, JRadioButton itemButton2, JRadioButton itemButton3,
            JLabel weakenStatusLabel) {

        pointsLabel.setText(String.valueOf(((Human) human).getPoints()));
        experienceLabel.setText(((Human) human).getExperience() + "/" + ((Human) human).getNextExperience());
        playerLevelLabel.setText(human.getLevel() + " level");
        enemyLevelLabel.setText(enemy.getLevel() + " level");
        playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
        playerDamageLabel.setText(String.valueOf(human.getDamage()));
        enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());

        updateTurnText(turnLabel, enemy.getName(), turnNumber);
        updateInventoryText(items, itemButton1, itemButton2, itemButton3);
        messageLabel.setText("");
        updateWeakenStatus(human, enemy, weakenStatusLabel);
    }

    /**
     * Обновляет текст интерфейса во время раунда боя.
     * Устанавливает значения здоровья игроков и текущего хода.
     *
     * @param human           игрок
     * @param enemy           противник
     * @param enemyHealthLabel метка для здоровья противника
     * @param playerHealthLabel метка для здоровья игрока
     * @param turnNumber      номер текущего хода
     * @param turnLabel       метка для текущего хода
     * @param weakenStatusLabel метка для статуса ослабления
     */
    public void updateRoundText(
            Player human, Player enemy, JLabel enemyHealthLabel, JLabel playerHealthLabel,
            int turnNumber, JLabel turnLabel, JLabel weakenStatusLabel) {

        enemyHealthLabel.setText(
                (enemy.getHealth() >= 0 ? enemy.getHealth() : 0) + "/" + enemy.getMaxHealth());
        playerHealthLabel.setText(
                (human.getHealth() >= 0 ? human.getHealth() : 0) + "/" + human.getMaxHealth());

        updateTurnText(turnLabel, enemy.getName(), turnNumber);
        updateWeakenStatus(human, enemy, weakenStatusLabel);
    }

    /**
     * Обновляет текст метки текущего хода в зависимости от номера хода.
     *
     * @param turnLabel  метка для текущего хода
     * @param enemyName  имя противника
     * @param turnNumber номер текущего хода
     */
    private void updateTurnText(JLabel turnLabel, String enemyName, int turnNumber) {
        if (turnNumber % 2 == 1) {
            turnLabel.setText("Your turn");
        } else {
            turnLabel.setText(enemyName + "'s turn");
        }
    }

    /**
     * Обновляет текст метки, отображающей статус ослабления игроков.
     *
     * @param human           игрок
     * @param enemy           противник
     * @param weakenStatusLabel метка для статуса ослабления
     */
    private void updateWeakenStatus(Player human, Player enemy, JLabel weakenStatusLabel) {
        StringBuilder status = new StringBuilder();
        if (human.isWeakened()) {
            status.append(human.getName())
                  .append(" weakened (")
                  .append(max(1,human.getWeakenDuration()))
                  .append(" turns left)\n");
        }
        if (enemy.isWeakened()) {
            status.append(enemy.getName())
                  .append(" weakened (")
                  .append(max(1,enemy.getWeakenDuration()))
                  .append(" turns left)");
        }
        weakenStatusLabel.setText(status.toString());
    }

    /**
     * Обновляет текст окончания игры в зависимости от результата.
     *
     * @param human игрок
     * @param resultLabel метка для результата игры
     */
    public void updateEndGameText(Human human, JLabel resultLabel) {
        if (human.getWin() == 12) {
            resultLabel.setText("Победа на вашей стороне");
        } else {
            resultLabel.setText("Победа не на вашей стороне");
        }
    }

    /**
     * Обновляет текст радиокнопок инвентаря, отображая название и количество предметов.
     *
     * @param items       массив предметов
     * @param itemButton1 радиокнопка для первого предмета
     * @param itemButton2 радиокнопка для второго предмета
     * @param itemButton3 радиокнопка для третьего предмета
     */
    public void updateInventoryText(Items[] items, JRadioButton itemButton1,
                                    JRadioButton itemButton2, JRadioButton itemButton3) {
        itemButton1.setText(items[0].getName() + ", " + items[0].getCount() + " шт");
        itemButton2.setText(items[1].getName() + ", " + items[1].getCount() + " шт");
        itemButton3.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
    }
}