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

public class Main {
    /**
     * Chess board instance.
     */
    private static Board chessBoard;
    /**
     * Constant lower bound for the size of board.
     */
    private final int lowerN = 3;
    /**
     * Constant upper bound for the size of board.
     */
    private final int upperN = 1000;
    /**
     * Constant lower bound for the number of pieces.
     */
    private final int lowerM = 2;
    /**
     * Constant lower bound for the coordinate of a piece.
     */
    private final int lowerCoord = 1;
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
        } finally {
            System.exit(0);
        }
    }

    private void openFile(String path, String opcode) {
        try {
            if (opcode.equals("w")) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            } else if (opcode.equals("r")) {
                scanner = new Scanner(new File(path));
            }
        } catch (FileNotFoundException exception) {
            return; // placeholder, we think that these files always exists
        }
    }

    private void writeFile(String text) {
        try {
            writer.write(text);
        } catch (IOException exception) {
            return; // placeholder, we think that we always can write
        }
    }

    private void closeFile() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            return; // placeholder, we think that we always can close file
        }
    }

    private int readN() throws InvalidBoardSizeException {
        int n;
        try {
            n = scanner.nextInt();
        } catch (NumberFormatException | InputMismatchException exception) {
            throw new InvalidBoardSizeException();
        }
        if (n < lowerN || n > upperN) {
            throw new InvalidBoardSizeException();
        }
        this.boardSize = n;
        return n;
    }

    private int readM() throws InvalidNumberOfPiecesException {
        int m;
        try {
            m = scanner.nextInt();
        } catch (NumberFormatException | InputMismatchException exception) {
            throw new InvalidNumberOfPiecesException();
        }
        if (m < lowerM || m > this.boardSize * this.boardSize) {
            throw new InvalidNumberOfPiecesException();
        }
        return m;
    }

    private ChessPiece readChessPiece() throws InvalidPieceNameException, InvalidNumberOfPiecesException,
            InvalidPieceColorException, InvalidPiecePositionException, InvalidGivenKingsException {
        if (!scanner.hasNext()) {
            throw new InvalidNumberOfPiecesException();
        }
        boolean toThrowName = false;
        boolean toThrowColor = false;
        boolean toThrowPosition = false;
        String pieceTypeString = "";
        String colorString = "";
        ChessPiece chessPiece = null;
        try {
            pieceTypeString = scanner.next();
        } catch (NoSuchElementException exception) {
            toThrowName = true;
        }
        try {
            colorString = scanner.next();
        } catch (NoSuchElementException exception) {
            toThrowColor = true;
        }
        int x = 0;
        int y = 0;
        try {
            x = scanner.nextInt();
            y = scanner.nextInt();
        } catch (NumberFormatException | NoSuchElementException exception) {
            toThrowPosition = true;
        }
        if (x < lowerCoord || x > this.boardSize) {
            toThrowPosition = true;
        }
        PieceColor color = PieceColor.parse(colorString);
        if (color == null) {
            toThrowColor = true;
        }
        PiecePosition position = new PiecePosition(x, y);
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
        if (toThrowName) {
            throw new InvalidPieceNameException();
        } else if (toThrowColor) {
            throw new InvalidPieceColorException();
        } else if (toThrowPosition) {
            throw new InvalidPiecePositionException();
        }
        if (this.numberOfBlackKings > 1 || this.numberOfWhiteKings > 1) {
            throw new InvalidGivenKingsException();
        }
        return chessPiece;
    }

    /**
     * Main method that reads the input, processes, and outputs the data.
     * @param args String array of program arguments.
     */
    public static void main(String[] args) {
        Main main = new Main();
        int n = 0;
        int m = 0;
        main.openFile("input.txt", "r");
        main.openFile("output.txt", "w");
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
        chessBoard = new Board(n);
        try {
            for (int i = 0; i < m; i++) {
                ChessPiece chessPiece = main.readChessPiece();
                chessBoard.addPiece(chessPiece);
            }
            if (main.numberOfBlackKings != 1 || main.numberOfWhiteKings != 1) {
                throw new InvalidGivenKingsException();
            }
            if (scanner.hasNext()) {
                throw new InvalidNumberOfPiecesException();
            }
        } catch (InvalidPieceNameException | InvalidPieceColorException | InvalidNumberOfPiecesException
                | InvalidPiecePositionException | InvalidGivenKingsException exception) {
            main.reportFatalError(exception.getMessage());
        }
        for (Map.Entry<String, ChessPiece> mapElement : chessBoard.getPiecePositions()) {
            ChessPiece chessPiece = mapElement.getValue();
            int piecePossibleMoves = chessBoard.getPiecePossibleMovesCount(chessPiece);
            int piecePossibleCaptures = chessBoard.getPiecePossibleCapturesCount(chessPiece);
            main.writeFile(piecePossibleMoves + " " + piecePossibleCaptures + "\n");
        }
        scanner.close();
        main.closeFile();
    }
}

class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}

class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece name";
    }
}

class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece color";
    }
}

class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece position";
    }
}

class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid given Kings";
    }
}

class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}

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
            return null;
        }
    }
}

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

    void setPosition(PiecePosition piecePosition) {
        this.position = piecePosition;
    }

    PiecePosition getPosition() {
        return this.position;
    }

    void setColor(PieceColor pieceColor) {
        this.color = pieceColor;
    }

    PieceColor getColor() {
        return this.color;
    }

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);
}

interface BishopMovement {
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

interface RookMovement {
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
    private int[][] knightMoves = {{-two, -one}, {-two, one}, {two, -one}, {two, one},
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
    private int[][] kingMoves = {{zero, one}, {one, one}, {one, zero}, {one, -one},
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

class Pawn extends ChessPiece {
    /**
     * Calculating captures count at moves to optimize.
     */
    private int capturesCount = 0;

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
                    this.capturesCount++;
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
                    this.capturesCount++;
                }
            }
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return this.capturesCount;
    }
}

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

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(PiecePosition position, PieceColor color) {
        super(position, color);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize)
            + getDiagonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize)
            + getDiagonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

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

class Board {
    /**
     * Map that stores pieces' position.
     * For example, you have a rook at (1, 2):
     * "1 2" -> Rook()
     */
    private Map<String, ChessPiece> positionsToPieces = new LinkedHashMap<String, ChessPiece>();
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
