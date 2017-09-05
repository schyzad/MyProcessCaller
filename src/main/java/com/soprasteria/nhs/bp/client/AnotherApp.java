package com.soprasteria.nhs.bp.client;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.services.client.api.RemoteRestRuntimeEngineFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.soprasteria.nhs.bp.client.model.*;




/**
 * Created by shehzadnagi on 20/08/2017.
 */
public class AnotherApp {


    public static void main(String[] args){
        System.out.println("**** Starting Business Process App Client ******");


//        executeLocal();

        executeSample();
        System.out.println("**** End Business Process App Client      ******");

    }


    public static void executeLocal(){

        String userId = "kieserver";
        String password = "kieserver1!";
        String applicationContext = "http://localhost:8080/business-central";
        String deploymentId ="com.soprasteria.nhs.eortho:eOrthoBusinessProcess:0.1";

        Map<String, Object> processVariables = new HashMap<String, Object>();
        processVariables.put("status", "Not Set");

        populateSamples( userId, password, applicationContext, deploymentId , processVariables, "eOrthoBusinessProcess.FirstBusinessProcess");


    }


    public static void executeSample(){

        String userId = "kieserver";
        String password = "kieserver1!";
        String applicationContext = "http://localhost:8080/business-central";
        String deploymentId ="com.sample:SampleBusinessProcess:1.0.0";

        Map<String, Object> processVariables = new HashMap<String, Object>();
        processVariables.put("status", "Not Set");

        populateSamples( userId, password, applicationContext, deploymentId , processVariables, "com.sample.bpmn.hello");


    }



    public static void populateSamples(String userId, String password, String applicationContext, String deploymentId, Map<String, Object> processVariables, String processName)
    {
        RuntimeEngine runtimeEngine = getRuntimeEngine( applicationContext, deploymentId, userId, password );



        KieSession kieSession = runtimeEngine.getKieSession();
        //kieSession.setGlobal("Status", "not set");

        //qualify with very low interest rate, great credit, non-jumbo loan
        //processVariables = getProcessArgs( "Amy", "12301 Wilshire", 333224449, 100000, 500000, 100000, 30 );
        ProcessInstance inst = kieSession.startProcess( processName, processVariables);



        System.out.println();
        System.out.println("Process instances loaded succesfully, see log for details.");
        System.out.println();
    }

    private static RuntimeEngine getRuntimeEngine(String applicationContext, String deploymentId, String userId, String password)
    {
        try
        {
            URL jbpmURL = new URL( applicationContext );

            RuntimeEngine runtimeEngine = RemoteRestRuntimeEngineFactory.newBuilder()
                    .addDeploymentId(deploymentId)
                    .addUrl(jbpmURL)
                    .addUserName(userId)
                    .addPassword(password)
                    .build();
//            RuntimeEngine runtimeEngine = remoteRestSessionFactory.newRuntimeEngine();
            return runtimeEngine;
        }
        catch( MalformedURLException e )
        {
            throw new IllegalStateException( "This URL is always expected to be valid!", e );
        }
    }





}
