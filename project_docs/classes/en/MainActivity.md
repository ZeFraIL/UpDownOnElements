# Class Description: MainActivity

________________________________________
## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** Activity (A component that provides a screen with which users can interact).
*   **Purpose:** This class is the heart of the application. It is responsible for managing the main screen and tracking the phone's physical tilt using a sensor called an **Accelerometer**. Depending on which way the phone is tilted, it increases or decreases a counter.
*   **Interaction:** It interacts with the Android system's `SensorManager` to get hardware data and updates the `activity_main.xml` layout to show the results to the user.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `tvNumber` | `TextView` | Displays the current counter value on the screen. | `onCreate`, `onSensorChanged` |
| `sensorManager` | `SensorManager` | A system service that gives access to all hardware sensors. | `onCreate`, `onDestroy` |
| `accelerometer` | `Sensor` | Represents the specific accelerometer sensor of the device. | `onCreate` |
| `number` | `int` | Stores the actual numeric value of the counter. | `onSensorChanged` |
| `SHAKE_THRESHOLD_POSITIVE` | `float` | The "trigger" value for forward tilt (constant: 2.5). | `onSensorChanged` |
| `SHAKE_THRESHOLD_NEGATIVE` | `float` | The "trigger" value for backward tilt (constant: -2.5). | `onSensorChanged` |
| `isCountingUp` | `boolean` | A flag to prevent the counter from jumping up too fast in one tilt. | `onSensorChanged` |
| `isCountingDown` | `boolean` | A flag to prevent the counter from jumping down too fast in one tilt. | `onSensorChanged` |

________________________________________
## 3. Classroom Methods

### Method Name: `onCreate`
*   **Type:** `protected`
*   **Return value:** `void` (returns nothing)
*   **Parameters:**
    *   `savedInstanceState` (`Bundle`): Contains saved data if the activity is recreated.
*   **What it does:**
    1. Sets the visual layout (`activity_main.xml`).
    2. Finds the text element (`tvNumber`) on the screen.
    3. Requests access to the phone's sensors from the Android system.
    4. Tries to find the Accelerometer. If it doesn't exist, it shows a warning (Toast).
    5. Starts "listening" for sensor movements.
*   **When called:** Automatically by the system when the app starts.
*   **What is important:** This is the setup stage. If the sensor is not initialized here, the app won't react to movements.

### Method Name: `onDestroy`
*   **Type:** `protected`
*   **Return value:** `void`
*   **What it does:** It tells the system to stop sending sensor updates to the app.
*   **When called:** When the user closes the app or the system kills it.
*   **What is important:** **Critical for battery saving.** If you don't unregister the listener, the phone will keep checking the sensor even after the app is closed, draining the battery.

### Method Name: `onSensorChanged`
*   **Type:** `public`
*   **Return value:** `void`
*   **Parameters:**
    *   `event` (`SensorEvent`): Contains the new sensor data (like tilt values).
*   **What it does:**
    1. Checks if the data is coming from the Accelerometer.
    2. Reads the `x` value (side-to-side tilt).
    3. If `x > 2.5` and we haven't counted yet, it adds +1 to the counter.
    4. If `x < -2.5` and we haven't counted yet, it subtracts -1 from the counter.
    5. If the phone returns to a flat position, it resets the "flags" so the user can tilt again.
*   **When called:** Every time the phone moves (many times per second).
*   **What is important:** This method needs to be very fast. If there is too much code here, the app will lag (stutter).

________________________________________
## 4. Lifecycle
*   **`onCreate()`**: Called first. We prepare everything here (connecting variables to UI, starting sensors).
*   **`onDestroy()`**: Called last. We clean up here (shutting down sensors to save power).

________________________________________
## 5. Interface Interaction (UI)
*   **Elements:** `TextView` (id: `tvNumber`).
*   **Connection:** Using `findViewById(R.id.tvNumber)`.
*   **Events:** It doesn't handle clicks. Instead, it handles **Sensor Events** through the `SensorEventListener` interface.

________________________________________
## 6. Interaction with other components
*   **Hardware:** Communicates directly with the Accelerometer hardware via `SensorManager`.
*   **Toast:** Uses the `Toast` class to show a pop-up message if the sensor is missing.

________________________________________
## 7. General Logic
The class acts as a bridge between the physical world and the digital screen.
1. The app starts and "listens" to the gravity/tilt.
2. When you tilt the phone, the hardware sends coordinates.
3. The class checks: "Is this tilt strong enough?".
4. If yes, it changes the number and tells the screen to show the new number.

________________________________________
## 8. Simplified Explanation
**"Explanation in simple words"**
Think of the phone as a **physical scale** or a **see-saw**.
*   Imagine a ball in the middle of the screen.
*   When you tilt the phone to the right, the ball hits a "Plus" button.
*   When you tilt it to the left, it hits a "Minus" button.
*   The `isCountingUp/Down` flags are like a "cooldown" – they make sure that one tilt only counts as one click, so the numbers don't spin like a crazy slot machine.

________________________________________
**Note on Code Quality:**
*   **Issue:** The thresholds `2.5f` are hardcoded. It would be better to make them adjustable settings.
*   **Improvement:** In modern Android, it is better to register/unregister sensors in `onStart`/`onStop` rather than `onCreate`/`onDestroy` to avoid using resources when the app is just in the background but not visible.
