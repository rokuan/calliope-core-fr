package com.rokuan.calliopecore.fr.parser.route;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.sentence.IAction;
import com.rokuan.calliopecore.sentence.structure.InterpretationObject;

public class RouteTree {
	public static final String ROUTE_FILE_EXTENSION = "json";
	
	private String rootDirectory = "/";
	
	public void setRootDirectory(String path){
		rootDirectory = path;
	}
	
	public void run(InterpretationObject obj){
		if(obj.getAction() == null){
			return;
		}
		
		IAction mainAction = obj.getAction().getMainAction();
		
		if(mainAction instanceof VerbConjugation){
			File verbRouteFile = new File(rootDirectory + "/" + ((VerbConjugation)mainAction).getVerb().getValue() + "." + ROUTE_FILE_EXTENSION);
			
			if(verbRouteFile.exists()){
				try(Scanner sc = new Scanner(verbRouteFile)){
					while(sc.hasNextLine()){
						VerbRoute route = VerbRoute.routeFromString(sc.nextLine());
						if(route.run(obj)){
							break;
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
