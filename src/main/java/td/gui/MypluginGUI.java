package td.gui;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledChoice;
import td.sampler.MyPluginSampler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/7/5/0005.
 */
public class MypluginGUI extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;

    //DOMAIN
    private JTextField DOMAIN;
    //PORT
    private JTextField PORT;
    //USERNAME
    private JTextField USERNAME;
    //password
    private JTextField PWD;
    private JCheckBox useKeepAlive;
    private JLabeledChoice TYPE;

    // area区域
    private JSyntaxTextArea postBodyContent = JSyntaxTextArea.getInstance(30, 50);
    // 滚动条
    private JTextScrollPane textPanel = JTextScrollPane.getInstance(postBodyContent);
    private JLabel textArea = new JLabel("Message");

    private JPanel getDOMAINPanel() {
        DOMAIN = new JTextField(10);
        JLabel label = new JLabel("IP"); // $NON-NLS-1$
        label.setLabelFor(DOMAIN);

        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(DOMAIN, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPORTPanel() {
        PORT = new JTextField(10);

        JLabel label = new JLabel(JMeterUtils.getResString("web_server_PORT")); // $NON-NLS-1$
        label.setLabelFor(PORT);

        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(PORT, BorderLayout.CENTER);

        return panel;
    }

    protected JPanel getUSERNAME() {

        // CONTENT_ENCODING
        USERNAME = new JTextField(10);
        JLabel USERNAMELabel = new JLabel("USERNAME"); // $NON-NLS-1$
        USERNAMELabel.setLabelFor(USERNAME);

        JPanel panel = new HorizontalPanel();
        panel.setMinimumSize(panel.getPreferredSize());
        panel.add(Box.createHorizontalStrut(5));

        panel.add(USERNAMELabel,BorderLayout.WEST);
        panel.add(USERNAME,BorderLayout.CENTER);
        panel.setMinimumSize(panel.getPreferredSize());
        return panel;
    }

    protected Component getPWD() {
        PWD = new JTextField(15);

        JLabel label = new JLabel(JMeterUtils.getResString("PWD")); //$NON-NLS-1$
        label.setLabelFor(PWD);

        JPanel PWDPanel = new HorizontalPanel();
        PWDPanel.add(label);
        PWDPanel.add(PWD);

        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(PWDPanel);

        return panel;
    }

    protected Component getMethodAndUseKeepAlive() {
        useKeepAlive = new JCheckBox(JMeterUtils.getResString("use_keepalive")); // $NON-NLS-1$
        useKeepAlive.setFont(null);
        useKeepAlive.setSelected(true);
        JPanel optionPanel = new HorizontalPanel();
        optionPanel.setMinimumSize(optionPanel.getPreferredSize());
        optionPanel.add(useKeepAlive);
        String Marry[] = { "GET", "POST" };
        TYPE = new JLabeledChoice(JMeterUtils.getResString("method"), // $NON-NLS-1$
                Marry, true, false);
        // method.addChangeListener(this);
        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(optionPanel,BorderLayout.WEST);
        panel.add(TYPE,BorderLayout.WEST);
        return panel;
    }

    protected Component getpostBodyContent() {

        JPanel panel = new HorizontalPanel();
        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
        messageContentPanel.add(this.textArea, BorderLayout.NORTH);
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Content"));
        panel.add(ContentPanel);
        return panel;
    }

    public MypluginGUI() {
        super();
        init();
    }

    private void init() { // WARNING: called from ctor so must not be overridden (i.e. must be private or
        // final)
        creatPanel();
    }

    public void creatPanel() {
        JPanel settingPanel = new VerticalPanel(5, 0);
        settingPanel.add(getDOMAINPanel());
        settingPanel.add(getPORTPanel());
        settingPanel.add(getUSERNAME());
        settingPanel.add(getPWD());
        settingPanel.add(getMethodAndUseKeepAlive());
        settingPanel.add(getpostBodyContent());
        JPanel dataPanel = new JPanel(new BorderLayout(5, 0));

        dataPanel.add(settingPanel, BorderLayout.NORTH);
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title
        add(dataPanel, BorderLayout.CENTER);
    }

    /*
     * 创建一个新的Sampler，然后将界面中的数据设置到这个新的Sampler实例中
     * */
    @Override
    public TestElement createTestElement() {
        // TODO Auto-generated method stub
        MyPluginSampler sampler = new MyPluginSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public String getLabelResource() {
        // TODO Auto-generated method stub
        throw new IllegalStateException("This shouldn't be called");
        // return "example_title";
        // 从messages_zh_CN.properties读取
    }

    /**
     * gui标题
     * @return
     */
    @Override
    public String getStaticLabel() {
        return "RabbitMQ TEST";
    }

    /*
     * 把界面的数据移到Sampler中，与configure方法相反
     * */
    @Override
    public void modifyTestElement(TestElement arg0) {
        // TODO Auto-generated method stub
        arg0.clear();
        configureTestElement(arg0);

        arg0.setProperty(MyPluginSampler.DOMAIN, DOMAIN.getText());
        arg0.setProperty(MyPluginSampler.PORT, PORT.getText());
        arg0.setProperty(MyPluginSampler.USERNAME, USERNAME.getText());
        arg0.setProperty(MyPluginSampler.PWD, PWD.getText());
        arg0.setProperty(MyPluginSampler.TYPE, TYPE.getText());
        arg0.setProperty(MyPluginSampler.postBodyContent, postBodyContent.getText());
        arg0.setProperty(new BooleanProperty(MyPluginSampler.useKeepAlive, useKeepAlive.isSelected()));

    }

    /*
     * reset新界面的时候调用，这里可以填入界面控件中需要显示的一些缺省的值
     * */
    @Override
    public void clearGui() {
        super.clearGui();

        DOMAIN.setText("");
        PORT.setText("");
        USERNAME.setText("");
        PWD.setText("");
        method.setText("GET");
        postBodyContent.setText("");
        useKeepAlive.setSelected(true);

    }

    /*
     * 把Sampler中的数据加载到界面中
     * */
    @Override
    public void configure(TestElement element) {

        super.configure(element);
        // jmeter运行后，保存参数，不然执行后，输入框会情况

        DOMAIN.setText(element.getPropertyAsString(MyPluginSampler.DOMAIN));
        PORT.setText(element.getPropertyAsString(MyPluginSampler.PORT));
        USERNAME.setText(element.getPropertyAsString(MyPluginSampler.USERNAME));
        PWD.setText(element.getPropertyAsString(MyPluginSampler.PWD));
        method.setText("GET");
        postBodyContent.setText(element.getPropertyAsString(MyPluginSampler.postBodyContent));
        useKeepAlive.setSelected(true);

    }

}