package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivebaseSubsystem extends SubsystemBase {
    private WPI_TalonFX m_rightFront = new WPI_TalonFX(Constants.RIGHT_FRONT_MOTOR_CAN_ID);
    private WPI_TalonFX m_rightBack = new WPI_TalonFX(Constants.RIGHT_REAR_MOTOR_CAN_ID);
    private WPI_TalonFX m_leftFront = new WPI_TalonFX(Constants.LEFT_FRONT_MOTOR_CAN_ID);
    private WPI_TalonFX m_leftBack = new WPI_TalonFX(Constants.LEFT_REAR_MOTOR_CAN_ID);

    private static final int kJoystickChannel = 0;

    private MecanumDrive m_robotDrive;
    private static XboxController m_controller = new XboxController(0);

    public DrivebaseSubsystem() {

        DriverStation.silenceJoystickConnectionWarning(true);

        m_leftFront.configOpenloopRamp(Constants.OPEN_RAMP_SECONDS_TO_FULL);
        m_leftBack.configOpenloopRamp(Constants.OPEN_RAMP_SECONDS_TO_FULL);
        m_rightFront.configOpenloopRamp(Constants.OPEN_RAMP_SECONDS_TO_FULL);
        m_rightBack.configOpenloopRamp(Constants.OPEN_RAMP_SECONDS_TO_FULL);

        m_leftBack.follow(m_leftFront);
        m_rightBack.follow(m_rightFront);

        m_leftFront.setInverted(true);
        m_leftBack.setInverted(true);

        m_leftFront.setNeutralMode(NeutralMode.Brake);
        m_leftBack.setNeutralMode(NeutralMode.Brake);
        m_rightFront.setNeutralMode(NeutralMode.Brake);
        m_rightBack.setNeutralMode(NeutralMode.Brake);

        m_robotDrive = new MecanumDrive(m_leftFront, m_leftBack, m_rightFront, m_rightBack);
    }

    private double deadband(final double value) {
        /* Upper deadband */
        if (value >= Constants.JOYSTICK_DEADBAND_PERCENTAGE) {
            return value;
        }

        /* Lower deadband */
        if (value <= -Constants.JOYSTICK_DEADBAND_PERCENTAGE) {
            return value;
        }

        /* Inside deadband */
        return 0.0;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        m_robotDrive.driveCartesian(deadband(-m_controller.getLeftY()),
                deadband(m_controller.getLeftX()),
                deadband(m_controller.getRightX()));
    }

    public static XboxController getController() {
        return m_controller;
    }
}
