package td.gui;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import td.sampler.RedisSampler;

import javax.swing.*;
import java.awt.*;

public class RedisSamplerGUI extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;

    private JTextField host;
    private JTextField port;
    private JTextField pwd;
    private JTextField db;
    private JLabeledChoice rtype;
    private JLabeledChoice rdotype;
    private JTextField hash;
    private JTextField key;

    // area区域
    private JSyntaxTextArea valueContent = JSyntaxTextArea.getInstance(30, 50);
    // 滚动条
    private JTextScrollPane textPanel = JTextScrollPane.getInstance(valueContent);

    private JPanel getHostPanel() {
        host = new JTextField(10);
        JLabel label = new JLabel("host："); // $NON-NLS-1$
        label.setLabelFor(host);

        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(host, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPortPanel() {
        port = new JTextField(10);
        JLabel label = new JLabel("port："); // $NON-NLS-1$
        label.setLabelFor(port);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(port, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPwdPanel() {
        pwd = new JTextField(10);
        JLabel label = new JLabel("pwd："); // $NON-NLS-1$
        label.setLabelFor(pwd);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(pwd, BorderLayout.CENTER);
        return panel;
    }

    protected JPanel getDb() {
        db = new JTextField(10);
        JLabel dbLabel = new JLabel("db："); // $NON-NLS-1$
        dbLabel.setLabelFor(db);

        JPanel panel = new HorizontalPanel();
        panel.setMinimumSize(panel.getPreferredSize());
        panel.add(Box.createHorizontalStrut(5));
        panel.add(dbLabel, BorderLayout.WEST);
        panel.add(db, BorderLayout.CENTER);
        panel.setMinimumSize(panel.getPreferredSize());
        return panel;
    }



    protected Component getRtype() {
        JLabel rlabel = new JLabel("类型:");
        rtype =  new JLabeledChoice();
        rtype.addValue("String");
        rtype.addValue("Hash");
//        rtype.addValue("Set");
//        rtype.addValue("ZSet");

        JLabel rdlabel = new JLabel("操作:");
        rdotype =  new JLabeledChoice();
        rdotype.addValue("SET");
        rdotype.addValue("GET");
        rdotype.addValue("DEL");

        JPanel panel = new HorizontalPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(rlabel,BorderLayout.WEST);
        panel.add(rtype, BorderLayout.CENTER);

        panel.add(rdlabel,BorderLayout.WEST);
        panel.add(rdotype, BorderLayout.CENTER);
        return panel;
    }

    protected Component getHash() {
        hash = new JTextField(15);
        JLabel label = new JLabel("hash："); //$NON-NLS-1$
        label.setLabelFor(hash);
        JPanel hashPanel = new HorizontalPanel();
        hashPanel.add(label);
        hashPanel.add(hash);
        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(hashPanel);
        return panel;
    }

    protected Component getKey() {
        key = new JTextField(15);
        JLabel label = new JLabel("key："); //$NON-NLS-1$
        label.setLabelFor(key);
        JPanel hashPanel = new HorizontalPanel();
        hashPanel.add(label);
        hashPanel.add(key);
        JPanel panel = new HorizontalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(hashPanel);
        return panel;
    }

    protected Component getvalueContent() {
        JPanel panel = new HorizontalPanel();
        JPanel ContentPanel = new VerticalPanel();
        JPanel messageContentPanel = new JPanel(new BorderLayout());
        messageContentPanel.add(this.textPanel, BorderLayout.CENTER);
        ContentPanel.add(messageContentPanel);
        ContentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "value："));
        panel.add(ContentPanel);
        return panel;
    }

    public RedisSamplerGUI() {
        super();
        init();
    }

    private void init() { // WARNING: called from ctor so must not be overridden (i.e. must be private or
        // final)
        creatPanel();
    }

    public void creatPanel() {
        JPanel settingPanel = new VerticalPanel(5, 0);
        settingPanel.add(getHostPanel());
        settingPanel.add(getPortPanel());
        settingPanel.add(getPwdPanel());
        settingPanel.add(getDb());
        settingPanel.add(getRtype());
//        settingPanel.add(getRdotype());
        settingPanel.add(getHash());
        settingPanel.add(getKey());
        settingPanel.add(getvalueContent());
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
        // TODO Auto-generated rtype stub
        RedisSampler sampler = new RedisSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public String getLabelResource() {
        // TODO Auto-generated rtype stub
        throw new IllegalStateException("This shouldn't be called");
        // return "example_title";
        // 从messages_zh_CN.properties读取
    }

    @Override
    public String getStaticLabel() {
        return "Redis sampler";
    }

    /*
     * 把界面的数据移到Sampler中，与configure方法相反
     * */
    @Override
    public void modifyTestElement(TestElement arg0) {
        // TODO Auto-generated rtype stub
        arg0.clear();
        configureTestElement(arg0);

        arg0.setProperty(RedisSampler.host, host.getText());
        arg0.setProperty(RedisSampler.port, port.getText());
        arg0.setProperty(RedisSampler.pwd, pwd.getText());
        arg0.setProperty(RedisSampler.db, db.getText());
        arg0.setProperty(RedisSampler.rtype, rtype.getText());
        arg0.setProperty(RedisSampler.rdotype, rdotype.getText());
        arg0.setProperty(RedisSampler.hash, hash.getText());
        arg0.setProperty(RedisSampler.key, key.getText());
        arg0.setProperty(RedisSampler.valueContent, valueContent.getText());
    }

    /*
     * reset新界面的时候调用，这里可以填入界面控件中需要显示的一些缺省的值
     * */
    @Override
    public void clearGui() {
        super.clearGui();

        host.setText("node.td-k8s.com");
        port.setText("1379");
        pwd.setText("mWRK6joVy5No");
        db.setText("5");
        rtype.setText("String");
        rdotype.setText("SET");
        hash.setText("");
        valueContent.setText("");
    }

    /*
     * 把Sampler中的数据加载到界面中
     * */
    @Override
    public void configure(TestElement element) {

        super.configure(element);
        // jmeter运行后，保存参数，不然执行后，输入框会情况

        host.setText(element.getPropertyAsString(RedisSampler.host));
        port.setText(element.getPropertyAsString(RedisSampler.port));
        pwd.setText(element.getPropertyAsString(RedisSampler.pwd));
        db.setText(element.getPropertyAsString(RedisSampler.db));
        rtype.setText(element.getPropertyAsString(RedisSampler.rtype));
        rdotype.setText(element.getPropertyAsString(RedisSampler.rdotype));
        hash.setText(element.getPropertyAsString(RedisSampler.hash));
        key.setText(element.getPropertyAsString(RedisSampler.key));
        valueContent.setText(element.getPropertyAsString(RedisSampler.valueContent));

    }

}