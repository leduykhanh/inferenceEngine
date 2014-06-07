/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author Vic
 */
public class clause {
    public booleanObject left = null;
    public booleanObject right = null;
    public model leftModel = null;
    public model rightModel = null;
    public ArrayList<model> models = new ArrayList<model>(100);
    public String clauseName;
    public clause(String clauseName){
        this.clauseName = clauseName;
        String rels[] = clauseName.split("=>");
        if(rels[0].trim().length()>2)
        {
            this.left = new relationship(rels[0].trim());
            String modnames[] = rels[0].trim().split("&");
            this.models.add(new model(modnames[0].trim()));
            this.models.add(new model(modnames[1].trim()));
        }
        else{
            model temp_model = new model(rels[0].trim());
            this.left = temp_model;
           this.models.add(temp_model);
        }
        if(rels[1].trim().length()>2){
            
            this.right = new relationship(rels[1].trim());
            String modnames[] = rels[1].trim().split("&");
            this.models.add(new model(modnames[0].trim()));
            models.add(new model(modnames[1].trim()));
        }
        else{
            model temp_model = new model(rels[1].trim());
            this.right = temp_model;
            models.add(temp_model);
        }
    }
    public boolean getBooleanValue(){
        return (!left.getBooleanValue()||right.getBooleanValue());
    }
    
    public boolean getBooleanValue(ArrayList<model> model){
       return (!left.getBooleanValue(model)||right.getBooleanValue(model));
    }
    public boolean has(model mod){
        boolean bol = false;
        for(model m : this.models)
        {
            if(m.name.equalsIgnoreCase(mod.name))
                bol = true;
        }
        return bol;
    }
}
