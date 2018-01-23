import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/** Constructs and displays the GUI interface of the application. Also passes the search information to World.java
 * which fetches a list of search results to display to user. GUI interface contains two JComboBoxes for
 * search modifiers, a JTextField for search input and a submit button. Two JScroll panels included to display
 * the data file content parsed by World.java and the other panel to display search results. */

public class SeaPortProgram extends JFrame {
    private static Font font = new Font("Monospace", Font.PLAIN, 18);
    private World world;
    private ArrayList<String> searchResults;


    private SeaPortProgram(String title) throws HeadlessException {
        super(title);
        File file = selectFileMenu();
        if (file != null) {
            world = new World("Test", file);
            showGUI();
        }
    }

    public static void main(String[] args) {
        new SeaPortProgram("SeaWorld");
    }

    /** Constructs and displays the GUI interface*/
    private void showGUI() {

        GridBagConstraints gridFormat = new GridBagConstraints();   //GUI component formatter
        JPanel topPanel = new JPanel(new GridBagLayout());  //panel for search elements
        JPanel midPanel = new JPanel(new GridBagLayout());  //panel for data display components

        JLabel label = new JLabel("Search by: ");
            label.setFont(font);
            topPanel.add(label);

        //Dropdown list of search subjects
        String[] options = {"Person", "Passenger Ship", "Cargo Ship", "Dock", "Job"};
        JComboBox<String> searchSubjects = new JComboBox<>(options);
            searchSubjects.setSelectedIndex(0);
            searchSubjects.setFont(font);
            gridFormat.gridx = 1;
            gridFormat.gridy = 0;
            topPanel.add(searchSubjects, gridFormat);

        //Dropdown list for search subject attributes
        options = new String[]{"Index", "Name", "Skill"};
        JComboBox<String> searchAttributes = new JComboBox<>(options);
        searchSubjects.addActionListener(e -> {
            /* Search attributes change dynamically based on the search subject currently selected
             * Only Persons and Jobs can be search for skills -- dynamic change reduces need of error
             * handling*/
            if (searchAttributes.getItemCount() < 3){   //Skill is not currently on dropdown
                if (Objects.equals(searchSubjects.getSelectedItem(), "Person") || Objects.equals(searchSubjects.getSelectedItem(), "Job")) {
                    searchAttributes.addItem("Skill");
                }

            }else if(!(Objects.equals(searchSubjects.getSelectedItem(), "Person") || Objects.equals(searchSubjects.getSelectedItem(), "Job")))
                //Skill is on dropdown but Ship or Dock is selected as subject
                searchAttributes.removeItemAt(searchAttributes.getItemCount()-1); //remove skill from dropdown
        });
            searchAttributes.setFont(font);
            gridFormat.gridx = 2;
            gridFormat.gridy = 0;
            gridFormat.insets = new Insets(0, 10, 0, 0);
            topPanel.add(searchAttributes, gridFormat);

        JTextField searchField = new JTextField();
            searchField.setFont(font);
            searchField.setPreferredSize(new Dimension(250, 40));
            gridFormat.insets = new Insets(10, 0, 10, 0);
            gridFormat.gridy = 2;
            gridFormat.gridx = 0;
            gridFormat.gridwidth = 2;
            gridFormat.anchor = GridBagConstraints.WEST;
            topPanel.add(searchField, gridFormat);

        //Creates the bottom panel for scrollable display
        JScrollPane midScroll = new JScrollPane();
            midScroll.setColumnHeaderView(new JLabel("Search Results"));
            midScroll.setPreferredSize(new Dimension(400, 300));
            gridFormat = new GridBagConstraints();
            gridFormat.gridx = 1;
            midPanel.add(midScroll, gridFormat);

        JButton button = new JButton("Search!");
            button.setFont(font);
            gridFormat.gridy = 2;
            gridFormat.gridx = 2;
            gridFormat.insets = new Insets(10, 10, 10, 10);
            button.addActionListener(e -> {
                /* Handles action when search is submitted. Search subject, attribute, and keyword
                 * is passed to search method for returned list of search results to be displayed*/
                String searchSub, searchAttribute, searchKeyWord;
                searchSub = Objects.requireNonNull(searchSubjects.getSelectedItem()).toString();
                searchAttribute = Objects.requireNonNull(searchAttributes.getSelectedItem()).toString();
                searchKeyWord = searchField.getText();
//                System.out.println(searchSub + searchAttribute + searchKeyWord);
                searchResults = search(searchSub, searchAttribute, searchKeyWord);
                int searchResultsSize;

                StringBuilder stringBuilder = new StringBuilder();
                //error handling in case search results returned with no hits
                try {
                    searchResultsSize = searchResults.size();
                    for (String result : searchResults) {
                        stringBuilder.append(result);   //each string result is appended to GUI
                        stringBuilder.append("\n");
                    }
                } catch (NullPointerException e1) {
                    stringBuilder.append("No Search Results Found!");   //no hit result error handling
                    searchResultsSize = 0;
                }
                JTextArea textArea = new JTextArea(stringBuilder.toString());
                    textArea.setEditable(false);
                    textArea.setFont(font);
                midScroll.setColumnHeaderView(new JLabel("Search Results (" + searchResultsSize +")"));
                midScroll.setViewportView(textArea);
            });
            topPanel.add(button, gridFormat);


        JScrollPane scrollPane = new JScrollPane();
            scrollPane.setColumnHeaderView(new JLabel("Data Display (" +world.getNumPorts()+ " ports)"));
            String printOut = world.printOut();
            JTextArea results = new JTextArea();
                results.setEditable(false);
                results.setFont(font);
                results.setText(printOut);
            scrollPane.setViewportView(results);    //adds text area to scroll panel viewport
            scrollPane.setPreferredSize(new Dimension(400, 300));
            gridFormat = new GridBagConstraints();
            gridFormat.gridx = 0;
            midPanel.add(scrollPane, gridFormat);


        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /** Stores the returned search results from World.java into an ArrayList*/
    private ArrayList<String> search(String searchType, String searchAttribute, String searchKeyWord) {

        ArrayList<? extends Thing> searchResults;
        ArrayList<String> returnedResults = new ArrayList<>();
        searchResults = world.search(searchType, searchAttribute, searchKeyWord);

        if (searchResults.isEmpty() || searchResults.contains(null))
            return null;    //error handling
        searchResults.sort(Thing::compareTo);   //sorts the result list based on custom Thing::compareTo sort
        for (Thing result : searchResults) {
            returnedResults.add(result.formatPrint());
        }
        return returnedResults;

    }

    /** Displays the file selector GUI to upload the data file for the application*/
    private static File selectFileMenu() {

        JFileChooser fileChooser = new JFileChooser("./PopulatedData/");    //sets current directory to start at
        fileChooser.setDialogTitle("Choose a data file: ");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only", "txt"); //only txt files
        fileChooser.addChoosableFileFilter(filter);     //applies file extension filter

        int returnValue = fileChooser.showDialog(null, "Open");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();   //returns selected file
        }
        return null;
    }
}
