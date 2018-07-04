package td.gui;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import td.sampler.ExampleSampler;

import java.awt.*;

/**
 * 简单模式，工作模式，发布订阅模式，路由模式以及通配符模式
 */
public class ExampleGUI extends AbstractSamplerGui {

    public ExampleGUI(){
        init();
    }

    private void init(){
        setLayout(new BorderLayout(0,5));
        setBorder(getBorder());
        //设置布局
        add(makeTitlePanel(),BorderLayout.NORTH);


    }

    /**
     * 清除页面数据信息
     */
    @Override
    public void clearGui() {
        super.clearGui();
    }

    /**
     * 配置组装元素信息
     * @param element
     */
    @Override
    public void configure(TestElement element) {
        super.configure(element);

    }

    /**
     * label信息，也就是创建HTTP请求、JAVA请求
     * @return
     */
    @Override
    public String getLabelResource() {
        return "创建RabbitMQ连接";
    }

    /**
     * 创建新的元素
     * @return
     */
    @Override
    public TestElement createTestElement() {
        ExampleSampler exampleSampler = new ExampleSampler();
        modifyTestElement(exampleSampler);
        return exampleSampler;
    }

    /**
     * 清除旧的组建，从新创建
     * @param testElement
     */
    @Override
    public void modifyTestElement(TestElement testElement) {
        testElement.clear();
        this.configureTestElement(testElement);
    }
}
