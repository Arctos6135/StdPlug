package com.arctos6135.stdplug.api.datatypes;

import com.arctos6135.stdplug.api.StdPlugDataTypes;
import com.arctos6135.stdplug.api.StdPlugWidgets;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Represents a set of PIDVA gains.
 * <p>
 * The {@link StdPlugWidgets#PIDVA_GAINS} widget can be used to display this.
 * </p>
 * <p>
 * As this class implements {@link Sendable}, it can be sent directly using the
 * {@link ShuffleboardTab#add(Sendable)} or
 * {@link ShuffleboardTab#add(String, Sendable)} method.
 * </p>
 * 
 * @author Tyler Tian
 * @since 0.1.0
 * @see StdPlugWidgets#PIDVA_GAINS
 */
public class PIDVAGains extends SendableBase {

    private double kP;
    private double kI;
    private double kD;
    private double kV;
    private double kA;

    /**
     * The name of the data type.
     * <p>
     * This is equivalent to {@link StdPlugDataTypes#PIDVA_GAINS}.
     * </p>
     * 
     * @see StdPlugDataTypes#PIDVA_GAINS
     */
    public static final String NAME = StdPlugDataTypes.PIDVA_GAINS;

    /**
     * Constructs a new set of gains with all gains set to 0.
     */
    public PIDVAGains() {
        super(false);
        this.kP = 0;
        this.kI = 0;
        this.kD = 0;
        this.kV = 0;
        this.kA = 0;
    }

    /**
     * Constructs a new set of gains with the specified values.
     * 
     * @param kP The proportional gain
     * @param kI The integral gain
     * @param kD The derivative gain
     * @param kV The velocity feedforward gain
     * @param kA The acceleration feedforward gain
     */
    public PIDVAGains(double kP, double kI, double kD, double kV, double kA) {
        super(false);
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(NAME);

        builder.addDoubleProperty("kP", this::getP, this::setP);
        builder.addDoubleProperty("kI", this::getI, this::setI);
        builder.addDoubleProperty("kD", this::getD, this::setD);
        builder.addDoubleProperty("kV", this::getV, this::setV);
        builder.addDoubleProperty("kA", this::getA, this::setA);
    }

    /**
     * Retrieves the proportional gain.
     * 
     * @return The proportional gain
     */
    public double getP() {
        return this.kP;
    }

    /**
     * Sets the proportional gain.
     * 
     * @param P The proportional gain
     */
    public void setP(double P) {
        this.kP = P;
    }

    /**
     * Retrieves the integral gain.
     * 
     * @return The integral gain
     */
    public double getI() {
        return this.kI;
    }

    /**
     * Sets the integral gain.
     * 
     * @param I The integral gain
     */
    public void setI(double I) {
        this.kI = I;
    }

    /**
     * Retrieves the derivative gain.
     * 
     * @return The derivative gain
     */
    public double getD() {
        return this.kD;
    }

    /**
     * Sets the derivative gain.
     * 
     * @param D The derivative gain
     */
    public void setD(double D) {
        this.kD = D;
    }

    /**
     * Retrieves the velocity feedforward gain.
     * 
     * @return The velocity feedforward gain
     */
    public double getV() {
        return this.kV;
    }

    /**
     * Sets the velocity feedforward gain.
     * 
     * @param V The velocity feedforward gain
     */
    public void setV(double V) {
        this.kV = V;
    }

    /**
     * Retrieves the acceleration feedforward gain.
     * 
     * @return The acceleration feedforward gain
     */
    public double getA() {
        return this.kA;
    }

    /**
     * Sets the acceleration feedforward gain.
     * 
     * @param A The acceleration feedforward gain
     */
    public void setA(double A) {
        this.kA = A;
    }
}
