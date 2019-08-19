package com.arctos6135.stdplug.data;

import java.util.Map;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

public final class PIDVAData extends ComplexData<PIDVAData> {

    private final double kP;
    private final double kI;
    private final double kD;
    private final double kV;
    private final double kA;

    public PIDVAData(double kP, double kI, double kD, double kV, double kA) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
    }

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
            "kP", this.kP,
            "kI", this.kI,
            "kD", this.kD,
            "kV", this.kV,
            "kA", this.kA
        );
    }
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof PIDVAData) {
            PIDVAData d = (PIDVAData) other;
            if(d.kP == kP && d.kI == kI && d.kD == kD && d.kV == kV && d.kA == kA) {
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
        return i;
    }
}
