package fabrics;

import characters.SonyaBlade;
import characters.Player;

/**
 * Фабрика для создания персонажа Сони Блейд.
 * Реализует интерфейс EnemyFabricInterface.
 */
public class SonyaBladeFabric implements EnemyFabricInterface {

    /**
     * Создаёт экземпляр персонажа Шао Кана с фиксированными характеристиками.
     *
     * @param type параметр, определяющий тип врага
     * @return объект персонажа Бараки
     */
    @Override
    public Player create(int type) {
        Player enemy;
        enemy = new SonyaBlade(1, 80, 16, 1);
        return enemy;
    }

}