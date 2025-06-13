package fabrics;

import characters.Player;

/**
 * Интерфейс для фабрик, создающих врагов в игре.
 */
public interface EnemyFabricInterface {

    /**
     * Создаёт экземпляр врага на основе указанного параметра.
     *
     * @param type параметр, определяющий характеристики врага
     * @return объект персонажа-врага
     */
    Player create(int type);
}