// import packages

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class for the solution.
 */
public class Main {
    /**
     * Chess board static instance.
     */
    private static Board chessBoard;
    /**
     * Board size for upper bounds.
     */
    private int boardSize = 0;
    /**
     * Number of white kings on board.
     */
    private int numberOfWhiteKings = 0;
    /**
     * Number of black kings on board.
     */
    private int numberOfBlackKings = 0;
    /**
     * Static scanner instance.
     */
    private static Scanner scanner = null;
    /**
     * Static writer instance.
     */
    private static Writer writer = null;

    /**
     * Method to report an error to file and exit the program.
     *
     * @param err Error string
     */
    private void reportFatalError(String err) {
        try {
            if (writer != null) {
                writer.write(err + "\n");
                writer.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        } catch (IOException ex) {
            // we think that these files always exists
        }
        System.exit(0);
    }

    /**
     * Method to open files to read/write.
     *
     * @param path   Path to the file
     * @param opcode "w" to open file to write or "r" to open file to read
     */
    private void openFile(String path, String opcode) {
        try {
            if (opcode.equals("w")) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            } else if (opcode.equals("r")) {
                scanner = new Scanner(new File(path));
            }
        } catch (FileNotFoundException exception) {
            // we think that these files always exists
        }
    }

    /**
     * Method to write string to file.
     *
     * @param text Text that we want to write
     */
    private void writeFile(String text) {
        try {
            writer.write(text);
        } catch (IOException exception) {
            // we think that we always can write
        }
    }

    /**
     * Method to close the file.
     */
    private void closeFile() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            // we think that we always can close file
        }
    }

    /**
     * Method to read the board size with additional checks.
     *
     * @return value n, that we have read
     */
    private int readN() throws InvalidBoardSizeException {
        int n;
        try {
            n = scanner.nextInt();
        } catch (NumberFormatException | InputMismatchException exception) {
            throw new InvalidBoardSizeException();
        }
        // Constant lower bound for the size of board.
        final int lowerN = 3;
        // Constant upper bound for the size of board.
        final int upperN = 1000;
        if (n < lowerN || n > upperN) {
            throw new InvalidBoardSizeException();
        }
        this.boardSize = n;
        return n;
    }

    /**
     * Method to read the number of pieces with additional checks.
     *
     * @return value m, that we have read
     */
    private int readM() throws InvalidNumberOfPiecesException {
        int m;
        try {
            m = scanner.nextInt();
        } catch (NumberFormatException | InputMismatchException exception) {
            throw new InvalidNumberOfPiecesException();
        }
        // Constant lower bound for the number of pieces.
        final int lowerM = 2;
        if (m < lowerM || m > this.boardSize * this.boardSize) {
            throw new InvalidNumberOfPiecesException();
        }
        return m;
    }

    /**
     * Method to read a chess piece with additional checks.
     *
     * @return a chesspiece object
     */
    private ChessPiece readChessPiece() throws InvalidPieceNameException, InvalidNumberOfPiecesException,
            InvalidPieceColorException, InvalidPiecePositionException, InvalidGivenKingsException {
        // if nothing to read, then the number of pieces is incorrect
        if (!scanner.hasNext()) {
            throw new InvalidNumberOfPiecesException();
        }
        // Remember if we need to throw exceptions
        // This can work, cause values can be default values,
        // Until we throw the exceptions
        // This is needed to throw exceptions in correct order
        boolean toThrowName = false;
        boolean toThrowColor = false;
        boolean toThrowPosition = false;
        String pieceTypeString = "";
        String colorString = "";
        ChessPiece chessPiece = null;
        // read piece name
        try {
            pieceTypeString = scanner.next();
        } catch (NoSuchElementException exception) {
            toThrowName = true;
        }
        // read piece color
        try {
            colorString = scanner.next();
        } catch (NoSuchElementException exception) {
            toThrowColor = true;
        }
        // read piece coordinates
        int x = 0;
        int y = 0;
        try {
            x = scanner.nextInt();
            y = scanner.nextInt();
        } catch (NumberFormatException | NoSuchElementException exception) {
            toThrowPosition = true;
        }
        // Constant lower bound for the coordinate of a piece.
        int lowerCoord = 1;
        if (x < lowerCoord || x > this.boardSize) {
            toThrowPosition = true;
        }
        // parse piece color
        PieceColor color = PieceColor.parse(colorString);
        if (color == null) {
            toThrowColor = true;
        }
        // parse piece coordinates
        PiecePosition position = new PiecePosition(x, y);
        // parse piece name and create objects
        switch (pieceTypeString) {
            case "Pawn":
                chessPiece = new Pawn(position, color);
                break;
            case "King":
                if (color == PieceColor.BLACK) {
                    this.numberOfBlackKings++;
                } else {
                    this.numberOfWhiteKings++;
                }
                chessPiece = new King(position, color);
                break;
            case "Knight":
                chessPiece = new Knight(position, color);
                break;
            case "Rook":
                chessPiece = new Rook(position, color);
                break;
            case "Queen":
                chessPiece = new Queen(position, color);
                break;
            case "Bishop":
                chessPiece = new Bishop(position, color);
                break;
            default:
                toThrowName = true;
        }
        // throw exceptions in correct order
        if (toThrowName) {
            throw new InvalidPieceNameException();
        } else if (toThrowColor) {
            throw new InvalidPieceColorException();
        } else if (toThrowPosition) {
            throw new InvalidPiecePositionException();
        }
        // if one of the teams has more than one king, then throw error
        if (this.numberOfBlackKings > 1 || this.numberOfWhiteKings > 1) {
            throw new InvalidGivenKingsException();
        }
        return chessPiece;
    }

    /**
     * Main method that reads the input, processes, and outputs the data.
     *
     * @param args String array of program arguments.
     */
    public static void main(String[] args) {
        Main main = new Main();
        int n = 0;
        int m = 0;
        // open input and output
        main.openFile("input.txt", "r");
        main.openFile("output.txt", "w");
        // read n and m
        try {
            n = main.readN();
        } catch (InvalidBoardSizeException exception) {
            main.reportFatalError(exception.getMessage());
        }
        try {
            m = main.readM();
        } catch (InvalidNumberOfPiecesException exception) {
            main.reportFatalError(exception.getMessage());
        }
        // read pieces
        chessBoard = new Board(n);
        try {
            for (int i = 0; i < m; i++) {
                ChessPiece chessPiece = main.readChessPiece();
                chessBoard.addPiece(chessPiece);
            }
            if (main.numberOfBlackKings != 1 || main.numberOfWhiteKings != 1) {
                throw new InvalidGivenKingsException();
            }
            // if after we finished reading we have more data, then our number of pieces is incorrect
            if (scanner.hasNext()) {
                throw new InvalidNumberOfPiecesException();
            }
        } catch (InvalidPieceNameException | InvalidPieceColorException | InvalidNumberOfPiecesException
                 | InvalidPiecePositionException | InvalidGivenKingsException exception) {
            main.reportFatalError(exception.getMessage());
        }
        // calculate moves and captures for each piece in order of appearing (with use of LinkedHashMap)
        for (Map.Entry<String, ChessPiece> mapElement : chessBoard.getPiecePositions()) {
            ChessPiece chessPiece = mapElement.getValue();
            int piecePossibleMoves = chessBoard.getPiecePossibleMovesCount(chessPiece);
            int piecePossibleCaptures = chessBoard.getPiecePossibleCapturesCount(chessPiece);
            main.writeFile(piecePossibleMoves + " " + piecePossibleCaptures + "\n");
        }
        // close files
        scanner.close();
        main.closeFile();
    }
}

/**
 * Exception to throw if we have an invalid board size.
 */
class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}

/**
 * Exception to throw if we have an invalid number of pieces.
 */
class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

/**
 * Exception to throw if we have an invalid piece name.
 */
class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece name";
    }
}

/**
 * Exception to throw if we have an invalid piece color.
 */
class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece color";
    }
}

/**
 * Exception to throw if we have an invalid piece position.
 */
class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece position";
    }
}

/**
 * Exception to throw if we have an invalid amount of kings.
 */
class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid given Kings";
    }
}

/**
 * Exception to throw if we have an invalid input.
 */
class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}

/**
 * Enumeration for piece color.
 */
enum PieceColor {
    /**
     * White piece.
     */
    WHITE,
    /**
     * Black piece.
     */
    BLACK;

    public static PieceColor parse(String color) {
        if (color.equals("Black")) {
            return BLACK;
        } else if (color.equals("White")) {
            return WHITE;
        } else {
            return null; // don't throw exception here, to throw them in correct order
        }
    }
}

/**
 * An abstract class for a chess piece.
 */
abstract class ChessPiece {
    /**
     * Coordinates of a piece.
     */
    protected PiecePosition position;
    /**
     * Color of a piece.
     */
    protected PieceColor color;

    ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    /**
     * Method to set position of a piece.
     *
     * @param piecePosition Position of a piece
     */
    void setPosition(PiecePosition piecePosition) {
        this.position = piecePosition;
    }

    /**
     * Method to get position of a piece.
     *
     * @return Position of a piece.
     */
    PiecePosition getPosition() {
        return this.position;
    }

    /**
     * Method to set color of a piece.
     *
     * @param pieceColor Color of a piece
     */
    void setColor(PieceColor pieceColor) {
        this.color = pieceColor;
    }

    /**
     * Method to get color of a piece.
     *
     * @return Color of a piece.
     */
    PieceColor getColor() {
        return this.color;
    }

    /**
     * Method to get amount of moves that a piece can do.
     *
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of moves that a piece can do
     */
    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    /**
     * Method to get amount of captures that a piece can do.
     *
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of captures that a piece can do
     */
    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
}

/**
 * Interface for diagonal moves.
 */
interface BishopMovement {
    /**
     * Method to get amount of diagonal moves that a piece can do.
     *
     * @param position  position of a piece
     * @param color     color of a piece
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of diagonal moves
     */
    default int getDiagonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                      int boardSize) {
        int count = 0;
        int newX = 0;
        int newY = 0;
        int[] mult = {-1, 1};
        for (int multI : mult) {
            for (int multJ : mult) {
                for (int i = 1; i <= boardSize; i++) {
                    newX = position.getX() + multI * i;
                    newY = position.getY() + multJ * i;
                    if (1 <= newX && newX <= boardSize && 1 <= newY && newY <= boardSize) {
                        if (!positions.containsKey(newX + " " + newY)) {
                            count++;
                        } else if (positions.get(newX + " " + newY).getColor() != color) {
                            count++;
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Method to get amount of diagonal captures that a piece can do.
     *
     * @param position  position of a piece
     * @param color     color of a piece
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of diagonal captures
     */
    default int getDiagonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                         int boardSize) {
        int count = 0;
        int newX = 0;
        int newY = 0;
        int[] mult = {-1, 1};
        for (int multI : mult) {
            for (int multJ : mult) {
                for (int i = 1; i <= boardSize; i++) {
                    newX = position.getX() + multI * i;
                    newY = position.getY() + multJ * i;
                    if (1 <= newX && newX <= boardSize && 1 <= newY && newY <= boardSize) {
                        if (!positions.containsKey(newX + " " + newY)) {
                            continue;
                        } else if (positions.get(newX + " " + newY).getColor() != color) {
                            count++;
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return count;
    }
}

/**
 * Interface for orthogonal moves.
 */
interface RookMovement {
    /**
     * Method to get amount of orthogonal moves that a piece can do.
     *
     * @param position  position of a piece
     * @param color     color of a piece
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of orthogonal moves
     */
    default int getOrthogonalMovesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                        int boardSize) {
        int count = 0;
        for (int i = position.getX() - 1; i >= 1; i--) {
            if (!positions.containsKey(i + " " + position.getY())) {
                count++;
            } else if (positions.get(i + " " + position.getY()).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int i = position.getX() + 1; i <= boardSize; i++) {
            if (!positions.containsKey(i + " " + position.getY())) {
                count++;
            } else if (positions.get(i + " " + position.getY()).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int j = position.getY() - 1; j >= 1; j--) {
            if (!positions.containsKey(position.getX() + " " + j)) {
                count++;
            } else if (positions.get(position.getX() + " " + j).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int j = position.getY() + 1; j <= boardSize; j++) {
            if (!positions.containsKey(position.getX() + " " + j)) {
                count++;
            } else if (positions.get(position.getX() + " " + j).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Method to get amount of orthogonal captures that a piece can do.
     *
     * @param position  position of a piece
     * @param color     color of a piece
     * @param positions map of positions of pieces
     * @param boardSize board size
     * @return amount of orthogonal captures
     */
    default int getOrthogonalCapturesCount(PiecePosition position, PieceColor color, Map<String, ChessPiece> positions,
                                           int boardSize) {
        int count = 0;
        for (int i = position.getX() - 1; i >= 1; i--) {
            if (!positions.containsKey(i + " " + position.getY())) {
                continue;
            } else if (positions.get(i + " " + position.getY()).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int i = position.getX() + 1; i <= boardSize; i++) {
            if (!positions.containsKey(i + " " + position.getY())) {
                continue;
            } else if (positions.get(i + " " + position.getY()).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int j = position.getY() - 1; j >= 1; j--) {
            if (!positions.containsKey(position.getX() + " " + j)) {
                continue;
            } else if (positions.get(position.getX() + " " + j).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        for (int j = position.getY() + 1; j <= boardSize; j++) {
            if (!positions.containsKey(position.getX() + " " + j)) {
                continue;
            } else if (positions.get(position.getX() + " " + j).getColor() != color) {
                count++;
                break;
            } else {
                break;
            }
        }
        return count;
    }
}

/**
 * Knight piece class.
 */
class Knight extends ChessPiece {
    /**
     * Dealing with magic numbers.
     */
    private final int one = 1;
    /**
     * Dealing with magic numbers.
     */
    private final int two = 2;
    /**
     * Knight moves array.
     */
    private final int[][] knightMoves = {{-two, -one}, {-two, one}, {two, -one}, {two, one},
            {-one, -two}, {-one, two}, {one, -two}, {one, two}};

    Knight(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int[] is : this.knightMoves) {
            int i = this.getPosition().getX() + is[0];
            int j = this.getPosition().getY() + is[1];
            if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
                boolean isEmpty = !positions.containsKey(i + " " + j);
                if (isEmpty) {
                    count++;
                } else {
                    PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                    if (newPieceColor != this.color) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int[] is : this.knightMoves) {
            int i = this.getPosition().getX() + is[0];
            int j = this.getPosition().getY() + is[1];
            if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
                boolean isEmpty = !positions.containsKey(i + " " + j);
                if (!isEmpty) {
                    PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                    if (newPieceColor != this.color) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

/**
 * King piece class.
 */
class King extends ChessPiece {
    /**
     * Dealing with magic numbers.
     */
    private final int one = 1;
    /**
     * Dealing with magic numbers.
     */
    private final int zero = 0;
    /**
     * Knight moves array.
     */
    private final int[][] kingMoves = {{zero, one}, {one, one}, {one, zero}, {one, -one},
            {zero, -one}, {-one, -one}, {-one, zero}, {-one, one}};

    King(PiecePosition position, PieceColor color) throws InvalidGivenKingsException {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int[] is : this.kingMoves) {
            int i = this.getPosition().getX() + is[0];
            int j = this.getPosition().getY() + is[1];
            if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
                boolean isEmpty = !positions.containsKey(i + " " + j);
                if (isEmpty) {
                    count++;
                } else {
                    PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                    if (newPieceColor != this.color) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int[] is : this.kingMoves) {
            int i = this.getPosition().getX() + is[0];
            int j = this.getPosition().getY() + is[1];
            if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
                boolean isEmpty = !positions.containsKey(i + " " + j);
                if (!isEmpty) {
                    PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                    if (newPieceColor != this.color) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

/**
 * Pawn piece class.
 */
class Pawn extends ChessPiece {
    Pawn(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        PiecePosition pos = this.getPosition();
        int i = pos.getX();
        int j = pos.getY();
        if (this.getColor() == PieceColor.WHITE) {
            j++;
        } else {
            j--;
        }
        if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
            boolean isEmpty = !positions.containsKey(i + " " + j);
            if (isEmpty) {
                count++;
            }
        }
        i--;
        if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
            boolean isEmpty = !positions.containsKey(i + " " + j);
            if (!isEmpty) {
                PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        i++;
        i++;
        if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
            boolean isEmpty = !positions.containsKey(i + " " + j);
            if (!isEmpty) {
                PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        PiecePosition pos = this.getPosition();
        int i = pos.getX();
        int j = pos.getY();
        if (this.getColor() == PieceColor.WHITE) {
            j++;
        } else {
            j--;
        }
        i--;
        if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
            boolean isEmpty = !positions.containsKey(i + " " + j);
            if (!isEmpty) {
                PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        i++;
        i++;
        if (1 <= i && i <= boardSize && 1 <= j && j <= boardSize) {
            boolean isEmpty = !positions.containsKey(i + " " + j);
            if (!isEmpty) {
                PieceColor newPieceColor = positions.get(i + " " + j).getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        return count;
    }
}

/**
 * Bishop piece class.
 */
class Bishop extends ChessPiece implements BishopMovement {
    Bishop(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

/**
 * Bishop piece class.
 */
class Rook extends ChessPiece implements RookMovement {
    Rook(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

/**
 * Bishop piece class.
 */
class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        // moves as rook and bishop combined
        return getOrthogonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize)
                + getDiagonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        // moves as rook and bishop combined
        return getOrthogonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize)
                + getDiagonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

/**
 * Piece position class.
 */
class PiecePosition {
    /**
     * X coordinate of a piece.
     */
    private int x;
    /**
     * Y coordinate of a piece.
     */
    private int y;

    PiecePosition(int onX, int onY) {
        this.x = onX;
        this.y = onY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y;
    }
}

/**
 * Chess board class.
 */
class Board {
    /**
     * Map that stores pieces' position.
     * For example, you have a rook at (1, 2):
     * "1 2" -> Rook()
     */
    private Map<String, ChessPiece> positionsToPieces = new LinkedHashMap<>();
    /**
     * Board size.
     */
    private int size;

    Board(int boardSize) {
        this.size = boardSize;
    }

    private boolean isEmpty(PiecePosition pos) {
        return !this.positionsToPieces.containsKey(pos.toString());
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(this.positionsToPieces, this.size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(this.positionsToPieces, this.size);
    }

    public void addPiece(ChessPiece piece) throws InvalidPiecePositionException {
        PiecePosition position = piece.getPosition();
        if (this.isEmpty(position)) {
            this.positionsToPieces.put(position.toString(), piece);
        } else {
            throw new InvalidPiecePositionException();
        }
    }

    public ChessPiece getPiece(PiecePosition position) {
        return this.positionsToPieces.get(position.toString());
    }

    public Set<Map.Entry<String, ChessPiece>> getPiecePositions() {
        return this.positionsToPieces.entrySet();
    }
}
