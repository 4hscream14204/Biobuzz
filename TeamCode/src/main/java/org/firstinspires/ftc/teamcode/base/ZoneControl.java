package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.follower.Follower;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class ZoneControl {

    public enum Cell{
        BLUEAUDIENCE,
        BLUESCORING,
        REDAUDIENCE,
        REDSCORING
    }
    PolygonZone audienceSide = new PolygonZone(
            new Point(0, 144),
            new Point(144, 144),
            new Point(144, 72),
            new Point(0, 72)
    );

    PolygonZone scoringSide = new PolygonZone(
            new Point(0, 0),
            new Point(0, 72),
            new Point(144, 72),
            new Point(144, 0)
    );

    PolygonZone robotZone;
    BiobuzzEnums.Alliance alliance;
    Follower follower;

    public ZoneControl(PolygonZone m_robotZone, Follower m_follower, BiobuzzEnums.Alliance m_alliance){
        robotZone = m_robotZone;
        alliance = m_alliance;
        follower = m_follower;
    }

    public Cell getCell(){
        if(robotZone.isFullyInside(audienceSide) && alliance == BiobuzzEnums.Alliance.BLUE){
            return Cell.BLUEAUDIENCE;
        }
        else if(robotZone.isFullyInside(scoringSide) && alliance == BiobuzzEnums.Alliance.BLUE){
            return Cell.BLUESCORING;
        }
        else if(robotZone.isFullyInside(audienceSide) && alliance == BiobuzzEnums.Alliance.RED){
            return Cell.REDAUDIENCE;
        }
        else{
            return Cell.REDSCORING;
        }
    }

    public void updateRobotZone(){
        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());
    }
}
