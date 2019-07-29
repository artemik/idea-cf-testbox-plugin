package idea.plugin.cf.testbox.ui;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.IdeBorderFactory;
import idea.plugin.cf.testbox.enums.HostOptions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class PluginSettingsView extends JPanel {

    private String hostChosen;

    public PluginSettingsView(String currentHost) {
        super(new BorderLayout());

        this.hostChosen = currentHost;

        add(createHostPanel(), BorderLayout.NORTH);
    }

    @NotNull
    private JPanel createHostPanel() {
        JPanel root = createRootPanel();

        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < HostOptions.values().length; i++) {
            addHostChoiceRadioButton(root, buttonGroup, i, HostOptions.values()[i].getValue());
        }
        addHostChoiceTextRadioButton(root, buttonGroup, HostOptions.values().length, HostOptions.DOCKER_IP.getValue());

        return root;
    }

    @NotNull
    private JPanel createRootPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(IdeBorderFactory.createTitledBorder("Host:"));
        root.setPreferredSize(new Dimension((int) root.getPreferredSize().getWidth(), 40 * 3));
        return root;
    }

    private void addHostChoiceRadioButton(JPanel root, ButtonGroup buttonGroup, int gridy, String value) {
        JRadioButton button = new JRadioButton(value);
        button.setSelected(value.equalsIgnoreCase(hostChosen));
        button.addActionListener(e -> setHostChosen(value));

        buttonGroup.add(button);

        root.add(button, createHostChoiceConstraints(gridy));
    }

    private void addHostChoiceTextRadioButton(JPanel root, ButtonGroup buttonGroup, int gridy, String defaultValue) {
        JPanel hostChoiceTextRadioButton = createHostChoiceTextRadioButton(buttonGroup, defaultValue);
        root.add(hostChoiceTextRadioButton, createHostChoiceConstraints(gridy));
    }

    @NotNull
    private JPanel createHostChoiceTextRadioButton(ButtonGroup buttonGroup, String defaultValue) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.LINE_AXIS));

        JRadioButton button = new JRadioButton();
        button.setSelected(!HostOptions.fromValue(hostChosen).isPresent());

        buttonGroup.add(button);
        root.add(button);

        JTextField textField = new JTextField(!(hostChosen == null || hostChosen.isEmpty()) ? hostChosen : defaultValue, 30);
        textField.setMaximumSize(textField.getPreferredSize());
        textField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                if (button.isSelected()) {
                    setHostChosen(textField.getText());
                }
            }
        });
        root.add(textField);

        button.addActionListener(e -> setHostChosen(textField.getText()));

        return root;
    }

    @NotNull
    private GridBagConstraints createHostChoiceConstraints(int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        return constraints;
    }

    public String getHostChosen() {
        return hostChosen;
    }

    public void setHostChosen(String hostChosen) {
        this.hostChosen = hostChosen;
    }
}
