package fabrics;

import characters.Player;

/**
 * Класс-фабрика для создания различных врагов в игре.
 * Использует соответствующие фабрики для создания конкретных персонажей.
 */
public class EnemyFabric {

    /**
     * Создаёт экземпляр врага на основе указанных параметров.
     *
     * @param enemyType тип врага (0: Барака, 1: Саб-Зиро, 2: Лю Кан, 3: Соня Блейд, 4: Шао Кан)
     * @param variant   дополнительный параметр для настройки врага
     * @return объект персонажа-врага
     */
    public Player create(int enemyType, int variant) {
        EnemyFabricInterface fabric = null;

        switch (enemyType) {
            case 0:
                fabric = new BarakaFabric();
                break;
            case 1:
                fabric = new SubZeroFabric();
                break;
            case 2:
                fabric = new LiuKangFabric();
                break;
            case 3:
                fabric = new SonyaBladeFabric();
                break;
            case 4:
                fabric = new ShaoKahnFabric();
                break;
        }

        return fabric.create(variant);
    }
}