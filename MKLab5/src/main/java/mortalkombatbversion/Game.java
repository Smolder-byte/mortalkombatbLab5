/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mortalkombatbversion;

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
 *
 * @author Мария
 */
public class Game {

    private int totalLocations;
    private int currentLocation;
    private int currentLocationEnemiesCount;
    private Human human;
    
    CharacterAction action = new CharacterAction();
    ChangeTexts change = new ChangeTexts();
    Fight fight = new Fight();
    private ArrayList<Result> results = new ArrayList<>();

    public Player NewEnemy(JLabel L1, JLabel L2,
            JLabel L3, JLabel L4, JProgressBar pr2) {
        action.setEnemyes();
        Player enemy = action.ChooseEnemy(L1, L2, L3, L4);
        action.HP(enemy, pr2);
        pr2.setMaximum(enemy.getMaxHealth());
        return enemy;
    }
    
    public Human NewHuman(JProgressBar pr1) {
    Human newHuman = new Human(0, 80, 16, 1);
    this.human = newHuman;  // Сохраняем ссылку
    action.HP(newHuman, pr1);
    pr1.setMaximum(newHuman.getMaxHealth());
    return newHuman;
}

    public void EndGameTop(Human human, JTextField text, JTable table) throws IOException {
        results.add(new Result(text.getText(), human.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        WriteToTable(table);
        WriteToExcel();
    }
    
    public void WriteToExcel() throws IOException{
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
        XSSFRow r = sheet.createRow(0);
        r.createCell(0).setCellValue("№");
        r.createCell(1).setCellValue("Имя");
        r.createCell(2).setCellValue("Количество баллов");
        for (int i=0; i<results.size();i++){
            if (i<10){
                XSSFRow r2 = sheet.createRow(i+1);
                r2.createCell(0).setCellValue(i+1);
                r2.createCell(1).setCellValue(results.get(i).getName());
                r2.createCell(2).setCellValue(results.get(i).getPoints());
            }
        }
    File file = new File(System.getProperty("user.dir") + "Results.xlsx");
    
    try (FileOutputStream fos = new FileOutputStream(file)) {
        book.write(fos);
    } finally {
        book.close();
    }
    }
    
    public ArrayList<Result> getResults(){
        return this.results;
    }

        public void ReadFromExcel() throws IOException, InvalidFormatException {
    File file = new File(System.getProperty("user.dir") + "Results.xlsx");
    
    if (file.exists()) {
        try (XSSFWorkbook book = new XSSFWorkbook(file)) {
        XSSFSheet sh = book.getSheetAt(0);
        for (int i=1; i<=sh.getLastRowNum();i++) {
            results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(),(int)sh.getRow(i).getCell(2).getNumericCellValue()));
        }
    }
    }
}
    
    public void WriteToTable(JTable table){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i=0; i<results.size();i++){
            if (i<10){
                model.setValueAt(results.get(i).getName(), i, 0);
                model.setValueAt(results.get(i).getPoints(), i, 1);
            }
        }
    }
    
    public void setTotalLocations(int locations) {
        this.totalLocations = locations;
        this.currentLocation = 1;
    }
    
    public int getCurrentLocation() {
        return currentLocation;
    }
    
    public void nextLocation() {
        this.currentLocation++;
        if (this.human != null) {
            this.currentLocationEnemiesCount = action.getEnemiesCountForLocation(
                human.getLevel(), this.currentLocation);
        }
    }
    
    public boolean isLastLocation() {
        return currentLocation > totalLocations;
    }
    
 public void setCurrentLocationEnemiesCount(int count) {
        this.currentLocationEnemiesCount = count;
    }
    
    public int getCurrentLocationEnemiesCount() {
        return this.currentLocationEnemiesCount;
    }
    
    public void setHuman(Human human) {
        this.human = human;
    }
}
