package agh.cs.lab2;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST;

    @Override
    public String toString() {
        return switch (this) {
            case EAST -> "East";
            case WEST -> "West";
            case SOUTH -> "South";
            case NORTH -> "North";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case EAST -> SOUTH;
            case WEST -> NORTH;
            case SOUTH -> WEST;
            case NORTH -> EAST;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case EAST -> NORTH;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case NORTH -> WEST;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case EAST -> new Vector2d(0,-1, 0);
            case WEST -> new Vector2d(0,2,0);
            case SOUTH -> new Vector2d(2,-1,0);
            case NORTH -> new Vector2d(1,1,0));
        };
    }
}
