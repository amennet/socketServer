package socketServer.bean.enums;

/**
 * Created by ZhangGe on 2016/6/20.
 */
public enum Command {
    A0((byte) (0xA0 & 0xFF)),
    A1((byte) (0xA1 & 0xFF)),
    A2((byte) (0xA2 & 0xFF)),
    A3((byte) (0xA3 & 0xFF)),
    A4((byte) (0xA4 & 0xFF)),
    B1((byte) (0xB1 & 0xFF)),
    B2((byte) (0xB2 & 0xFF)),
    B3((byte) (0xB3 & 0xFF)),
    C0((byte) (0xC0 & 0xFF)),
    C1((byte) (0xC1 & 0xFF)),
    C2((byte) (0xC2 & 0xFF)),
    BF((byte) (0xBF & 0xFF)),
    EE((byte) (0xEE & 0xFF)),
    EF((byte) (0xEF & 0xFF)),
    FE((byte) (0xFE & 0xFF)),
    FF((byte) (0xFF & 0xFF));

    private byte command;

    Command(byte c) {
        command = c;
    }

    public byte getByte() {
        return command;
    }

    public static Command getCommand(String command) {
        for(Command c : Command.values()) {
            if (c.name().equals(command)) {
                return c;
            }
        }
        return null;
    }
}
