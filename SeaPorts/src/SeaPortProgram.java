import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SeaPortProgram extends JFrame {
    private static Font font = new Font("Monospace", 0, 18);
    private World world;
    private ArrayList<String> searchResults;


    public SeaPortProgram(String title) throws HeadlessException {
        super(title);
        File file = selectFileMenu();
        world = new World("Test", 1, 0, file);
        showGUI();
    }

    public static void main(String[] args) {
        new SeaPortProgram("SeaWorld");
    }

    private void showGUI() {

        GridBagConstraints gridFormat = new GridBagConstraints();
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel midPanel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel("Search by: ");
        label.setFont(font);
        topPanel.add(label);

        String[] options = {"Person", "Passenger Ship", "Cargo Ship", "Dock", "Job"};
        JComboBox searchOptions = new JComboBox(options);
            searchOptions.setFont(font);
            gridFormat.gridx = 1;
            gridFormat.gridy = 0;
            topPanel.add(searchOptions, gridFormat);

        String searchChoice = (String) searchOptions.getSelectedItem();
//        System.out.println(searchChoice);
        if (searchChoice.equals("Person")) {
            options = new String[]{"Name", "Index", "Skill"};
        } else
            options = new String[]{"Name", "Index"};
        JComboBox searchModifiers = new JComboBox(options);
            searchModifiers.setFont(font);
            gridFormat.gridx = 2;
            gridFormat.gridy = 0;
            gridFormat.insets = new Insets(0, 10, 0, 0);
            topPanel.add(searchModifiers, gridFormat);

        JTextField searchField = new JTextField();
            searchField.setFont(font);
            searchField.setPreferredSize(new Dimension(250, 40));
            gridFormat.insets = new Insets(10, 0, 10, 0);
            gridFormat.gridy = 2;
            gridFormat.gridx = 0;
            gridFormat.gridwidth = 3;
            topPanel.add(searchField, gridFormat);

        JScrollPane midScroll = new JScrollPane();
            midScroll.setColumnHeaderView(new JLabel("Search Results"));
            midScroll.setPreferredSize(new Dimension(400, 300));
            gridFormat = new GridBagConstraints();
            gridFormat.gridx = 1;
            midPanel.add(midScroll, gridFormat);

        JButton button = new JButton("Search!");
            button.setFont(font);
            gridFormat.gridy = 2;
            gridFormat.gridx = 3;
            gridFormat.gridwidth = 0;
            button.addActionListener(e -> {
                String searchType, searchAttribute, searchKeyWord;
                searchType = searchOptions.getSelectedItem().toString();
                searchAttribute = searchModifiers.getSelectedItem().toString();
                searchKeyWord = searchField.getText();
                System.out.println(searchType + searchAttribute + searchKeyWord);
                searchResults = search(searchType, searchAttribute, searchKeyWord);
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    for (String result : searchResults) {
                        stringBuilder.append(result);
                        stringBuilder.append("\n");
                    }
                } catch (NullPointerException e1) {
                    stringBuilder.append("No Search Results Found!");
                }
                JTextArea textArea = new JTextArea(stringBuilder.toString());
                    textArea.setFont(font);
                midScroll.setViewportView(textArea);
            });
            topPanel.add(button, gridFormat);


        JScrollPane scrollPane = new JScrollPane();
            scrollPane.setColumnHeaderView(new JLabel("Data Display"));
            String printOut = world.printOut();
            JTextArea results = new JTextArea();
            results.setFont(font);
            results.setText(printOut);
            scrollPane.setViewportView(results);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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

    private ArrayList<String> search(String searchType, String searchAttribute, String searchKeyWord) {

        ArrayList<? extends Thing> searchResults;
        ArrayList<String> returnedResults = new ArrayList<>();
        searchResults = world.search(searchType, searchAttribute, searchKeyWord);

        if (searchResults.isEmpty())
            return null;

        for (Thing result : searchResults) {
            returnedResults.add(result.formatPrint());
        }
        return returnedResults;

    }

    private static File selectFileMenu() {

        JFileChooser fileChooser = new JFileChooser("./PopulatedData/");
        fileChooser.setDialogTitle("Choose a data file: ");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only", "txt");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showDialog(null, "Open");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
