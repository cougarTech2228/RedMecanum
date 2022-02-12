package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.OI;

public class StorageSubsystem extends SubsystemBase {
    WPI_TalonSRX m_storageDrive = new WPI_TalonSRX(Constants.STORAGE_DRIVE_MOTOR_CAN_ID);
    WPI_TalonFX m_shooterFeed = new WPI_TalonFX(Constants.SHOOTER_FEED_MOTOR_CAN_ID);
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

    // @Override
    // public void periodic(){
    //     if(OI.getXboxXButton() && !m_driving){
    //         m_storageDrive.set(ControlMode.PercentOutput, m_driveVelocityEntry.getDouble(0));
    //         m_shooterFeed.set(ControlMode.PercentOutput, m_feedVelocityEntry.getDouble(0));
    //         m_driving = true;
    //     }
    //     else if(OI.getXboxYButton() && m_driving){
    //         m_storageDrive.stopMotor();
    //         m_shooterFeed.stopMotor();
    //         m_driving = false;
    //     }
    // }

    public void setDriveMotor(){
        m_storageDrive.set(ControlMode.PercentOutput, Constants.STORAGE_DRIVE_SPEED);

    }
    public void setFeedMotor(){
        m_shooterFeed.set(ControlMode.PercentOutput, Constants.SHOOTER_FEED_SPEED);
    }

    public void stopMotors(){
        m_storageDrive.stopMotor();
        m_shooterFeed.stopMotor();
    }
}
