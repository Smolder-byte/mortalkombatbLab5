package foundation;

import additions.ChangeTexts;
import additions.Result;
import characters.Human;
import characters.Player;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Класс для управления основным игровым процессом.
 * Отвечает за создание персонажей, сохранение результатов и работу с таблицей лидеров.
 */
public class Game {

    private int totalLocations;
    private int currentLocation;
    private int currentLocationEnemiesCount;
    private Human human;
    private final ArrayList<Result> results = new ArrayList<>();
    final CharacterAction action = new CharacterAction();
    final ChangeTexts textUpdater = new ChangeTexts();
    final Fight fight = new Fight();

    /**
     * Создаёт нового врага и обновляет интерфейс.
     *
     * @param enemyImageLabel метка для изображения врага
     * @param enemyInfoLabel метка для информации о враге
     * @param damageLabel    метка для урона врага
     * @param healthLabel    метка для здоровья врага
     * @param enemyHealthBar шкала здоровья врага
     * @return созданный враг
     */
    public Player createNewEnemy(JLabel enemyImageLabel, JLabel enemyInfoLabel, JLabel damageLabel,
                                 JLabel healthLabel, JProgressBar enemyHealthBar) {
        action.setEnemies();
        Player enemy = action.chooseEnemy(enemyImageLabel, enemyInfoLabel, damageLabel, healthLabel);
        action.updateHealthBar(enemy, enemyHealthBar);
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
        return enemy;
    }

    /**
     * Создаёт нового игрока-человека и инициализирует его шкалу здоровья.
     *
     * @param playerHealthBar шкала здоровья игрока
     * @return созданный игрок
     */
    public Human createNewHuman(JProgressBar playerHealthBar) {
        Human newHuman = new Human(0, 80, 16, 1);
        this.human = newHuman;
        action.updateHealthBar(newHuman, playerHealthBar);
        playerHealthBar.setMaximum(newHuman.getMaxHealth());
        return newHuman;
    }

    /**
     * Сохраняет результат игрока в таблицу лидеров и файл Excel.
     *
     * @param human игрок
     * @param nameField текстовое поле с именем игрока
     * @param table таблица лидеров
     * @throws IOException при ошибке записи в файл
     */
    public void endGameTop(Human human, JTextField nameField, JTable table) throws IOException {
        results.add(new Result(nameField.getText(), human.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        updateLeaderboardTable(table);
        writeToExcel();
    }

    /**
     * Записывает результаты в файл Excel.
     *
     * @throws IOException при ошибке записи в файл
     */
    public void writeToExcel() throws IOException {
        try (XSSFWorkbook book = new XSSFWorkbook()) {
            XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("№");
            headerRow.createCell(1).setCellValue("Имя");
            headerRow.createCell(2).setCellValue("Количество баллов");

            for (int i = 0; i < Math.min(results.size(), 10); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(results.get(i).getName());
                row.createCell(2).setCellValue(results.get(i).getPoints());
            }

            File file = new File(System.getProperty("user.dir") + "/Results.xlsx");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                book.write(fos);
            }
        }
    }

    /**
     * Читает результаты из файла Excel и заполняет список результатов.
     *
     * @throws IOException при ошибке чтения файла
     * @throws InvalidFormatException при неверном формате файла
     */
    public void readFromExcel() throws IOException, InvalidFormatException {
        File file = new File(System.getProperty("user.dir") + "/Results.xlsx");
        if (file.exists()) {
            try (XSSFWorkbook book = new XSSFWorkbook(file)) {
                XSSFSheet sheet = book.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    XSSFRow row = sheet.getRow(i);
                    results.add(new Result(row.getCell(1).getStringCellValue(),
                                           (int) row.getCell(2).getNumericCellValue()));
                }
            }
        }
    }

    /**
     * Обновляет таблицу лидеров в интерфейсе.
     *
     * @param table таблица лидеров
     */
    public void updateLeaderboardTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < Math.min(results.size(), 10); i++) {
            model.setValueAt(results.get(i).getName(), i, 0);
            model.setValueAt(results.get(i).getPoints(), i, 1);
        }
    }

    /**
     * Возвращает список результатов игроков.
     *
     * @return список результатов
     */
    public ArrayList<Result> getResults() {
        return results;
    }

    /**
     * Устанавливает общее количество локаций и инициализирует текущую локацию.
     *
     * @param locations общее количество локаций
     */
    public void setTotalLocations(int locations) {
        this.totalLocations = locations;
        this.currentLocation = 1;
    }

    /**
     * Возвращает номер текущей локации.
     *
     * @return текущая локация
     */
    public int getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Переходит к следующей локации и обновляет количество врагов.
     */
    public void nextLocation() {
        this.currentLocation++;
        if (this.human != null) {
            this.currentLocationEnemiesCount = action.getEnemiesCountForLocation(
                    human.getLevel(), this.currentLocation);
        }
    }

    /**
     * Проверяет, является ли текущая локация последней.
     *
     * @return true, если текущая локация последняя
     */
    public boolean isLastLocation() {
        return currentLocation > totalLocations;
    }

    /**
     * Устанавливает количество врагов в текущей локации.
     *
     * @param count количество врагов
     */
    public void setCurrentLocationEnemiesCount(int count) {
        this.currentLocationEnemiesCount = count;
    }

    /**
     * Возвращает количество врагов в текущей локации.
     *
     * @return количество врагов
     */
    public int getCurrentLocationEnemiesCount() {
        return currentLocationEnemiesCount;
    }

    /**
     * Устанавливает игрока-человека.
     *
     * @param human игрок
     */
    public void setHuman(Human human) {
        this.human = human;
    }

    /**
     * Устанавливает номер текущей локации.
     *
     * @param location номер локации
     */
    public void setCurrentLocation(int location) {
        this.currentLocation = location;
    }

    /**
     * Сбрасывает состояние игры, возвращая к первой локации.
     */
    public void resetGame() {
        this.currentLocation = 1;
        this.currentLocationEnemiesCount = 0;
        if (this.human != null) {
            this.human.setWin(0);
        }
    }
}