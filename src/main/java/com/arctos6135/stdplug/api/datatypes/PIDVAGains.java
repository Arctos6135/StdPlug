package com.arctos6135.stdplug.api.datatypes;

import com.arctos6135.stdplug.api.StdPlugDataTypes;

import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class PIDVAGains extends SendableBase {

    private double kP;
    private double kI;
    private double kD;
    private double kV;
    private double kA;

    public static final String NAME = StdPlugDataTypes.PIDVA_GAINS;

    public PIDVAGains() {
        this.kP = 0;
        this.kI = 0;
        this.kD = 0;
        this.kV = 0;
        this.kA = 0;
    }

    public PIDVAGains(double kP, double kI, double kD, double kV, double kA) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(NAME);

        builder.addDoubleProperty("kP", this::getP, this::setP);
        builder.addDoubleProperty("kI", this::getI, this::setI);
        builder.addDoubleProperty("kD", this::getD, this::setD);
        builder.addDoubleProperty("kV", this::getV, this::setV);
        builder.addDoubleProperty("kA", this::getA, this::setA);
    }

    public double getP() {
        return this.kP;
    }

    public void setP(double P) {
        this.kP = P;
    }

    public double getI() {
        return this.kI;
    }

    public void setI(double I) {
        this.kI = I;
    }

    public double getD() {
        return this.kD;
    }

    public void setD(double D) {
        this.kD = D;
    }

    public double getV() {
        return this.kV;
    }

    public void setV(double V) {
        this.kV = V;
    }

    public double getA() {
        return this.kA;
    }

    public void setA(double A) {
        this.kA = A;
    }    
}
