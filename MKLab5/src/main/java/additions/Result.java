package additions;

/**
 * Класс для хранения результата игрока, включая его имя и набранные очки.
 */
public class Result {

    private String playerName;
    private int points;

    /**
     * Конструктор для создания объекта результата.
     *
     * @param name   имя игрока
     * @param points количество очков
     */
    public Result(String name, int points) {
        this.playerName = name;
        this.points = points;
    }

    /**
     * Устанавливает имя игрока.
     *
     * @param name новое имя игрока
     */
    public void setName(String name) {
        this.playerName = name;
    }

    /**
     * Устанавливает количество очков.
     *
     * @param points новое количество очков
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Возвращает имя игрока.
     *
     * @return имя игрока
     */
    public String getName() {
        return playerName;
    }

    /**
     * Возвращает количество очков.
     *
     * @return количество очков
     */
    public int getPoints() {
        return points;
    }
}