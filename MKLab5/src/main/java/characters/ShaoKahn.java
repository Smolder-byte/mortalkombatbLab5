package characters;

/**
 * Класс, представляющий персонажа Шао Кана в игре.
 * Наследуется от класса Player.
 */
public class ShaoKahn extends Player{
    
    /**
     * Конструктор для создания экземпляра Шао Кана.
     *
     * @param level  уровень персонажа
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param attack значение атаки
     */
    public ShaoKahn(int level, int health, int  damage, int attack){
        super (level, health, damage, attack);
    }
    
    /**
     * Возвращает имя персонажа.
     *
     * @return имя "Shao Khan"
     */
    @Override
    public String getName(){
        return "Shao Kahn";
    }
}