package dazhou;

import robocode.Robot;

public class MyRobot extends Robot {
    public void run() {
        while(true) {
            turnGunRight(100);
            turnLeft(90);
            ahead(100);
        }
    }
}
