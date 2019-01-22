package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

public class ModifyAggregationKindEndCommand extends RecordingCommand {

    private Association association;

    public ModifyAggregationKindEndCommand(TransactionalEditingDomain domain) {
        super(domain);
    }

    public ModifyAggregationKindEndCommand(TransactionalEditingDomain editingDomain,
            Association association) {
        this(editingDomain);
        this.association = association;
    }

    @Override
    protected void doExecute() {
        // semantic part
        Class sourceType = null, targetType = null;
        Property sourceEnd = null, targetEnd = null;
  
        List<Property> memberEnds = association.getMemberEnds();
        sourceEnd = memberEnds.get(0);
        sourceType = (Class) sourceEnd.getType();
        targetEnd = memberEnds.get(1);
        targetType = (Class) targetEnd.getType();
        
        
        AggregationKind oldSourceAggKind = null;
        AggregationKind oldTargetAggKind = null;       
        Property oldSourceProp = null;
        Property oldTargetProp = null;
           
        EList<Property> lstProps = sourceType.getOwnedAttributes();
        
        for (Property prop: lstProps){
            Association assoc = prop.getAssociation();
            if ((assoc != null) && (assoc == association)){                    
                //prop.setAggregation(AggregationKind.NONE_LITERAL);
                oldSourceAggKind = prop.getAggregation();
                oldSourceProp = prop;
                break;
            }
        }
        
        lstProps = targetType.getOwnedAttributes();         
        
        for (Property prop: lstProps){
            Association assoc = prop.getAssociation();
            if ((assoc != null) && (assoc == association)){                    
                //prop.setAggregation(AggregationKind.COMPOSITE_LITERAL);
                oldTargetAggKind = prop.getAggregation();
                oldTargetProp = prop;
                break;
            }
        }
        
        // Now swap them over
        oldSourceProp.setAggregation(oldTargetAggKind);
        oldTargetProp.setAggregation(oldSourceAggKind);


//        List<Property> memberEnds = association.getMemberEnds();
//        sourceEnd = memberEnds.get(0);
        //sourceType = (Class) sourceEnd.getType();
//        targetEnd = memberEnds.get(1);
        //targetType = (Class) targetEnd.getType();

        // Now swap over the member end of the association
        memberEnds.remove(sourceEnd);
        memberEnds.remove(targetEnd);

        memberEnds.add(targetEnd);
        memberEnds.add(sourceEnd);

    }

}
