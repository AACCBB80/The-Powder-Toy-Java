package thePowderToyJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FallingSandGame extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    public static int WIDTH = 1000;
    public static int HEIGHT = 420;
    public static int SIZE = 6;
    private Element[][] grid = new Element[HEIGHT / SIZE][WIDTH / SIZE];
    private double[][] pressure = new double[HEIGHT / SIZE][WIDTH / SIZE];
    private double[][] temperatureGrid;
    private int brushSize = 3;
    long frameCounter = 0;
    public static boolean replaceMode = false;
    public static boolean tempType = true;
    public static boolean paused = false;

    public static String keyPressed = null;
    public static boolean debugMode = true;
    public static JTabbedPane pane = new JTabbedPane(JTabbedPane.BOTTOM);
    private int fps;
    private long lastTime = System.nanoTime();
    private int frames = 0;
    public static int nameFrames = 0;
    public static int displayMode = 0;
    public static String hoveredElement = "";
    private String username = null;
    private String password = null;

    public Element createElementInstance(String className) {
        try {
            Class<?> clazz = Class.forName("thePowderToyJava." + className);
            return (Element) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) { /*JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);*/
            return null;
        }
    }

    private void initializePressureGrid() {
        pressure = new double[grid.length][grid[0].length];
        for (int y = 0; y < pressure.length; y++) {
            for (int x = 0; x < pressure[0].length; x++) {
                pressure[y][x] = 0;
            }
        }
    }

    private void updateFPS() {
        long now = System.nanoTime();
        frames++;

        if (now - lastTime >= 1_000_000_000) {
            fps = frames;
            frames = 0;
            lastTime = now;
        }
    }

    private void initializeTemperatureGrid() {
        temperatureGrid = new double[grid.length][grid[0].length];
        for (int y = 0; y < temperatureGrid.length; y++) {
            for (int x = 0; x < temperatureGrid[0].length; x++) {
                temperatureGrid[y][x] = 70.0;
            }
        }
    }

    public Element getElementAt(int x, int y) {
        if (grid == null) {return null;}
        if (isInBounds(x, y)) {
            return grid[y][x];
        }
        return null;
    }

    public void setElementAt(int x, int y, Element element) {
        if (isInBounds(x, y)) {
            grid[y][x] = element;
        }
    }

    public void addPressureAt(int x, int y, double pressureC) {
        if (isInBounds(x, y)) {
            pressure[y][x] = pressureC + pressure[y][x];
        }
    }

    public double getPressureAt(int x, int y) {
        if (pressure == null) {return 0;}
        if (isInBounds(x, y)) {
            return pressure[y][x];
        }
        return 0;
    }

    public void addHeatAt(int x, int y, double pressureC) {
        if (isInBounds(x, y)) {
            temperatureGrid[y][x] = pressureC + temperatureGrid[y][x];
        }
    }

    public void setHeatAt(int x, int y, double pressureC) {
        if (isInBounds(x, y)) {
            temperatureGrid[y][x] = pressureC;
        }
    }

    public double getHeatAt(int x, int y) {
        if (temperatureGrid == null) {return 0;}
        if (isInBounds(x, y)) {
            return temperatureGrid[y][x];
        }
        return 0;
    }

    private void updatePressure() {
        double[][] newPressureGrid = new double[pressure.length][pressure[0].length];
        double diffusionFactor = 0.35;
        double decayFactor = 0.60;

        for (int y = 0; y < pressure.length; y++) {
            for (int x = 0; x < pressure[0].length; x++) {
                double currentPressure = pressure[y][x];
                double accumulatedPressure = currentPressure * decayFactor;
                int count = 1;

                if (x > 0) {
                    accumulatedPressure += pressure[y][x - 1] * diffusionFactor;
                    count++;
                }
                if (x < pressure[0].length - 1) {
                    accumulatedPressure += pressure[y][x + 1] * diffusionFactor;
                    count++;
                }
                if (y > 0) {
                    accumulatedPressure += pressure[y - 1][x] * diffusionFactor;
                    count++;
                }
                if (y < pressure.length - 1) {
                    accumulatedPressure += pressure[y + 1][x] * diffusionFactor;
                    count++;
                }

                newPressureGrid[y][x] = accumulatedPressure / count;
            }
        }

        pressure = newPressureGrid;
    }

    private void updateTemperature() {
        double[][] newTemperatureGrid = new double[temperatureGrid.length][temperatureGrid[0].length];

        for (int y = 0; y < temperatureGrid.length; y++) {
            for (int x = 0; x < temperatureGrid[0].length; x++) {
                double currentTemp = temperatureGrid[y][x];
                double diffusionFactor = 0.40;
                double accumulatedTemp = currentTemp;
                int count = 1;

                if (x > 0) {
                    accumulatedTemp += temperatureGrid[y][x - 1];
                    count++;
                }
                if (x < temperatureGrid[0].length - 1) {
                    accumulatedTemp += temperatureGrid[y][x + 1];
                    count++;
                }
                if (y > 0) {
                    accumulatedTemp += temperatureGrid[y - 1][x];
                    count++;
                }
                if (y < temperatureGrid.length - 1) {
                    accumulatedTemp += temperatureGrid[y + 1][x];
                    count++;
                }

                double newTemp = currentTemp + diffusionFactor * (accumulatedTemp / count - currentTemp);
                newTemperatureGrid[y][x] = newTemp;
            }
        }

        temperatureGrid = newTemperatureGrid;
    }

    public boolean isEmpty(int x, int y) {
        
        return isInBounds(x, y) && grid[y][x] == null;
    }

   public boolean canPush(int x1, int y1, int x2, int y2) { if (!isInBounds(x1, y1) || !isInBounds(x2, y2)) { return false; } Element element1 = grid[y1][x1]; Element element2 = grid[y2][x2]; if (element1 != null && element2 != null) { return element1.getWeight() > element2.getWeight(); } return false;}
   
    public static Element[][] resizeArray2D(Element[][] originalArray, int newRowCount, int newColCount) {

        Element[][] newArray = null;
        try {
            newArray = new Element[newRowCount][newColCount];
        } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
        }

        for (int i = 0; i < Math.min(originalArray.length, newRowCount); i++) {
            for (int j = 0; j < Math.min(originalArray[i].length, newColCount); j++) {
                try {
                    newArray[i][j] = originalArray[i][j];
                } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
                }
            }
        }

        return newArray;
    }

    public static double[][] resizePressureArray2D(double[][] originalArray, int newRowCount, int newColCount) {

        double[][] newArray = null;
        try {
            newArray = new double[newRowCount][newColCount];
        } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
        }

        for (int i = 0; i < Math.min(originalArray.length, newRowCount); i++) {
            for (int j = 0; j < Math.min(originalArray[i].length, newColCount); j++) {
                try {
                    newArray[i][j] = originalArray[i][j];
                } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
                }
            }
        }

        return newArray;
    }

    public void setNeighbors(int x, int y, Element element, boolean corners) {
        int[][] directions;
        if (corners) {
            directions = new int[][]{
                    {-1, -1}, {0, -1}, {1, -1},
                    {-1, 0}, {1, 0},
                    {-1, 1}, {0, 1}, {1, 1}
            };
        } else {
            directions = new int[][]{
                    {0, -1},
                    {-1, 0},          {1, 0},
                    {0, 1}
            };
        }

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            Element neighbor = getElementAt(newX, newY);
            if (neighbor == null) {
                setElementAt(newX, newY, element);
            }
        }
    }

    public boolean isInBounds(int x, int y) {
        try { return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length; }
        catch (Exception e) {
        }

        return false;
    }

    private boolean running = true;
    private boolean mouseHeld = false;
    private int mouseX, mouseY;
    private Element selectedElement = new Dust();
    private int mouseButtonPressed = -1;
    private Dimension newSize = getSize();


    public static void hover1(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(new Color(239, 233, 255));
                hover2(button);
            } else if (component instanceof Container) {
                hover1((Container) component);
            }
        }
    }

    public static void hover2(JButton button) {
        if (button.getClientProperty("hov") == null) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(new Color(214, 197, 255));
                    hoveredElement = button.getText();
                    nameFrames = 255*2;
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    button.setBackground(new Color(239, 233, 255));
                }
            });
            button.putClientProperty("hov", Boolean.TRUE);
        }
    }

    public FallingSandGame() {
        try { } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);  }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyListenerHandler());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        initializePressureGrid();
        initializeTemperatureGrid();
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    brushSize = Math.min(brushSize + 2, 150);
                } else {
                    brushSize = Math.max(brushSize - 2, 1);
                }
            }
        });

        JFrame frame = new JFrame("The Powder Toy Java (9.0)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        Color customColor = new Color(28, 28, 28);
        frame.getContentPane().setBackground(customColor);
        this.setBackground(customColor);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension oldSize = newSize;
                newSize = getSize();
                if (newSize.width
                        <= 0 | newSize.height <= 0) {
                    setSize(oldSize);
                } else {
                    if (!newSize.equals(oldSize)) {
                        HEIGHT = newSize.height;
                        WIDTH = newSize.width;
                        int newRowCount = HEIGHT / SIZE;
                        int newColCount = WIDTH / SIZE;

                        grid = resizeArray2D(grid, newRowCount, newColCount);
                        pressure = resizePressureArray2D(pressure, newRowCount, newColCount);

                        temperatureGrid = resizePressureArray2D(temperatureGrid, newRowCount, newColCount);
                        repaint();
                    }}
            }
        });

        JButton clearButton = new JButton("Clear Simulation");
        clearButton.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the simulation?", "Clear Simulation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {

                JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 51, SIZE);
                slider.setMajorTickSpacing(10);
                slider.setMinorTickSpacing(1);
                slider.setPaintTicks(true);

                JSpinner spinner = new JSpinner(new SpinnerNumberModel(SIZE, 1, 51, 1));

                slider.addChangeListener(f -> spinner.setValue(slider.getValue()));
                spinner.addChangeListener(f -> slider.setValue((int) spinner.getValue()));

                JLabel sliderValue = new JLabel("Set cell size (in pixels)");
                JPanel pnl = new JPanel();
                pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
                pnl.add(sliderValue);
                pnl.add(Box.createVerticalStrut(10));
                pnl.add(slider);
                pnl.add(Box.createVerticalStrut(10));
                pnl.add(spinner);

                int result = JOptionPane.showConfirmDialog(null, pnl, "Clear Simulation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                int content = slider.getValue();

                if (result == JOptionPane.OK_OPTION) {
                    grid = null;
                    pressure = null;
                    temperatureGrid = null;

                    SIZE = content;
                    grid = new Element[HEIGHT / SIZE][WIDTH / SIZE];
                    pressure = new double[grid.length][grid[0].length];
                    temperatureGrid = new double[grid.length][grid[0].length];
                    for (int y = 0; y < pressure.length; y++) {
                        for (int x = 0; x < pressure[0].length; x++) {
                            pressure[y][x] = 0;
                            temperatureGrid[y][x] = 70;
                        }
                    }

                } else {
                    grid = null;
                    pressure = null;
                    temperatureGrid = null;
                    grid = new Element[HEIGHT / SIZE][WIDTH / SIZE];
                    pressure = new double[grid.length][grid[0].length];
                    temperatureGrid = new double[grid.length][grid[0].length];
                    for (int y = 0; y < pressure.length; y++) {
                        for (int x = 0; x < pressure[0].length; x++) {
                            pressure[y][x] = 0;
                            temperatureGrid[y][x] = 70;
                        }
                    }
                }

                repaint();
            }
        });

        JButton pauseButton = new JButton("Toggle Pause");
        pauseButton.addActionListener(e -> {
            paused = !paused;

        });

        JButton replaceButton = new JButton("Toggle Replace Brush");
        replaceButton.addActionListener(e -> {
            replaceMode = !replaceMode;

        });

        JButton systemButton = new JButton("Toggle Units");
        systemButton.addActionListener(e -> {
            tempType = !tempType;
        });

        JButton HUDButton = new JButton("Toggle HUD");
        HUDButton.addActionListener(e -> {
            debugMode = !debugMode;
        });

        JPanel controlPanel = new JPanel();

        pane.setPreferredSize(new Dimension(frame.getWidth(), 45));

        // ELEMENT INIT 

        Element[]  solids = {new Wood(), new Coal(), new Stone(), new Glass(), new Ice(), new Paper(), new TNT(), new Plastic(), new Lead(), new Plant(), new Flesh(), new Bread(), new Cake(), new Brick()};
        Element[] liquids = {new Water(), new Goop(), new Nitroglycerin(), new Liquid_Nitrogen(), new Acid(), new Oil(), new Lava(), new Ethanol(), new Salt_Water(), new Milk(), new Batter(), new Cake_Batter()};
        Element[] powders = {new Dust(), new Sand(), new Sawdust(), new Gravel(), new Broken_Coal(), new Broken_Glass(), new Dirt(), new Salt(), new Flour()};
        Element[]  gasses = {new Fire(), new Plasma(), new Gas(), new Smoke(), new Oxygen(), new Hydrogen(), new Water_Vapor(), new Carbon_Dioxide()};
        Element[]  radioactive = {new Neutron(), new Deuterium(), new Uranium()};
        Element[]  electricity = {new Battery(), new Wire(), new Delay(), new Engine()};
        Element[] special = {new Clone(), new Void(), new Virus(), new Stickman(), new Infiniburn(), new Seed(), new Diamond(), new Heat_Conductor()};
        Element[]   tools = {new Heat(), new Cool(), new Spark()};

        JPanel tab1 = new JPanel(new GridLayout(1,15,4,4));
        for(int i=0 ; i<solids.length ; i++){
            JButton b = new JButton(solids[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = solids[finalI]);
            tab1.add(b);
        }

        

        JPanel tab2 = new JPanel(new GridLayout(1,8,4,4));

        for(int i=0 ; i<liquids.length ; i++){
            JButton b = new JButton(liquids[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = liquids[finalI]);
            tab2.add(b);
        }

        JPanel tab3 = new JPanel(new GridLayout(1,8,4,4));

        for(int i=0 ; i<powders.length ; i++){
            JButton b = new JButton(powders[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = powders[finalI]);
            tab3.add(b);
        }

        JPanel tab4 = new JPanel(new GridLayout(1,8,4,4));
        for(int i=0 ; i<gasses.length ; i++){
            JButton b = new JButton(gasses[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = gasses[finalI]);
            tab4.add(b);
        }

        JPanel tab5 = new JPanel(new GridLayout(1,8,4,4));

        for(int i=0 ; i<special.length ; i++){
            JButton b = new JButton(special[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = special[finalI]);
            tab5.add(b);
        }

        JPanel tab6 = new JPanel(new GridLayout(1,8,4,4));
        for(int i=0 ; i<electricity.length ; i++){
            JButton b = new JButton(electricity[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = electricity[finalI]);
            tab6.add(b);
        }

        JPanel tab7 = new JPanel(new GridLayout(1,8,4,4));
for(int i=0 ; i<tools.length ; i++){
            JButton b = new JButton(tools[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = tools[finalI]);
            tab7.add(b);
        }

        JPanel tab8 = new JPanel(new GridLayout(1,8,4,4));
        for(int i=0 ; i<radioactive.length ; i++){
            JButton b = new JButton(radioactive[i].getClass().getSimpleName().replaceAll("_", " "));
            int finalI = i;
            b.addActionListener(e -> selectedElement = radioactive[finalI]);
            tab8.add(b);
        }

        JPanel tab9 = new JPanel(new GridLayout(1,8,4,4));


        JButton saveButton = new JButton("Export");
        saveButton.addActionListener(e -> {
            StringBuilder exported = new StringBuilder();

            exported.append(SIZE).append(":").append(getSize().width).append(":").append(getSize().height).append("|");

            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {
                    Element element = grid[y][x];
                    exported.append((element == null ? "0" : element.getId()));
                    if (x < grid[0].length - 1) exported.append(",");
                }
                if (y < grid.length - 1) exported.append("-");
            }

            JOptionPane.showInputDialog(null, "Copy this data to save your simulation:",
                    "Export Simulation", JOptionPane.PLAIN_MESSAGE, null, null, exported.toString());
        });

        JButton importButton = new JButton("Import");
        importButton.addActionListener(e -> {
            String content = JOptionPane.showInputDialog(null, "Paste your simulation data:", "Import Simulation", JOptionPane.PLAIN_MESSAGE);
            if (content == null || content.isEmpty()) return;

            try {
            	String[] parts = content.split("\\|", 2);
                String[] header = parts[0].split(":");
                int cellSize = Integer.parseInt(header[0]);
                int width = Integer.parseInt(header[1]);
                int height = Integer.parseInt(header[2]);

                SIZE = Integer.parseInt(header[0].replaceFirst("^0+(?!$)", ""));
                WIDTH = width;
                HEIGHT = height;
                Debug.print(frame.getSize());
               

                grid = new Element[HEIGHT / SIZE][WIDTH / SIZE];
                pressure = new double[grid.length][grid[0].length];
                temperatureGrid = new double[grid.length][grid[0].length];
                
frame.setPreferredSize(new Dimension(WIDTH+11, HEIGHT + 55)); //omfg this bug has been scaring me for weeks and now i fix it :)
                frame.pack();

                for (int y = 0; y < pressure.length; y++) {
                    for (int x = 0; x < pressure[0].length; x++) {
                        pressure[y][x] = 0;
                        temperatureGrid[y][x] = 70;
                    }
                }

                String[] rows = parts[1].split("-");
                for (int y = 0; y < rows.length; y++) {
                    String[] elements = rows[y].split(",");
                    for (int x = 0; x < elements.length; x++) {
                        int elementId = Integer.parseInt(elements[x]);
                        grid[y][x] = (elementId == 0) ? null : createElementInstance(Element.getElementById(elementId).getSimpleName());
                    }
                }

                frame.repaint();
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        });

        JButton consoleButton = new JButton("Console");
        consoleButton.addActionListener(e -> {
            boolean commandOpen = true;
            while (commandOpen) {
                String cmd = JOptionPane.showInputDialog(null, "Enter a command (use \"help\" for list of commands, press cancel to exit): ", "Console", JOptionPane.PLAIN_MESSAGE);
                if (cmd != null) {

                    String[] parts = cmd.trim().split("\\s+");

                    String command = parts[0];

                    String[] args = new String[parts.length - 1];
                    System.arraycopy(parts, 1, args, 0, args.length);

                    switch (command.toLowerCase()) {
                        case "set":
                            setElementAt(Integer.parseInt(args[0]), Integer.parseInt(args[1]), createElementInstance(args[2]));
                            break;
                        case "delay":
                            Delay.delayDuration = Integer.parseInt(args[0]);
                            break;
                        case "brush":
                            selectedElement = createElementInstance(args[0]);
                            break;
                        case "fill":
                                for (int xe = Integer.valueOf(args[0]); xe <= Integer.valueOf(args[2]); xe++) {
                                    for (int ye = Integer.valueOf(args[1]); ye <= Integer.valueOf(args[3]); ye++) {
                                        if (isInBounds(xe,ye)) {
                                            setElementAt(xe, ye, createElementInstance(args[4]));
                                        }
                                    }
                                }
                            break;
                        case "help":
                            JOptionPane.showMessageDialog(null,
                            "Commands:\n" +
                                    "set <x> <y> <element/null> - Set cell at [x], [y] to [element/null]\n" +
                                    "brush <element> - Sets your brush to [element]\n" +
                                    "fill <x> <y> <x> <y> <element/null> - Fill area from [x], [y] to [x], [y] with [element/null]\n" +
                                    "delay <frames> - Sets how often the Delay element releases pulses\n",
                            "Commands",
                            JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                } else {
                    commandOpen = false;
                }
            }
        });

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(e -> {
            String[] items = null;
            try {
                String temp69 = new java.util.Scanner(new URL("https://6d6ldeoyebpfbivviclcauaejm.srv.us/TPTJ/list.php").openStream(), "UTF-8").useDelimiter("\\A").next();
                temp69 = temp69.substring(0, temp69.length() - 1);
                items = temp69.split(",");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JLabel searchLabel = new JLabel("Search:");
            JTextField searchField = new JTextField(15);
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String item : items) {
                listModel.addElement(item);
            }

            JList<String> list = new JList<>(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            String[] finalItems = items;
            searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { filterList(); }

                private void filterList() {
                    String query = searchField.getText().toLowerCase();
                    listModel.clear();
                    for (String item : finalItems) {
                        if (item.toLowerCase().contains(query)) {
                            listModel.addElement(item);
                        }
                    }
                }
            });

            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
            searchPanel.add(searchLabel);
            searchPanel.add(searchField);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(searchPanel, BorderLayout.NORTH);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(200, 100));
            panel.add(scrollPane, BorderLayout.CENTER);

            int option = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Save Browser",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String selectedItem = list.getSelectedValue();
                if (selectedItem != null) {
                    String urlString = "https://6d6ldeoyebpfbivviclcauaejm.srv.us/TPTJ/saves/" + selectedItem + ".save";
                    urlString = urlString.replaceAll(" ", "%20");
                    Debug.print(urlString);
                    StringBuilder result = new StringBuilder();

                    try {
                        URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Save doesn't exist!", "Download Simulation", JOptionPane.ERROR_MESSAGE);
                    }
                    String content1 = result.toString();
                    if (content1 == null || content1.isEmpty()) return;

                    try {
                        String[] parts = content1.split("\\|", 2);
                        String[] header = parts[0].split(":");
                        int cellSize = Integer.parseInt(header[0]);
                        int width = Integer.parseInt(header[1]);
                        int height = Integer.parseInt(header[2]);

                        SIZE = Integer.parseInt(header[0].replaceFirst("^0+(?!$)", ""));
                        WIDTH = width;
                        HEIGHT = height;

                        grid = new Element[HEIGHT / SIZE][WIDTH / SIZE];
                        pressure = new double[grid.length][grid[0].length];
                        temperatureGrid = new double[grid.length][grid[0].length];
                        
frame.setPreferredSize(new Dimension(WIDTH+11, HEIGHT + 55)); //omfg this bug has been scaring me for weeks and now i fix it :)
                        frame.pack();

                        for (int y = 0; y < pressure.length; y++) {
                            for (int x = 0; x < pressure[0].length; x++) {
                                pressure[y][x] = 0;
                                temperatureGrid[y][x] = 70;
                            }
                        }

                        String[] rows = parts[1].split("-");
                        for (int y = 0; y < rows.length; y++) {
                            String[] elements = rows[y].split(",");
                            for (int x = 0; x < elements.length; x++) {
                                int elementId = Integer.parseInt(elements[x]);
                                grid[y][x] = (elementId == 0) ? null : createElementInstance(Element.getElementById(elementId).getSimpleName());
                            }
                        }

                        repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });



        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(e -> {
            if (username == null || password == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Please sign in first.", "Upload Simulation", JOptionPane.WARNING_MESSAGE);
            } else {
                String content = JOptionPane.showInputDialog(null, "Name your simulation:", "Upload Simulation", JOptionPane.PLAIN_MESSAGE);
                String REGEX = "^[a-zA-Z0-9. ]*$";
                Pattern PATTERN = Pattern.compile(REGEX);
                Matcher matcher = PATTERN.matcher(content);
                boolean result = matcher.matches();
                if (!result) {
                    JOptionPane.showMessageDialog(new JFrame(), "The name cannot contain non-alphanumeric characters!", "Upload Simulation", JOptionPane.WARNING_MESSAGE);
                } else if (content.isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame(), "You must enter a name!", "Upload Simulation", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        StringBuilder exported = new StringBuilder();

                        exported.append(SIZE).append(":").append(getSize().width).append(":").append(getSize().height).append("|");

                        for (int y = 0; y < grid.length; y++) {
                            for (int x = 0; x < grid[0].length; x++) {
                                Element element = grid[y][x];
                                exported.append((element == null ? "0" : element.getId()));
                                if (x < grid[0].length - 1) exported.append(",");
                            }
                            if (y < grid.length - 1) exported.append("-");
                        }

                        String data = "username=" + URLEncoder.encode(username, "UTF-8") +
                                "&password=" + URLEncoder.encode(password, "UTF-8") +
                                "&name=" + URLEncoder.encode(content, "UTF-8") +
                                "&data=" + URLEncoder.encode(exported.toString(), "UTF-8");
                        URL url = new URL("https://6d6ldeoyebpfbivviclcauaejm.srv.us/TPTJ/upload.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
                        connection.setDoOutput(true);

                        try (OutputStream os = connection.getOutputStream()) {
                            os.write(data.getBytes("UTF-8"));
                        }

                        int responseCode = connection.getResponseCode();

                        if (responseCode != 200) {
                            JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to server!", "Upload Simulation", JOptionPane.ERROR_MESSAGE);
                        } else {
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                                String line;
                                StringBuilder response = new StringBuilder();
                                while ((line = br.readLine()) != null) {
                                    response.append(line);
                                }
                                if (response.toString().equals("OK")) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Uploaded!", "Upload Simulation", JOptionPane.INFORMATION_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(new JFrame(), "Upload error!\n\n" + response, "Upload Simulation", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                            connection.disconnect();
                        }
                    } catch (MalformedURLException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "i dunno what the fuck happened here but it was not good", "Upload Simulation", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to server!", "Upload Simulation", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton loginButton = new JButton("Log In/Sign Up");
        loginButton.addActionListener(e -> {
            JTextField username1 = new JTextField();
            JPasswordField password1 = new JPasswordField();

            JLabel l1 = new JLabel("Username");
            JLabel l2 = new JLabel("Password");
            JPanel pnl = new JPanel();
            pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
            pnl.add(l1);
            pnl.add(Box.createVerticalStrut(10));
            pnl.add(username1);
            pnl.add(Box.createVerticalStrut(10));
            pnl.add(l2);
            pnl.add(Box.createVerticalStrut(10));
            pnl.add(password1);

            int result = JOptionPane.showConfirmDialog(null, pnl, "Log In", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            String pwd = String.valueOf(password1.getPassword());
            String usr = username1.getText();

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String data = "username=" + URLEncoder.encode(usr, "UTF-8") +
                            "&password=" + URLEncoder.encode(pwd, "UTF-8");
                    URL url = new URL("https://6d6ldeoyebpfbivviclcauaejm.srv.us/TPTJ/login.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        os.write(data.getBytes("UTF-8"));
                    }

                    int responseCode = connection.getResponseCode();

                    if (responseCode != 200) {
                        JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to server!", "Log In", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = br.readLine()) != null) {
                                response.append(line);
                            }
                            if (response.toString().equals("OK")) {
                                username = usr;
                                password = pwd;
                                JOptionPane.showMessageDialog(new JFrame(), "Logged in as "+usr+".", "Log In", JOptionPane.INFORMATION_MESSAGE);

                            } else {
                                if (JOptionPane.showConfirmDialog(new JFrame(), "This account does not exist or your password is incorrect! Sign up now?", "Log In", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    try {
                                        String da = "username=" + URLEncoder.encode(usr, "UTF-8") +
                                                "&password=" + URLEncoder.encode(pwd, "UTF-8");
                                        URL url2 = new URL("https://6d6ldeoyebpfbivviclcauaejm.srv.us/TPTJ/signup.php");
                                        HttpURLConnection connection1 = (HttpURLConnection) url2.openConnection();
                                        connection1.setRequestMethod("POST");
                                        connection1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                        connection1.setRequestProperty("Content-Length", String.valueOf(da.length()));
                                        connection1.setDoOutput(true);

                                        try (OutputStream os = connection1.getOutputStream()) {
                                            os.write(da.getBytes("UTF-8"));
                                        }

                                        int responseCode1 = connection1.getResponseCode();

                                        if (responseCode1 != 200) {
                                            JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to server!", "Sign Up", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            try (BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()))) {
                                                String line1;
                                                StringBuilder response1 = new StringBuilder();
                                                while ((line1 = br1.readLine()) != null) {
                                                    response1.append(line1);
                                                }
                                                if (response1.toString().equals("OK")) {
                                                    username = usr;
                                                    password = pwd;
                                                    JOptionPane.showMessageDialog(new JFrame(), "Logged in as " + usr + ".", "Log In", JOptionPane.INFORMATION_MESSAGE);
                                                } else {
                                                    JOptionPane.showMessageDialog(new JFrame(), response1, "Sign Up", JOptionPane.WARNING_MESSAGE);
                                                }
                                            }}

                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (HeadlessException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                }
                            }
                        } catch (UnsupportedEncodingException ex) {
                            throw new RuntimeException(ex);
                        } catch (ProtocolException ex) {
                            throw new RuntimeException(ex);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        connection.disconnect();
                    }
                } catch (MalformedURLException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "i dunno what the fuck happened here but it was not good", "!", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Cannot connect to server!", "Log In", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton aboutButton = new JButton("Credits");
        aboutButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(null, "Based off of Powder Toy, The Powder Toy, Java Powder Game, TheJavaPowder, and The Blocky Toy", "Credits (1/3)", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Testers\n\nIn Person:\nB-J. K. 12th\nC. G. 11th\nN. R. 11th\nK. D. 10th\nM. E. 10th\nJ. E. 10th\nJ. W. 10th\nS. W. 10th\nC. B. 10th\nL. L. 9th\nN. S. 9th\nR. L. 9th\n\nOnline:\nSunny da moth\nloopy\nIEATDIRT\nThatoneguy\nJosh", "Credits (2/3)", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Programmed by aaccbb80 during Computer Science\n(thank you Mr. C!)\n\nmade with <3 by aaccbb", "Credits (3/3)", JOptionPane.INFORMATION_MESSAGE);

        });

        /*JButton heatButton = new JButton("Heat");
        aboutButton.addActionListener(e -> {
            selectedElement = Heat;
        });

        JButton coolButton = new JButton("Cool");
        aboutButton.addActionListener(e -> {
            selectedElement = Cool;
        });*/

        tab9.add(downloadButton);
        tab9.add(uploadButton);

        
        tab9.add(importButton);
        tab9.add(saveButton);
        //tab7.add(heatButton);
        //tab7.add(coolButton);
        tab7.add(consoleButton);
        tab7.add(replaceButton);
        tab7.add(systemButton);
        tab7.add(HUDButton);
        tab7.add(aboutButton);
        tab9.add(loginButton);
        tab9.add(pauseButton);
        tab9.add(clearButton);

        pane.addTab("Simulation", tab9);
        pane.addTab("Solids", tab1);
        pane.addTab("Liquids", tab2);
        pane.addTab("Powders", tab3);
        pane.addTab("Gasses", tab4);
        pane.addTab("Radioactive", tab8);
        pane.addTab("Electricity", tab6);
        pane.addTab("Special", tab5);
        pane.addTab("Tools", tab7);

        hover1(tab1);
        hover1(tab2);
        hover1(tab3);
        hover1(tab4);
        hover1(tab5);
        hover1(tab6);
        hover1(tab7);
        hover1(tab8);
        hover1(tab9);

        frame.add(pane, BorderLayout.SOUTH);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerUpdate = 1_000_000_000.0 / 100.0;
        double delta = 0;
        pane.setSelectedIndex(1);

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            
                if (mouseHeld) {
                    if (mouseButtonPressed == MouseEvent.BUTTON1) {
                        addElement(mouseX, mouseY, selectedElement);
                    }
                }
            
                while (delta >= 1) {
                    if (!paused) {
                    updateGrid();}
                    delta--;
                
                frameCounter++;

                
            }
            repaint();

            try {
                Thread.sleep(2);
            } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);

            }
        }
    }

    private void updateGrid() {
        try {
            for (int y = grid.length - 1; y >= 0; y--) {
                for (int x = 0; x < grid[0].length; x++) {
                    if (grid[y][x] != null) {
                        if (!paused) {
                        grid[y][x].update(this, x, y);
                        }
                    }
                }
            }
            updatePressure();
            updateTemperature();
        } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
        }}
    Color customColor;
    int sinething;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xOffset = getInsets().left;
        int yOffset = getInsets().top;
        g.setColor(Color.BLACK);
        g.fillRect(xOffset,yOffset, WIDTH, HEIGHT);

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] != null) {
                    switch (displayMode%3) {
                        case 0:
                            grid[y][x].draw(g, x * SIZE + xOffset, y * SIZE + yOffset, SIZE, SIZE);
                            break;
                        case 1:
                            int tmpheat = (int) Math.round(Math.max(0, Math.min(255, (getHeatAt(x, y)+58)/5)));
                            g.setColor(new Color(tmpheat,tmpheat,tmpheat));
                            g.fillRect(x * SIZE + xOffset, y * SIZE + yOffset, SIZE, SIZE);
                            break;
                        case 2:
                            int tmppres = (int) Math.round(Math.max(0, Math.min(255, (getPressureAt(x,y))/5)));
                            g.setColor(new Color(tmppres,tmppres,tmppres));
                            g.fillRect(x * SIZE + xOffset, y * SIZE + yOffset, SIZE, SIZE);
                            break;
                    }
                }

            }
        }

        sinething = (int)Math.abs((Math.sin(frameCounter / 100.0) * 255));
        customColor = new Color(sinething, sinething, sinething);
        g.setColor(customColor);

        int brushOutlineX = mouseX * SIZE - (brushSize * SIZE-SIZE) / 2;
        int brushOutlineY = mouseY * SIZE - (brushSize * SIZE-SIZE) / 2;
        g.drawRect(brushOutlineX, brushOutlineY, brushSize * SIZE, brushSize * SIZE);

        if (debugMode) {

            String temp1 = "";
            Element elementAtPosition = getElementAt(mouseX, mouseY);

            if (elementAtPosition != null) {
                temp1 = elementAtPosition.getClass().getSimpleName();
            } else {
                temp1 = "None";
            }
            updateFPS();
            g.setColor(Color.GRAY);
            g.setFont(new Font("Courier New", Font.PLAIN, 11));

            int incrementerthingy = 10;
            int draw = incrementerthingy;
            String text1 = "FPS: " + fps;
            g.drawString(text1, 2, draw);
            draw += incrementerthingy;
            String text = "X, Y: " + mouseX + ", " + mouseY;
            g.drawString(text, 2, draw);
            draw += incrementerthingy;
            String text2 = "Element: " + temp1;
            g.drawString(text2, 2, draw);
            draw += incrementerthingy;
            String text3 = "Pressure: " + Math.round(getPressureAt(mouseX, mouseY) * 100) / 100.0;
            g.drawString(text3, 2, draw);
            draw += incrementerthingy;
            String text4 = "Temperature: " + Math.round(((getHeatAt(mouseX, mouseY) - 32) * 5 / 9) * 100) / 100.0 + "°C";
            String text7 = paused ? "Paused" : "Unpaused";
            if (tempType) {
            text4 = "Temperature: " + Math.round(getHeatAt(mouseX, mouseY) * 100) / 100.0 + "°F";
            }
            g.drawString(text4, 2, draw);
            draw += incrementerthingy;
            int abois = 0;
            if (elementAtPosition != null) {

                abois = elementAtPosition.getWeight();
            }
            String text5 = "Weight: " + abois;
            String text6 = hoveredElement;
            g.drawString(text5, 2, draw);
            draw += incrementerthingy;
            g.drawString(text7, 2, draw);
            g.setColor(new Color(153,153,153,Math.min(nameFrames,255)));
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString(text6, 2+3, this.getSize().height-9);
            g.setFont(new Font("Courier New", Font.PLAIN, 11));
            g.setColor(Color.WHITE);
            draw = incrementerthingy+1;
            g.drawString(text1, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text2, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text3, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text4, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text5, 2+1, draw);
            draw += incrementerthingy;
            g.drawString(text7, 2+1, draw);
            g.setColor(new Color(255,255,255,Math.min(nameFrames,255)));
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString(text6, 2+4, this.getSize().height-8);
            if (!(nameFrames <= 0)) {
                nameFrames--;
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseHeld = true;
        updateMousePosition(e);
        mouseButtonPressed = e.getButton();

        if (mouseButtonPressed == MouseEvent.BUTTON3) {
            eraseElement(mouseX, mouseY);
        } else if (mouseButtonPressed == MouseEvent.BUTTON1) {
            addElement(mouseX, mouseY, selectedElement);
        } else if (mouseButtonPressed == MouseEvent.BUTTON2) {
            selectedElement = getElementAt(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        mouseButtonPressed = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newMouseX = e.getX() / SIZE;
        int newMouseY = e.getY() / SIZE;

        if (mouseButtonPressed == MouseEvent.BUTTON3) {
            drawLine(mouseX, mouseY, newMouseX, newMouseY, null);
        } else if (mouseButtonPressed == MouseEvent.BUTTON1) {
            drawLine(mouseX, mouseY, newMouseX, newMouseY, selectedElement);
        }

        mouseX = newMouseX;
        mouseY = newMouseY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

    private void updateMousePosition(MouseEvent e) {
        mouseX = e.getX() / SIZE;
        mouseY = e.getY() / SIZE;
    }

    private void eraseElement(int x, int y) {
        for (int i = -brushSize / 2; i <= brushSize / 2; i++) {
            for (int j = -brushSize / 2; j <= brushSize / 2; j++) {
                int newX = x + i;
                int newY = y + j;
                if (isInBounds(newX, newY)) {
                    grid[newY][newX] = null;
                }
            }
        }
    }

    private void drawLine(int x0, int y0, int x1, int y1, Element element) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (element != null) {
                addElement(x0, y0, element);
            } else {
                eraseElement(x0, y0);
            }

            if (x0 == x1 && y0 == y1) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    private void addElement(int x, int y, Element element) {

        if (element instanceof Stickman || element instanceof Seed) {

            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] instanceof Stickman || grid[row][col] instanceof Seed ) {

                        return;
                    }
                }
            }
        }

        int oldBrush = brushSize;
        if (element instanceof Stickman || element instanceof Seed) {

            brushSize = 1;

        }
        for (int i = -brushSize / 2; i <= brushSize / 2; i++) {
            for (int j = -brushSize / 2; j <= brushSize / 2; j++) {
                int newX = x + i;
                int newY = y + j;
                if (isInBounds(newX, newY)) {
                    try {
                        Element newElement = element.getClass().getDeclaredConstructor().newInstance();
                        if (isEmpty(newX, newY) | replaceMode) {
                            if (!(element instanceof Heat || element instanceof Cool || element instanceof Spark)) {
                                grid[newY][newX] = newElement;
                                if (element instanceof Fire) {
                                    addHeatAt(x, y, 1000);
                                } else if (element instanceof Plasma) {
                                    addHeatAt(x, y, 5000.0);
                                }else if (element instanceof Liquid_Nitrogen) {
                                    addHeatAt(x, y, -450);
                                }else if (element instanceof Ice) {
                                    addHeatAt(x, y, -40);
                                }else if (element instanceof Lava) {
                                    addHeatAt(x, y, 610);
                                }
                            } else if (element instanceof Heat){
                                addHeatAt(x, y, 1);
                            }else if (element instanceof Cool){
                                addHeatAt(x, y, -1);
                            }else if (element instanceof Spark){
                                Element neighbor = getElementAt(x, y);
                                if (neighbor instanceof Wire) {
                                        ((Wire) neighbor).transferPower(true);
                                }
                            }
                        }
                    } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame(), "Fatal error!\n\n"+e+"\n\nThe program will now exit.", "Error!", JOptionPane.ERROR_MESSAGE); System.exit(0);
                    }
                }
            }
        }
        brushSize = oldBrush;
    }




    public void swapElements(int x1, int y1, int x2, int y2) {
        if (isInBounds(x1, y1) && isInBounds(x2, y2)) {
            Element tempa = grid[y1][x1];
            Element tempb = grid[y2][x2];

            if (tempa != null && (tempb == null || tempb.getWeight() != -1)) {
                if (isEmpty(x2, y2) || tempa.getWeight() > Objects.requireNonNull(tempb).getWeight()) {
                    grid[y2][x2] = tempa;
                    grid[y1][x1] = tempb;
                }
            }
        }
    }





    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FallingSandGame::new);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}