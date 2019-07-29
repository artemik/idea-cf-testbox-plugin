package idea.plugin.cf.testbox.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import idea.plugin.cf.testbox.config.TestBoxConfig;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginSettings implements Configurable {

    private TestBoxConfig testBoxConfig;
    private PluginSettingsView view;

    public PluginSettings(TestBoxConfig testBoxConfig) {
        this.testBoxConfig = testBoxConfig;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "CF TestBox Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        String currentHost = testBoxConfig.getHost();
        view = new PluginSettingsView(currentHost);
        return view;
    }

    @Override
    public boolean isModified() {
        return testBoxConfig.getHost() == null || !testBoxConfig.getHost().equalsIgnoreCase(view.getHostChosen());
    }

    @Override
    public void apply() throws ConfigurationException {
        testBoxConfig.setHost(view.getHostChosen());
    }
}
