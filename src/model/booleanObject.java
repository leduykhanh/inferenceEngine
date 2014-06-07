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
public class booleanObject {
    public boolean logicValue = false;
    public String name = "";
    public String type = "";
    ArrayList<model> models = new ArrayList<model>();

    protected boolean getBooleanValue(){
       return logicValue;
    }
    public boolean getBooleanValue(ArrayList<model> model){
       return logicValue;
    }
    public ArrayList<model> getModels(){
        //if(this.name.length() < 3)
            //models.add(new model(this.name));
        return models;
    }
}
