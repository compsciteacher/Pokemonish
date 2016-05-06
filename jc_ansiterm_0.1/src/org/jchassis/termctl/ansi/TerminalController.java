/***************************************************************************

    Copyright (C) 2003-2005 Sam Stainsby. All rights reserved.

    This file is part of the JChassis Project.

    JChassis is free software; you can redistribute it and/or
    modify it under the terms of version 2.1 of the GNU Lesser
    General Public License as published by the Free Software Foundation.

    JChassis is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to:

        Free Software Foundation, Inc.,
        59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

    At the time of writing, the license can also be found on the
    world-wide web at:

        http://www.fsf.org/licenses/lgpl.txt

    JChassis project management can be contacted via email sent to
    sjstainsby@yahoo.com.au.

***************************************************************************/

package org.jchassis.termctl.ansi;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import org.jchassis.event.DefaultEventCoordinator;
import org.jchassis.event.EventCoordinator;
import org.jchassis.event.EventSource;
import org.jchassis.geom.int2d.*;
import org.jchassis.termctl.*;

/**
 * A controller for an ANSI/VT100 text terminal or terminal emulator.
 * The terminal is controlled using stdin and stdout. Under GNU/Linux,
 * the use of raw I/O is requried for this to work: for example use
 * 'stty rawe -echo' before running the application, and use 'stty
 * sane' afterwards to return to normal I/O. Similar arragements may
 * work on other OSs but have not been attempted.
 *
 * <P><FONT SIZE=-1 COLOR="GRAY">
 * Copyright &copy; 2003-2005 Sam Stainsby.<BR>
 * Verbatim copying and distribution of this entire generated javadoc
 * document is permitted in any medium, provided this notice is preserved.
 * </FONT>
 */
public class TerminalController
    implements org.jchassis.termctl.TerminalController,
               StyledCharacterTerminal,
               ColoredCharacterTerminal,
               GraphicCharacterTerminal
{

    // useful ASCII characters

    private static final char ESC_CHAR = '\033';  // ASCII Escape character
    private static final char BELL_CHAR = '\007'; // ASCII Bell character

    // ANSI/VT100 control sequences

    private static final String ESC = "" + ESC_CHAR;     // Escape
    private static final String RIS = ESC + "c";  // Reset to Initial State
    private static final String CSI = ESC + "[";  // Command Seq'nce Intro
    private static final String CPR = CSI + "6n"; // CUrsor Position Report
    private static final String CUU_1 = CSI + "A"; // CUrsor Up 1 Position
    private static final String CUD_1 = CSI + "B"; // CUrsor Down 1 Position
    private static final String CUF_1 = CSI + "C"; // CUrsor Forward 1 Position
    private static final String CUB_1 = CSI + "D"; // CUrsor Back'd 1 Position
    private static final String CUU_S = "A"; // CUrsor Up Suffix
    private static final String CUD_S = "B"; // CUrsor Down Suffix
    private static final String CUF_S = "C"; // CUrsor Forward Suffix
    private static final String CUB_S = "D"; // CUrsor Backward Suffix
    private static final String ED_ALL = CSI + "2J"; // Erase in Display - All

    // CONTROL SEQUENCE PARAMETER SEPARATOR

    private static final char PARAM_SEPARATOR = ';'; // separate sq. params

    // CHARACTER ATTRIBUTE CODE PARTS

    private static final char CHAR_CLEAR = '0'; // clear all attributes
    private static final char CHAR_BOLD = '1'; // "bold" attribute
    private static final char CHAR_UNDERSCORE = '4'; // "underscore" attribute
    private static final char CHAR_BLINK = '5'; // "blink" attribute
    private static final char CHAR_REVERSE_VIDEO = '7'; // "reverse video" att.
    private static final char CHAR_END = 'm'; // end of the attribute sequence

    // COLOUR CODES

    private static final Hashtable foregroundColorCodes = new Hashtable();
    private static final Hashtable backgroundColorCodes = new Hashtable();
    static {

        foregroundColorCodes.put(CharacterColor.BLACK,   "30");
        foregroundColorCodes.put(CharacterColor.RED,     "31");
        foregroundColorCodes.put(CharacterColor.GREEN,   "32");
        foregroundColorCodes.put(CharacterColor.YELLOW,  "33");
        foregroundColorCodes.put(CharacterColor.BLUE,    "34");
        foregroundColorCodes.put(CharacterColor.MAGENTA, "35");
        foregroundColorCodes.put(CharacterColor.CYAN,    "36");
        foregroundColorCodes.put(CharacterColor.WHITE,   "37");

        backgroundColorCodes.put(CharacterColor.BLACK,   "40");
        backgroundColorCodes.put(CharacterColor.RED,     "41");
        backgroundColorCodes.put(CharacterColor.GREEN,   "42");
        backgroundColorCodes.put(CharacterColor.YELLOW,  "43");
        backgroundColorCodes.put(CharacterColor.BLUE,    "44");
        backgroundColorCodes.put(CharacterColor.MAGENTA, "45");
        backgroundColorCodes.put(CharacterColor.CYAN,    "46");
        backgroundColorCodes.put(CharacterColor.WHITE,   "47");
    }
    
    private static final Hashtable graphicCharacterCodes = new Hashtable();
    static {
        graphicCharacterCodes.put(GraphicCharacter.BLANK,              " ");
        graphicCharacterCodes.put(GraphicCharacter.LOWER_RIGHT_CORNER, "j");
        graphicCharacterCodes.put(GraphicCharacter.UPPER_RIGHT_CORNER, "k");
        graphicCharacterCodes.put(GraphicCharacter.UPPER_LEFT_CORNER,  "l");
        graphicCharacterCodes.put(GraphicCharacter.LOWER_LEFT_CORNER,  "m");
        graphicCharacterCodes.put(GraphicCharacter.CROSSING_LINES,     "n");
        graphicCharacterCodes.put(GraphicCharacter.MIDDLE_LINE,        "q");
        graphicCharacterCodes.put(GraphicCharacter.LEFT_T,             "t");
        graphicCharacterCodes.put(GraphicCharacter.RIGHT_T,            "u");
        graphicCharacterCodes.put(GraphicCharacter.BOTTOM_T,           "v");
        graphicCharacterCodes.put(GraphicCharacter.TOP_T,              "w");
        graphicCharacterCodes.put(GraphicCharacter.VERTICAL_LINE,      "x");
    }
    
    /** used to convert stdin to keystrokes */
    private final KeycodeComposer inputComposer = new KeycodeComposer();

    /** a buffered sequence of characters to send to the terminal */
    private StringBuffer sequence = new StringBuffer();

    /** the BOLD character attribute */
    private boolean isBold = false;

    /** the UNDERSCORE character attribute */
    private boolean isUnderscore = false;

    /** the BLINK character attribute */
    private boolean isBlink = false;

    /** the REVERSE VIDEO character attribute */
    private boolean isReverseVideo = false;
    
    /** the cached code for the foreground color */
    private String foregroundColorCode;
    
    /** the cached code for the background color */
    private String backgroundColorCode;
    
    /** the source for keyboard events */
    private final EventCoordinator eventCoordinator
        = new DefaultEventCoordinator();

    /** the keyboard event dispatching thread */
    private Thread inputThread = null;

    /** flag to tell the input thread to stop as soon as possible */
    private boolean stoppingInput = false;

    /** flag for whether are waiting for the terminal to reply */
    private boolean expectingReply = false;

    /** where to place output */
    private final OutputStream out;

    /**
     * Creates a new ANSI/VT100 terminal controller.
     */
    public TerminalController() {
        this.out = System.out;
    }

    // javadoc inherited
    public void init() throws TerminalIoException {
        stopInput();
        clearCharacterColors();
        clearCharacterStyles();
        setGraphicMode(false);
        // this sets the font set to ASCII
        // TODO - make a constant for this
        putCharacters("\033(B");
        eraseScreen();
        home();
    }

    // javadoc inherited
    public void reset() throws TerminalIoException {
        stopInput();
        putCharacters(RIS);
        init();
    }

    // javadoc inherited
    public void moveCursor(Direction direction) throws TerminalIoException {

        if (direction == Direction.RIGHT) {
            putCharacters(CUF_1);
        } else if (direction == Direction.LEFT) {
            putCharacters(CUB_1);
        } else if (direction == Direction.UP) {
            putCharacters(CUU_1);
        } else if (direction == Direction.DOWN) {
            putCharacters(CUD_1);
        }
        // ignore unknown direction values
    }

    // javadoc inherited
    public void moveCursor(Direction direction, int count)
        throws TerminalIoException {

        if (count == 0) {
            return;
        }

        if (count < 0) {
            moveCursor(direction, -count);
            return;
        }

        appendToSequence(CSI);
        appendToSequence(Integer.toString(count));

        final String suffix;

        if (direction == Direction.RIGHT) {
            suffix = CUF_S;
        } else if (direction == Direction.LEFT) {
            suffix = CUB_S;
        } else if (direction == Direction.UP) {
            suffix = CUU_S;
        } else if (direction == Direction.DOWN) {
            suffix = CUD_S;
        } else {
            return; // ignore unknown direction values
        }

        appendToSequence(suffix);

        sendSequence();
    }

    // javadoc inherited
    public void putCharacter(char ch) throws TerminalIoException {
        try {
            this.out.write(ch);
            flush();
        } catch (IOException ioe) {
            throw new TerminalIoException(ioe.getMessage());
        }
    }

    // javadoc inherited
    public void putCharacters(String sequence) throws TerminalIoException {
        try {
            this.out.write(sequence.getBytes("US-ASCII"));
            flush();
        } catch (IOException ioe) {
            throw new TerminalIoException(ioe.getMessage());
        }
    }

    private void flush() throws TerminalIoException {
        try {
            this.out.flush();
        } catch (IOException ioe) {
            throw new TerminalIoException(ioe.getMessage());
        }
    }

    // javadoc inherited
    public Point getCursorPosition() throws TerminalIoException {

        int row = -1;
        int col = -1;

        synchronized(this) {

            this.expectingReply = true;

            putCharacters(CPR);

            // answer should be <ESC> '[' <row> ';' <col> 'R'

            try {

                int ch;

                // first two chars should be ESC and '['

                ch = getReplyChar();
                while (ch != ESC_CHAR) {
                    // TODO - BUFFER other characters instead of
                    // DISCARDING them
                    ch = getReplyChar();
                }

                ch = getReplyChar();
                if (ch != '[') {
                    throw new
                        TerminalIoException("illegal cursor position report");
                }

                boolean readingRow = true;
                int rowOrCol = 0;
                while (true) {

                    ch = getReplyChar();
                    int digit = ch - '0';

                    if (readingRow && (ch == PARAM_SEPARATOR)) {
                        readingRow = false;
                        row = rowOrCol;
                        rowOrCol = 0;
                    } else if (!readingRow && (ch == 'R')) {
                        col = rowOrCol;
                        break;
                    } else if ((digit >= 0) && (digit <= 9)) {
                        rowOrCol = rowOrCol*10 + digit;
                    } else {
                        throw new
                            TerminalIoException("illegal cursor position "
                                                + "report");
                    }
                }
            } catch (IOException ioe) {
                throw new TerminalIoException("failed to get cursor report"
                                              + ioe.getMessage());
            }

            if ((row == -1) || (col == -1)) {
                throw new TerminalIoException("failed to get cursor report");
            }

            this.expectingReply = false;
            notifyAll();
        }

        return new ConstantPoint(col - 1, row - 1);
    }

    // javadoc inherited
    public void setCursorPosition(Point position)
        throws TerminalIoException {

        String sequence =
            CSI + Integer.toString(position.getY() + 1) + ";"
            + Integer.toString(position.getX() + 1) + 'H';

        putCharacters(sequence);
    }

    // javadoc inherited
    public void home() throws TerminalIoException {
        setCursorPosition(Point.ZERO);
    }

    // javadoc inherited
    public void beep() throws TerminalIoException {
        putCharacter(BELL_CHAR);
    }

    // javadoc inherited
    public void eraseScreen() throws TerminalIoException {
        putCharacters(ED_ALL);
    }

    private static final Point MAX_SIZE = new ConstantPoint(999, 999);

    // javadoc inherited
    public Dimension getScreenSize() throws TerminalIoException {

        Point oldPosition = getCursorPosition();
        setCursorPosition(MAX_SIZE);
        Point position = getCursorPosition();
        setCursorPosition(oldPosition);

        return new ConstantDimension(position.getX() + 1,
                                     position.getY() + 1);
    }

    // javadoc inherited
    public void setInputEnabled(boolean on) {
        if (on) {
            startInput();
        } else {
            stopInput();
        }
    }

    // javadoc inherited
    public EventSource getEventSource() {
        return this.eventCoordinator;
    }


    // METHODS TO IMPLEMENT INTERFACE: StyledCharacterTerminal


    // javadoc inherited
    public void setBold(boolean on) throws TerminalIoException {

        if (on != this.isBold) {
            this.isBold = on;
            updateStyleAttributes();
        }
    }

    // javadoc inherited
    public void setUnderscore(boolean on) throws TerminalIoException {

        if (on != this.isUnderscore) {
            this.isUnderscore = on;
            updateStyleAttributes();
        }
    }

    // javadoc inherited
    public void setBlink(boolean on) throws TerminalIoException {

        if (on != this.isBlink) {
            this.isBlink = on;
            updateStyleAttributes();
        }
    }

    // javadoc inherited
    public void setReverseVideo(boolean on) throws TerminalIoException {

        if (on != this.isReverseVideo) {
            this.isReverseVideo = on;
            updateStyleAttributes();
        }
    }

    // javadoc inherited
    public void clearCharacterStyles() throws TerminalIoException {

        this.isBold = false;
        this.isUnderscore = false;
        this.isBlink = false;
        this.isReverseVideo = false;

        updateStyleAttributes();
    }


    // METHODS TO IMPLEMENT INTERFACE: ColoredCharacterTerminal


    // javadoc inherited
    public void setForegroundColor(CharacterColor color)
        throws TerminalIoException {

        appendToSequence(CSI);
        String code = codeForColor(color, true);
        this.foregroundColorCode = code;
        appendToSequence(code);
        appendToSequence('m');

        sendSequence();
    }

    // javadoc inherited
    public void setBackgroundColor(CharacterColor color)
        throws TerminalIoException {

        appendToSequence(CSI);
        String code = codeForColor(color, false);
        this.backgroundColorCode = code;
        appendToSequence(code);
        appendToSequence('m');

        sendSequence();
    }

    // javadoc inherited
    public void clearCharacterColors() throws TerminalIoException {
        setForegroundColor(CharacterColor.WHITE);
        setBackgroundColor(CharacterColor.BLACK);
    }

    private char getReplyChar() throws IOException {
        return (char) System.in.read();
    }
    
    
    // METHODS TO IMPLEMENT INTERFACE: GraphicCharacterTerminal
    
    
    public void setGraphicMode(boolean on) throws TerminalIoException {
        appendToSequence(ESC_CHAR);
        appendToSequence('(');
        appendToSequence(on ? '0' : 'B');
        sendSequence();
    }
    
    public void putGraphicCharacter(GraphicCharacter graphic)
            throws TerminalIoException {
        appendToSequence((String) this.graphicCharacterCodes.get(graphic));
        sendSequence();
    }
    
    
    // OTHER METHODS
    
    
    /**
     * Gets a character from the terminal.
     *
     * @return the character retrieved from the terminal
     */
    protected synchronized char getChar() throws IOException {

        while (true) {
            int available = System.in.available();
            if (available > 0) {
                break;
            } else {
                try {
                    wait(200);
                } catch (InterruptedException ie) {
                    // do nothing
                }
            }
        }

        return (char) System.in.read();
    }

    /**
     * Appends the specified characters to output buffer.
     *
     * @param chars the characters to append
     */
    protected void appendToSequence(String chars) {
        sequence.append(chars);
    }

    /**
     * Appends the specified character to the output buffer.
     *
     * @param ch the character to append
     */
    protected void appendToSequence(char ch) {
        sequence.append(ch);
    }

    /**
     * Sends the characters in the output buffer to the terminal.
     */
    protected void sendSequence() throws TerminalIoException {
        putCharacters(this.sequence.toString());
        this.sequence.setLength(0);
    }

    /**
     * Updates the terminal with all cached style attributes.
     */
    protected void updateStyleAttributes() throws TerminalIoException {

        appendToSequence(CSI);
        
        // reset to a known state
        
        appendToSequence(CHAR_CLEAR);
        
        // BUT we have to put back the foreground & background colours
        
        appendParam(this.foregroundColorCode);
        appendParam(this.backgroundColorCode);

        if (this.isBold) {
            appendParam(CHAR_BOLD);
        }

        if (this.isUnderscore) {
            appendParam(CHAR_UNDERSCORE);
        }

        if (this.isBlink) {
            appendParam(CHAR_BLINK);
        }

        if (this.isReverseVideo) {
            appendParam(CHAR_REVERSE_VIDEO);
        }

        appendToSequence('m');

        sendSequence();
    }
    
    private void appendParam(char param) {
        appendToSequence(PARAM_SEPARATOR);
        appendToSequence(param);
    }
    
    private void appendParam(String param) {
        appendToSequence(PARAM_SEPARATOR);
        appendToSequence(param);
    }

    /**
     * Gets the ANSI code for setting a character colour.
     * This is the number that is placed into an excape sequence to
     * change the forground or background colour. For example, the 
     * code for red is "31".
     *
     * @param color the colour to set
     * @param isForeground if true, this is a foreground colour; otherwise
     *     this is a background colour
     *
     * @return the characters that form the colour code
     */
    protected String codeForColor(CharacterColor color,
                                  boolean isForeground) {
        return (isForeground ?
                (String) foregroundColorCodes.get(color) :
                (String) backgroundColorCodes.get(color));
    }

    /**
     * Creates the handler for keyboard input.
     * By default, the hanlder simply calls doHandlerLoop until
     * it returns true;
     *
     * @return the Runnable instance to pass to the input thread
     */
    protected Runnable createInputHandler() {

        return new Runnable() {

            public void run() {

                boolean exitLoop = false;
                while(!exitLoop) {
                    exitLoop = doHandlerLoop();
                }
            }
        };
    }

    /**
     * Perform one loop of the input handler.
     *
     * @return true if the input loop should terminate; false otherwise
     */
    protected synchronized boolean doHandlerLoop() {

        if (this.stoppingInput) {
            this.stoppingInput = false;
            this.inputThread = null;
            return true;
        }

        while (this.expectingReply) {
            try {
                wait();
            } catch (InterruptedException ie) {
                return false;
            }
        }

        try {

            int keycode = getChar();
            Keystroke keystroke = this.inputComposer.composeKeycode(keycode);

            if (keystroke != null) {
                this.eventCoordinator
                    .notifyEventListeners(KEYBOARD_INPUT_EVENT_ROLE,
                                          keystroke);
            }

        } catch (IOException ioe) {
            // TODO
            ioe.printStackTrace();
            return true;
        }

        return false;
    }
    
    /**
     * Starts receiving keyboard input.
     * The generation of events generated by any keyboard input will be
     * enabled.
     */
    private synchronized void startInput() {
        this.stoppingInput = false;
        if (this.inputThread == null) {
            this.inputThread = new Thread(createInputHandler(),
                                          "ANSI Terminal Input");
            this.inputThread.start();
        }
    }

    /**
     * Stops receiving keyboard input.
     * The generation of events generated by any keyboard input will be
     * disabled.
     * 
     */
    private synchronized void stopInput() {
        if (this.inputThread != null) {
            this.stoppingInput = true;
            this.inputThread.interrupt();
        }
    }

}
