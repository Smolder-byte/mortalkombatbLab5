package fabrics;

import characters.ShaoKahn;
import characters.Player;

/**
 * Фабрика для создания персонажа Шао Кана.
 * Реализует интерфейс EnemyFabricInterface.
 */
public class ShaoKahnFabric implements EnemyFabricInterface{
    
    /**
     * Создаёт экземпляр персонажа Шао Кана с фиксированными характеристиками.
     *
     * @param type параметр, определяющий тип врага
     * @return объект персонажа Бараки
     */
    @Override
    public Player create(int type) {
        Player enemy;
        enemy = new ShaoKahn(1, 85, 25, 1);
        return enemy;
    }
}