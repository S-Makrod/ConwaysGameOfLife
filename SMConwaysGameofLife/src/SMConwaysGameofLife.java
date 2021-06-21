//Saad Makrod
//Mr Jay
//ICS 4U1
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class SMConwaysGameofLife extends JFrame implements ActionListener, ChangeListener, MouseListener, MouseMotionListener {//Beginning of SMConwaysGameofLife
    static Colony colony = new Colony(0.1);
    static JSlider speedSldr = new JSlider();
    static Timer t;
    JLabel genNum;
    int gens = 0;
    String[] choice = {"Populate", "Eradicate"};
    JComboBox choices = new JComboBox(choice);
    JButton simulateBtn = new JButton("Simulate");
    JTextField name = new JTextField(10);
    JComboBox patterns = new JComboBox();

    //======================================================== constructor
    public SMConwaysGameofLife() {
        // 1... Create/initialize components
        simulateBtn.addActionListener(this);

        JLabel gen = new JLabel("Generations: ");
        genNum = new JLabel("0");

        speedSldr.addChangeListener(this);

        JButton clear = new JButton("Clear");
        clear.addActionListener(this);

        JButton random = new JButton("Random");
        random.addActionListener(this);

        JButton load = new JButton("Load");
        load.addActionListener(this);

        JButton save = new JButton("Save");
        save.addActionListener(this);

        JLabel choose = new JLabel("Drag or click to either (40% chance of success): ");

        JLabel note = new JLabel("Note: Do add a file extension as it is automatically done for you!");

        JPanel one = new JPanel();//Panels created to manage layout better
        JPanel two = new JPanel();
        JPanel three = new JPanel();

        patterns.addItem("None");
        patterns.addItem("ShipPattern");
        patterns.addItem("CrabGliderPattern");
        patterns.addItem("MovingPattern");
        patterns.addItem("CloverLeafPattern");

        // 2... Create content pane, set layout
        JPanel content = new JPanel();        // Create a content pane
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // Use BorderLayout for panel
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout()); // Use FlowLayout for input area

        DrawArea board = new DrawArea(500, 520);//DrawArea obkect created

        board.addMouseListener(this);//MouseListener and MouseMotionListener added to make user able to populate and eradicate
        board.addMouseMotionListener(this);
        // 3... Add the components to the input area.

        north.add(simulateBtn);
        north.add(speedSldr);
        north.add(gen);
        north.add(genNum);

        one.add(choose);
        one.add(choices);
        north.add(one);

        three.add(clear);
        three.add(random);
        north.add(three);

        two.add(name);
        two.add(patterns);
        two.add(save);
        two.add(load);
        north.add(two);

        north.add(note);

        north.setPreferredSize(new Dimension(500, 180));

        content.add(north, "North"); // Input area
        content.add(board, "South"); // Output area

        // 4... Set this window's attributes.
        setContentPane(content);
        pack();
        setTitle("Life Simulation Demo");
        setSize(520, 715);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Center window.
    }

    public void stateChanged(ChangeEvent e) {//StateChanged Method
        if (t != null)
            t.setDelay(400 - 4 * speedSldr.getValue()); // 0 to 400 ms
    }//Method end

    public void mouseClicked(MouseEvent e) {//MouseClick method
        int x = e.getX();//Gets the x and y coordinates of the mouse
        int y = e.getY();
        boolean[][] grid = colony.getGrid();

        if (choices.getSelectedItem().toString().equals("Populate")) {//If the JComboBox Item is currently populate
            if (Math.random() <= 0.4) {//40% chance
                try {
                    grid[Math.round(y / 5)][Math.round(x / 5)] = true;//Calculates the cell and sets it to true
                } catch (Exception ignored) {
                }
            }
            colony.setGrid(grid);
        } else {//It is on eradicate
            if (Math.random() <= 0.4) {
                try {
                    grid[Math.round(y / 5)][Math.round(x / 5)] = false;//Calculates the cell and sets it to false
                } catch (Exception ignored) {
                }
            }
            colony.setGrid(grid);
        }
        repaint();
    }//End Method

    public void mousePressed(MouseEvent e) {//Mandatory method, but nothing added
    }

    public void mouseReleased(MouseEvent e) {//Mandatory method, but nothing added
    }

    public void mouseEntered(MouseEvent e) {//Mandatory method, but nothing added
    }

    public void mouseExited(MouseEvent e) {//Mandatory method, but nothing added
    }

    public void mouseDragged(MouseEvent e) {//MouseDragged method
        int x = e.getX();//Gets the x and y coordinates of the mouse
        int y = e.getY();
        boolean[][] grid = colony.getGrid();

        if (choices.getSelectedItem().toString().equals("Populate")) {//If the JComboBox Item is currently populate
            if (Math.random() <= 0.4) {
                try {
                    grid[Math.round(y / 5)][Math.round(x / 5)] = true;//Calculates the cell and sets it to true
                } catch (Exception ignored) {
                }
            }
            colony.setGrid(grid);
        } else {//It is on eradicate
            if (Math.random() <= 0.4) {
                try {
                    grid[Math.round(y / 5)][Math.round(x / 5)] = false;//Calculates the cell and sets it to false
                } catch (Exception ignored) {
                }
            }
            colony.setGrid(grid);
        }
        repaint();
    }//Method end

    public void mouseMoved(MouseEvent e) {//Mandatory method, but nothing added
    }

    public void actionPerformed(ActionEvent e) {//ActionPerformed method
        if (e.getActionCommand().equals("Simulate")) {//if simulate clicked
            Movement moveColony = new Movement(colony); // ActionListener
            t = new Timer(200, moveColony); // set up timer
            t.start(); // start simulation
            simulateBtn.setText("Stop");//simulate button becomes stop button
        } else if (e.getActionCommand().equals("Stop")) {//if stop clicked
            t.stop(); // stop simulation
            simulateBtn.setText("Simulate");//stop button become simulate button
        } else if (e.getActionCommand().equals("Clear")) {//if clear clicked
            boolean[][] grid = colony.getGrid();

            for (int i = 0; i < grid.length; i++)//grid values all become false
                for (int x = 0; x < grid[i].length; x++)
                    grid[i][x] = false;

            colony.setGrid(grid);//set grid
        } else if (e.getActionCommand().equals("Random")) {//if random is clicked
            boolean[][] grid = colony.getGrid();

            for (int row = 0; row < grid.length; row++)//same as the constructor, just fill area randomly
                for (int col = 0; col < grid[0].length; col++)
                    grid[row][col] = Math.random() < colony.getDensity();

            colony.setGrid(grid);
        } else if (e.getActionCommand().equals("Save")) {//if save is clicked
            try {//try catch statement
                save();//go to save method
            } catch (IOException ignored) {
                name.setText("File could not be saved!");
            }
        } else if (e.getActionCommand().equals("Load")) {//if load is clicked
            File file;
            if (patterns.getSelectedItem().equals("None"))//if the combobox is set to none get the file from the textbox
                file = new File(name.getText() + ".txt");
            else//get file from combobox
                file = new File(patterns.getSelectedItem().toString() + ".txt");
            try {//try catch statement in case file not found
                Scanner sc = new Scanner(file);//scanner object will read the file
                boolean[][] grid = colony.getGrid();//get the grid

                for (int i = 0; i < grid.length; i++) {//run double for loop for the entire grid
                    String[] names = sc.nextLine().split(",");//read line "i" and split the values by commas to get string array

                    for (int x = 0; x < grid[0].length; x++) {
                        if (names[x].equals(" true"))//if it is true
                            grid[i][x] = true;
                        else if (names[x].equals(" [true"))//different possible output for true due to the Array.toString function
                            grid[i][x] = true;
                        else if (names[x].equals(" true]"))//same as above
                            grid[i][x] = true;
                        else//if false
                            grid[i][x] = false;
                    }
                }

                colony.setGrid(grid);//set grid
            } catch (FileNotFoundException x) {//if file not found
                name.setText("File Not Found!");
            }
        }
        repaint();            // refresh display of deck
    }//end method

    public void save() throws IOException {//save method
        try {//try catch statement in case file cannot be created
            String File = name.getText() + ".txt";//add extension to the name
            FileWriter fw = new FileWriter(File);//file writer object
            Writer w = new BufferedWriter(fw);// writer object created based off of the file writer object
            boolean[][] grid = colony.getGrid();
            boolean[] line;//line array

            for (int i = 0; i < grid.length; i++) {//run loop for all rows of grid
                line = grid[i];//line = row [i] of grid
                w.write(Arrays.toString(line) + "\n");//save the array to each line
            }
            w.close();//close file --> will result in a 100 line file with 100 values in each row
        } catch (IOException e) {//if file not found
            name.setText("File cannot be created");
        }
    }//end method

    class DrawArea extends JPanel {//DrawArea class
        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // size
        }

        public void paintComponent(Graphics g) {
            colony.show(g);
        }
    }//end class

    class Movement implements ActionListener {//Movement class
        private Colony colony;

        public Movement(Colony col) {//Constructor
            colony = col;
        }//Constructor

        public void actionPerformed(ActionEvent event) {//action perfomed
            colony.advance();
            gens++;
            genNum.setText(Integer.toString(gens));//used to show each generation, generation counter at top right
            repaint();
        }//end method
    }//end class

    //======================================================== method main
    public static void main(String[] args) {
        SMConwaysGameofLife window = new SMConwaysGameofLife();
        window.setVisible(true);
    }
}//End SMConwaysGameofLife class

class Colony {//Colony class
    private boolean grid[][];
    private double density;

    public Colony(double density) {//constructor
        grid = new boolean[100][100];
        this.density = density;

        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[0].length; col++)
                grid[row][col] = Math.random() < this.density;
    }//end constructor

    public void show(Graphics g) {//show method
        for (int row = 0; row < grid.length; row++)//double for loop for the entire grid
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col]) // life
                    g.setColor(Color.black);
                else
                    g.setColor(Color.white);
                g.fillRect(col * 5 + 2, row * 5 + 2, 5, 5); // draw life form
            }
    }//end of method

    public boolean live(int r, int c) {//live method
        // count number of life forms surrounding to determine life/death
        int count = 0; // count tracks how many values are being totaled
        if (r > 0) { // above exists
            if (grid[r - 1][c])
                count++;
        }
        if (r < grid.length - 1) { // below exists
            if (grid[r + 1][c])
                count++;
        }
        if (c > 0) {//if column to left exists
            if (grid[r][c - 1])
                count++;
        }
        if (c < grid[0].length - 1) {//if column to right exists
            if (grid[r][c + 1])
                count++;
        }
        if (r > 0 && c > 0) {//if column to left and row above exists
            if (grid[r - 1][c - 1])
                count++;
        }
        if (r > 0 && c < grid[0].length - 1) {//row above and column right exists
            if (grid[r - 1][c + 1])
                count++;
        }
        if (r < grid.length - 1 && c > 0) {//row below and column left exists
            if (grid[r + 1][c - 1])
                count++;
        }
        if (r < grid.length - 1 && c < grid[0].length - 1) {//row below and column to right exists
            if (grid[r + 1][c + 1])
                count++;
        }

        if (grid[r][c])//if the cell was originally alive
        {
            if (count == 2 || count == 3)//If there are 2 or 3 surrounding cells it lives
                return true;
            else//Anything else it dies
                return false;
        } else//The cell was dead
        {
            if (count == 3)//If there are 3 surrounding cells it is reborn
                return true;
            else
                return false;
        } // temporary life death status
    }//end method

    public void advance() {//advance method
        boolean nextGen[][] = new boolean[grid.length][grid[0].length]; // create next generation of life forms
        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[0].length; col++)
                nextGen[row][col] = live(row, col); // determine life/death status
        grid = nextGen; // update life forms
    }//end method

    public boolean[][] getGrid() {
        return grid;
    }//get grid

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }//set grid

    public double getDensity() {
        return density;
    }//get density
}//end class