package fabrics;

import characters.LiuKang;
import characters.Player;

/**
 * Фабрика для создания персонажа Лю Кэнга.
 * Реализует интерфейс EnemyFabricInterface.
 */
public class LiuKangFabric implements EnemyFabricInterface {

    /**
     * Создаёт экземпляр персонажа Лю Кэнга с фиксированными характеристиками.
     *
     * @param type параметр, определяющий тип врага
     * @return объект персонажа Бараки
     */
    @Override
    public Player create(int type) {
        Player enemy;
        enemy = new LiuKang(1, 70, 20, 1);
        return enemy;
    }
}