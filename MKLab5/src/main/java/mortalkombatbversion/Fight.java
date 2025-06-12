/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mortalkombatbversion;

import static java.lang.Math.max;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class Fight {
    ChangeTexts change = new ChangeTexts();
    int kind_attack[] = {0};
    int experiences[] = {40, 90, 180, 260, 410};
    EnemyFabric fabric = new EnemyFabric();
    int i = 1;
    int k = -1;
    int stun = 0;
    double v = 0.0;
    private JFrames frames;

    public void Move(Player p1, Player p2, JLabel l, JLabel l2) {
        // Update weaken duration for both players
        if (p1.isWeakened() && p1.getWeakenDuration() > 0) {
            p1.setWeakenDuration(p1.getWeakenDuration() - 1);
            if (p1.getWeakenDuration() == 0) {
                p1.setWeakened(false);
                l.setText(p1.getName() + "'s weaken debuff has ended");
            }
        }
        if (p2.isWeakened() && p2.getWeakenDuration() > 0) {
            p2.setWeakenDuration(p2.getWeakenDuration() - 1);
            if (p2.getWeakenDuration() == 0) {
                p2.setWeakened(false);
                l2.setText(p2.getName() + "'s weaken debuff has ended");
            }
        }
        
        if (stun == 1){
            p1.setAttack(-1);
        }
        
        // Adjust damage based on weaken debuff
        int p1Damage = p1.isWeakened() ? (int)(p1.getDamage() * 0.5) : p1.getDamage();
        int p2Damage = p2.isWeakened() ? (int)(p2.getDamage() * 0.5) : p2.getDamage();

        switch (Integer.toString(p1.getAttack()) + Integer.toString(p2.getAttack())) {
            case "10": // Attack vs Block
                    v = Math.random();
                    if (p1 instanceof ShaoKahn && v < 0.15) {
                        p2.setHealth(-(int) (p1Damage * 0.5));
                        l2.setText("Your block is broken");
                    } else {
                        p1.setHealth(-(int) p2Damage);
                        l2.setText(p2.getName() + " counteratacked");
                }
                break;
            case "11": // Attack vs Attack
                p2.setHealth(-(int)(p1Damage * (p2.isWeakened() ? 1.25 : 1.0)));
                l2.setText(p1.getName() + " attacked");
                break;
            case "00": // Block vs Block
                v = Math.random();
                if (v <= 0.5) {
                    stun = 1;
                l.setText(p2.getName() + " was stunned");
                }
                l2.setText("Both defended themselves");
                break;
            case "01": // Block vs Attack
                l2.setText(p1.getName() + " didn't attacked");
                break;
            case "-10": // Stun vs Block
                stun = 0;
                l2.setText(p2.getName() + " didn't attacked");
                break;
            case "-11": // Stun vs Attack
                p1.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 : 1.0)));
                stun = 0;
                l2.setText(p2.getName() + " attacked");
                break;
            case "-12":
                p1.setWeakened(true);
                p1.setWeakenDuration(max(1, p2.getLevel()));
                l.setText(p1.getName() + " is weakened for " + max(1, p2.getLevel()) + " turns");
                 break;
            case "20": // Weaken vs Block
                v = Math.random();
                if (v < 0.75) {
                    p2.setWeakened(true);
                    p2.setWeakenDuration(max(1, p1.getLevel()));
                    l.setText(p2.getName() + " is weakened for " + max(1, p1.getLevel()) + " turns");
                } else {
                    l2.setText(p2.getName() + " resisted weaken");
                }
                break;
            case "21": // Weaken vs Attack
                p1.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 * 1.15 : 1.15)));
                l.setText(p1.getName() + "'s weaken failed, damage increased by 15%");
                break;
            case "02": // Block vs Weaken
                v = Math.random();
                if (v < 0.75) {
                    p1.setWeakened(true);
                    p1.setWeakenDuration(p2.getLevel());
                    l.setText(p1.getName() + " is weakened for " + max(1, p2.getLevel()) + " turns");
                } else {
                    l2.setText(p1.getName() + " resisted weaken");
                }
                break;
            case "12": // Attack vs Weaken
                p2.setHealth(-(int)(p2Damage * (p1.isWeakened() ? 1.25 * 1.15 : 1.15)));
                l.setText(p2.getName() + "'s weaken failed, damage increased by 15%");
                break;
            case "22": // Weaken vs Weaken
                l2.setText("Both attempted to weaken");
                break;
        }
    }

    public void Hit(Player human, Player enemy, int a, JLabel label,
            JLabel label2, JDialog dialog, JLabel label3, CharacterAction action,
            JProgressBar pr1, JProgressBar pr2, JDialog dialog1,
            JDialog dialog2, JFrame frame, ArrayList<Result> results,
            JLabel label4, JLabel label5, JLabel label6, JLabel label7,
            JLabel label8, Items[] items, JRadioButton rb, Game game, 
            JLabel weakenLabel) {
        label7.setText("");

        human.setAttack(a);

        if (k < kind_attack.length - 1) {
        k++;
        } else {
        kind_attack = action.ChooseBehavior(enemy, action);
        k = 0;
        }
        enemy.setAttack(kind_attack[k]);

        if (i % 2 == 1) {
            Move(human, enemy, label7, label8);
        } else {
            Move(enemy, human, label7, label8);
        }
        
        i++;
        change.RoundTexts(human, enemy, label, label2, i, label6, weakenLabel);
        action.HP(human, pr1);
        action.HP(enemy, pr2);
        if (human.getHealth() <= 0 && items[2].getCount() > 0) {
            human.setNewHealth((int) (human.getMaxHealth() * 0.05));
            items[2].setCount(-1);
            action.HP(human, pr1);
            label2.setText(human.getHealth() + "/" + human.getMaxHealth());
            rb.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
            label7.setText("Вы воскресли");
        }
        if (human.getHealth() <= 0 || enemy.getHealth() <= 0) {
            if (((Human) human).getWin() == 11) {
                EndFinalRound(((Human) human), action, results, dialog1, dialog2,
                        frame, label4, label5);
            } else {
                EndRound(human, enemy, dialog, label3, action, items, game,
                        dialog1, dialog2, frame, label4, label5);
            }
        }
    }

    public void EndRound(Player human, Player enemy, JDialog dialog, JLabel label,
            CharacterAction action, Items[] items, Game game, 
            JDialog victoryDialog, JDialog noTopDialog, JFrame mainFrame,
            JLabel victoryLabel, JLabel noTopLabel) {
        Human player = (Human) human;
        dialog.setVisible(true);
        dialog.setBounds(300, 150, 700, 600);
        player.setWin(player.getWin() + 1);
        if (player.getHealth() > 0) {
            label.setText("You win");
            if (enemy instanceof ShaoKahn) {
                action.AddItems(38, 23, 8, items);
                action.AddPointsBoss(player, action.getEnemyes(), frames);
                game.nextLocation();
                if (game.isLastLocation()) {
                    EndFinalRound(player, action, game.getResults(), 
                        victoryDialog, noTopDialog, mainFrame, victoryLabel, noTopLabel);
                    return;
                }
                player.setWin(0);
            } else {
                action.AddItems(25, 15, 5, items);
                action.AddPoints(player, action.getEnemyes(), frames);
            }
            player.setNewHealth(player.getMaxHealth());
        } else {
            label.setText(enemy.getName() + " win");
            player.setNewHealth(player.getMaxHealth());
            if (enemy instanceof ShaoKahn) {
                game.nextLocation();
                if (game.isLastLocation()) {
                    EndFinalRound(player, action, game.getResults(), 
                        victoryDialog, noTopDialog, mainFrame, victoryLabel, noTopLabel);
                    return;
                }
                player.setWin(0);
            }
        }
        i = 1;
        k = -1;
        kind_attack = ResetAttack();
        human.setWeakened(false);
        human.setWeakenDuration(0);
        enemy.setWeakened(false);
        enemy.setWeakenDuration(0);
    }

    public void EndFinalRound(Human human, CharacterAction action,
            ArrayList<Result> results, JDialog dialog1, JDialog dialog2, JFrame frame,
            JLabel label1, JLabel label2) {
        String text = "Победа не на вашей стороне";
        if (human.getHealth() > 0) {
            human.setWin(12);
            action.AddPoints(human, action.getEnemyes(), frames);
            text = "Победа на вашей стороне! Игра пройдена!";
        }
        boolean top = false;
        if (results == null) {
            top = true;
        } else {
            int i = 0;
            for (int j = 0; j < results.size(); j++) {
                if (human.getPoints() < results.get(j).getPoints()) {
                    i++;
                }
            }
            if (i < 10) {
                top = true;
            }
        }
        if (top) {
            dialog1.setVisible(true);
            dialog1.setBounds(150, 150, 600, 500);
            label1.setText(text);
        } else {
            dialog2.setVisible(true);
            dialog2.setBounds(150, 150, 470, 360);
            label2.setText(text);
        }
        frame.dispose();
    }

    public int[] ResetAttack() {
        int a[] = {0};
        return a;
    }

    public Player NewRound(Player human, JLabel enemyImageLabel, JProgressBar pr1,
            JProgressBar pr2, JLabel enemyInfoLabel, JLabel damageLabel, 
            JLabel healthLabel, CharacterAction action, Game game) {
        Human player = (Human) human;
        int enemiesInLocation = action.getEnemiesCountForLocation(player.getLevel(), game.getCurrentLocation());
        int currentEnemyNumber = player.getWin() + 1;
        boolean isBossFight = currentEnemyNumber >= enemiesInLocation;
        Player enemy;
        if (isBossFight) {
            enemy = action.ChooseBoss(enemyImageLabel, enemyInfoLabel, damageLabel, healthLabel, game.getCurrentLocation());
            enemyInfoLabel.setText("Босс локации " + game.getCurrentLocation() + " (" + currentEnemyNumber + "/" + enemiesInLocation + ")");
        } else {
            enemy = action.ChooseEnemy(enemyImageLabel, enemyInfoLabel, damageLabel, healthLabel);
            enemyInfoLabel.setText(enemy.getName() + " (" + currentEnemyNumber + "/" + enemiesInLocation + ")");
        }
        pr1.setMaximum(human.getMaxHealth());
        pr2.setMaximum(enemy.getMaxHealth());
        enemy.setNewHealth(enemy.getMaxHealth());
        action.HP(human, pr1);
        action.HP(enemy, pr2);
        return enemy;
    }
}