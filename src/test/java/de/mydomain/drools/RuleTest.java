package de.mydomain.drools;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() {

        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        Results verifyResults = kieContainer.verify();
        for (Message message : verifyResults.getMessages()) {
            LOG.info("{}", message);
        }

        LOG.info("Creating kieBase");
        KieBase kieBase = kieContainer.getKieBase();

        LOG.info("There should be rules: ");
        for (KiePackage kiePackage : kieBase.getKiePackages()) {
            for (Rule rule : kiePackage.getRules()) {
                LOG.info("kiePackage " + kiePackage + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();

        LOG.info("Populating globals");
        Set<String> check = new HashSet<>();
        session.setGlobal("controlSet", check);

        LOG.info("Now running data");

        Measurement mRed = new Measurement("color", "red");
        session.insert(mRed);
        session.fireAllRules();

        Measurement mGreen = new Measurement("color", "green");
        session.insert(mGreen);
        session.fireAllRules();

        Measurement mBlue = new Measurement("color", "blue");
        session.insert(mBlue);
        session.fireAllRules();

        LOG.info("Final checks");

        assertEquals("Size of object in Working Memory is 3", 3, session.getObjects().size());
        assertTrue("contains red", check.contains("red"));
        assertTrue("contains green", check.contains("green"));
        assertTrue("contains blue", check.contains("blue"));
    }
}