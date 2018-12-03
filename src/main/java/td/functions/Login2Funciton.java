package td.functions;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Login2Funciton extends AbstractFunction {

    private static final String SECRET = "~#^&tuandai*%#junte#111!";
    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__tuandaim"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable varName;

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
            String timeValue = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
            String a1 = Cryptography.tripleDESEncrypt(timeValue, SECRET);
            try {
                String fn = URLEncoder.encode(a1, "UTF-8");
                return fn;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
    }

    @Override
    public void setParameters(Collection<CompoundVariable> collection) throws InvalidVariableException {
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}
