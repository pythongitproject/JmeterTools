package td.gui;

import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.sampler.MongoDBSampler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/9/12/0012.
 */
public class MongoDBSamplerUI extends AbstractSamplerGui {

    private static final Logger log = LoggerFactory.getLogger(MongoDBSamplerUI.class);

    //Host
    private final JLabeledTextField HostField = new JLabeledTextField("Host");
    //port
    private final JLabeledTextField PortField = new JLabeledTextField("Port");
    //username
    private final JLabeledTextField UsernameField = new JLabeledTextField("Username");
    //Password
    private final JLabeledTextField PasswordrField = new JLabeledTextField("Password");
    //db
    private final JLabeledTextField DbField = new JLabeledTextField("db");

    private final JSyntaxTextArea ScriptField = new JSyntaxTextArea(25, 50);

    //请求报文Message
    private final JLabel textArea = new JLabel("Script");
    private final JTextScrollPane textPanel = new JTextScrollPane(ScriptField);

    public MongoDBSamplerUI() {
        super();
        this.init();
    }

    /**
     * 初始化页面元素
     */
    private void init() {
        log.info("Initializing the UI.");
        setLayout(new BorderLayout());
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);
        JPanel mainPanel = new VerticalPanel();
        add(mainPanel, BorderLayout.CENTER);

        JPanel DPanel = new JPanel();
        DPanel.setLayout(new GridLayout(3, 2));
        DPanel.add(HostField);
        DPanel.add(PortField);
        DPanel.add(UsernameField);
        DPanel.add(PasswordrField);
        DPanel.add(DbField);

        JPanel ControlPanel = new VerticalPanel();
        ControlPanel.add(DPanel);
        ControlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Parameters"));
        mainPanel.add(ControlPanel);

        JPanel ContentPanel = new VerticalPanel();
        JPanel MessageFieldContentPanel = new JPanel(new BorderLayout());
        MessageFieldContentPanel.add(this.textArea, BorderLayout.NORTH);
        MessageFieldContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(MessageFieldContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Content"));
        mainPanel.add(ContentPanel);
    }


    @Override
    public String getLabelResource() {
        throw new IllegalStateException("This shouldn't be called");
    }

    @Override
    public TestElement createTestElement() {
        MongoDBSampler mongoDBSampler = new MongoDBSampler();
        this.setupSamplerProperties(mongoDBSampler);
        return mongoDBSampler;
    }

    private void setupSamplerProperties(MongoDBSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setHost(this.HostField.getText());
        sampler.setPort(this.PortField.getText());
        sampler.setUsername(this.UsernameField.getText());
        sampler.setPassword(this.PasswordrField.getText());
        sampler.setDb(this.DbField.getText());
        sampler.setScript(this.ScriptField.getText());
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        MongoDBSampler sampler = (MongoDBSampler)element;
        this.HostField.setText(sampler.getHost());
        this.PortField.setText(sampler.getPort());
        this.UsernameField.setText(sampler.getUsername());
        this.PasswordrField.setText(sampler.getPassword());
        this.DbField.setText(sampler.getDb());
        this.ScriptField.setText(sampler.getScript());
    }

    @Override
    public void clearGui() {
        super.clearGui();
        this.HostField.setText("");
        this.PortField.setText("");
        this.UsernameField.setText("");
        this.PasswordrField.setText("");
        this.DbField.setText("");
        this.ScriptField.setText("");
    }

    @Override
    public String getStaticLabel() {
        return "MongoDB Sampler";
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        MongoDBSampler sampler = (MongoDBSampler) testElement;
        this.setupSamplerProperties(sampler);
    }
}
