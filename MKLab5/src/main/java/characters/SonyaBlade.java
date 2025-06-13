package characters;

/**
 * Класс, представляющий персонажа Соню Блейд в игре.
 * Наследуется от класса Player.
 */
public class SonyaBlade extends Player{
    
    /**
     * Конструктор для создания экземпляра Сони Блейд.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public SonyaBlade (int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    /**
     * Возвращает имя персонажа.
     *
     * @return имя "Sonya Blade"
     */
    @Override
    public String getName(){
        return "Sonya Blade";
    }
}