package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Position implements Comparable<Position> {
    private final int x, y, layer;

    public Position(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.layer = 0;
    }

    @Override
    public int compareTo(Position other) {
        // Compares this object with the specified object for order. 
        // Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
        
        if (this.x > other.getX()) {
            if (this.y > other.getY()) {
                return 1;
            } else if (this.y == other.getY()) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.x == other.getX()) {
            if (this.y > other.getY()) {
                return 1;
            } else if (this.y == other.getY()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (this.y > other.getY()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public final int hashCode() {
        return Objects.hash(x, y, layer);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;

        // z doesn't matter
        return x == other.x && y == other.y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getLayer() {
        return layer;
    }

    public final Position asLayer(int layer) {
        return new Position(x, y, layer);
    }

    public final Position translateBy(int x, int y) {
        return this.translateBy(new Position(x, y));
    }

    public final Position translateBy(Direction direction) {
        return this.translateBy(direction.getOffset());
    }

    public final Position translateBy(Position position) {
        return new Position(this.x + position.x, this.y + position.y, this.layer + position.layer);
    }

    // (Note: doesn't include z)

    /**
     * Calculates the position vector of b relative to a (ie. the direction from a
     * to b)
     * @return The relative position vector
     */
    public static final Position calculatePositionBetween(Position a, Position b) {
        return new Position(b.x - a.x, b.y - a.y);
    }

    /**
     * Calculate the distance to a position p
     * @param p
     * @return
     */
    public double distanceTo(Position p) {
        return Math.pow(Math.pow((x - p.getX()), 2) + Math.pow((y - p.getY()), 2), 0.5);
    }

    public  static final boolean isAdjacent(Position a, Position b) {
        int x = a.x - b.x;
        int y = a.y - b.y;
        return x + y == 1;
    }

    // (Note: doesn't include z)
    public final Position scale(int factor) {
        return new Position(x * factor, y * factor, layer);
    }

    @Override
    public final String toString() {
        return "Position [x=" + x + ", y=" + y + ", z=" + layer + "]";
    }

    // Return Adjacent positions in an array list with the following element positions:
    // 0 1 2
    // 7 p 3
    // 6 5 4
    public List<Position> getAdjacentPositions() {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x-1, y-1));
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x+1, y+1));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y+1));
        adjacentPositions.add(new Position(x-1, y));
        return adjacentPositions;
    }

}
