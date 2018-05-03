package dazhou;

import robocode.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class MyRobot extends AdvancedRobot {
    boolean movingForward;
    int direction = 1;
    double previousEng = 100;


    public void run() {
        //setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while(true) {
            //turnGunRight(100);
            //turnLeft(90);
            //ahead(100);
            movingForward = true;

            //setAhead(10);

            setTurnRadarRight(360);

            //waitFor(new TurnCompleteCondition(this));

            //setTurnLeft(180);
            // ... and wait for the turn to finish ...
            //waitFor(new TurnCompleteCondition(this));
            execute();
        }
    }

    public void onScannedRobot (ScannedRobotEvent e){


        if (e.getDistance() > 350) {
            turnRight(e.getBearing());
            setAhead(e.getDistance() - 350);
            fire(3);
        } else if (e.getDistance() < 250) {
            turnRight(e.getBearing());
            setBack(250 - e.getDistance());
            fire(2);
        }

        execute();

        if (previousEng != 0) {
            setTurnRight(e.getBearing() + 90);
            double change = previousEng - e.getEnergy();

            if (change > 0 && change <= 3) {
                direction = -direction;
                setAhead((e.getDistance() / 4 + 25) * direction);
            }

            setTurnRadarRight(1000);

            previousEng = e.getEnergy();

            execute();
        }

        if (e.getEnergy() < 5) {
            double absoluteBearing = getHeading() + e.getBearing();
            double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

            // If it's close enough, fire!
            if (Math.abs(bearingFromGun) <= 3) {
                turnGunRight(bearingFromGun);
                // We check gun heat here, because calling fire()
                // uses a turn, which could cause us to lose track
                // of the other robot.
                if (getGunHeat() == 0) {
                    fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
                }
            } // otherwise just set the gun to turn.
            // Note:  This will have no effect until we call scan()
            else {
                turnGunRight(bearingFromGun);
            }
            // Generates another scan event if we see a robot.
            // We only need to call this if the gun (and therefore radar)
            // are not turning.  Otherwise, scan is called automatically.
            if (bearingFromGun == 0) {
                scan();
            }
        }

    }


    /**
     * onHitWall:  Handle collision with wall.
     */
    public void onHitWall(HitWallEvent e) {
        // Bounce off!
        reverseDirection();
    }

    public void reverseDirection() {
        if (movingForward) {
            setTurnRight(90);
            setBack(250);
            movingForward = false;
        } else {
            setTurnRight(90);
            setAhead(250);
            movingForward = true;
        }
        execute();
    }

    public void onHitRobot(HitRobotEvent e) {
        // If we're moving the other robot, reverse!
        if (e.isMyFault()) {
            reverseDirection();
        }
    }
}
