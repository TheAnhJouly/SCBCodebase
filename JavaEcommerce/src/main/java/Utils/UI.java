package Utils;
public class UI {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String SET_PLAIN_TEXT = "\033[0;0m";
    public static final String SET_BOLD_TEXT = "\033[0;1m";

    public static final String SET_ITALIC_TEXT = "\033[0;3m";

    public static String bold(String str) {
        return (SET_BOLD_TEXT + str + SET_PLAIN_TEXT);
    }

    public static String italic(String str) {
        return (SET_ITALIC_TEXT + str + SET_PLAIN_TEXT);
    }

    public static String changeColor(Color color,String str){
        String setColor;
        switch (color){
            case RED :
                setColor = ANSI_RED;
                break;
            case BLACK:
                setColor = ANSI_BLACK;
                break;
            case GREEN:
                setColor = ANSI_GREEN;
                break;
            case YELLOW:
                setColor = ANSI_YELLOW;
                break;
            case BLUE:
                setColor = ANSI_BLUE;
                break;
            case PURPLE :
                setColor = ANSI_PURPLE;
                break;
            case CYAN :
                setColor = ANSI_CYAN;
                break;
            case WHITE :
                setColor = ANSI_WHITE;
                break;
            default:
                setColor = ANSI_RESET;
                break;
        }
        return (setColor + str + ANSI_RESET);
    }

    public static String errorString(String str){
        return UI.italic(UI.changeColor(Color.RED ,str));
    }

    public static String successString(String str){
        return UI.italic(UI.changeColor(Color.GREEN ,str));
    }

    public static String infoString(String str){
        return UI.italic(UI.changeColor(Color.BLUE, str));
    }

}
