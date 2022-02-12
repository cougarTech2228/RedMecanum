package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AcquisitionSubsystem extends SubsystemBase{
    WPI_TalonFX m_spinnerMotor = new WPI_TalonFX(Constants.ACQUIRER_SPIN_MOTOR_CAN_ID);
    WPI_TalonFX m_deployMotor = new WPI_TalonFX(Constants.ACQUIRER_DEPLOY_MOTOR_CAN_ID);
    boolean m_isSpinning = false;
    public AcquisitionSubsystem(){
        m_spinnerMotor.configFactoryDefault();
        m_deployMotor.configFactoryDefault();
    }
    public void setSpinnerMotor(double speed){
        m_spinnerMotor.set(ControlMode.PercentOutput, speed);
        m_isSpinning = true;
        System.out.println("SPINNER");
    }
    public void stopSpinnerMotor(){
        m_spinnerMotor.stopMotor();
        m_isSpinning = false;
    }

    public boolean getIsSpinning(){
        return m_isSpinning;
    }
}
