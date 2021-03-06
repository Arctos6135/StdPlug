package com.arctos6135.stdplug.datatypes;

import java.util.Map;
import java.util.function.Function;

import com.arctos6135.stdplug.api.StdPlugDataTypes;
import com.arctos6135.stdplug.data.PIDVAData;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

public final class PIDVADataType extends ComplexDataType<PIDVAData> {

    public static final PIDVADataType Instance = new PIDVADataType();

    public PIDVADataType() {
        super(StdPlugDataTypes.PIDVA_GAINS, PIDVAData.class);
    }

    @Override
    public Function<Map<String, Object>, PIDVAData> fromMap() {
        return map -> {
            return new PIDVAData(
                (double) map.getOrDefault("kP", 0.0),
                (double) map.getOrDefault("kI", 0.0),
                (double) map.getOrDefault("kD", 0.0),
                (double) map.getOrDefault("kV", 0.0),
                (double) map.getOrDefault("kA", 0.0)
            );
        };
    }

    @Override
	public PIDVAData getDefaultValue() {
		return new PIDVAData(0, 0, 0, 0, 0);
	}
}
