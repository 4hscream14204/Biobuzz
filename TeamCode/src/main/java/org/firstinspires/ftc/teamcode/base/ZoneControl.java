package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.follower.Follower;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class ZoneControl {

    public enum Cell{
        BLUEAUDIENCE,
        BLUESCORING,
        REDAUDIENCE,
        REDSCORING,
        NOZONE
    }
    PolygonZone scoringSide = new PolygonZone(
            new Point(0, 150),
            new Point(144, 150),
            new Point(144, 72),
            new Point(0, 72)
    );

    PolygonZone audienceSide = new PolygonZone(
            new Point(0, -10),
            new Point(0, 72),
            new Point(144, 72),
            new Point(144, -10)
    );

    PolygonZone robotZone;
    Follower follower;

    public ZoneControl(PolygonZone m_robotZone, Follower m_follower){
        robotZone = m_robotZone;
        follower = m_follower;
    }

    public Cell getCell(){
        if(robotZone.isFullyInside(audienceSide) && DataStorage.alliance == BiobuzzEnums.Alliance.BLUE){
            return Cell.BLUEAUDIENCE;
        }
        else if(robotZone.isFullyInside(scoringSide) && DataStorage.alliance == BiobuzzEnums.Alliance.BLUE){
            return Cell.BLUESCORING;
        }
        else if(robotZone.isFullyInside(audienceSide) && DataStorage.alliance == BiobuzzEnums.Alliance.RED){
            return Cell.REDAUDIENCE;
        }
        else if(robotZone.isFullyInside(scoringSide) && DataStorage.alliance == BiobuzzEnums.Alliance.RED){
            return Cell.REDSCORING;
        }
        return Cell.NOZONE;
    }

    public void updateRobotZone(){
        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());
    }
}
