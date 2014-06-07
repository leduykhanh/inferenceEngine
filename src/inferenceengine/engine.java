/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inferenceengine;

import java.util.ArrayList;
import model.booleanObject;
import model.clause;
import model.model;
import model.relationship;

/**
 *
 * @author LEDU0_000
 */
public class engine {
       public engine(){}
       public  void TT(model ask, ArrayList<model> models,ArrayList<clause> clauses,
               ArrayList<model> known_models){
       ArrayList<model> related_models = new ArrayList<model>(100);
       ArrayList<clause> related_clauses = new ArrayList<clause>(100);
       related_models.add(ask);
       addingTT(related_models,clauses,related_clauses,known_models);
       /*for(model md : related_models)
            System.out.println(md.name);
       for(clause md : related_clauses)
            System.out.println(md.clauseName);*/
       int size = related_models.size();
       //model arraym[] = new model[size];
      // for(int i=0;i<size;i++){
        //   arraym[i] = related_models.get(i);
       //}
       ArrayList arrayTT[] = new ArrayList[(int)Math.pow(2, size)];
       //model arrayTT[][] = new model[(int)Math.pow(2, related_models.size())][related_models.size()];
       for(int i = 0;i<arrayTT.length;i++){
               arrayTT[i] = new ArrayList<model>();
               for(int j=size - 1;j>=0;j--){
                   model md = new model(related_models.get(j).name);
                   md.logicValue = ((int)(i/(int) Math.pow(2, j))%2 == 1) ?true:false;
                 //  System.out.print(md.logicValue + " ");
                   arrayTT[i].add(md);
               }
               //System.out.println();
               }
       for(int i = 0;i<arrayTT.length;i++){
           boolean allTrue = true;
       /*    for(int j=0;j<size;j++){
               System.out.print(((model)arrayTT[i].get(j)).logicValue);
           
           }*/
           System.out.print("|");
           for (clause cl : related_clauses){
             //  System.out.print(cl.getBooleanValue((ArrayList<model>)arrayTT[i]));
               if(!cl.getBooleanValue((ArrayList<model>)arrayTT[i]))
                   allTrue = false;
           }
           if(allTrue) 
           {
               System.out.println("YES : " + (related_models.size()- 2));
               return;
           } 
           else{
               System.out.println("NO");
               return;
           }
           //System.out.println("|");
       }
         /* for (int i=0; i<(int)Math.pow(2, size); i++) {
            for (int j=size-1; j>=0; j--) {
                System.out.print((i/(int) Math.pow(2, j))%2 + " ");
            }
            System.out.println();
        }*/
   } 
   public void FC(model ask, ArrayList<model> models,ArrayList<clause> clauses,
               ArrayList<model> known_models){
       ArrayList<model> related_models = new ArrayList<model>(100);
       ArrayList<clause> related_clauses = new ArrayList<clause>(100);
       related_models.add(ask);
       //addingTT(related_models,clauses,related_clauses,known_models);
       //for(model md : related_models)
            //System.out.println(md.name);
       //for(clause md : related_clauses)
         //   System.out.println(md.clauseName);

       //int size = related_models.size();
       String yes = "NO";
       ArrayList<model> entailed = new ArrayList<model>();

       while(!known_models.isEmpty()){

	 	model p = known_models.remove(0);
		// add to entailed
		entailed.add(p);

		for (int i=0;i<clauses.size();i++){

			if (clauses.get(i).has(p)){

					booleanObject head = clauses.get(i).right;
					// have we just proven the 'ask'?
					if (head.name.equalsIgnoreCase(ask.name))
                                        {
                                                yes = "YES";
						break ;
                                        }
					//for(model m:head.getModels())
                                        if(clauses.get(i).left.type == "model" && !head.name.equalsIgnoreCase(p.name))
                                        {
                                            known_models.add((model)head);					

                                        }
                                        else if(clauses.get(i).left.type == "rela" && !head.name.equalsIgnoreCase(p.name))
                                        {   
                                            relationship rela = (relationship)clauses.get(i).left;
                                            if(isIn(rela.left,entailed) && isIn(rela.right,entailed))
                                                known_models.add((model)head);					
                                        }
				//}
			}	
		}
	}
       if(yes.equalsIgnoreCase("YES")){
           System.out.print(yes + ": ");
           for(model md : entailed)
                System.out.print(md.name + ", " );
           System.out.print(ask.name);
       }
       else 
            System.out.print(yes + "! ");
   
   } 
   public void BC(model ask, ArrayList<model> models,ArrayList<clause> clauses,
               ArrayList<model> known_models){
       ArrayList<model> related_models = new ArrayList<model>();
       ArrayList<clause> related_clauses = new ArrayList<clause>();
       related_models.add(ask);

       String yes = "YES";
       
       ArrayList<model> entailed = new ArrayList<model>();
       	while(!related_models.isEmpty()){
		// get current symbol
		model q = related_models.remove(related_models.size()-1);
		// add the entailed array
		entailed.add(q);
		

		if (!isIn(q,known_models)){

			ArrayList<model> p = new ArrayList<model>();
			for(int i=0;i<clauses.size();i++){
			// for each clause..
					if (clauses.get(i).right.name.equalsIgnoreCase(q.name)){
					// that contains the symbol as its conclusion
					
							ArrayList<model> temp = clauses.get(i).left.getModels();
                                             if(clauses.get(i).left.type == "model") 
                                                 p.add((model)clauses.get(i).left);

						}						
			}

			if (p.size()==0){
                                yes = "NO";
				break ;
			}
			else{

					for(int i=0;i<p.size();i++){
							if (!entailed.contains(p.get(i)))
									related_models.add(p.get(i));
							}
	

			}
		}
		
	}
       
        if(yes.equalsIgnoreCase("YES")){
           System.out.print(yes + ": ");
           for(int i = entailed.size() - 1;i>=0;i--)
                System.out.print(entailed.get(i).name + ", " );
           
       }
       else 
            System.out.print(yes + "! ");
   
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
   public void addingTT(ArrayList<model> related_models,ArrayList<clause> clauses,ArrayList<clause> related_clauses,
           ArrayList<model> known_models){
       int a = 0;
       ArrayList<model> tmpList = new ArrayList<model> (100);
       ArrayList<clause> tmpListC = new ArrayList<clause> (100);
       for(model tmpmd:related_models)
           tmpList.add(tmpmd);
       for(clause tmpmd:clauses)
           tmpListC.add(tmpmd);
       for(clause cl:tmpListC){
           boolean hasAll = true;
         for(model tmpmd:tmpList)
         {
           
           if(cl.has(tmpmd)){
           //System.out.println(cl.clauseName);
               a =1;
               related_clauses.add(cl);
               clauses.remove(cl);
               for(model md:cl.models){
                   if(!isIn(md,related_models))
                   {
                       related_models.add(md);
                   }
                   if (isIn(md,known_models)) return;
               }

           }
           //else hasAll = false;
       }
         //if (hasAll) return;
            }
       
            addingTT(related_models,clauses,related_clauses,known_models);
       
   }
}
