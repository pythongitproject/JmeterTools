package td.gui;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import td.sampler.RedisSampler;
import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/7/6/0006.
 */
public class RedisSamplerUI extends AbstractSamplerGui {

    private static final Logger log = LoggerFactory.getLogger(RedisSamplerUI.class);

    //账号；密码；所在库
    private JTextField Host;
    private JTextField Port;
    private JTextField Password;
    private JTextField Db;

    //操作类型；对应得键值对
    private JLabeledChoice RType;
    private JLabeledChoice RDoType;
    private JTextField Hash;
    private JTextField Key;
    // area区域
    private JSyntaxTextArea ValueBody = JSyntaxTextArea.getInstance(30, 50);
    // 滚动条
    private JTextScrollPane textPanel = JTextScrollPane.getInstance(ValueBody);
//    private JLabel textArea = new JLabel("Value:");

    private JPanel getHostPanel() {
        Host = new JTextField(4);
        JLabel label = new JLabel("Host:");
        label.setLabelFor(Host);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Host, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPortPanel() {
        Port = new JTextField(2);
        JLabel label = new JLabel("Port:");
        label.setLabelFor(Port);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Port, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPasswordPanel() {
        Password = new JTextField(2);
        JLabel label = new JLabel("Password:");
        label.setLabelFor(Password);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Password, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getDbPanel() {
        Db = new JTextField(2);
        JLabel label = new JLabel("Host:");
        label.setLabelFor(Db);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Db, BorderLayout.CENTER);
        return panel;
    }

    protected Component getRTypeAndRDoType() {
        JLabel rlabel = new JLabel("类型:");
        RType =  new JLabeledChoice();
        RType.addValue("String");
        RType.addValue("Hash");
        RType.addValue("Set");
        RType.addValue("ZSet");
        JPanel panel = new HorizontalPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(rlabel, BorderLayout.WEST);
        panel.add(RType, BorderLayout.CENTER);
        JLabel rdlabel = new JLabel("操作:");
        RDoType =  new JLabeledChoice();
        RDoType.addValue("SET");
        RDoType.addValue("GET");
        RDoType.addValue("DEL");
        panel.add(rdlabel, BorderLayout.WEST);
        panel.add(RDoType, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getHashPanel() {
        Hash = new JTextField(2);
        JLabel label = new JLabel("Hash:");
        label.setLabelFor(Hash);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Hash, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getKeyPanel() {
        Key = new JTextField(2);
        JLabel label = new JLabel("Key:");
        label.setLabelFor(Key);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(Key, BorderLayout.CENTER);
        return panel;
    }


    protected Component getValueContent(){
        JPanel panel = new HorizontalPanel();
        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
//        messageContentPanel.add(this.textArea, BorderLayout.NORTH);
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Value:"));
        panel.add(ContentPanel);
        return panel;
    }

    public RedisSamplerUI() {
        super();
        this.init();
    }

    /**
     * 初始化页面元素
     */
    private void init() {
        creatPanel();
    }

    public void creatPanel() {
        JPanel settingPanel = new VerticalPanel(5, 0);
        settingPanel.add(getHostPanel());
        settingPanel.add(getPortPanel());
        settingPanel.add(getPasswordPanel());
        settingPanel.add(getDbPanel());
        settingPanel.add(getRTypeAndRDoType());
        settingPanel.add(getHashPanel());
        settingPanel.add(getKeyPanel());
        settingPanel.add(getValueContent());
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
        log.info("createTestElement");
        RedisSampler sampler = new RedisSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }

    @Override
    public String getLabelResource() {
        log.info("getLabelResource");
// TODO Auto-generated method stub
        throw new IllegalStateException("This shouldn't be called");
    }

    @Override
    public String getStaticLabel() {
        log.info("getStaticLabel");
        return "Redis Sampler";
    }

    /*
     * 把界面的数据移到Sampler中，与configure方法相反
     * */
    @Override
    public void modifyTestElement(TestElement testElement) {
        log.info("modifyTestElement");
        RedisSampler sampler = (RedisSampler) testElement;
        this.setupSamplerProperties(sampler);
    }

    private void setupSamplerProperties(RedisSampler sampler) {
        this.configureTestElement(sampler);
        log.info("setupSamplerProperties: hash{}",this.Hash.getText());
        log.info("setupSamplerProperties: key{}",this.Key.getText());
        log.info("setupSamplerProperties: value{}",this.ValueBody.getText());
        sampler.setHost(this.Host.getText());
        sampler.setPort(this.Port.getText());
        sampler.setPassword(this.Password.getText());
        sampler.setDb(this.Db.getText());
        sampler.setRType(this.RType.getText());
        sampler.setRDoType(this.RDoType.getText());
        sampler.setHash(this.Hash.getText());
        sampler.setKey(this.Key.getText());
        sampler.setValueBody(this.ValueBody.getText());
    }

    /*
     * reset新界面的时候调用，这里可以填入界面控件中需要显示的一些缺省的值
     * */
    @Override
    public void clearGui() {
        super.clearGui();
        log.info("clearGui");
        Host.setText("node.td-k8s.com");
        Port.setText("1379");
        Password.setText("mWRK6joVy5No");
        Db.setText("5");
        RType.setText("String");
        RDoType.setText("SET");
        Hash.setText("");
        Key.setText("");
        ValueBody.setText("");
    }

    /*
     * 把Sampler中的数据加载到界面中
     * */
    @Override
    public void configure(TestElement element) {
        super.configure(element);
        RedisSampler sampler = (RedisSampler) element;
        log.info("configure: hash{}",sampler.getHash());
        log.info("configure: key{}",sampler.getKey());
        log.info("configure: value{}",sampler.getValueBody());
        // jmeter运行后，保存参数，不然执行后，输入框会情况
        this.Host.setText(sampler.getHost());
        this.Port.setText(sampler.getPort());
        this.Password.setText(sampler.getPassword());
        this.Db.setText(sampler.getDb());
        this.RType.setText(sampler.getRType());
        this.RDoType.setText(sampler.getRDoType());
        this.Hash.setText(sampler.getHash());
        this.Key.setText(sampler.getKey());
        this.ValueBody.setText(sampler.getValueBody());
    }



}
