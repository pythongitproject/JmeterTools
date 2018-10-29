package td.gui;

import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.sampler.RabbitMQSampler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/7/6/0006.
 */
public class RabbitMQSamplerUI extends AbstractSamplerGui {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQSamplerUI.class);

    /**
     * 基本参数
     */
    //Host
    private final JLabeledTextField HostField = new JLabeledTextField("Host");
    //port
    private final JLabeledTextField PortField = new JLabeledTextField("Port");
    //username
    private final JLabeledTextField VirtualHostField = new JLabeledTextField("VirtualHost");
    //password
    private final JLabeledTextField UsernameField = new JLabeledTextField("Username");
    //Password
    private final JLabeledTextField PasswordrField = new JLabeledTextField("Password");
    //ExchangeType
    private final JLabeledTextField ExchangeTypeField = new JLabeledTextField("ExchangeType");
    //ExchangeName
    private final JLabeledTextField ExchangeNameField = new JLabeledTextField("ExchangeName");
    //QueueName
    private final JLabeledTextField QueueNameField = new JLabeledTextField("QueueName");
    //Routingkey
    private final JLabeledTextField RoutingkeyField = new JLabeledTextField("Routingkey");
    //Durable
    private final JLabeledTextField DurableField = new JLabeledTextField("Durable");


    private final JSyntaxTextArea MessageField = new JSyntaxTextArea(25, 50);
    //请求报文Message
    private final JLabel textArea = new JLabel("Message");
    private final JTextScrollPane textPanel = new JTextScrollPane(MessageField);

    public RabbitMQSamplerUI() {
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
        DPanel.add(VirtualHostField);
        DPanel.add(ExchangeTypeField);
        DPanel.add(ExchangeNameField);
        DPanel.add(QueueNameField);
        DPanel.add(RoutingkeyField);
        DPanel.add(DurableField);


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
        RabbitMQSampler sampler = new RabbitMQSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }

    @Override
    public void clearGui() {
        super.clearGui();
        this.HostField.setText("");
        this.PortField.setText("");
        this.VirtualHostField.setText("");
        this.UsernameField.setText("");
        this.PasswordrField.setText("");
        this.ExchangeTypeField.setText("");
        this.ExchangeNameField.setText("");
        this.MessageField.setText("");
        this.QueueNameField.setText("");
        this.RoutingkeyField.setText("");
        this.DurableField.setText("");

    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        RabbitMQSampler sampler = (RabbitMQSampler)element;
        this.HostField.setText(sampler.getHost());
        this.PortField.setText(sampler.getPort());
        this.VirtualHostField.setText(sampler.getVirtualHost());
        this.UsernameField.setText(sampler.getUsername());
        this.PasswordrField.setText(sampler.getPassword());
        this.ExchangeTypeField.setText(sampler.getExchangeType());
        this.ExchangeNameField.setText(sampler.getExchangeName());
        this.MessageField.setText(sampler.getMessage());
        this.QueueNameField.setText(sampler.getQueueName());
        this.RoutingkeyField.setText(sampler.getRoutingkey());
        this.DurableField.setText(sampler.getDurable());

    }

    private void setupSamplerProperties(RabbitMQSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setHost(this.HostField.getText());
        sampler.setPort(this.PortField.getText());
        sampler.setVirtualHost(this.VirtualHostField.getText());
        sampler.setUsername(this.UsernameField.getText());
        sampler.setPassword(this.PasswordrField.getText());
        sampler.setExchangeType(this.ExchangeTypeField.getText());
        sampler.setExchangeName(this.ExchangeNameField.getText());
        sampler.setMessage(this.MessageField.getText());
        sampler.setQueueName(this.QueueNameField.getText());
        sampler.setRoutingkey(this.RoutingkeyField.getText());
        sampler.setDurable(this.DurableField.getText());

    }

    @Override
    public String getStaticLabel() {
        return "RabbitMQ Sampler";
    }

    @Override
    public String getLabelResource() {
        throw new IllegalStateException("This shouldn't be called");
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        RabbitMQSampler sampler = (RabbitMQSampler) testElement;
        this.setupSamplerProperties(sampler);
    }
}
