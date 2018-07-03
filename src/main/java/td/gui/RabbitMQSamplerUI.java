package td.gui;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledChoice;
import td.sampler.RabbitMQSampler;

import javax.swing.*;
import java.awt.*;

/**
 * Created by linweili on 2018/7/3/0003.
 */
public class RabbitMQSamplerUI extends AbstractSamplerGui {

    private JTextField HOST;
    private JTextField PORT;
    private JTextField USERNAME;
    private JTextField PASSWORD;
    private JTextField QUEUE_NAME;

    // area区域1
    private JSyntaxTextArea postBodyContent = JSyntaxTextArea.getInstance(30, 50);
    // 滚动条
    private JTextScrollPane textPanel = JTextScrollPane.getInstance(postBodyContent);
    private JLabel textArea = new JLabel("DATA");



    private JPanel getHOSTPanel() {
        HOST = new JTextField(10);
        JLabel label = new JLabel("HOST"); // $NON-NLS-1$
        label.setLabelFor(HOST);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(HOST, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPORTPanel() {
        PORT = new JTextField(10);
        JLabel label = new JLabel("PORT"); // $NON-NLS-1$
        label.setLabelFor(PORT);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(PORT, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getUSERNAMEPanel() {
        USERNAME = new JTextField(10);
        JLabel label = new JLabel("PORT"); // $NON-NLS-1$
        label.setLabelFor(USERNAME);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(USERNAME, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getPASSWORDPanel() {
        PASSWORD = new JTextField(10);
        JLabel label = new JLabel("PORT"); // $NON-NLS-1$
        label.setLabelFor(PASSWORD);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(PASSWORD, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getQUEUE_NAMEPanel() {
        QUEUE_NAME = new JTextField(10);
        JLabel label = new JLabel("PORT"); // $NON-NLS-1$
        label.setLabelFor(QUEUE_NAME);
        JPanel panel = new HorizontalPanel();
        panel.add(label, BorderLayout.WEST);
        panel.add(QUEUE_NAME, BorderLayout.CENTER);
        return panel;
    }





    @Override
    public String getLabelResource() {
        return null;
    }


    /**
     * 创建一个新的Sampler,然后将界面中的数据设置到这个新的Sampler实例中
     * @return
     */
    @Override
    public TestElement createTestElement() {
        RabbitMQSampler rabbitMQSampler = new RabbitMQSampler();
        modifyTestElement(rabbitMQSampler);
        return rabbitMQSampler;
    }

    @Override
    public void modifyTestElement(TestElement testElement) {

    }
}
