import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
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
     * Total number of kings on board.
     */
    private int numberOfKings = 0;
    /**
     * Static scanner instance.
     */
    private static Scanner scanner = null;
    /**
     * Static writer instance.
     */
    private static Writer writer = null;

    private int readN() throws InvalidBoardSizeException {
        int n = scanner.nextInt();
        if (n < lowerN || n > upperN) {
            throw new InvalidBoardSizeException();
        }
        this.boardSize = n;
        return n;
    }

    private int readM() throws InvalidNumberOfPiecesException {
        int m = scanner.nextInt();
        if (m < lowerM || m > this.boardSize * this.boardSize) {
            throw new InvalidNumberOfPiecesException();
        }
        return m;
    }

    private ChessPiece readChessPiece() throws InvalidPieceNameException,
            InvalidPieceColorException, InvalidPiecePositionException, InvalidGivenKingsException {
        String pieceTypeString = scanner.next();
        String colorString = scanner.next();
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        if (x < lowerCoord || x > this.boardSize) {
            throw new InvalidPiecePositionException();
        }

        ChessPiece chessPiece = null;
        Position position = new Position(x, y);
        PieceColor color = PieceColor.parse(colorString);
        switch (pieceTypeString) {
            case "Pawn":
                chessPiece = new Pawn(position, color);
                break;
            case "King":
                chessPiece = new King(position, color);
                this.numberOfKings++;
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
                throw new InvalidPieceNameException();
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
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("output.txt")));
        } catch (FileNotFoundException exception0) {
            return;
        }
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException exception1) {
            return;
        }
        try {
            n = main.readN();
            m = main.readM();
            chessBoard = new Board(n);
            for (int i = 0; i < m; i++) {
                ChessPiece chessPiece = main.readChessPiece();
                chessBoard.addPiece(chessPiece);
            }
            if (main.numberOfKings != 2) {
                throw new InvalidGivenKingsException();
            }
        } catch (InvalidBoardSizeException | InvalidNumberOfPiecesException
                | InvalidPieceNameException | InvalidPieceColorException
                | InvalidPiecePositionException | InvalidGivenKingsException exception2) {
            try {
                writer.write(exception2.getMessage() + "\n");
                writer.close();
            } catch (IOException ex) {
                return; // exiting with finally running
            }
            return;
        } finally {
            scanner.close();
        }
        try {
            for (Map.Entry<String, ChessPiece> mapElement : chessBoard.getPiecePositions()) {
                ChessPiece chessPiece = mapElement.getValue();
                int piecePossibleMoves = chessBoard.getPiecePossibleMovesCount(chessPiece);
                int piecePossibleCaptures = chessBoard.getPiecePossibleCapturesCount(chessPiece);
                writer.write(piecePossibleMoves + " " + piecePossibleCaptures + "\n");
            }
            writer.close();
        } catch (IOException exception3) {
            return;
        }
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

    public static PieceColor parse(String color) throws InvalidPieceColorException {
        if (color.equals("Black")) {
            return BLACK;
        } else if (color.equals("White")) {
            return WHITE;
        } else {
            throw new InvalidPieceColorException();
        }
    }
}

abstract class ChessPiece {
    /**
     * Coordinates of a piece.
     */
    protected Position position;
    /**
     * Color of a piece.
     */
    protected PieceColor color;

    ChessPiece(Position positionNew, PieceColor colorNew) {
        this.position = positionNew;
        this.color = colorNew;
    }

    Position getPosition() {
        return this.position;
    }

    PieceColor getColor() {
        return this.color;
    }

    public abstract int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);

    public abstract boolean canMove(int i, int j);
}

interface BishopMovement {
    default int getDiagonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions,
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
                    }
                }
            }
        }
        return count;
    }

    default int getDiagonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions,
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
                    }
                }
            }
        }
        return count;
    }
}

interface RookMovement {
    default int getOrthogonalMovesCount(Position position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize) {
        int count = 0;
        int newX = 0;
        int newY = 0;
        int[] mult = {0, -1, 1};
        for (int multI : mult) {
            for (int multJ : mult) {
                if (multI == 0 || multJ == 0) {
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
                        }
                    }
                }
            }
        }
        return count;
    }

    default int getOrthogonalCapturesCount(Position position, PieceColor color, Map<String, ChessPiece> positions,
            int boardSize) {
        int count = 0;
        int newX = 0;
        int newY = 0;
        int[] mult = {0, -1, 1};
        for (int multI : mult) {
            for (int multJ : mult) {
                if (multI == 0 || multJ == 0) {
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
                        }
                    }
                }
            }
        }
        return count;
    }
}

class Knight extends ChessPiece {
    Knight(Position position, PieceColor color) {
        super(position, color);
    }

    public boolean canMove(int i, int j) {
        Position pos = this.getPosition();
        int dx = Math.abs(pos.getX() - i);
        int dy = Math.abs(pos.getY() - j);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (this.canMove(i, j)) {
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
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (Map.Entry<String, ChessPiece> mapElement : positions.entrySet()) {
            if (this.canMove(mapElement.getValue().getPosition().getX(), mapElement.getValue().getPosition().getY())) {
                PieceColor newPieceColor = mapElement.getValue().getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        return count;
    }
}

class King extends ChessPiece {
    /**
     * Number of white kings.
     */
    private static int numberOfWhite = 0;
    /**
     * Number of black kings.
     */
    private static int numberOfBlack = 0;

    King(Position position, PieceColor color) throws InvalidGivenKingsException {
        super(position, color);
        if (color == PieceColor.WHITE) {
            numberOfWhite++;
        } else {
            numberOfBlack++;
        }
        if (numberOfBlack > 1 || numberOfWhite > 1) {
            throw new InvalidGivenKingsException();
        }
    }

    public boolean canMove(int i, int j) {
        Position pos = this.getPosition();
        int dx = Math.abs(pos.getX() - i);
        int dy = Math.abs(pos.getY() - j);
        return dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0);
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (this.canMove(i, j)) {
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
        }
        return count;
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (Map.Entry<String, ChessPiece> mapElement : positions.entrySet()) {
            if (this.canMove(mapElement.getValue().getPosition().getX(), mapElement.getValue().getPosition().getY())) {
                PieceColor newPieceColor = mapElement.getValue().getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        return count;
    }
}

class Pawn extends ChessPiece {
    Pawn(Position position, PieceColor color) {
        super(position, color);
    }

    public boolean canMove(int i, int j) {
        Position pos = this.getPosition();
        if (this.getColor() == PieceColor.WHITE) {
            return (j - pos.getY() == 1) && (i == pos.getX());
        } else {
            return (pos.getY() - j == 1) && (i == pos.getX());
        }
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int count = 0;
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                boolean isEmpty = !positions.containsKey(i + " " + j);
                if (isEmpty && this.canMove(i, j)) {
                    count++;
                } else if (!isEmpty && (this.canMove(i + 1, j) || this.canMove(i - 1, j))) {
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
        for (Map.Entry<String, ChessPiece> mapElement : positions.entrySet()) {
            Position pos = mapElement.getValue().getPosition();
            if (this.canMove(pos.getX() + 1, pos.getY())
                    || this.canMove(pos.getX() - 1, pos.getY())) {
                PieceColor newPieceColor = positions.get(pos.getX() + " " + pos.getY()).getColor();
                if (newPieceColor != this.color) {
                    count++;
                }
            }
        }
        return count;
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    Bishop(Position position, PieceColor color) {
        super(position, color);
    }

    public boolean canMove(int i, int j) {
        throw new UnsupportedOperationException();
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

class Rook extends ChessPiece implements RookMovement {
    Rook(Position position, PieceColor color) {
        super(position, color);
    }
    public boolean canMove(int i, int j) {
        throw new UnsupportedOperationException();
    }

    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }

    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(this.getPosition(), this.getColor(), positions, boardSize);
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    Queen(Position position, PieceColor color) {
        super(position, color);
    }
    public boolean canMove(int i, int j) {
        throw new UnsupportedOperationException();
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

class Position {
    /**
     * X coordinate of a piece.
     */
    private int x;
    /**
     * Y coordinate of a piece.
     */
    private int y;

    Position(int xX, int yY) {
        this.x = xX;
        this.y = yY;
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
    private Map<String, ChessPiece> piecePositions = new LinkedHashMap<String, ChessPiece>();
    /**
     * Board size.
     */
    private int size;

    Board(int boardSize) {
        this.size = boardSize;
    }

    private boolean isEmpty(Position pos) {
        return !this.piecePositions.containsKey(pos.toString());
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(this.piecePositions, this.size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(this.piecePositions, this.size);
    }

    public void addPiece(ChessPiece piece) throws InvalidPiecePositionException {
        Position position = piece.getPosition();
        if (this.isEmpty(position)) {
            this.piecePositions.put(position.toString(), piece);
        } else {
            throw new InvalidPiecePositionException();
        }
    }

    public ChessPiece getPiece(Position position) {
        return this.piecePositions.get(position.toString());
    }

    public Set<Map.Entry<String, ChessPiece>> getPiecePositions() {
        return this.piecePositions.entrySet();
    }
}
