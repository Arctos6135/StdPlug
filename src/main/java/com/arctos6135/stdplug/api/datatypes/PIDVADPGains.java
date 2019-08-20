package com.arctos6135.stdplug.api.datatypes;

import com.arctos6135.stdplug.api.StdPlugDataTypes;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class PIDVADPGains extends PIDVAGains {

    private double kDP;

    public static final String NAME = StdPlugDataTypes.PIDVADP_GAINS;

    public PIDVADPGains() {
        super();
        this.kDP = 0;
    }

    public PIDVADPGains(double kP, double kI, double kD, double kV, double kA, double kDP) {
        super(kP, kI, kD, kV, kA);
        this.kDP = kDP;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        
        builder.setSmartDashboardType(NAME);
        
        builder.addDoubleProperty("kDP", this::getDP, this::setDP);
    }

    public double getDP() {
        return kDP;
    }

    public void setDP(double kDP) {
        this.kDP = kDP;
    }
}
