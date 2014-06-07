/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inferenceengine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.clause;
import model.model;
import model.relationship;

/**
 *
 * @author Vic
 */
public class InferenceEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList <relationship> KB = new ArrayList <relationship>(100); 
        ArrayList <model> models = new ArrayList <model>(100); 
        ArrayList <model> known_models = new ArrayList <model>(100);
        ArrayList <clause> clauses = new ArrayList<clause>(100);
        model ask = new model();
        BufferedReader input = null;
        engine eg = new engine();
        try{
        input = new BufferedReader(new FileReader("test/test.txt"));
        String line;
        while ((line = input.readLine()) != null) {
            if(line.trim().equalsIgnoreCase("TELL")){
                line = input.readLine();
                while(!line.trim().equalsIgnoreCase("ASK")){
                    String clause_names[] = line.split(";");
                    for (String clause_name : clause_names){
                        
                        String modelNames[] = clause_name.split("=>");
                        if(modelNames.length == 1)
                        {   
                            model temp_models = new model(modelNames[0].trim(),true);
                            
                            for(model m : models){
                                if(m.name.equalsIgnoreCase(temp_models.name))
                                    continue;
                            }
                            models.add(temp_models);
                            known_models.add(temp_models);
                        }
                        else {
                            //System.out.println(clause_name);
                            clause temp_clause = new clause(clause_name);
                            clauses.add(temp_clause);
                        }
                        //for(String modelName : modelNames){
                           // System.out.println(modelName.trim());
                      //  }
                    }
                    line = input.readLine();
                } 
            }
            if(line.trim().equalsIgnoreCase("ASK"))
            {
                line = input.readLine();
                //System.out.println(line);
                ask = new model(line);
            }
            }
        }
          catch (IOException e) { System.out.println("IOError: "+e); }
         

        for(clause cl : clauses){
            for(model clmod : cl.models){
                boolean in = false;
                for(model mod : models )
                    if(clmod.name.equalsIgnoreCase(mod.name))
                        in = true;
                if(!in)
                    models.add(clmod);
            }
               // System.out.println(cl.toString());
        }
       // for(model mod : known_models){
         //   System.out.println(mod.name + "=>" + mod.logicValue);
        //} 
        //for(clause cl : clauses)
          //  System.out.println(cl.clauseName);
        if(args[0].equalsIgnoreCase("TT"))
                eg.TT(ask,models,clauses,known_models);
        else if(args[0].equalsIgnoreCase("FC"))
                eg.FC(ask,models,clauses,known_models);
        else if(args[0].equalsIgnoreCase("BC"))
                eg.BC(ask,models,clauses,known_models);
        //FC fc = new FC("d","p2=>p3; p3=>p1;c=>e;b&e=>f;f&g=>h;p1=>d;p1&p3=>c;a;b;p2;");
        //System.out.println(fc.execute());
        
    }

}
