package com.arctos6135.stdplug.datatypes;

import java.util.Map;
import java.util.function.Function;

import com.arctos6135.stdplug.api.StdPlugDataTypes;
import com.arctos6135.stdplug.data.PIDVADPData;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

public final class PIDVADPDataType extends ComplexDataType<PIDVADPData> {
    
    public static final PIDVADPDataType Instance = new PIDVADPDataType();

    public PIDVADPDataType() {
        super(StdPlugDataTypes.PIDVADP_GAINS, PIDVADPData.class);
    }

    @Override
    public Function<Map<String, Object>, PIDVADPData> fromMap() {
        return map -> {
            return new PIDVADPData(
                (double) map.getOrDefault("kP", 0.0),
                (double) map.getOrDefault("kI", 0.0),
                (double) map.getOrDefault("kD", 0.0),
                (double) map.getOrDefault("kV", 0.0),
                (double) map.getOrDefault("kA", 0.0),
                (double) map.getOrDefault("kDP", 0.0)
            );
        };
    }

    @Override
	public PIDVADPData getDefaultValue() {
		return new PIDVADPData(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	}
}
