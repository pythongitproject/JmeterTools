package td.functions;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class AES128Function extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__AES128"; //$NON-NLS-1$

    static {
        desc.add(JMeterUtils.getResString("param"));
        desc.add(JMeterUtils.getResString("ekey"));
        desc.add(JMeterUtils.getResString("function_name_paropt")); //$NON-NLS-1$
    }

    private CompoundVariable param;
    private CompoundVariable ekey;
    private CompoundVariable varName;


    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult previousResult, Sampler currentSampler){
        String content = param.execute().trim();
        String encryptKey = ekey.execute().trim();

        if(StringUtils.isBlank(content)){
            return null;
        }
        if(StringUtils.isBlank(encryptKey)){
            return null;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(content.getBytes());
            String end = Base64.encodeBase64String(result);
            if (varName != null) {
                JMeterVariables vars = getVariables();
                final String varTrim = varName.execute().trim();
                if (vars != null && varTrim.length() > 0) {
                    vars.put(varTrim, end);
                }
            }
            return end;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "";
        }

}

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 2, 3);
        Object[] values = parameters.toArray();
        param = (CompoundVariable)values[0];
        ekey = (CompoundVariable)values[1];
        if (values.length>2){
            varName = (CompoundVariable) values[2];
        } else {
            varName = null;
        }
    }
}
