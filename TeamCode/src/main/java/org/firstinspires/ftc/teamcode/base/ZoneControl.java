package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class ZoneControl {

    public enum Cell{
        REDAUDIENCE,
        REDSCORING,
        BLUEAUDIENCE,
        BLUESCORING,
        NOZONE
    }

    public final Pose redCellPoseScoring = new Pose(64, 85);
    public final Pose redCellPoseAudience = new Pose(64, 60);
    public final Pose blueCellPoseScoring = new Pose(83.5, 85);
    public final Pose blueCellPoseAudience = new Pose(83.5, 60);

    PolygonZone scoringSideLeft = new PolygonZone(
            new Point(0, 150),
            new Point(48, 150),
            new Point(48, 72),
            new Point(0, 72)
    );

    PolygonZone scoringSideRight = new PolygonZone(
            new Point(48, 150),
            new Point(72, 150),
            new Point(72, 72),
            new Point(48, 72)
    );

    PolygonZone audienceSideLeft = new PolygonZone(
            new Point(0, 0),
            new Point(0, 72),
            new Point(48, 72),
            new Point(48, 0)
    );

    PolygonZone audienceSideRight = new PolygonZone(
            new Point(48, 0),
            new Point(48, 72),
            new Point(72, 72),
            new Point(72, 0)
    );

    PolygonZone robotZone;
    Follower follower;

    public ZoneControl(PolygonZone m_robotZone, Follower m_follower){
        robotZone = m_robotZone;
        follower = m_follower;
    }

    public Cell getCell(){
        if(robotZone.isFullyInside(audienceSideLeft)){
            return Cell.REDAUDIENCE;
        }
        else if(robotZone.isFullyInside(audienceSideRight)){
            return Cell.REDSCORING;
        }
        else if(robotZone.isFullyInside(scoringSideLeft)){
            return Cell.BLUEAUDIENCE;
        }
        else if(robotZone.isFullyInside(scoringSideRight)){
            return Cell.BLUESCORING;
        }
        return Cell.NOZONE;
    }

    public void updateRobotZone(){
        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());
    }

    public Pose getCellPosition() {
        Cell cell = getCell();

    }
}
