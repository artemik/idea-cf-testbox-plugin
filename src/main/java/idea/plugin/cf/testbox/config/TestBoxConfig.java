package idea.plugin.cf.testbox.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "TestBoxConfig", storages = @Storage("cf-testbox-plugin.xml"), defaultStateAsResource = true)
public class TestBoxConfig implements PersistentStateComponent<TestBoxConfig> {

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static TestBoxConfig getInstance() {
        return ServiceManager.getService(TestBoxConfig.class);
    }

    @Nullable
    @Override
    public TestBoxConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TestBoxConfig testBoxConfig) {
        XmlSerializerUtil.copyBean(testBoxConfig, this);
    }
}
