package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class StorageSubsystem extends SubsystemBase {
    WPI_TalonSRX m_storageDrive = new WPI_TalonSRX(Constants.STORAGE_DRIVE_CAN_ID);
    WPI_TalonFX m_shooterFeed = new WPI_TalonFX(Constants.SHOOTER_FEED_CAN_ID);
    XboxController controller = DrivebaseSubsystem.getController();
    private NetworkTableEntry m_driveVelocityEntry;
    private NetworkTableEntry m_feedVelocityEntry;
    private boolean m_driving, m_feeding;
    public StorageSubsystem(){
        m_storageDrive.configFactoryDefault();
        m_shooterFeed.configFactoryDefault();

        m_driveVelocityEntry = Shuffleboard.getTab("Storage Drive Velocity Adjuster").add("Drive Velocity", 1)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1.0, "max", 1.0)).getEntry();
    m_feedVelocityEntry = Shuffleboard.getTab("Shooter Feed Velocity Adjuster").add("Feed Velocity", 1)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1.0, "max", 1.0)).getEntry();
    }

    @Override
    public void periodic(){
        if(controller.getBButtonPressed() && !m_driving){
            m_storageDrive.set(ControlMode.PercentOutput, m_driveVelocityEntry.getDouble(0));
        }
        else if(controller.getBButtonPressed() && m_driving){
            m_storageDrive.stopMotor();
        }

        if(controller.getYButtonPressed() && !m_feeding){
            m_shooterFeed.set(ControlMode.PercentOutput, m_feedVelocityEntry.getDouble(0));
        }
        else if(controller.getYButtonPressed() && m_feeding){
            m_shooterFeed.stopMotor();
        }
    }

}
