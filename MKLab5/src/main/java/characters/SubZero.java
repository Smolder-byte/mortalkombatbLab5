package characters;

/**
 * Класс, представляющий персонажа Саб-Зиро в игре.
 * Наследуется от класса Player.
 */
public class SubZero extends Player{
    
    /**
     * Конструктор для создания экземпляра Саб-Зиро.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public SubZero(int level, int health, int damage , int attack){
        super (level, health, damage, attack);
    }
    
    /**
     * Возвращает имя персонажа.
     *
     * @return имя "Sub-Zero"
     */
    @Override
    public String getName(){
        return "Sub-Zero";
    }
}