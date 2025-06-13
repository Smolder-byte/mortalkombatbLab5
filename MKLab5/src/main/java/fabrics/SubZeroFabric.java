package fabrics;

import characters.SubZero;
import characters.Player;

/**
 * Фабрика для создания персонажа Саб-Зиро.
 * Реализует интерфейс EnemyFabricInterface.
 */
public class SubZeroFabric implements EnemyFabricInterface {

    /**
     * Создаёт экземпляр персонажа Саб-Зиро с фиксированными характеристиками.
     *
     * @param type параметр, определяющий тип врага
     * @return объект персонажа Бараки
     */
    @Override
    public Player create(int type) {
        Player enemy;
        enemy = new SubZero(1, 60, 16, 1);
        return enemy;
    }

}