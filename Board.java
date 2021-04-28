package sokoban;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that represents a Sokoban level board.
 * 
 * <p>
 * The class can read a Sokoban level from a plain text file where the following
 * symbols are used:
 * 
 * <ul>
 * <li>space is an empty square
 * <li># is a wall
 * <li>@ is the player
 * <li>$ is a box
 * <li>. is a storage location
 * <li>+ is the player on a storage location
 * <li>* is a box on a storage location
 * </ul>  nn 
 * 
 * <p>
 * The class manages a single {@code Player} object and a number of {@code Box},
 * {@code Wall}, and {@code Storage} objects. The class determines whether the
 * player can move to a specified square depending on the configuration of boxes
 * and walls.
 * 
 * <p>
 * The level is won when every box is moved to a storage location.
 * 
 * <p>
 * The class provides several methods that return information about a location
 * on the board .
 *
 */
public class Board {
	/*
	 * ADD SOME FIELDS HERE TO STORE THE WALLS, BOXES, AND STORAGE SITES
	 */
		
	private Player player;
	private int width;
	private int height;
	private ArrayList<Wall> walls;
	private ArrayList<Box> boxes;
	private ArrayList<Storage> storages;

	/**
	 * Initialize a board of width 11 and height 11 with a {@code Player} located at
	 * (4, 5), a {@code Box} located at (5, 5), and a storage location located at
	 * (6, 5).
	 */
	public Board() {
		width = 11;
		height = 11;
		Location playerLo =  new Location(4, 5);
		player = new Player(playerLo);
		
		boxes = new ArrayList<Box>();
		Location boxLo = new Location(5, 5);
		Box box = new Box(boxLo);
		boxes.add(box);
		
		storages = new ArrayList<Storage>();
		Location storageLo = new Location(6, 5);
		Storage storage= new Storage(storageLo);
		storages.add(storage);
		
		walls = new ArrayList<Wall>();
	}

	/**
	 * Initialize a board by reading a level from the file with the specified
	 * filename.
	 * 
	 * <p>
	 * The height of the board is determined by the number of lines in the file. The
	 * width of the board is determined by the longest line in the file where
	 * trailing spaces in a line are ignored.
	 * 
	 * @param filename the filename of the level
	 * @throws IOException if the level file cannot be read
	 */
	public Board(String filename) throws IOException {
		width = 0;
		height = 0;
		walls = new ArrayList<Wall>();
		storages = new ArrayList<Storage>();
		boxes = new ArrayList<Box>();
		// call readLevel after initializing your fields
		this.readLevel(filename);
	}

	private final void readLevel(String filename) throws IOException {
		Path path = FileSystems.getDefault().getPath("src", "sokoban", filename);
		List<String> level = Files.readAllLines(path);
		this.height = level.size();
		this.width = 0;
		for (int y = 0; y < this.height; y++) {
			String row = level.get(y);
			if (row.length() > this.width) {
				this.width = row.length();
			}
			for (int x = 0; x < row.length(); x++) {
				// the location of this square
				Location loc = new Location(x, y);
				
				// the symbol at location (x, y)
				char c = row.charAt(x);
				if (c == ' ') {
					continue;
				} else if (c == '#') {
					// there is a wall here
					// make a Wall object and add it to your collection
					Wall wall = new Wall(loc);
					walls.add(wall);
					
				} else if (c == '@') {
					Player p = new Player(loc);
					this.player = p;
				} else if (c == '$') {
					// there is a box here
					// make a Box object and add it to your collection
					Box box = new Box(loc);
					boxes.add(box);
					
				} else if (c == '.') {
					// there is a storage site here
					// make a Storage object and add it to your collection
					Storage storage= new Storage(loc);
					storages.add(storage);
					
				} else if (c == '+') {
					Player p = new Player(loc);
					this.player = p;
					// there is also storage site here
					// make a Storage object and add it to your collection
					Storage storage= new Storage(loc);
					storages.add(storage);
					
				} else if (c == '*') {
					// there is a box and a storage site here
					// make a Box object and a Storage object and add them 
					//    to your collections
					Box box = new Box(loc);
					boxes.add(box);
					
					Storage storage= new Storage(loc);
					storages.add(storage);
					
				}
			}
		}
	}

	/**
	 * Returns the width of this board.
	 * 
	 * @return the width of this board
	 */
	public int width() {
		// ALREADY DONE FOR YOU
		return this.width;
	}

	/**
	 * Returns the height of this board.
	 * 
	 * @return the height of this board
	 */
	public int height() {
		// ALREADY DONE FOR YOU
		return this.height;
	}

	/**
	 * Returns a list of the walls in this board. The order of the walls is
	 * unspecified in the returned list.
	 * 
	 * @return a list of the walls in this board
	 */
	public List<Wall> getWalls() {
		return this.walls;
	}

	/**
	 * Returns a list of the boxes in this board. The order of the boxes is
	 * unspecified in the returned list.
	 * 
	 * @return a list of the boxes in this board
	 */
	public List<Box> getBoxes() {
		return this.boxes;
	}

	/**
	 * Returns a list of the storage locations in this board. The order of the
	 * storage locations is unspecified in the returned list.
	 * 
	 * @return a list of the storage locations in this board
	 */
	public List<Storage> getStorage() {
		return this.storages;
	}

	/**
	 * Returns the player.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		// ALREADY DONE FOR YOU
		return this.player;
	}

	/**
	 * Returns the {@code Box} object located at the specified location on the
	 * board, or {@code null} if there is no such object.
	 * 
	 * @param loc a location
	 * @return the box object located at the specified location on the board, or
	 *         {@code null} if there is no such object
	 */
	public Box getBox(Location loc) {
		 Iterator<Box> boxIterator = this.boxes.iterator(); 
		 Box box; 
		 
		 while (boxIterator.hasNext()) { 
			 box = boxIterator.next();
			 if (box.location().equals(loc)) {
				 return box;
			 }
		}
		 
		return null;
	}

	/**
	 * Returns {@code true} if there is a wall, player, or box at the specified
	 * location, {@code false} otherwise. Note that storage locations are considered
	 * unoccupied if there is no player or box on the location.
	 * 
	 * @param loc a location
	 * @return {@code true} if there is a wall, player, or box at the specified
	 *         location, {@code false} otherwise
	 */
	public boolean isOccupied(Location loc) {

		Iterator<Box> boxIterator = this.boxes.iterator(); 
		 Box box; 
		 
		 while (boxIterator.hasNext()) {
			 box = boxIterator.next();
			 
			 if (box.location().equals(loc)) {
				 return true;
			 }
		 }
		 
		 Iterator<Wall> wallIterator = this.walls.iterator(); 
		 Wall wall; 
		 
		 while (wallIterator.hasNext()) { 
			 wall = wallIterator.next();
			 if (wall.location().equals(loc)) {
				 return true;
			 }
		 }
		 
		 if (this.player.location().equals(loc)) {
			 return true;
		 }
		 
		 return false;
	}

	/**
	 * Returns {@code true} if the specified location is unoccupied or has only a
	 * storage location, {@code false} otherwise
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location is unoccupied or has only a
	 *         storage location, {@code false} otherwise
	 */
	public boolean isFree(Location loc) {
		if (isOccupied(loc)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Returns {@code true} if the specified location has a wall on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a wall on it,
	 *         {@code false} otherwise
	 */
	public boolean hasWall(Location loc) {
		Iterator<Wall> wallIterator = this.walls.iterator(); 
		Wall wall; 
		 
		while (wallIterator.hasNext()) { 
			 wall = wallIterator.next();
			 if (wall.location().equals(loc)) {
				 return true;
			 }
		}
		
		return false;
	}

	/**
	 * Returns {@code true} if the specified location has a box on it, {@code false}
	 * otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a box on it, {@code false}
	 *         otherwise
	 */
	public boolean hasBox(Location loc) {
		Iterator<Box> boxIterator = this.boxes.iterator(); 
		 Box box; 
		 
		 while (boxIterator.hasNext()) { 
			 box = boxIterator.next();
			 
			 if (box.location().equals(loc)) {
				 return true;
			 }
		 }
		 
		 return false;
	}

	/**
	 * Returns {@code true} if the specified location has a storage location on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a storage location on it,
	 *         {@code false} otherwise
	 */
	public boolean hasStorage(Location loc) {
		Iterator<Storage> storageIterator = this.storages.iterator(); 
		 Storage storage; 
		 
		 while (storageIterator.hasNext()) { 
			 storage = storageIterator.next();
			 if (storage.location().equals(loc)) {
				 return true;
			 }
		 }
		 return false;
	}

	/**
	 * Returns {@code true} if the specified location has a player on it,
	 * {@code false} otherwise.
	 * 
	 * @param loc a location
	 * @return {@code true} if the specified location has a player on it,
	 *         {@code false} otherwise
	 */
	public boolean hasPlayer(Location loc) {
		if (this.player.location().equals(loc)) {
			 return true;
		 }
		 return false;
	}

	/**
	 * Returns {@code true} if every storage location has a box on it, {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if every storage location has a box on it, {@code false}
	 *         otherwise
	 */
	public boolean isSolved() {
		Iterator<Storage> storageIterator = this.storages.iterator(); 
		 Storage storage; 
		 
		 while (storageIterator.hasNext()) { 
			 storage = storageIterator.next();
			 if(!hasBox(storage.location())) {
				 return false;
			 }
		 }
		 
		 return true;
	}

	
	/**
	 * Moves the player to the left adjacent location if possible. If there is a box
	 * in the left adjacent location then the box is pushed to the adjacent location
	 * left of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the left adjacent location
	 * (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the left adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerLeft() {
				
		if (hasBox(player.location().left()) && isFree(player.location().left().left())) {
	
			Box movingBox = getBox(player.location().left());
			movingBox.moveLeft();
		} 
		
		if (isFree(player.location().left())) {
			this.player.moveLeft();
			return true;
		}
		
		return false;
	}

	/**
	 * Moves the player to the right adjacent location if possible. If there is a
	 * box in the right adjacent location then the box is pushed to the adjacent
	 * location right of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the right adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the right adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerRight() {
		if (hasBox(player.location().right()) && isFree(player.location().right().right())) {
			Box movingBox = getBox(player.location().right());
			movingBox.moveRight();
		} 
		
		if (isFree(player.location().right())) {
			this.player.moveRight();
			return true;
		}
		
		return false;
	}

	/**
	 * Moves the player to the above adjacent location if possible. If there is a
	 * box in the above adjacent location then the box is pushed to the adjacent
	 * location above the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the above adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the above adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerUp() {
		if (hasBox(player.location().up()) && isFree(player.location().up().up())) {
			Box movingBox = getBox(player.location().up());
			movingBox.moveUp();
		} 
		
		if (isFree(player.location().up())) {
			this.player.moveUp();
			return true;
		}
		
		return false;
	}

	/**
	 * Moves the player to the below adjacent location if possible. If there is a
	 * box in the below adjacent location then the box is pushed to the adjacent
	 * location below of the box.
	 * 
	 * <p>
	 * Returns {@code false} if the player cannot move to the below adjacent
	 * location (leaving the player location unchanged).
	 * 
	 * @return true if the player is moved to the below adjacent location, false
	 *         otherwise
	 */
	public boolean movePlayerDown() {
		if (hasBox(player.location().down()) && isFree(player.location().down().down())) {
			Box movingBox = getBox(player.location().down());
			movingBox.moveDown();
		} 
		
		if (isFree(player.location().down())) {
			this.player.moveDown();
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Returns a string representation of this board. The string representation
	 * is identical to file format describing Sokoban levels.
	 * 
	 * @return a string representation of this board
	 */
	@Override
	public String toString() {
		// ALREADY DONE FOR YOU
		StringBuilder b = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Location loc = new Location(x, y);
				if (this.isFree(loc) ) {
					if (this.hasStorage(loc)) {
						b.append(".");					
					}
					else {
						b.append(" ");
					}
				}
				else if (this.hasWall(loc)) {
					b.append("#");
				}
				else if (this.hasBox(loc)) {
					if (this.hasStorage(loc)) {
						b.append("*");
					}
					else {
						b.append("$");
					}
				}
				else if (this.hasPlayer(loc)) {
					if (this.hasStorage(loc)) {
						b.append("+");
					}
					else {
						b.append("@");
					}
				}
			}
			b.append('\n');
		}
		
		return b.toString();
	}
	
	public static void main(String[] args) throws IOException {
		Board b = new Board();
		System.out.println(b);
	}
}
