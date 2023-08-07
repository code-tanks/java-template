package api;

public class Command {
    static final int NONE = 0;
    static final int MOVE_FORWARD = 1 << 0;
    static final int MOVE_BACKWARD = 1 << 1;
    static final int ROTATE_TANK_CLOCKWISE = 1 << 2;
    static final int ROTATE_TANK_COUNTER_CLOCKWISE = 1 << 3;
    static final int FIRE = 1 << 4;
    static final int ROTATE_GUN_CLOCKWISE = 1 << 5;
    static final int ROTATE_GUN_COUNTER_CLOCKWISE = 1 << 6;
    static final int ROTATE_RADAR_CLOCKWISE = 1 << 7;
    static final int ROTATE_RADAR_COUNTER_CLOCKWISE = 1 << 8;
    static final int LOCK_GUN = 1 << 9;
    static final int UNLOCK_GUN = 1 << 10;
    static final int LOCK_RADAR = 1 << 11;
    static final int UNLOCK_RADAR = 1 << 12;
}
