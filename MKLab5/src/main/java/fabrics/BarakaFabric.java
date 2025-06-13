package fabrics;

import characters.Baraka;
import characters.Player;

/**
 * Фабрика для создания персонажа Бараки.
 * Реализует интерфейс EnemyFabricInterface.
 */
public class BarakaFabric implements EnemyFabricInterface {

    /**
     * Создаёт экземпляр персонажа Бараки с фиксированными характеристиками.
     *
     * @param type параметр, определяющий тип врага
     * @return объект персонажа Бараки
     */
    @Override
    public Player create(int type) {
        return new Baraka(1, 100, 12, 1);
    }
}