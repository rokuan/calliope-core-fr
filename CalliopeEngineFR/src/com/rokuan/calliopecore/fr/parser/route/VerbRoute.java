package com.rokuan.calliopecore.fr.parser.route;

import com.google.gson.Gson;
import com.rokuan.calliopecore.fr.sentence.Action;
import com.rokuan.calliopecore.fr.sentence.VerbConjugation;
import com.rokuan.calliopecore.sentence.ActionObject;
import com.rokuan.calliopecore.sentence.IAction;
import com.rokuan.calliopecore.sentence.IAction.ActionType;
import com.rokuan.calliopecore.sentence.structure.content.IVerbalObject;

public class VerbRoute extends Route {
	private ActionType action = ActionType.UNDEFINED;
	private String what = null;
	private boolean replaceWhat = false;
	private boolean reflexive = false;
	
	@Override
	public boolean run(IVerbalObject obj) {
		if(obj.getAction() == null){
			return false;
		}
		
		/* We check the values */
		
		if(what == null && obj.getDirectObject() != null){
			return false;
		}
		
		if(what != null){
			if(obj.getDirectObject() == null){
				return false;
			}
			
			if(RouteUtils.getValueFromNominalGroup(obj.getDirectObject()).matches(what)){
				return false;
			}
		}
		
		if(reflexive){
			// TODO:
			
			if(obj.getDirectObject() == null){
				
			}
		}
		
		/* We update the attributes */
		
		if(replaceWhat){
			obj.setDirectObject(null);
		}
		
		if(reflexive){
			// TODO:
		}
		
		ActionObject oldAction = obj.getAction();
		final IAction oldMainAction = oldAction.getMainAction();
		
		if(oldMainAction instanceof VerbConjugation){
			final Action newVerbAction = ((VerbConjugation)oldMainAction).getVerb().getAction(action); 
			ActionObject newAction = new ActionObject(oldAction.getTense(), 
					new IAction() {					
						@Override
						public boolean isFieldBound() {
							return newVerbAction.isAFieldAction();
						}
						
						@Override
						public String getValue() {
							return oldMainAction.getValue();
						}
						
						@Override
						public Tense getTense() {
							return oldMainAction.getTense();
						}
						
						@Override
						public Form getForm() {
							return oldMainAction.getForm();
						}
						
						@Override
						public String getBoundField() {
							return newVerbAction.getBoundField();
						}
						
						@Override
						public ActionType getAction() {
							return newVerbAction.getAction();
						}
					}, oldAction.getPrefixActions());
			obj.setAction(newAction);
		}
		
		return true;
	}
	
	public static VerbRoute routeFromString(String s){
		return new Gson().fromJson(s, VerbRoute.class);
	}
}
