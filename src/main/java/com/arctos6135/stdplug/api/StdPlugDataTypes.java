package com.arctos6135.stdplug.api;

/**
 * This class contains the names of all data types offered by StdPlug.
 * <p>
 * These names are rarely used directly, as usually classes in the
 * {@link com.arctos6135.stdplug.api.datatypes} package are already enough for
 * sending an object over NetworkTables.
 * </p>
 * <p>
 * The names here can be used to define custom
 * {@link edu.wpi.first.wpilibj.Sendable Sendable}s that can be sent over
 * NetworkTables to appear on Shuffleboard by passing them into the
 * {@link edu.wpi.first.wpilibj.smartdashboard.SendableBuilder#setSmartDashboardType(String)
 * SendableBuilder.setSmartDashboardType(String)} method in
 * {@link edu.wpi.first.wpilibj.Sendable#initSendable(edu.wpi.first.wpilibj.smartdashboard.SendableBuilder)
 * Sendable.initSendable(SendableBuilder)}. For example:
 * 
 * <pre>
 * &#64;Override
 * public void initSendable(SendableBuilder builder) {
 *     builder.setSmartDashboardType(StdPlugDataTypes.PIDVA_GAINS);
 * 
 *     builder.addDoubleProperty("kP", () -&gt; {
 *         return kP;
 *     }, (p) -&gt; {
 *         kP = p;
 *     });
 *     // And do this for every property...
 * }
 * </pre>
 * 
 * The properties for each data type can be found in its documentation.
 * </p>
 * <p>
 * For more information about a data type, see its documentation.
 * </p>
 * 
 * @author Tyler Tian
 * @since 0.1.0
 */
public final class StdPlugDataTypes {

    private StdPlugDataTypes() {
    }

    /**
     * Represents a set of PIDVA gains.
     * <p>
     * The {@link com.arctos6135.stdplug.api.datatypes.PIDVAGains PIDVAGains} class
     * can be used to send a set of PIDVA gains.
     * </p>
     * <p>
     * Properties:
     * <table>
     * <tr>
     * <th>Name</th>
     * <th>Type</th>
     * <th>Default Value</th>
     * <th>Notes</th>
     * </tr>
     * <tr>
     * <td>kP</td>
     * <td>Double</td>
     * <td>0.0</td>
     * <td>The proportional gain</td>
     * </tr>
     * <tr>
     * <td>kI</td>
     * <td>Double</td>
     * <td>0.0</td>
     * <td>The integral gain</td>
     * </tr>
     * <tr>
     * <td>kD</td>
     * <td>Double</td>
     * <td>0.0</td>
     * <td>The derivative gain</td>
     * </tr>
     * <tr>
     * <td>kV</td>
     * <td>Double</td>
     * <td>0.0</td>
     * <td>The velocity feedforward gain</td>
     * </tr>
     * <tr>
     * <td>kA</td>
     * <td>Double</td>
     * <td>0.0</td>
     * <td>The acceleration feedforward gain</td>
     * </tr>
     * </table>
     * </p>
     */
    public static final String PIDVA_GAINS = "PIDVA Gains";
}
