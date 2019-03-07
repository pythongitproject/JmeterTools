package td.gui;

import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.sampler.RedisSampler;
import td.sampler.RedisSampler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/7/6/0006.
 */
public class RedisSamplerUI extends AbstractSamplerGui {

    private static final Logger log = LoggerFactory.getLogger(RedisSamplerUI.class);

    /**
     * 基本参数
     */
    //Host
    private final JLabeledTextField HostField = new JLabeledTextField("Host：");
    //port
    private final JLabeledTextField PortField = new JLabeledTextField("Port：");
    //username
    private final JLabeledTextField PasswordField = new JLabeledTextField("Password：");
    //password
    private final JLabeledTextField DbField = new JLabeledTextField("db：");

    //Hash
    private final JLabeledTextField HashField = new JLabeledTextField("Hash：");
    //Key
    private final JLabeledTextField KeyField = new JLabeledTextField("Key：");
    //Value
    private final JSyntaxTextArea ValueField = new JSyntaxTextArea(25, 50);
    //请求报文Message
    private final JLabel textArea = new JLabel("Value：");
    private final JTextScrollPane textPanel = new JTextScrollPane(ValueField);

    public RedisSamplerUI() {
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
        DPanel.add(PasswordField);
        DPanel.add(DbField);
        DPanel.add(HashField);
        DPanel.add(KeyField);
        DPanel.add(ValueField);


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
    public TestElement createTestElement() {
        RedisSampler sampler = new RedisSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }

    @Override
    public void clearGui() {
        super.clearGui();
        this.DbField.setText("");
        this.HashField.setText("");
        this.KeyField.setText("");
        this.ValueField.setText("");
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        RedisSampler sampler = (RedisSampler)element;
        this.HostField.setText(sampler.getHost());
        this.PortField.setText(sampler.getPort());
        this.PasswordField.setText(sampler.getPassword());
        this.DbField.setText(sampler.getDb());
        this.HashField.setText(sampler.getHash());
        this.KeyField.setText(sampler.getKey());
        this.ValueField.setText(sampler.getValue());
    }

    private void setupSamplerProperties(RedisSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setHost(this.HostField.getText());
        sampler.setPort(this.PortField.getText());
        sampler.setPassword(this.PasswordField.getText());
        sampler.setDb(this.DbField.getText());
        sampler.setHash(this.HashField.getText());
        sampler.setKey(this.KeyField.getText());
        sampler.setValue(this.ValueField.getText());
    }

    @Override
    public String getStaticLabel() {
        return "Redis Sampler";
    }

    @Override
    public String getLabelResource() {
        throw new IllegalStateException("This shouldn't be called");
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        RedisSampler sampler = (RedisSampler) testElement;
        this.setupSamplerProperties(sampler);
    }
}
