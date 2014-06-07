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
public class model extends booleanObject{
    public String logicState = "unknown";

    public model(){
    logicState = "unknown";
    name = "";
    }
    public model(String name){
    logicState = "unknown";
    this.name = name;
    this.type = "model";
    //this.models.add(this);
    }
    public model(String name,boolean logicValue ){
    logicState = "known";
    this.name = name;
    this.logicValue = logicValue;
    this.type = "model";
    }
    public boolean isSet(){
        return !logicState.equalsIgnoreCase("unknown");
    }
    @Override
     public boolean getBooleanValue(ArrayList<model> model){
      for(model m : model)
        {
            
            this.logicState = "known";
            if(m.name.equalsIgnoreCase(this.name))
                this.logicValue = m.logicValue;
        }
      return this.logicValue;
}
}