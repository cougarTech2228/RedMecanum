// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ShooterSubsystem extends SubsystemBase {
  WPI_TalonFX m_shooterMaster;
  WPI_TalonFX m_shooterFollower;

  private NetworkTableEntry m_velocityEntry;
  private XboxController m_xboxController = new XboxController(0);
  private PowerDistribution m_pD = new PowerDistribution();
  private boolean isShooting = false;

  /** Creates a new ExampleSubsystem. */
  public ShooterSubsystem() {
    m_shooterMaster = new WPI_TalonFX(Constants.SHOOTER_MASTER_CAN_ID);
    m_shooterFollower = new WPI_TalonFX(Constants.SHOOTER_FOLLOWER_CAN_ID);
    configMotor(m_shooterMaster);
    configMotor(m_shooterFollower);  
    m_shooterFollower.follow(m_shooterMaster);

    m_shooterMaster.setInverted(false);
    m_shooterFollower.setInverted(true);

    m_velocityEntry = Shuffleboard.getTab("Shooter Velocity Adjuster").add("Shooter Velocity", 1)
    .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", Constants.SHOOTER_MAX_OUTPUT)).getEntry();
   m_velocityEntry.setDefaultNumber(0);
  }

  public void setMotors(double percent){
    m_shooterMaster.set(ControlMode.PercentOutput, percent);
  }
  public void stopMotors(){
    m_shooterMaster.stopMotor();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(m_xboxController.getAButton() && !isShooting){
      isShooting = true;
      setMotors(m_velocityEntry.getDouble(0));
   }
   else if(m_xboxController.getBButton() && isShooting){
      stopMotors();
      isShooting = false;
   }
   SmartDashboard.putNumber("Leader Motor Draw", m_pD.getCurrent(9));
   SmartDashboard.putNumber("Follower Motor Draw", m_pD.getCurrent(10));
   SmartDashboard.putNumber("Motor Voltage", m_pD.getVoltage());
  }

   private void configMotor(WPI_TalonFX motor){

        motor.configFactoryDefault();
        motor.setSensorPhase(false);
        motor.configVoltageCompSaturation(11);

        motor.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
        motor.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.supplyCurrLimit.enable = true;
        config.supplyCurrLimit.triggerThresholdCurrent = Constants.SHOOTER_CURRENT_LIMIT;
        config.supplyCurrLimit.triggerThresholdTime = Constants.SHOOTER_CURRENT_DURATION;
        config.supplyCurrLimit.currentLimit = Constants.SHOOTER_CONTINUOUS_CURRENT_LIMIT;
        motor.configAllSettings(config);
    }
}
