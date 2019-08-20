package com.arctos6135.stdplug.data;

import java.util.Map;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

public final class PIDVADPData extends ComplexData<PIDVADPData> {

    public final double kP;
    public final double kI;
    public final double kD;
    public final double kV;
    public final double kA;
    public final double kDP;

    public PIDVADPData(double kP, double kI, double kD, double kV, double kA, double kDP) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
        this.kDP = kDP;
    }

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
            "kP", this.kP,
            "kI", this.kI,
            "kD", this.kD,
            "kV", this.kV,
            "kA", this.kA,
            "kDP", this.kDP
        );
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof PIDVADPData) {
            PIDVADPData d = (PIDVADPData) other;
            if(d.kP == kP && d.kI == kI && d.kD == kD && d.kV == kV && d.kA == kA && d.kDP == kDP) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int i = 23;
        i = i * 31 + Double.valueOf(kP).hashCode();
        i = i * 31 + Double.valueOf(kI).hashCode();
        i = i * 31 + Double.valueOf(kD).hashCode();
        i = i * 31 + Double.valueOf(kV).hashCode();
        i = i * 31 + Double.valueOf(kA).hashCode();
        i = i * 31 + Double.valueOf(kDP).hashCode();
        return i;
    }
}
