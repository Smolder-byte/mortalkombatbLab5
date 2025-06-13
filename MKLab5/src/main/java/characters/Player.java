package characters;

/**
 * Базовый класс для всех персонажей в игре.
 * Управляет характеристиками, такими как уровень, здоровье, урон и статус ослабления.
 */
public class Player {

    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int attack;
    private int weakenDuration;
    private boolean isWeakened;

    /**
     * Конструктор для создания персонажа.
     *
     * @param level  начальный уровень
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public Player(int level, int health, int damage, int attack) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.attack = attack;
        this.maxHealth = health;
        this.weakenDuration = 0;
        this.isWeakened = false;
    }

    /**
     * Увеличивает уровень персонажа на один.
     */
    public void plusLevel() {
        this.level++;
    }

    /**
     * Устанавливает новый уровень персонажа.
     *
     * @param level новый уровень
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Добавляет указанное количество здоровья к текущему.
     *
     * @param healthChange изменение здоровья
     */
    public void setHealth(int healthChange) {
        this.health += healthChange;
    }

    /**
     * Устанавливает новое значение здоровья.
     *
     * @param health новое здоровье
     */
    public void setNewHealth(int health) {
        this.health = health;
    }

    /**
     * Добавляет указанное количество урона к текущему.
     *
     * @param damageChange изменение урона
     */
    public void setDamage(int damageChange) {
        this.damage += damageChange;
    }

    /**
     * Устанавливает новое значение атаки.
     *
     * @param attack новое значение атаки
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Добавляет указанное количество к максимальному здоровью.
     *
     * @param maxHealthChange изменение максимального здоровья
     */
    public void setMaxHealth(int maxHealthChange) {
        this.maxHealth += maxHealthChange;
    }

    /**
     * Возвращает текущий уровень персонажа.
     *
     * @return уровень персонажа
     */
    public int getLevel() {
        return level;
    }

    /**
     * Возвращает текущее здоровье персонажа.
     *
     * @return здоровье персонажа
     */
    public int getHealth() {
        return health;
    }

    /**
     * Возвращает текущий урон персонажа.
     *
     * @return урон персонажа
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Возвращает значение атаки персонажа.
     *
     * @return значение атаки
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Возвращает максимальное здоровье персонажа.
     *
     * @return максимальное здоровье
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Возвращает имя персонажа.
     * Переопределяется в подклассах.
     *
     * @return имя персонажа (по умолчанию пустая строка)
     */
    public String getName() {
        return "";
    }

    /**
     * Возвращает длительность ослабления персонажа.
     *
     * @return количество оставшихся ходов ослабления
     */
    public int getWeakenDuration() {
        return weakenDuration;
    }

    /**
     * Устанавливает длительность ослабления.
     *
     * @param duration новая длительность ослабления
     */
    public void setWeakenDuration(int duration) {
        this.weakenDuration = duration;
    }

    /**
     * Проверяет, ослаблен ли персонаж.
     *
     * @return true, если персонаж ослаблен
     */
    public boolean isWeakened() {
        return isWeakened;
    }

    /**
     * Устанавливает статус ослабления.
     *
     * @param weakened true для включения ослабления
     */
    public void setWeakened(boolean weakened) {
        this.isWeakened = weakened;
    }
}