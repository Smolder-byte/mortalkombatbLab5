package characters;

/**
 * Класс, представляющий игрока-человека в игре.
 * Наследуется от класса Player и добавляет управление очками, опытом и победами.
 */
public class Human extends Player {

    private int points;
    private int experience;
    private int wins;
    private int nextExperience;

    /**
     * Конструктор для создания экземпляра игрока-человека.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public Human(int level, int health, int damage, int attack) {
        super(level, health, damage, attack);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
        this.wins = 0;
    }

    /**
     * Возвращает текущее количество очков игрока.
     *
     * @return очки игрока
     */
    public int getPoints() {
        return points;
    }

    /**
     * Возвращает текущий опыт игрока.
     *
     * @return опыт игрока
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Возвращает количество опыта, необходимое для следующего уровня.
     *
     * @return опыт для следующего уровня
     */
    public int getNextExperience() {
        return nextExperience;
    }

    /**
     * Возвращает количество побед игрока.
     *
     * @return количество побед
     */
    public int getWin() {
        return wins;
    }

    /**
     * Добавляет указанное количество очков к текущим.
     *
     * @param pointsChange количество добавляемых очков
     */
    public void setPoints(int pointsChange) {
        this.points += pointsChange;
    }

    /**
     * Добавляет указанное количество опыта к текущему.
     *
     * @param experienceChange количество добавляемого опыта
     */
    public void setExperience(int experienceChange) {
        this.experience += experienceChange;
    }

    /**
     * Устанавливает количество опыта, необходимое для следующего уровня.
     *
     * @param nextExperience опыт для следующего уровня
     */
    public void setNextExperience(int nextExperience) {
        this.nextExperience = nextExperience;
    }

    /**
     * Увеличивает количество побед на одну.
     */
    public void plusWin() {
        this.wins++;
    }

    /**
     * Устанавливает общее количество побед.
     *
     * @param wins новое количество побед
     */
    public void setWin(int wins) {
        this.wins = wins;
    }

    /**
     * Возвращает имя игрока.
     *
     * @return имя "You"
     */
    @Override
    public String getName() {
        return "You";
    }
}