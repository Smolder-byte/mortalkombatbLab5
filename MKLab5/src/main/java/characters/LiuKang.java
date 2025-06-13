package characters;

/**
 * Класс, представляющий персонажа Лю Кэнга в игре.
 * Наследуется от класса Player.
 */
public class LiuKang extends Player{
    
    /**
     * Конструктор для создания экземпляра Лю Кэнга.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public LiuKang(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    /**
     * Возвращает имя персонажа.
     *
     * @return имя "Liu Kang"
     */
    @Override
    public String getName(){
        return "Liu Kang";
    }
}