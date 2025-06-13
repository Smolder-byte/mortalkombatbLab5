package characters;

/**
 * Класс, представляющий персонажа Бараку в игре.
 * Наследуется от класса Player.
 */
public class Baraka extends Player {

    /**
     * Конструктор для создания экземпляра Бараки.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public Baraka(int level, int health, int damage, int attack) {
        super(level, health, damage, attack);
    }

    /**
     * Возвращает имя персонажа.
     *
     * @return имя "Baraka"
     */
    @Override
    public String getName() {
        return "Baraka";
    }
}