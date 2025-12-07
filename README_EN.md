# Up Down On Elements Application

## Application Purpose

This application serves as a simple counter that is controlled by the device's movement. It utilizes the accelerometer sensor to detect tilting motions.

-   **Tilt Forward:** Tilting the device forward (positive tilt on the x-axis) will increment the number displayed on the screen.
-   **Tilt Backward:** Tilting the device backward (negative tilt on the x-axis) will decrement the number.

## MainActivity

`MainActivity` is the primary screen of the application.

-   It initializes the `SensorManager` and registers a listener for the accelerometer.
-   It displays a `TextView` that shows the current count.
-   The core logic resides in the `onSensorChanged` method, which monitors the accelerometer data (`event.values[0]` for the x-axis) to determine the direction of the tilt and updates the counter accordingly.
-   Flags (`isCountingUp`, `isCountingDown`) are used to ensure that the number is incremented or decremented only once per tilt gesture, preventing multiple changes from a single continuous tilt. The flags are reset when the device returns to a relatively neutral position.
