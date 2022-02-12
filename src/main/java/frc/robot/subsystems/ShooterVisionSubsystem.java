package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterVisionSubsystem extends SubsystemBase{
    static NetworkTable m_shooterVisionTable = NetworkTableInstance.getDefault().getTable("Hub");
    static NetworkTableEntry m_distanceFt = m_shooterVisionTable.getEntry("distanceFeet"); //last 10 distances calculated as a rolling average
    static NetworkTableEntry m_deviationFromCenter = m_shooterVisionTable.getEntry("deviationFromCenter");

    public static double getDistanceFt(){
        return m_distanceFt.getDouble(-1.0);
    }
    public static double getDeviationFromCenter(){
        return m_deviationFromCenter.getDouble(0);
    }
}
