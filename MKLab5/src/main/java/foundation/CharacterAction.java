package foundation;

import additions.Items;
import characters.Baraka;
import characters.Human;
import characters.LiuKang;
import characters.Player;
import characters.ShaoKahn;
import characters.SonyaBlade;
import characters.SubZero;
import fabrics.EnemyFabric;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 * Класс для управления действиями персонажей в игре.
 * Обрабатывает выбор врагов, поведение, начисление очков и предметов, а также обновление характеристик.
 */
public class CharacterAction {

    private static final int[] EXPERIENCE_FOR_NEXT_LEVEL = {40, 90, 180, 260, 410, 1000};
    private static final int[][] FIGHT_TYPES = {{1, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1, 1}, {2}, {3}};
    private final Player[] enemies = new Player[6];
    private final EnemyFabric enemyFabric = new EnemyFabric();
    private Player currentEnemy;

    /**
     * Инициализирует массив врагов, создавая их с помощью фабрики.
     */
    public void setEnemies() {
        enemies[0] = enemyFabric.create(0, 0); // Барака
        enemies[1] = enemyFabric.create(1, 0); // Саб-Зиро
        enemies[2] = enemyFabric.create(2, 0); // Лю Кан
        enemies[3] = enemyFabric.create(3, 0); // Соня Блейд
        enemies[4] = enemyFabric.create(4, 0); // Шао Кан
    }

    /**
     * Возвращает массив всех врагов.
     *
     * @return массив врагов
     */
    public Player[] getEnemies() {
        return enemies;
    }

    /**
     * Выбирает случайного врага (кроме босса) и обновляет интерфейс.
     *
     * @param imageLabel   метка для изображения врага
     * @param infoLabel    метка для информации о враге
     * @param damageLabel  метка для урона врага
     * @param healthLabel  метка для здоровья врага
     * @return выбранный враг
     */
    public Player chooseEnemy(JLabel imageLabel, JLabel infoLabel, JLabel damageLabel, JLabel healthLabel) {
        int index = (int) (Math.random() * 4);
        ImageIcon icon = null;
        switch (index) {
            case 0:
                currentEnemy = enemies[0];
                icon = createScaledIcon("/Pictures/Baraka.png");
                infoLabel.setText("Baraka (танк)");
                break;
            case 1:
                currentEnemy = enemies[1];
                icon = createScaledIcon("/Pictures/Sub-Zero.png");
                infoLabel.setText("Sub-Zero (маг)");
                break;
            case 2:
                currentEnemy = enemies[2];
                icon = createScaledIcon("/Pictures/Liu Kang.png");
                infoLabel.setText("Liu Kang (боец)");
                break;
            case 3:
                currentEnemy = enemies[3];
                icon = createScaledIcon("/Pictures/Sonya Blade.png");
                infoLabel.setText("Sonya Blade (солдат)");
                break;
        }
        imageLabel.setIcon(icon);
        damageLabel.setText(String.valueOf(currentEnemy.getDamage()));
        healthLabel.setText(currentEnemy.getHealth() + "/" + currentEnemy.getMaxHealth());
        return currentEnemy;
    }

    /**
     * Создаёт масштабированную иконку из указанного пути.
     *
     * @param path путь к изображению
     * @return масштабированная иконка
     */
    private ImageIcon createScaledIcon(String path) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 275, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Выбирает босса (Шао Кана) и обновляет интерфейс.
     *
     * @param imageLabel   метка для изображения босса
     * @param infoLabel    метка для информации о боссе
     * @param damageLabel  метка для урона босса
     * @param healthLabel  метка для здоровья босса
     * @param locationNumber номер текущей локации
     * @return босс (Шао Кан)
     */
    public Player chooseBoss(JLabel imageLabel, JLabel infoLabel, JLabel damageLabel,
                             JLabel healthLabel, int locationNumber) {
        currentEnemy = enemies[4];
        ImageIcon icon = createScaledIcon("/Pictures/Shao Kahn.png");
        imageLabel.setIcon(icon);
        damageLabel.setText(String.valueOf(currentEnemy.getDamage()));
        healthLabel.setText(currentEnemy.getHealth() + "/" + currentEnemy.getMaxHealth());
        return currentEnemy;
    }

    /**
     * Определяет поведение врага на основе вероятностей.
     *
     * @param blockChance        шанс блока
     * @param attackChance       шанс атаки
     * @param counterChance      шанс контратаки
     * @param specialChance      шанс специальной атаки
     * @param weakenChance       шанс ослабления
     * @param regenerationChance шанс регенерации
     * @param randomValue        случайное значение от 0 до 1
     * @return массив действий врага
     */
    public int[] enemyBehavior(int blockChance, int attackChance, int counterChance, int specialChance,
                               int weakenChance, int regenerationChance, double randomValue) {
        int[] actions = null;
        double total = blockChance + attackChance + counterChance + specialChance + weakenChance + regenerationChance;
        if (randomValue < blockChance * 0.01) {
            actions = FIGHT_TYPES[0]; // {1, 0}
        } else if (randomValue < (blockChance + attackChance) * 0.01) {
            actions = FIGHT_TYPES[1]; // {1, 1, 0}
        } else if (randomValue < (blockChance + attackChance + counterChance) * 0.01) {
            actions = FIGHT_TYPES[2]; // {0, 1, 0}
        } else if (randomValue < (blockChance + attackChance + counterChance + specialChance) * 0.01) {
            actions = FIGHT_TYPES[3]; // {1, 1, 1, 1}
        } else if (randomValue < (blockChance + attackChance + counterChance + specialChance + weakenChance) * 0.01) {
            actions = FIGHT_TYPES[4]; // {2} - Ослабление
        } else if (randomValue < total * 0.01) {
            actions = FIGHT_TYPES[5]; // {3} - Регенерация
        }
        return actions;
    }

    /**
     * Выбирает поведение врага в зависимости от его типа.
     *
     * @param enemy   текущий враг
     * @param action  экземпляр CharacterAction для вызова enemyBehavior
     * @return массив действий врага
     */
    public int[] chooseBehavior(Player enemy, CharacterAction action) {
        int[] actions = FIGHT_TYPES[0]; // По умолчанию {1, 0}
        double randomValue = Math.random();
        if (enemy instanceof Baraka) {
            actions = action.enemyBehavior(15, 15, 60, 10, 0, 0, randomValue);
        } else if (enemy instanceof SubZero) {
            actions = action.enemyBehavior(20, 20, 0, 30, 30, 0, randomValue);
        } else if (enemy instanceof LiuKang) {
            actions = action.enemyBehavior(13, 13, 10, 64, 0, 0, randomValue);
        } else if (enemy instanceof SonyaBlade) {
            actions = action.enemyBehavior(25, 25, 50, 0, 0, 0, randomValue);
        } else if (enemy instanceof ShaoKahn) {
            actions = action.enemyBehavior(20, 30, 35, 0, 0, 15, randomValue);
        }
        return actions;
    }

    /**
     * Обновляет шкалу здоровья персонажа.
     *
     * @param player   персонаж
     * @param progress шкала здоровья
     */
    public void updateHealthBar(Player player, JProgressBar progress) {
        progress.setValue(Math.max(0, player.getHealth()));
    }

    /**
     * Начисляет очки и опыт игроку после победы над обычным врагом.
     *
     * @param human   игрок
     * @param enemies массив врагов
     * @param frames  интерфейс игры
     */
    public void addPoints(Human human, Player[] enemies, JFrames frames) {
        int oldLevel = human.getLevel();
        switch (human.getLevel()) {
            case 0:
                human.setExperience(20);
                human.setPoints(25 + human.getHealth() / 4);
                break;
            case 1:
                human.setExperience(25);
                human.setPoints(30 + human.getHealth() / 4);
                break;
            case 2:
                human.setExperience(30);
                human.setPoints(35 + human.getHealth() / 4);
                break;
            case 3:
                human.setExperience(40);
                human.setPoints(45 + human.getHealth() / 4);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(55 + human.getHealth() / 4);
                break;
        }
        checkLevelUp(human, enemies, oldLevel);
    }

    /**
     * Начисляет очки и опыт игроку после победы над боссом.
     *
     * @param human   игрок
     * @param enemies массив врагов
     * @param frames  интерфейс игры
     */
    public void addPointsBoss(Human human, Player[] enemies, JFrames frames) {
        int oldLevel = human.getLevel();
        switch (human.getLevel()) {
            case 0:
                human.setExperience(20);
                human.setPoints(35 + human.getHealth() / 2);
                break;
            case 1:
                human.setExperience(25);
                human.setPoints(40 + human.getHealth() / 2);
                break;
            case 2:
                human.setExperience(30);
                human.setPoints(45 + human.getHealth() / 2);
                break;
            case 3:
                human.setExperience(40);
                human.setPoints(55 + human.getHealth() / 2);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(65 + human.getHealth() / 2);
                break;
        }
        checkLevelUp(human, enemies, oldLevel);
    }

    /**
     * Проверяет, достиг ли игрок нового уровня, и обновляет характеристики.
     *
     * @param human     игрок
     * @param enemies   массив врагов
     * @param oldLevel  предыдущий уровень игрока
     */
    private void checkLevelUp(Human human, Player[] enemies, int oldLevel) {
        for (int i = 0; i < 5; i++) {
            if (EXPERIENCE_FOR_NEXT_LEVEL[i] == human.getExperience()) {
                human.plusLevel();
                human.setNextExperience(EXPERIENCE_FOR_NEXT_LEVEL[i + 1]);
                updateHumanHealth(human);
                if (human.getLevel() > oldLevel) {
                    showLevelUpDialog(human);
                }
                for (Player enemy : enemies) {
                    if (enemy != null) {
                        updateEnemyHealth(enemy, human);
                    }
                }
            }
        }
    }

    /**
     * Отображает диалоговое окно для выбора улучшения характеристик при повышении уровня.
     *
     * @param human игрок
     */
    public void showLevelUpDialog(Human human) {
        JDialog dialog = new JDialog((Frame) null, "Уровень " + human.getLevel(), true);
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new FlowLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        JLabel label = new JLabel("Выбери улучшение:");
        label.setForeground(Color.WHITE);
        dialog.add(label);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);

        ButtonGroup group = new ButtonGroup();
        JRadioButton healthButton = new JRadioButton("Здоровье (+20)");
        healthButton.setForeground(Color.WHITE);
        healthButton.setBackground(Color.BLACK);
        group.add(healthButton);
        buttonPanel.add(healthButton);

        JRadioButton damageButton = new JRadioButton("Урон (+4)");
        damageButton.setForeground(Color.WHITE);
        damageButton.setBackground(Color.BLACK);
        group.add(damageButton);
        buttonPanel.add(damageButton);

        dialog.add(buttonPanel);

        JButton okButton = new JButton("ОК");
        okButton.addActionListener(e -> {
            if (healthButton.isSelected()) {
                human.setMaxHealth(human.getMaxHealth() + 20);
                human.setHealth(human.getMaxHealth());
            } else if (damageButton.isSelected()) {
                human.setDamage(human.getDamage() + 4);
            }
            dialog.dispose();
        });
        dialog.add(okButton);

        dialog.setVisible(true);
    }

    /**
     * Добавляет случайный предмет в инвентарь игрока на основе вероятностей.
     *
     * @param item1Chance шанс получения первого предмета
     * @param item2Chance шанс получения второго предмета
     * @param item3Chance шанс получения третьего предмета
     * @param items       массив предметов
     */
    public void addItems(int item1Chance, int item2Chance, int item3Chance, Items[] items) {
        double randomValue = Math.random();
        if (randomValue < item1Chance * 0.01) {
            items[0].plusCount(1);
        } else if (randomValue < (item1Chance + item2Chance) * 0.01) {
            items[1].plusCount(1);
        } else if (randomValue < (item1Chance + item2Chance + item3Chance) * 0.01) {
            items[2].plusCount(1);
        }
    }

    /**
     * Обновляет здоровье и урон игрока в зависимости от его уровня.
     *
     * @param human игрок
     */
    public void updateHumanHealth(Human human) {
        int healthIncrease = 0;
        int damageIncrease = 0;
        switch (human.getLevel()) {
            case 1:
                healthIncrease = 10;
                damageIncrease = 2;
            case 2:
                healthIncrease = 10;
                damageIncrease = 2;
            case 3:
                healthIncrease = 10;
                damageIncrease = 2;
            case 4:
                healthIncrease = 10;
                damageIncrease = 2;
                break;
        }
        human.setMaxHealth(healthIncrease);
        human.setDamage(damageIncrease);
    }

    /**
     * Обновляет характеристики врага в зависимости от уровня игрока.
     *
     * @param enemy враг
     * @param human игрок
     */
    public void updateEnemyHealth(Player enemy, Human human) {
        int healthMultiplier = 0;
        int damageMultiplier = 0;
        switch (human.getLevel()) {
            case 1:
                healthMultiplier = 32;
                damageMultiplier = 25;
                break;
            case 2:
                healthMultiplier = 30;
                damageMultiplier = 20;
                break;
            case 3:
                healthMultiplier = 23;
                damageMultiplier = 24;
                break;
            case 4:
                healthMultiplier = 25;
                damageMultiplier = 26;
                break;
        }
        enemy.setMaxHealth((int) (enemy.getMaxHealth() * healthMultiplier / 100));
        enemy.setDamage((int) (enemy.getDamage() * damageMultiplier / 100));
        enemy.setLevel(human.getLevel());
    }

    /**
     * Применяет выбранный предмет для игрока.
     *
     * @param human      игрок
     * @param items      массив предметов
     * @param buttonName имя радиокнопки, связанной с предметом
     * @param errorDialog диалог для ошибки (недостаточно предметов)
     * @param parentDialog родительский диалог для закрытия
     */
    public void useItem(Player human, Items[] items, String buttonName, JDialog errorDialog, JDialog parentDialog) {
        switch (buttonName) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].plusCount(-1);
                } else {
                    errorDialog.setVisible(true);
                    errorDialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].plusCount(-1);
                } else {
                    errorDialog.setVisible(true);
                    errorDialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                errorDialog.setVisible(true);
                errorDialog.setBounds(300, 200, 400, 300);
                break;
        }
        if (!errorDialog.isVisible()) {
            parentDialog.dispose();
        }
    }

    /**
     * Возвращает количество врагов для текущей локации.
     *
     * @param playerLevel   уровень игрока
     * @param locationNumber номер локации
     * @return количество врагов
     */
    public int getEnemiesCountForLocation(int playerLevel, int locationNumber) {
        int baseCount = 3 + locationNumber / 2;
        return Math.min(baseCount, 5);
    }
}