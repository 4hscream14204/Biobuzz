package org.firstinspires.ftc.teamcode.base;

import com.pedropathing.follower.Follower;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class ZoneControl {
    public enum Flower{
        BLUEAUDIENCEFLOWER,
        BLUESCORINGFLOWER,
        REDAUDIENCEFLOWER,
        REDSCORINGFLOWER,
        NOZONE
    }
    //x47y0
    //
    PolygonZone redScoringFlowerZone = new PolygonZone(
            new Point(47, 0),
            new Point(0, 94),
            new Point(0, 0),
            new Point(47, 94)
    );
    PolygonZone blueAudienceFlowerZone = new PolygonZone(
            new Point(47, 0),
            new Point(144, 47),
            new Point(144, 0),
            new Point(47, 47)
    );
    PolygonZone redAudienceFlowerZone = new PolygonZone(
            new Point(0, 144),
            new Point(94, 144),
            new Point(94, 94),
            new Point(0, 94)
    );
    PolygonZone blueScoringFlowerZone = new PolygonZone(
            new Point(94, 47),
            new Point(94, 144),
            new Point(144, 47),
            new Point(144, 144)
    );

    PolygonZone robotZone;
    Follower follower;

    public ZoneControl(PolygonZone m_robotZone, Follower m_follower){
        robotZone = m_robotZone;
        follower = m_follower;
    }

    public Flower getFlower(){
        if(robotZone.isFullyInside(redScoringFlowerZone)){
            return Flower.REDSCORINGFLOWER;
        }
        else if(robotZone.isFullyInside(blueAudienceFlowerZone)){
            return Flower.BLUEAUDIENCEFLOWER;
        }
        else if(robotZone.isFullyInside(redAudienceFlowerZone)){
            return Flower.REDAUDIENCEFLOWER;
        }
        else if (robotZone.isFullyInside(blueScoringFlowerZone)){
            return Flower.BLUESCORINGFLOWER;
        }
        else return Flower.NOZONE;
    }

    public void updateRobotZone(){
        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());
    }
}
