package com.arctos6135.stdplug.api;

/**
 * This class contains the names of all the widgets offered by StdPlug.
 * <p>
 * For more information about a widget, see its documentation.
 * </p>
 * 
 * @author Tyler Tian
 * @since 0.1.0
 */
public final class StdPlugWidgets {

    /*
     * Note: When writing Javadoc requires linking to anything in any sub-package,
     * please be very careful and only use fully-qualified class names instead of
     * importing.
     * 
     * This is because the sub-packages of this package are not included in the
     * plugin jar as they're not needed. So if there is an import statement for a
     * class in a sub-package, a ClassNotFoundException will be thrown.
     */

    private StdPlugWidgets() {
    }

    /**
     * Displays an image.
     * <p>
     * The image to be displayed is specified by its absolute path.
     * </p>
     * <p>
     * Supported types:
     * <ul>
     * <li>String</li>
     * </ul>
     * Custom properties:
     * <table>
     * <tr>
     * <th>Name</th>
     * <th>Type</th>
     * <th>Default Value</th>
     * <th>Notes</th>
     * </tr>
     * <tr>
     * <td>Keep Aspect Ratio</td>
     * <td>Boolean</td>
     * <td>false</td>
     * <td>If set, the aspect ratio of the image will be kept</td>
     * </tr>
     * </table>
     * </p>
     * 
     * @since 0.1.0
     */
    public static final String IMAGE = "Image";

    /**
     * Displays a set of PIDVA gains.
     * <p>
     * Supported types:
     * <ul>
     * <li>{@link com.arctos6135.stdplug.api.datatypes.PIDVAGains PIDVAGains}</li>
     * </ul>
     * This widget has no custom properties.
     * </p>
     * 
     * @since 0.1.0
     * @see com.arctos6135.stdplug.api.datatypes.PIDVAGains PIDVAGains
     * @see StdPlugDataTypes#PIDVA_GAINS
     */
    public static final String PIDVA_GAINS = "PIDVA Gains";
}
