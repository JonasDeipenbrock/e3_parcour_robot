package wrappers;

public enum LEDPattern {
    LED_BLACK(0),
    LED_GREEN(1),
    LED_RED(2),
    LED_ORANGE(3),
    LED_GREEN_FLASH(4),
    LED_RED_FLASH(5),
    LED_ORANGE_FLASH(6),
    LED_GREEN_PULSE(7),
    LED_RED_PULSE(8),
    LED_ORANGE_PULSE(9);

    private final int value;

    LEDPattern(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}