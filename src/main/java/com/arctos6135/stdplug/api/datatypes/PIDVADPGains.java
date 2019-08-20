package com.arctos6135.stdplug.api.datatypes;

import com.arctos6135.stdplug.api.StdPlugDataTypes;
import com.arctos6135.stdplug.api.StdPlugWidgets;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Represents a set of PIDVA + DP gains.
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
public class PIDVADPGains extends PIDVAGains {

    private double kDP;

    /**
     * The name of the data type.
     * <p>
     * This is equivalent to {@link StdPlugDataTypes#PIDVADP_GAINS}.
     * </p>
     * 
     * @see StdPlugDataTypes#PIDVADP_GAINS
     */
    public static final String NAME = StdPlugDataTypes.PIDVADP_GAINS;

    /**
     * Constructs a new set of gains with all gains set to 0.
     */
    public PIDVADPGains() {
        super();
        this.kDP = 0;
    }

    /**
     * Constructs a new set of gains with the specified values.
     * 
     * @param kP  The proportional gain
     * @param kI  The integral gain
     * @param kD  The derivative gain
     * @param kV  The velocity feedforward gain
     * @param kA  The acceleration feedforward gain
     * @param kDP The directional-proportional gain
     */
    public PIDVADPGains(double kP, double kI, double kD, double kV, double kA, double kDP) {
        super(kP, kI, kD, kV, kA);
        this.kDP = kDP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);

        builder.setSmartDashboardType(NAME);

        builder.addDoubleProperty("kDP", this::getDP, this::setDP);
    }

    /**
     * Retrieves the directional-proportional gain.
     * 
     * @return The directional-proportional gain
     */
    public double getDP() {
        return kDP;
    }

    /**
     * Sets the directional-proportional gain.
     * 
     * @param kDP The directional-proportional gain
     */
    public void setDP(double kDP) {
        this.kDP = kDP;
    }
}
