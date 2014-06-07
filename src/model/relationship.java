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
public class relationship  extends booleanObject{
    public model left;
    public model right;
    public String relaName = "";
    
    
    public relationship(model left, model right, String relaName ){
        this.models.add(left);
        this.models.add(right);
        this.left = left;
        this.right = right;
        this.relaName = new String(relaName);
        this.type = "rela";
    }
    public relationship(String relaExpression){
       
        String modnames[] = relaExpression.split("&");
        this.left = new model(modnames[0].trim());
        this.right = new model(modnames[1].trim());
        this.relaName = "&";
        this.type = "rela";
    }
    @Override
    public boolean getBooleanValue(){
    
        if(this.relaName.equals("")||!isBothSet()) return false;
        else {
            if(this.relaName.equals("=>")) return (!left.logicValue||right.logicValue);
            if(this.relaName.equals("&")) return (left.logicValue&&right.logicValue);
        }
        return false;
    }
    public boolean isBothSet(){
        return (left.isSet()&&right.isSet());
    }
    @Override
     public boolean getBooleanValue(ArrayList<model> model){
      for(model m : model)
        {
            this.left.logicState = "known";
            this.right.logicState = "known";
            if(m.name.equalsIgnoreCase(this.left.name))
                this.left.logicValue = m.logicValue;
            if(m.name.equalsIgnoreCase(this.right.name))
                this.right.logicValue = m.logicValue;
        }
       return getBooleanValue();
    }
    public boolean isIn(model mod, ArrayList<model> list){
           boolean bol = false;
        for(model m : list)
        {
            if(m.name.equalsIgnoreCase(mod.name))
                bol = true;
        }
        return bol;
   }
}
