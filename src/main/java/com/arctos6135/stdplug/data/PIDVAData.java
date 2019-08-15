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
}
