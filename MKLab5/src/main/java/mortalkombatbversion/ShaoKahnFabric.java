/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mortalkombatbversion;

/**
 *
 * @author Мария
 */
public class ShaoKahnFabric implements EnemyFabricInterface{
    
    @Override
    public Player create(int i) {
        Player enemy;
        enemy = new ShaoKahn(1, 100, 30, 1);
        return enemy;
    }
}