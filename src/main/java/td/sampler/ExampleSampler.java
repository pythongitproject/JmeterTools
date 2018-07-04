package td.sampler;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestIterationListener;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.ThreadListener;

public class ExampleSampler extends AbstractSampler
        implements TestStateListener,TestIterationListener,ThreadListener,Interruptible {


    @Override
    public boolean interrupt() {
        return false;
    }

    @Override
    public SampleResult sample(Entry entry) {
        return null;
    }

    @Override
    public void testIterationStart(LoopIterationEvent loopIterationEvent) {

    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String s) {

    }

    @Override
    public void threadStarted() {

    }

    @Override
    public void threadFinished() {

    }
}
