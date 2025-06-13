package additions;

/**
 * Класс для представления предметов в игре, включая их название и количество.
 */
public class Items {

    private String itemName;
    private int count;

    /**
     * Конструктор для создания объекта предмета.
     *
     * @param name  название предмета
     * @param count начальное количество
     */
    public Items(String name, int count) {
        this.itemName = name;
        this.count = count;
    }

    /**
     * Устанавливает название предмета.
     *
     * @param name новое название предмета
     */
    public void setName(String name) {
        this.itemName = name;
    }

    /**
     * Изменяет количество предметов, добавляя или вычитая указанное значение.
     *
     * @param countChange изменение количества (может быть положительным или отрицательным)
     */
    public void setCount(int countChange) {
        this.count += countChange;
    }

    /**
     * Возвращает название предмета.
     *
     * @return название предмета
     */
    public String getName() {
        return itemName;
    }

    /**
     * Возвращает текущее количество предметов.
     *
     * @return количество предметов
     */
    public int getCount() {
        return count;
    }
}