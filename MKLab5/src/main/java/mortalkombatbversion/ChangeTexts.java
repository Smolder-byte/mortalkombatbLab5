/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mortalkombatbversion;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class ChangeTexts {
    public void NewRoundTexts(Player human, Player enemy, JProgressBar pr1,
            JProgressBar pr2, JLabel pointsLabel, JLabel expLabel, 
            JLabel playerLevelLabel, JLabel enemyLevelLabel, 
            JLabel playerHealthLabel, JLabel enemyHealthLabel, 
            JLabel playerDamageLabel, JLabel turnLabel, JLabel messageLabel,
            int i, Items[] items, JRadioButton rb1, JRadioButton rb2, JRadioButton rb3,
            JLabel weakenLabel) {
        
        pointsLabel.setText(Integer.toString(((Human) human).getPoints()));
        expLabel.setText(Integer.toString(((Human) human).getExperience()) + "/" + ((Human) human).getNextExperience());
        playerLevelLabel.setText(Integer.toString(human.getLevel()) + " level");
        enemyLevelLabel.setText(Integer.toString(enemy.getLevel()) + " level");
        playerHealthLabel.setText(Integer.toString(human.getHealth()) + "/" + Integer.toString(human.getMaxHealth()));
        playerDamageLabel.setText(Integer.toString(human.getDamage()));
        enemyHealthLabel.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        
        if (i % 2 == 1) {
            turnLabel.setText("Your turn");
        } else {
            turnLabel.setText(enemy.getName() + "'s turn");
        }
        BagText(items, rb1, rb2, rb3);
        messageLabel.setText("");
        updateWeakenStatus(human, enemy, weakenLabel);
    }

    public void RoundTexts(Player human, Player enemy, JLabel label, JLabel label2, int i, 
            JLabel label3, JLabel weakenLabel) {
        if (enemy.getHealth() >= 0) {
            label.setText(Integer.toString(enemy.getHealth()) + "/" + Integer.toString(enemy.getMaxHealth()));
        } else {
            label.setText("0/" + Integer.toString(enemy.getMaxHealth()));
        }
        if (human.getHealth() >= 0) {
            label2.setText(Integer.toString(human.getHealth()) + "/" + Integer.toString(human.getMaxHealth()));
        } else {
            label2.setText("0/" + Integer.toString(human.getMaxHealth()));
        }
        if (i % 2 == 1) {
            label3.setText("Your turn");
        } else {
            label3.setText(enemy.getName() + "'s turn");
        }
        updateWeakenStatus(human, enemy, weakenLabel);
    }

    private void updateWeakenStatus(Player human, Player enemy, JLabel weakenLabel) {
        StringBuilder status = new StringBuilder();
        if (human.isWeakened()) {
            status.append(human.getName()).append(" weakened (").append(human.getWeakenDuration()).append(" turns left)\n");
        }
        if (enemy.isWeakened()) {
            status.append(enemy.getName()).append(" weakened (").append(enemy.getWeakenDuration()).append(" turns left)");
        }
        weakenLabel.setText(status.toString());
    }

    public void EndGameText(Human human, JLabel label) {
        if (human.getWin() == 12) {
            label.setText("Победа на вашей стороне");
        } else {
            label.setText("Победа не на вашей стороне");
        }
    }

    public void BagText(Items[] items, JRadioButton rb1, JRadioButton rb2, JRadioButton rb3) {
        rb1.setText(items[0].getName() + ", " + items[0].getCount() + " шт");
        rb2.setText(items[1].getName() + ", " + items[1].getCount() + " шт");
        rb3.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
    }
}